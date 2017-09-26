package ai.exemplar.callbacks.oauth.providers.spotify;

import ai.exemplar.callbacks.oauth.providers.OAuthProvider;
import ai.exemplar.callbacks.oauth.providers.values.OAuthClientCredentials;
import ai.exemplar.common.LocationsService;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.dynamodb.schema.square.LocationSchema;
import ai.exemplar.persistence.model.OAuthToken;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.util.IOUtils;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

public class SpotifyOAuthProvider implements OAuthProvider {

    static final Logger log = Logger.getLogger(SpotifyOAuthProvider.class);

    public static final String PROVIDER_NAME = "spotify";

    private final OAuthClientCredentials credentials;

    private final OAuthTokenRepository repository;

    private final LocationsService locationsService;

    private final Gson gson = GsonFabric.gson();

    @Inject
    public SpotifyOAuthProvider(@SpotifyClientCredentials OAuthClientCredentials credentials, OAuthTokenRepository repository, LocationsService locationsService) {
        this.credentials = credentials;
        this.repository = repository;
        this.locationsService = locationsService;
    }

    @Override
    public String processOAuthCallback(Map<String, String> queryParameters) {
        try {
            if (queryParameters.containsKey("code")) {
                String[] state = queryParameters.get("state").split("#", 2);

                if (state.length != 2) {
                    log.warn("incorrect format of the state=" + queryParameters.get("state"));

                    return Boolean.FALSE.toString();
                }

                String location = state[0];
                String account = state[1];

                log.debug(String.format("received nonce for user=%s, location=%s", account, location));

                LocationSchema locationSchema = locationsService.get(account, location);

                if (locationSchema == null) {
                    log.warn("location not found for state=" + queryParameters.get("state"));

                    return Boolean.FALSE.toString();
                }

                String nonse = queryParameters.get("code");

                HttpClient client = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost("https://accounts.spotify.com/api/token");

                ArrayList<NameValuePair> postParameters = new ArrayList<>();

                postParameters.add(new BasicNameValuePair(
                        "client_id", credentials.getClientId()));
                postParameters.add(new BasicNameValuePair(
                        "client_secret", credentials.getClientSecret()));
                postParameters.add(new BasicNameValuePair(
                        "grant_type", "authorization_code"));
                postParameters.add(new BasicNameValuePair(
                        "redirect_uri",
                        "https://arceaq2a1d.execute-api.us-west-2.amazonaws.com/prod/callbacks/oauth/spotify"));
                postParameters.add(new BasicNameValuePair(
                        "code", nonse));

                request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

                HttpResponse response = client.execute(request);

                if (response.getStatusLine().getStatusCode() != 200) {
                    log.debug("token exchange failed for user=" + queryParameters.get("state"));

                    throw new RuntimeException("token exchange failed: " +
                            response.getStatusLine().getStatusCode() + " " +
                            response.getStatusLine().getReasonPhrase());
                }

                String responseEntity = IOUtils.toString(response.getEntity().getContent());
                ExchangeResponseBody exchangeResponseBody = gson.fromJson(
                        responseEntity,
                        ExchangeResponseBody.class
                );

                repository.save(new OAuthToken(
                        location,
                        PROVIDER_NAME,
                        exchangeResponseBody.getAccess_token(),
                        exchangeResponseBody.getRefresh_token(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                                .plus(exchangeResponseBody
                                        .getExpires_in(), ChronoUnit.SECONDS),
                        null,
                        null
                ));

                locationSchema.setPlayHistoryProviders(
                        ImmutableMap.<String, String>builder()
                                .putAll(locationSchema
                                        .getPlayHistoryProviders())
                                .put(PROVIDER_NAME, location)
                                .build()
                );

                locationsService.save(locationSchema);

                return location;

            } else {
                log.warn("auth discarded for state=" + queryParameters.get("state"));

                return Boolean.FALSE.toString();

            }

        } catch (Throwable e) {
            log.error("auth callback failed:", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public void renewToken(OAuthToken token) {
        try {
            log.debug("renew started for id=" + token.getId());

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("https://accounts.spotify.com/api/token");

            ArrayList<NameValuePair> postParameters = new ArrayList<>();

            postParameters.add(new BasicNameValuePair(
                    "client_id", credentials.getClientId()));
            postParameters.add(new BasicNameValuePair(
                    "client_secret", credentials.getClientSecret()));
            postParameters.add(new BasicNameValuePair(
                    "grant_type", "refresh_token"));
            postParameters.add(new BasicNameValuePair(
                    "refresh_token", token.getRefreshToken()));

            request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                log.debug("renew failed for id=" + token.getId());

                throw new RuntimeException("token exchange failed: " +
                        response.getStatusLine().getStatusCode() + " " +
                        response.getStatusLine().getReasonPhrase());
            }

            String responseEntity = IOUtils.toString(response.getEntity().getContent());
            ExchangeResponseBody exchangeResponseBody = gson.fromJson(
                    responseEntity,
                    ExchangeResponseBody.class
            );

            repository.save(new OAuthToken(
                    token.getId(),
                    PROVIDER_NAME,
                    exchangeResponseBody.getAccess_token(),
                    token.getRefreshToken(),
                    token.getCreated(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
                            .plus(exchangeResponseBody
                                    .getExpires_in(), ChronoUnit.SECONDS),
                    token.getLastFetched(),
                    null
            ));

        } catch (Throwable e) {
            log.error("renew failed:", e);

            //todo: handle failed renew
        }
    }

    private static class ExchangeResponseBody {

        private String access_token;

        private String token_type;

        private String scope;

        private Integer expires_in;

        private String refresh_token;

        public ExchangeResponseBody() {
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public Integer getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(Integer expires_in) {
            this.expires_in = expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }
}

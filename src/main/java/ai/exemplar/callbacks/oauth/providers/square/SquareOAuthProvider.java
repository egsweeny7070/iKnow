package ai.exemplar.callbacks.oauth.providers.square;

import ai.exemplar.callbacks.oauth.providers.OAuthProvider;
import ai.exemplar.callbacks.oauth.providers.values.OAuthClientCredentials;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.model.OAuthToken;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

public class SquareOAuthProvider implements OAuthProvider {

    static final Logger log = Logger.getLogger(SquareOAuthProvider.class);

    public static final String PROVIDER_NAME = "square";

    private final OAuthClientCredentials credentials;

    private final OAuthTokenRepository repository;

    private final Gson gson = GsonFabric.gson();

    @Inject
    public SquareOAuthProvider(@SquareClientCredentials OAuthClientCredentials credentials, OAuthTokenRepository repository) {
        this.credentials = credentials;
        this.repository = repository;
    }

    @Override
    public String processOAuthCallback(Map<String, String> queryParameters) {
        try {
            if (queryParameters.containsKey("code")) {
                log.debug("received nonce for user=" + queryParameters.get("state"));

                String nonse = queryParameters.get("code");

                HttpClient client = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost("https://connect.squareup.com/oauth2/token");

                StringEntity requestEntity = new StringEntity(gson.toJson(
                        new NonseExchangeRequest(
                                credentials.getClientId(),
                                credentials.getClientSecret(),
                                nonse
                        )
                ));
                requestEntity.setContentType("application/json");

                request.setEntity(requestEntity);

                HttpResponse response = client.execute(request);

                if (response.getStatusLine().getStatusCode() != 200) {
                    log.debug("token exchange failed for user=" + queryParameters.get("state"));

                    throw new RuntimeException("token exchange failed");
                }

                String responseEntity = IOUtils.toString(response.getEntity().getContent());
                ExchangeResponseBody exchangeResponseBody = gson.fromJson(
                        responseEntity,
                        ExchangeResponseBody.class
                );

                repository.save(new OAuthToken(
                        queryParameters.get("state"),
                        PROVIDER_NAME,
                        exchangeResponseBody.getAccess_token(),
                        null,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        ZonedDateTime.parse(exchangeResponseBody.getExpires_at()).toLocalDateTime(),
                        null,
                        exchangeResponseBody.getMerchant_id()
                ));

                return Boolean.TRUE.toString();

            } else {
                log.warn("auth discarded for user=" + queryParameters.get("state"));

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

            HttpPost request = new HttpPost("https://connect.squareup.com/oauth2/clients/" + credentials.getClientId() + "/access-token/renew");

            request.setHeader("Authorization", "Client " + credentials.getClientSecret());

            StringEntity requestEntity = new StringEntity(gson.toJson(
                    new RenewTokenRequest(
                            token.getToken()
                    )
            ));
            requestEntity.setContentType("application/json");

            request.setEntity(requestEntity);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                log.debug("renew failed for id=" + token.getId());

                throw new RuntimeException("token exchange failed");
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
                    null,
                    token.getCreated(),
                    LocalDateTime.now(),
                    ZonedDateTime.parse(exchangeResponseBody.getExpires_at()).toLocalDateTime(),
                    token.getLastFetched(),
                    exchangeResponseBody.getMerchant_id()
            ));

        } catch (Throwable e) {
            log.error("renew failed:", e);

            //todo: handle failed renew
        }
    }

    private static class NonseExchangeRequest {

        private final String client_id;

        private final String client_secret;

        private final String code;

        public NonseExchangeRequest(String client_id, String client_secret, String code) {
            this.client_id = client_id;
            this.client_secret = client_secret;
            this.code = code;
        }

        public String getClient_id() {
            return client_id;
        }

        public String getClient_secret() {
            return client_secret;
        }

        public String getCode() {
            return code;
        }
    }

    private static class ExchangeResponseBody {

        private String access_token;

        private String token_type;

        private String expires_at;

        private String merchant_id;

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

        public String getExpires_at() {
            return expires_at;
        }

        public void setExpires_at(String expires_at) {
            this.expires_at = expires_at;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }
    }

    private static class RenewTokenRequest {

        private String access_token;

        public RenewTokenRequest(String access_token) {
            this.access_token = access_token;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }
}

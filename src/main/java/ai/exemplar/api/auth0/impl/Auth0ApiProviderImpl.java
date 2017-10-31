package ai.exemplar.api.auth0.impl;

import ai.exemplar.api.auth0.Auth0ApiProvider;
import ai.exemplar.api.spotify.impl.SpotifyApiProviderImpl;
import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import org.apache.log4j.Logger;

public class Auth0ApiProviderImpl implements Auth0ApiProvider {

    static final Logger log = Logger.getLogger(SpotifyApiProviderImpl.class);

    private static final String CLIENT_ID = "pCyVliEG7uH0YiqTrPZmWGF80LTXBIpS";

    private static final String CLIENT_SECRET = "p_9VR85bxqOjn_KP1BR21tlBZEyHMVA72dXWiFoEL8Kmrt0hHnSyIjHH5aRSQfkO";

    private final AuthAPI api;

    public Auth0ApiProviderImpl() {
        this.api = new AuthAPI(
                String.format("%s.auth0.com", TENANT_NAME),
                CLIENT_ID,
                CLIENT_SECRET
        );
    }

    @Override
    public String email(String token) {
        try {
            return (String) api
                    .userInfo(token)
                    .execute()
                    .getValues()
                    .get("email");

        } catch (Auth0Exception e) {
            throw new RuntimeException(e);
        }
    }
}

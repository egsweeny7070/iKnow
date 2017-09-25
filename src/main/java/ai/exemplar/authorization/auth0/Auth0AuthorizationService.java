package ai.exemplar.authorization.auth0;

import ai.exemplar.authorization.AuthorizationService;
import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.apache.log4j.Logger;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Auth0AuthorizationService implements AuthorizationService {

    static final Logger log = Logger.getLogger(Auth0AuthorizationService.class);

    private static final String TENANT_NAME = "exemplar";

    private static final String AUDIENCE_NAME = "https://api.exemplar.ai/";

    private static final String CLIENT_ID = "pCyVliEG7uH0YiqTrPZmWGF80LTXBIpS";

    private static final String CLIENT_SECRET = "p_9VR85bxqOjn_KP1BR21tlBZEyHMVA72dXWiFoEL8Kmrt0hHnSyIjHH5aRSQfkO";

    private final JWTVerifier verifier;

    private final AuthAPI api;

    public Auth0AuthorizationService() {
        verifier = JWT.require(Algorithm
                .RSA256(new RSAKeyProvider() {
                    private final JwkProvider provider = new UrlJwkProvider(String
                            .format("https://%s.auth0.com", TENANT_NAME));

                    @Override
                    public RSAPublicKey getPublicKeyById(String kid) {
                        try {
                            return (RSAPublicKey) provider
                                    .get(kid).getPublicKey();
                        } catch (JwkException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public RSAPrivateKey getPrivateKey() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public String getPrivateKeyId() {
                        throw new UnsupportedOperationException();
                    }
                }))
                .withIssuer(String
                        .format("https://%s.auth0.com/", TENANT_NAME))
                .withAudience(AUDIENCE_NAME)
                .build();

        api = new AuthAPI(
                String.format("%s.auth0.com", TENANT_NAME),
                CLIENT_ID,
                CLIENT_SECRET
        );
    }

    @Override
    public String authorization(String headerValue) {
        if (headerValue == null) {
            return null;
        }

        String[] token = headerValue
                .split(" ");

        if (token.length != 2) {
            return null;
        }

        String accessToken = token[token.length - 1];

        try {
            DecodedJWT jwt = verifier
                    .verify(accessToken);

            log.info("authorized subject " + jwt.getSubject());

        } catch (JWTVerificationException e) {
            log.error(e);
            return null;
        }

        try {
            return (String) api
                    .userInfo(accessToken)
                    .execute()
                    .getValues().get("email");

        } catch (Auth0Exception e) {
            log.error(e);
            return null;
        }
    }
}

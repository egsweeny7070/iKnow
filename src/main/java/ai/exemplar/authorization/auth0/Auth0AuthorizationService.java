package ai.exemplar.authorization.auth0;

import ai.exemplar.api.auth0.Auth0ApiProvider;
import ai.exemplar.authorization.AuthorizationService;
import ai.exemplar.persistence.AccountsRepository;
import ai.exemplar.persistence.dynamodb.schema.AccountSchema;
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

import javax.inject.Inject;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Auth0AuthorizationService implements AuthorizationService {

    static final Logger log = Logger.getLogger(Auth0AuthorizationService.class);

    private final JWTVerifier verifier;

    private final AccountsRepository accountsRepository;

    private final Auth0ApiProvider auth0ApiProvider;

    @Inject
    public Auth0AuthorizationService(AccountsRepository accountsRepository, Auth0ApiProvider auth0ApiProvider) {
        this.accountsRepository = accountsRepository;
        this.auth0ApiProvider = auth0ApiProvider;

        verifier = JWT.require(Algorithm
                .RSA256(new RSAKeyProvider() {
                    private final JwkProvider provider = new UrlJwkProvider(String
                            .format("https://%s.auth0.com", Auth0ApiProvider.TENANT_NAME));

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
                        .format("https://%s.auth0.com/", Auth0ApiProvider.TENANT_NAME))
                .withAudience(Auth0ApiProvider.AUDIENCE_NAME)
                .build();
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

        DecodedJWT jwt;
        try {
            jwt = verifier.verify(accessToken);

        } catch (JWTVerificationException e) {
            log.error(e);
            return null;
        }

        String accountId = jwt.getSubject();

        log.info("authorized subject " + accountId);

        AccountSchema account = accountsRepository.get(accountId);

        if (account == null) {
            log.info("creating account for subject " + accountId);

            String email;
            try {
                email = auth0ApiProvider
                        .email(accessToken);

            } catch (Throwable e) {
                log.warn("auth0 api exception", e);
                return null;
            }

            account = new AccountSchema(accountId, email);

            accountsRepository
                    .save(account);
        }

        return account.getName();
    }
}

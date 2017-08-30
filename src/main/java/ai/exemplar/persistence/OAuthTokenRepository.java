package ai.exemplar.persistence;

import ai.exemplar.persistence.model.OAuthToken;

import java.util.List;

public interface OAuthTokenRepository {

    OAuthToken get(String username, String provider);

    void save(OAuthToken token);

    void delete(String username, String provider);

    List<OAuthToken> list();
}

package ai.exemplar.callbacks.oauth.providers;

import ai.exemplar.persistence.model.OAuthToken;

import java.util.Map;

public interface OAuthProvider {

    String processOAuthCallback(Map<String, String> queryParameters);

    void renewToken(OAuthToken token);
}

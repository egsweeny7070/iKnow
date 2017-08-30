package ai.exemplar.callbacks.oauth.service;

import java.io.InputStream;
import java.io.OutputStream;

public interface OAuthCallbacksService {

    void handleCallback(InputStream input, OutputStream output);
}

package ai.exemplar.callbacks.oauth;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.apache.log4j.Logger;

import java.io.*;

public class OAuthCallbacksProxyHandler implements RequestStreamHandler {

    static final Logger log = Logger.getLogger(OAuthCallbacksProxyHandler.class);

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        try {
            DaggerExemplarOAuthCallbacksComponent
                    .create()
                    .oAuthCallbacksService()
                    .handleCallback(input, output);

        } catch (Throwable e) {
            log.error("callbacks proxy handler processing failed", e);
        }
    }
}
package ai.exemplar.proxy.analytics;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AnalyticsProxyHandler  implements RequestStreamHandler {

    static final Logger log = Logger.getLogger(AnalyticsProxyHandler.class);

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        try {
            DaggerAnalyticsProxyComponent
                    .create()
                    .analyticsApiService()
                    .handleRequest(input, output);

        } catch (Throwable e) {
            log.error("analytics proxy handler processing failed", e);
        }
    }
}

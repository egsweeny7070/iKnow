package ai.exemplar.proxy.locations;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LocationsProxyHandler implements RequestStreamHandler {

    static final Logger log = Logger.getLogger(LocationsProxyHandler.class);

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        try {
            DaggerLocationsProxyComponent
                    .create()
                    .locationsApiService()
                    .handleRequest(input, output);

        } catch (Throwable e) {
            log.error("locations proxy handler processing failed", e);
        }
    }
}

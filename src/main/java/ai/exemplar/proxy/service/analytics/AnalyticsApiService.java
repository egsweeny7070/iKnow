package ai.exemplar.proxy.service.analytics;

import java.io.InputStream;
import java.io.OutputStream;

public interface AnalyticsApiService {

    void handleRequest(InputStream input, OutputStream output);
}

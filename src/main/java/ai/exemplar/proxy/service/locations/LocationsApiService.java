package ai.exemplar.proxy.service.locations;

import java.io.InputStream;
import java.io.OutputStream;

public interface LocationsApiService {

    void handleRequest(InputStream input, OutputStream output);
}

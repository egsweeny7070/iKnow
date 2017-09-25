package ai.exemplar.callbacks.oauth.service.impl;

import ai.exemplar.callbacks.oauth.providers.OAuthProvider;
import ai.exemplar.callbacks.oauth.service.OAuthCallbacksService;
import ai.exemplar.utils.lambda.LambdaRequest;
import ai.exemplar.utils.lambda.LambdaResponse;
import ai.exemplar.utils.json.GsonFabric;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

public class OAuthCallbacksServiceImpl implements OAuthCallbacksService {

    static final Logger log = Logger.getLogger(OAuthCallbacksServiceImpl.class);

    private final Map<String, OAuthProvider> providers;

    private final Gson gson = GsonFabric.gson();

    @Inject
    public OAuthCallbacksServiceImpl(Map<String, OAuthProvider> providers) {
        this.providers = providers;
    }

    @Override
    public void handleCallback(InputStream input, OutputStream output) {
        try {
            LambdaRequest request = gson.fromJson(new InputStreamReader(input), LambdaRequest.class);

            String provider = request.getPathParameters().get("proxy");

            String result = providers.get(provider)
                    .processOAuthCallback(request
                            .getQueryStringParameters());

            IOUtils.copy(
                    new ByteArrayInputStream(
                            gson.toJson(
                                    new LambdaResponse(
                                            false, 302,
                                            Collections.singletonMap(
                                                    "Location",
                                                    String.format(
                                                            "https://admin.exemplar.ai/dashboard?%s=%s",
                                                            provider, result
                                                    )
                                            ),
                                            null
                                    )
                            ).getBytes(StringUtils
                                    .UTF8)
                    ),
                    output
            );

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

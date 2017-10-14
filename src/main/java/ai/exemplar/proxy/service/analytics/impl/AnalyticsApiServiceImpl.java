package ai.exemplar.proxy.service.analytics.impl;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.authorization.AuthorizationService;
import ai.exemplar.proxy.service.analytics.AnalyticsApiService;
import ai.exemplar.proxy.service.exceptions.BadRequestException;
import ai.exemplar.proxy.service.exceptions.NotFoundException;
import ai.exemplar.proxy.service.exceptions.UnauthorizedException;
import ai.exemplar.utils.json.GsonFabric;
import ai.exemplar.utils.lambda.LambdaRequest;
import ai.exemplar.utils.lambda.LambdaResponse;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class AnalyticsApiServiceImpl implements AnalyticsApiService {

    static final Logger log = Logger.getLogger(AnalyticsApiServiceImpl.class);

    private final AuthorizationService authorizationService;

    //todo: [urgent] inject locations service to check permissions

    private final Map<String, AnalyticsProvider> analyticsProviders;

    private final Gson gson = GsonFabric.gson();

    @Inject
    public AnalyticsApiServiceImpl(AuthorizationService authorizationService, Map<String, AnalyticsProvider> analyticsProviders) {
        this.authorizationService = authorizationService;
        this.analyticsProviders = analyticsProviders;
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output) {
        try {
            try {
                LambdaRequest request = gson.fromJson(new InputStreamReader(input), LambdaRequest.class);

                if (request.getHttpMethod().equals("OPTIONS")) {
                    IOUtils.copy(
                            new ByteArrayInputStream(
                                    gson.toJson(
                                            new LambdaResponse(
                                                    false,
                                                    200,
                                                    ImmutableMap.<String, String>builder()
                                                            .put("Content-Type", "application/json")
                                                            .put("Access-Control-Allow-Methods", "GET, OPTIONS")
                                                            .put("Access-Control-Allow-Origin", "*")
                                                            .put("Access-Control-Allow-Headers", "Authorization, Accept, Origin")
                                                            .build(),
                                                    null
                                            )
                                    ).getBytes(StringUtils
                                            .UTF8)
                            ),
                            output
                    );

                    return;
                }

                if (!request.getHttpMethod().equals("GET")) {
                    throw new NotFoundException();
                }

                String account = Optional.ofNullable(authorizationService
                        .authorization(Optional.ofNullable(request
                                .getHeaders().get("Authorization"))
                                .orElseThrow(BadRequestException::new)
                        ))
                        .orElseThrow(UnauthorizedException::new);

                String location = Optional.ofNullable(request
                        .getPathParameters().get("location"))
                        .orElseThrow(BadRequestException::new);

                //todo: [urgent] assert account permissions to access the specified location
                // requires significant migration of the data for the old customers

                IOUtils.copy(
                        new ByteArrayInputStream(
                                gson.toJson(
                                        new LambdaResponse(
                                                false,
                                                200,
                                                ImmutableMap.<String, String>builder()
                                                        .put("Content-Type", "application/json")
                                                        .put("Access-Control-Allow-Origin", "*")
                                                        .build(),
                                                gson.toJson(Optional
                                                        .ofNullable(analyticsProviders.get(
                                                                request.getPathParameters()
                                                                        .get("proxy")
                                                        ))
                                                        .map(provider -> provider.get(
                                                                location,
                                                                request.getQueryStringParameters()
                                                        ))
                                                        .orElseThrow(NotFoundException::new)
                                                )
                                        )
                                ).getBytes(StringUtils
                                        .UTF8)
                        ),
                        output
                );

            } catch (NotFoundException e) {
                log.warn(e);

                IOUtils.copy(
                        new ByteArrayInputStream(
                                gson.toJson(
                                        new LambdaResponse(
                                                false,
                                                404,
                                                Collections.singletonMap("Access-Control-Allow-Origin", "*"),
                                                null
                                        )
                                ).getBytes(StringUtils
                                        .UTF8)
                        ),
                        output
                );

            } catch (UnauthorizedException e) {
                log.warn(e);

                IOUtils.copy(
                        new ByteArrayInputStream(
                                gson.toJson(
                                        new LambdaResponse(
                                                false,
                                                403,
                                                Collections.singletonMap("Access-Control-Allow-Origin", "*"),
                                                null
                                        )
                                ).getBytes(StringUtils
                                        .UTF8)
                        ),
                        output
                );

            } catch (BadRequestException e) {
                log.warn(e);

                IOUtils.copy(
                        new ByteArrayInputStream(
                                gson.toJson(
                                        new LambdaResponse(
                                                false,
                                                400,
                                                Collections.singletonMap("Access-Control-Allow-Origin", "*"),
                                                null
                                        )
                                ).getBytes(StringUtils
                                        .UTF8)
                        ),
                        output
                );

            }

        } catch (Throwable e) {
            log.error(e);

            throw new RuntimeException(e);
        }
    }
}

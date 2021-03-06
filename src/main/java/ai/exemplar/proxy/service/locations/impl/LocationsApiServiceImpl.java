package ai.exemplar.proxy.service.locations.impl;

import ai.exemplar.authorization.AuthorizationService;
import ai.exemplar.common.LocationsService;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.proxy.service.locations.LocationsApiService;
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
import java.util.Optional;

public class LocationsApiServiceImpl implements LocationsApiService {

    static final Logger log = Logger.getLogger(LocationsApiServiceImpl.class);

    private final AuthorizationService authorizationService;

    private final OAuthTokenRepository oAuthTokenRepository;

    private final LocationsService locationsService;

    private final Gson gson = GsonFabric.gson();

    @Inject
    public LocationsApiServiceImpl(AuthorizationService authorizationService, OAuthTokenRepository oAuthTokenRepository, LocationsService locationsService) {
        this.authorizationService = authorizationService;
        this.oAuthTokenRepository = oAuthTokenRepository;
        this.locationsService = locationsService;
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output) {
        try {
            try {
                LambdaRequest request = gson.fromJson(new InputStreamReader(input), LambdaRequest.class);

                if (Optional.ofNullable(request.getHeaders()
                        .get("X-Exemplar-Warm-Up"))
                        .map(Boolean::parseBoolean)
                        .orElse(false)) {
                    return;
                }

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
                                                gson.toJson(locationsService
                                                        .locations(Optional.ofNullable(
                                                                oAuthTokenRepository.get(Optional.ofNullable(
                                                                        authorizationService
                                                                                .authorization(Optional.ofNullable(request
                                                                                        .getHeaders().get("Authorization"))
                                                                                        .orElseThrow(BadRequestException::new)
                                                                                )
                                                                        ).orElseThrow(UnauthorizedException::new),
                                                                        "square"
                                                                )).orElseThrow(NotFoundException::new)
                                                        )
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

package ai.exemplar.common.square;

import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.api.square.model.GlobalAddress;
import ai.exemplar.common.LocationsService;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.SquareLocationRepository;
import ai.exemplar.persistence.dynamodb.schema.square.LocationSchema;
import ai.exemplar.persistence.model.OAuthToken;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SquareLocationsService implements LocationsService {

    static final Logger log = Logger.getLogger(SquareLocationsService.class);

    private final OAuthTokenRepository tokensRepository;

    private final SquareApiProvider api;

    private final SquareLocationRepository locationRepository;

    @Inject
    public SquareLocationsService(OAuthTokenRepository tokensRepository, SquareApiProvider api, SquareLocationRepository locationRepository) {
        this.tokensRepository = tokensRepository;
        this.api = api;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<LocationSchema> locations(OAuthToken token) {
        if (Optional.ofNullable(token.getLastFetched())
                .map(lastFetched -> lastFetched
                        .isBefore(LocalDateTime.now()
                                .minus(1L, ChronoUnit.DAYS)))
                .orElse(true)) {

            return fetchLocations(token);

        } else {

            return locationRepository
                    .list(token.getId());

        }
    }

    private List<LocationSchema> fetchLocations(OAuthToken token) {
        Map<String, LocationSchema> existingLocations = locationRepository
                .list(token.getId()).stream()
                .collect(Collectors.toMap(
                        LocationSchema::getId,
                        location -> location
                ));

        List<LocationSchema> locations = api.listLocations(token.getToken()).stream()
                .map(merchant -> new LocationSchema(
                        token.getId(),
                        merchant.getId(),
                        merchant.getName(),
                        merchant.getBusinessName(),
                        merchant.getBusinessType(),
                        merchant.nickname(),
                        merchant.getEmail(),
                        merchant.phone(),
                        merchant.getCountry(),
                        Optional.ofNullable(merchant.getAddress())
                                .map(GlobalAddress::getLocality).orElse(null),
                        Optional.ofNullable(merchant.getAddress())
                                .map(GlobalAddress::address).orElse(null),
                        Optional.ofNullable(existingLocations.get(merchant.getId()))
                                .map(LocationSchema::getPlayHistoryProviders)
                                .orElse(Collections.emptyMap()),
                        Optional.ofNullable(existingLocations.get(merchant.getId()))
                                .map(LocationSchema::getLastFetched)
                                .orElse(null)
                )).collect(Collectors.toList());

        log.info(String.format("fetched %d square locations for key %s", locations.size(), token.getId()));

        locationRepository.batchSave(locations);

        tokensRepository
                .save(new OAuthToken(
                        token.getId(),
                        token.getProvider(),
                        token.getToken(),
                        token.getRefreshToken(),
                        token.getCreated(),
                        token.getUpdated(),
                        token.getExpiration(),
                        LocalDateTime.now(),
                        token.getInternalId()
                ));

        return locations;
    }

    @Override
    public LocationSchema get(String account, String id) {
        return locationRepository.get(account, id);
    }

    @Override
    public void save(LocationSchema location) {
        locationRepository.save(location);
    }
}

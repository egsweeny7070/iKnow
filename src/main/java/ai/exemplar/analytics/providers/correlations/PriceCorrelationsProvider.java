package ai.exemplar.analytics.providers.correlations;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.correlations.values.CorrelationValue;
import ai.exemplar.proxy.service.exceptions.NotFoundException;
import ai.exemplar.storage.CorrelationsStorageService;
import ai.exemplar.storage.values.LocationCorrelationsEntry;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriceCorrelationsProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(PriceCorrelationsProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "correlations.price";

    private final CorrelationsStorageService storageService;

    @Inject
    public PriceCorrelationsProvider(CorrelationsStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Object get(String location, Map<String, String> query) {
        return Optional.ofNullable(storageService
                .priceCorrelations().stream()
                .collect(Collectors.toMap(LocationCorrelationsEntry::getLocationId, entry -> entry))
                .get(location))
                .map(entry ->
                        Stream.of(
                                new CorrelationValue("Danceability", entry.getDanceability()),
                                new CorrelationValue("Speechiness", entry.getSpeechiness()),
                                new CorrelationValue("Instrumentalness", entry.getInstrumentalness()),
                                new CorrelationValue("Explicit Lyrics", entry.getExplicitLyrics()),
                                new CorrelationValue("Popularity", entry.getPopularity()),
                                new CorrelationValue("Duration", entry.getDuration()),
                                new CorrelationValue("Loudness", entry.getLoudness()),
                                new CorrelationValue("Tempo", entry.getTempo()),
                                new CorrelationValue("Acousticness", entry.getAcousticness()),
                                new CorrelationValue("Energy", entry.getEnergy()),
                                new CorrelationValue("Valence", entry.getValence()),
                                new CorrelationValue("Mode", entry.getMode())
                        )
                        .sorted(Comparator
                                .comparing(CorrelationValue::getCorrelationValue)
                                .reversed())
                        .collect(Collectors.toList())
                )
                .orElseThrow(NotFoundException::new);
    }
}

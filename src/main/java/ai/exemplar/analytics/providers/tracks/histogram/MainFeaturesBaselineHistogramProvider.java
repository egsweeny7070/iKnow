package ai.exemplar.analytics.providers.tracks.histogram;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.tracks.histogram.values.FeaturesHistogramItem;
import ai.exemplar.analytics.providers.tracks.histogram.values.MainFeatures;
import ai.exemplar.persistence.TracksAnalyticsRepository;
import ai.exemplar.persistence.dynamodb.schema.analytics.TrackFeatureAnalyticsDocumentSchema;
import ai.exemplar.persistence.dynamodb.schema.analytics.TracksAnalyticsItemSchema;
import ai.exemplar.proxy.service.exceptions.BadRequestException;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainFeaturesBaselineHistogramProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(MainFeaturesBaselineHistogramProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "tracks.features";

    /**
     * Query parameter names
     */

    public static final String QUERY_START_DATE = "from";

    public static final String QUERY_END_DATE = "to";

    public static final String TIMEZONE_OFFSET = "offset";

    /**
     * Dependencies
     */

    private final TracksAnalyticsRepository repository;

    @Inject
    public MainFeaturesBaselineHistogramProvider(TracksAnalyticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object get(String location, Map<String, String> query) {
        LocalDate from = Optional.ofNullable(query
                .get(QUERY_START_DATE))
                .map(LocalDate::parse)
                .orElseThrow(BadRequestException::new);

        LocalDate to = Optional.ofNullable(query
                .get(QUERY_END_DATE))
                .map(LocalDate::parse)
                .orElseThrow(BadRequestException::new);

        ZoneOffset offset = Optional.ofNullable(query
                .get(TIMEZONE_OFFSET))
                .map(ZoneOffset::of)
                .orElseThrow(BadRequestException::new);

        LocalDateTime startTimestamp = ZonedDateTime
                .of(from, LocalTime.MIDNIGHT, offset)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endTimestamp = ZonedDateTime
                .of(to, LocalTime.MIDNIGHT, offset)
                .plus(1, ChronoUnit.DAYS)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        List<TracksAnalyticsItemSchema> data = repository.query(location, startTimestamp, endTimestamp).stream()
                .collect(Collectors
                        .groupingBy(TracksAnalyticsItemSchema::getRowTime))
                .entrySet().stream()
                .map(entry -> new TracksAnalyticsItemSchema(
                        location,
                        entry.getKey().atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(offset).toLocalDateTime(),
                        entry.getValue().stream()
                                .mapToInt(TracksAnalyticsItemSchema::getTracksCount).sum(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getLoudness).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getMin).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .min().orElse(0),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getLoudness).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getMax).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .max().orElse(0),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getLoudness).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getSum).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .sum(),
                                null
                        ),
                        null,
                        null,
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getDuration).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getMin).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .min().orElse(0),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getDuration).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getMax).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .max().orElse(0),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getDuration).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getSum).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getTempo).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getMin).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .min().orElse(0),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getTempo).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getMax).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .max().orElse(0),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getTempo).filter(Objects::nonNull)
                                        .map(TrackFeatureAnalyticsDocumentSchema::getSum).filter(Objects::nonNull)
                                        .mapToDouble(v -> v)
                                        .sum(),
                                null
                        )
                ))
                .collect(Collectors.toList());

        if (data.isEmpty()) {
            return new MainFeatures(
                    new FeaturesHistogramItem(0.0, 0.0, 0.0),
                    new FeaturesHistogramItem(0.0, 0.0, 0.0),
                    new FeaturesHistogramItem(0.0, 0.0, 0.0)
            );
        }

        return new MainFeatures(
                new FeaturesHistogramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getLoudness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getLoudness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getLoudness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                        / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesHistogramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getDuration)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getDuration)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getDuration)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesHistogramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getTempo)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getTempo)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getTempo)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                )
        );
    }
}

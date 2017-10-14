package ai.exemplar.analytics.providers.tracks.histogram;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.tracks.histogram.values.FeaturesHistogramItem;
import ai.exemplar.analytics.providers.tracks.histogram.values.MainFeatures;
import ai.exemplar.persistence.TracksAnalyticsRepository;
import ai.exemplar.persistence.dynamodb.schema.analytics.TrackFeatureAnalyticsDocumentSchema;
import ai.exemplar.persistence.model.TracksAnalyticsItem;
import ai.exemplar.proxy.service.exceptions.BadRequestException;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
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

    public static final String QUERY_DATE = "date";

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
        LocalDate date = Optional.ofNullable(query
                .get(QUERY_DATE))
                .map(LocalDate::parse)
                .orElseThrow(BadRequestException::new);

        ZoneOffset offset = Optional.ofNullable(query
                .get(TIMEZONE_OFFSET))
                .map(ZoneOffset::of)
                .orElseThrow(BadRequestException::new);

        LocalDateTime startTimestamp = ZonedDateTime
                .of(date, LocalTime.MIDNIGHT, offset)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endTimestamp = ZonedDateTime
                .of(date, LocalTime.MIDNIGHT, offset)
                .plus(1, ChronoUnit.DAYS)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        List<TracksAnalyticsItem> data = repository.query(location, startTimestamp, endTimestamp).stream()
                .collect(Collectors
                        .groupingBy(TracksAnalyticsItem::getTimestamp))
                .entrySet().stream()
                .map(entry -> new TracksAnalyticsItem(
                        location,
                        entry.getKey().atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(offset).toLocalDateTime(),
                        null,
                        entry.getValue().stream()
                                .mapToInt(TracksAnalyticsItem::getTracksCount).sum(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getLoudness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getLoudness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getLoudness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        null,
                        null,
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getDuration)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getDuration)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getDuration)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getTempo)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getTempo)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getTempo)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
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
                        data.stream().map(TracksAnalyticsItem::getLoudness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getLoudness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getLoudness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                        / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesHistogramItem(
                        data.stream().map(TracksAnalyticsItem::getDuration)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getDuration)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getDuration)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesHistogramItem(
                        data.stream().map(TracksAnalyticsItem::getTempo)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getTempo)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getTempo)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                )
        );
    }
}

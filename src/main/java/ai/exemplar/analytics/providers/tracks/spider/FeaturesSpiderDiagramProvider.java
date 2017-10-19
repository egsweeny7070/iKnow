package ai.exemplar.analytics.providers.tracks.spider;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.tracks.spider.values.FeaturesDiagram;
import ai.exemplar.analytics.providers.tracks.spider.values.FeaturesDiagramItem;
import ai.exemplar.analytics.providers.tracks.spider.values.FeaturesSpiderDiagram;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class FeaturesSpiderDiagramProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(FeaturesSpiderDiagramProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "tracks.spider";

    /**
     * Query parameter names
     */

    public static final String QUERY_START_DATE = "from";

    public static final String QUERY_END_DATE = "to";

    public static final String QUERY_BREAK_TIME = "break";

    public static final String TIMEZONE_OFFSET = "offset";

    /**
     * Dependencies
     */

    private final TracksAnalyticsRepository repository;

    @Inject
    public FeaturesSpiderDiagramProvider(TracksAnalyticsRepository repository) {
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

        LocalTime breakTime = Optional.ofNullable(query
                .get(QUERY_BREAK_TIME))
                .map(LocalTime::parse)
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

        List<TracksAnalyticsItemSchema> data = load(location, startTimestamp, endTimestamp);

        return new FeaturesSpiderDiagram(
                prepareDiagram(data),
                prepareDiagram(data.stream()
                        .filter(item -> item.getRowTime()
                                .atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(offset).toLocalTime()
                                .isBefore(breakTime))
                        .collect(Collectors.toList())),
                prepareDiagram(data.stream()
                        .filter(item -> item.getRowTime()
                                .atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(offset).toLocalTime()
                                .isAfter(breakTime))
                        .collect(Collectors.toList()))
        );
    }

    private List<TracksAnalyticsItemSchema> load(String location, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        return repository.query(location, startTimestamp, endTimestamp).stream()
                .collect(Collectors
                        .groupingBy(TracksAnalyticsItemSchema::getRowTime))
                .entrySet().stream()
                .map(entry -> new TracksAnalyticsItemSchema(
                        null,
                        entry.getKey(),
                        entry.getValue().stream()
                                .mapToInt(TracksAnalyticsItemSchema::getTracksCount).sum(),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getAcousticness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getAcousticness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getAcousticness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getDanceability)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getDanceability)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getDanceability)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getEnergy)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getEnergy)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getEnergy)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getInstrumentalness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getInstrumentalness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getInstrumentalness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getLiveness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getLiveness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getLiveness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        null,
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getSpeechiness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getSpeechiness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getSpeechiness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getValence)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getValence)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItemSchema::getValence)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        null,
                        null
                ))
                .collect(Collectors.toList());
    }

    private FeaturesDiagram prepareDiagram(List<TracksAnalyticsItemSchema> data) {
        if (data.isEmpty()) {
            return new FeaturesDiagram(
                    new FeaturesDiagramItem(0.0, 0.0, 0.0),
                    new FeaturesDiagramItem(0.0, 0.0, 0.0),
                    new FeaturesDiagramItem(0.0, 0.0, 0.0),
                    new FeaturesDiagramItem(0.0, 0.0, 0.0),
                    new FeaturesDiagramItem(0.0, 0.0, 0.0),
                    new FeaturesDiagramItem(0.0, 0.0, 0.0),
                    new FeaturesDiagramItem(0.0, 0.0, 0.0)
            );
        }

        return new FeaturesDiagram(
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getAcousticness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getAcousticness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getAcousticness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getDanceability)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getDanceability)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getDanceability)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getEnergy)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getEnergy)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getEnergy)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getInstrumentalness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getInstrumentalness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getInstrumentalness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getLiveness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getLiveness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getLiveness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getSpeechiness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getSpeechiness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getSpeechiness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItemSchema::getValence)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getValence)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItemSchema::getValence)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItemSchema::getTracksCount).sum()
                )
        );
    }
}

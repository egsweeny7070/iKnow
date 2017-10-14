package ai.exemplar.analytics.providers.tracks.spider;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.tracks.spider.values.FeaturesDiagram;
import ai.exemplar.analytics.providers.tracks.spider.values.FeaturesDiagramItem;
import ai.exemplar.analytics.providers.tracks.spider.values.FeaturesSpiderDiagram;
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

public class FeaturesSpiderDiagramProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(FeaturesSpiderDiagramProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "tracks.spider";

    /**
     * Query parameter names
     */

    public static final String QUERY_DATE = "date";

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
        LocalDate date = Optional.ofNullable(query
                .get(QUERY_DATE))
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
                .of(date, LocalTime.MIDNIGHT, offset)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime breakTimestamp = ZonedDateTime
                .of(date, breakTime, offset)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endTimestamp = ZonedDateTime
                .of(date, LocalTime.MIDNIGHT, offset)
                .plus(1, ChronoUnit.DAYS)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();

        return new FeaturesSpiderDiagram(
                prepareDiagram(location, startTimestamp, breakTimestamp),
                prepareDiagram(location, breakTimestamp, endTimestamp)
        );
    }

    private FeaturesDiagram prepareDiagram(String location, LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        List<TracksAnalyticsItem> data = repository.query(location, startTimestamp, endTimestamp).stream()
                .collect(Collectors
                        .groupingBy(TracksAnalyticsItem::getTimestamp))
                .entrySet().stream()
                .map(entry -> new TracksAnalyticsItem(
                        null,
                        null,
                        null,
                        entry.getValue().stream()
                                .mapToInt(TracksAnalyticsItem::getTracksCount).sum(),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getAcousticness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getAcousticness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getAcousticness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getDanceability)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getDanceability)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getDanceability)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getEnergy)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getEnergy)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getEnergy)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getInstrumentalness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getInstrumentalness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getInstrumentalness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getLiveness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getLiveness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getLiveness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        null,
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getSpeechiness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getSpeechiness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getSpeechiness)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        new TrackFeatureAnalyticsDocumentSchema(
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getValence)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin).min().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getValence)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax).max().getAsDouble(),
                                entry.getValue().stream()
                                        .map(TracksAnalyticsItem::getValence)
                                        .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum).sum(),
                                null
                        ),
                        null,
                        null
                ))
                .collect(Collectors.toList());

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
                        data.stream().map(TracksAnalyticsItem::getAcousticness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getAcousticness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getAcousticness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItem::getDanceability)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getDanceability)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getDanceability)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItem::getEnergy)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getEnergy)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getEnergy)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItem::getInstrumentalness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getInstrumentalness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getInstrumentalness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItem::getLiveness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getLiveness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getLiveness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItem::getSpeechiness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getSpeechiness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getSpeechiness)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                ),
                new FeaturesDiagramItem(
                        data.stream().map(TracksAnalyticsItem::getValence)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMin)
                                .min().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getValence)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getMax)
                                .max().orElse(0),
                        data.stream().map(TracksAnalyticsItem::getValence)
                                .mapToDouble(TrackFeatureAnalyticsDocumentSchema::getSum)
                                .sum()
                                / data.stream().mapToDouble(TracksAnalyticsItem::getTracksCount).sum()
                )
        );
    }
}

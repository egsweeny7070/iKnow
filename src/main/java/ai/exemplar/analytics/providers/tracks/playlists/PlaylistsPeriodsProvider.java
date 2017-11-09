package ai.exemplar.analytics.providers.tracks.playlists;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.tracks.playlists.values.PlaylistDateItem;
import ai.exemplar.analytics.providers.tracks.playlists.values.PlaylistPeriodItem;
import ai.exemplar.persistence.SpotifyHistoryRepository;
import ai.exemplar.persistence.dynamodb.schema.spotify.LinkDocumentSchema;
import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.proxy.service.exceptions.BadRequestException;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class PlaylistsPeriodsProvider  implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(PlaylistsPeriodsProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "tracks.playlists";

    /**
     * Query parameter names
     */

    public static final String QUERY_START_DATE = "from";

    public static final String QUERY_END_DATE = "to";

    public static final String TIMEZONE_OFFSET = "offset";

    /**
     * Dependencies
     */

    private final SpotifyHistoryRepository repository;

    @Inject
    public PlaylistsPeriodsProvider(SpotifyHistoryRepository repository) {
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

        return repository.list(location, startTimestamp, endTimestamp).stream()
                .collect(Collectors.groupingBy(playHistoryItem -> playHistoryItem
                        .getTimestamp().atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(offset).toLocalDate()))
                .entrySet().stream()
                .map(entry -> {
                    List<PlaylistPeriodItem> items = new ArrayList<>();

                    Iterator<PlayHistoryItemSchema> itemsIterator = entry.getValue().stream()
                            .sorted(Comparator
                                    .comparing(PlayHistoryItemSchema::getTimestamp))
                            .collect(Collectors.toList())
                            .iterator();

                    PlayHistoryItemSchema periodBegin = itemsIterator.next();
                    PlayHistoryItemSchema periodEnd = periodBegin;

                    String contextUri = Optional.ofNullable(periodBegin.getContext())
                            .map(LinkDocumentSchema::getUri).orElse(null);

                    while (itemsIterator.hasNext()) {
                        PlayHistoryItemSchema current = itemsIterator.next();

                        String currentContextUri = Optional.ofNullable(current.getContext())
                                .map(LinkDocumentSchema::getUri).orElse(null);

                        if (periodEnd.getTimestamp()
                                .plus(periodEnd.getTrack().getDuration() / 1000, ChronoUnit.SECONDS)
                                .plus(15L, ChronoUnit.MINUTES)
                                .isAfter(current.getTimestamp())) {

                            boolean urisNull = (contextUri == null) && (currentContextUri == null);
                            boolean urisEqual = (contextUri != null) && (contextUri.equals(currentContextUri));

                            if (urisNull || urisEqual) {
                                periodEnd = current;

                                continue;
                            }
                        }

                        LocalTime endTime = periodEnd.getTimestamp()
                                .atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(offset)
                                .plus(
                                        periodEnd.getTrack().getDuration() / 1000,
                                        ChronoUnit.SECONDS
                                )
                                .toLocalTime();

                        if (periodEnd.getTimestamp()
                                .plus(
                                        periodEnd.getTrack().getDuration() / 1000,
                                        ChronoUnit.SECONDS
                                )
                                .isAfter(current.getTimestamp())) {
                            endTime = current.getTimestamp()
                                    .atZone(ZoneId.systemDefault())
                                    .withZoneSameInstant(offset)
                                    .toLocalTime();
                        }

                        items.add(new PlaylistPeriodItem(
                                periodBegin.getTimestamp()
                                        .atZone(ZoneId.systemDefault())
                                        .withZoneSameInstant(offset)
                                        .toLocalTime(),
                                endTime,
                                periodBegin.getContext()
                        ));

                        periodEnd = periodBegin = current;
                        contextUri = currentContextUri;
                    }

                    items.add(new PlaylistPeriodItem(
                            periodBegin.getTimestamp()
                                    .atZone(ZoneId.systemDefault())
                                    .withZoneSameInstant(offset)
                                    .toLocalTime(),
                            periodEnd.getTimestamp()
                                    .atZone(ZoneId.systemDefault())
                                    .withZoneSameInstant(offset)
                                    .plus(
                                            periodEnd.getTrack().getDuration() / 1000,
                                            ChronoUnit.SECONDS
                                    )
                                    .toLocalTime(),
                            periodBegin.getContext()
                    ));

                    return new PlaylistDateItem(entry.getKey(), items);
                })
                .sorted(Comparator
                        .comparing(PlaylistDateItem::getDate))
                .collect(Collectors.toList());
    }
}

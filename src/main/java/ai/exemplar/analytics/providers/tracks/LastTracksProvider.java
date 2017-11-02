package ai.exemplar.analytics.providers.tracks;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.persistence.SpotifyHistoryRepository;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class LastTracksProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(LastTracksProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "tracks.recent";

    /**
     * Query parameter names
     */

    public static final String QUERY_LIMIT = "limit";

    /**
     * Dependencies
     */

    private final SpotifyHistoryRepository repository;

    @Inject
    public LastTracksProvider(SpotifyHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object get(String location, Map<String, String> query) {
        int limit = Integer.parseInt(Optional
                .ofNullable(query).orElse(Collections
                        .emptyMap())
                .getOrDefault(QUERY_LIMIT, "10"));

        return repository.recent(location, limit);
    }
}

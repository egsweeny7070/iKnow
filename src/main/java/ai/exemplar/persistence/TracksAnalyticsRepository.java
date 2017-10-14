package ai.exemplar.persistence;

import ai.exemplar.persistence.model.TracksAnalyticsItem;

import java.time.LocalDateTime;
import java.util.List;

public interface TracksAnalyticsRepository {

    void save(List<TracksAnalyticsItem> batch);

    List<TracksAnalyticsItem> query(String location, LocalDateTime timestampFrom, LocalDateTime timestampTo);
}

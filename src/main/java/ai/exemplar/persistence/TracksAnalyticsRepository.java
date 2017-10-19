package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.schema.analytics.TracksAnalyticsItemSchema;

import java.time.LocalDateTime;
import java.util.List;

public interface TracksAnalyticsRepository {

    void save(List<TracksAnalyticsItemSchema> batch);

    List<TracksAnalyticsItemSchema> query(String location, LocalDateTime timestampFrom, LocalDateTime timestampTo);
}

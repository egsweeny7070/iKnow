package ai.exemplar.persistence;

import ai.exemplar.persistence.dynamodb.schema.analytics.PaymentsAnalyticsItemSchema;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentsAnalyticsRepository {

    void save(List<PaymentsAnalyticsItemSchema> batch);

    List<PaymentsAnalyticsItemSchema> query(String location, LocalDateTime timestampFrom, LocalDateTime timestampTo);
}

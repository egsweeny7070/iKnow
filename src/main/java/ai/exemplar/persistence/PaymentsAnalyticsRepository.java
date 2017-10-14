package ai.exemplar.persistence;

import ai.exemplar.persistence.model.PaymentsAnalyticsItem;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentsAnalyticsRepository {

    void save(List<PaymentsAnalyticsItem> batch);

    List<PaymentsAnalyticsItem> query(String location, LocalDateTime timestampFrom, LocalDateTime timestampTo);
}

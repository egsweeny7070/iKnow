package ai.exemplar.analytics.providers.payments;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.payments.values.DayPaymentsStatistics;
import ai.exemplar.persistence.PaymentsAnalyticsRepository;
import ai.exemplar.persistence.model.PaymentsAnalyticsItem;
import ai.exemplar.proxy.service.exceptions.BadRequestException;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BasicPaymentsDiagramProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(BasicPaymentsDiagramProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "payments.general";

    /**
     * Query parameter names
     */

    public static final String QUERY_START_DATE = "from";

    public static final String QUERY_END_DATE = "to";

    public static final String TIMEZONE_OFFSET = "offset";

    /**
     * Dependencies
     */

    private final PaymentsAnalyticsRepository repository;

    @Inject
    public BasicPaymentsDiagramProvider(PaymentsAnalyticsRepository repository) {
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

        return repository.query(location, startTimestamp, endTimestamp).stream()
                .collect(Collectors
                        .groupingBy(PaymentsAnalyticsItem::getTimestamp))
                .entrySet().stream()
                .map(entry -> new PaymentsAnalyticsItem(
                        location,
                        entry.getKey().atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(offset).toLocalDateTime(),
                        null,
                        entry.getValue().stream()
                                .mapToInt(PaymentsAnalyticsItem::getTotalPaymentsCount).sum(),
                        entry.getValue().stream()
                                .mapToInt(PaymentsAnalyticsItem::getTotalItemsCount).sum(),
                        entry.getValue().stream()
                                .mapToDouble(PaymentsAnalyticsItem::getTotalDiscountPercent).sum(),
                        entry.getValue().stream()
                                .mapToDouble(PaymentsAnalyticsItem::getCollectedAmount).sum()))
                .collect(Collectors
                        .groupingBy(item -> item.getRowTime().toLocalDate()))
                .entrySet().stream()
                .map(localDateListEntry -> new DayPaymentsStatistics(
                        localDateListEntry.getKey().toString(),
                        localDateListEntry.getValue().stream()
                                .mapToDouble(PaymentsAnalyticsItem::getCollectedAmount).sum(),
                        localDateListEntry.getValue().stream()
                                .mapToInt(PaymentsAnalyticsItem::getTotalItemsCount).sum(),
                        localDateListEntry.getValue().stream()
                                .mapToDouble(PaymentsAnalyticsItem::getCollectedAmount).sum() /
                                localDateListEntry.getValue().stream()
                                        .mapToDouble(PaymentsAnalyticsItem::getTotalPaymentsCount).sum(),
                        localDateListEntry.getValue().stream()
                                .mapToDouble(PaymentsAnalyticsItem::getTotalItemsCount).sum() /
                                localDateListEntry.getValue().stream()
                                        .mapToDouble(PaymentsAnalyticsItem::getTotalPaymentsCount).sum(),
                        localDateListEntry.getValue().stream()
                                .mapToDouble(PaymentsAnalyticsItem::getCollectedAmount).sum() /
                                localDateListEntry.getValue().stream()
                                        .mapToDouble(PaymentsAnalyticsItem::getTotalItemsCount).sum()
                ))
                .collect(Collectors.toList());
    }
}

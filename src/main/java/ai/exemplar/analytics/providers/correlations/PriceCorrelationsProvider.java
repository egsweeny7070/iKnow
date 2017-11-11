package ai.exemplar.analytics.providers.correlations;

import ai.exemplar.analytics.AnalyticsProvider;
import ai.exemplar.analytics.providers.correlations.values.CorrelationValue;
import ai.exemplar.proxy.service.exceptions.NotFoundException;
import ai.exemplar.storage.CorrelationsStorageService;
import ai.exemplar.storage.values.CorrelationLegendEntry;
import ai.exemplar.storage.values.LocationCorrelationsEntry;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriceCorrelationsProvider implements AnalyticsProvider {

    static final Logger log = Logger.getLogger(PriceCorrelationsProvider.class);

    /**
     * Analytics provider name
     */

    public static final String PROVIDER_NAME = "correlations.price";

    private final CorrelationsStorageService storageService;

    @Inject
    public PriceCorrelationsProvider(CorrelationsStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Object get(String location, Map<String, String> query) {
        final Map<String, String> canonicalNames = storageService.correlationsLegend().stream()
                .filter(CorrelationLegendEntry::getInclude)
                .collect(Collectors.toMap(
                        CorrelationLegendEntry::getFeatureName,
                        CorrelationLegendEntry::getCanonicalName
                ));
        return Optional.ofNullable(storageService
                .priceCorrelations().stream()
                .collect(Collectors.groupingBy(LocationCorrelationsEntry::getLocationId))
                .get(location))
                .map(correlationsEntries ->
                        correlationsEntries.stream()
                                .filter(entry -> canonicalNames.containsKey(entry
                                        .getFeatureName()))
                                .map(entry -> new CorrelationValue(
                                        canonicalNames.get(entry.getFeatureName()),
                                        entry.getCorrelationValue() * 100.0
                                ))
                                .sorted(Comparator
                                        .<CorrelationValue, Double>comparing(correlationValue -> Math
                                                .abs(correlationValue.getCorrelationValue()))
                                        .reversed())
                                .limit(12)
                                .sorted(Comparator
                                        .comparing(CorrelationValue::getCorrelationValue)
                                        .reversed())
                                .collect(Collectors.toList())
                )
                .orElseThrow(NotFoundException::new);
    }
}

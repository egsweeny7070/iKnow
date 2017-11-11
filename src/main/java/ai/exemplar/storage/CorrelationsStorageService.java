package ai.exemplar.storage;

import ai.exemplar.storage.values.CorrelationLegendEntry;
import ai.exemplar.storage.values.LocationCorrelationsEntry;

import java.util.List;

public interface CorrelationsStorageService {

    List<LocationCorrelationsEntry> salesCorrelations();

    List<LocationCorrelationsEntry> priceCorrelations();

    List<CorrelationLegendEntry> correlationsLegend();
}

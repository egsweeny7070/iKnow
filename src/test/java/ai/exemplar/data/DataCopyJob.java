package ai.exemplar.data;

import ai.exemplar.utils.test.DaggerExemplarServicesComponent;
import ai.exemplar.utils.test.ExemplarServicesComponent;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Ignore
public class DataCopyJob {

    static final Logger log = Logger.getLogger(DataIngestJob.class);

    @Test
    public void dataCopyJob() {
        ExemplarServicesComponent service = DaggerExemplarServicesComponent
                .create();

        service.spotifyHistoryRepository()
                .batchSave(
                        service.spotifyHistoryRepository()
                                .list(
                                        "Jesus",
                                        LocalDate.parse("2017-08-01").atStartOfDay(),
                                        LocalDate.parse("2017-11-01").atStartOfDay()
                                )
                                .stream()
                                .map(entry -> {
                                    entry.setKey("8BSTTGBX5Z7VM");
                                    return entry;
                                })
                                .collect(Collectors.toList())
                );
    }
}

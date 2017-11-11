package ai.exemplar.data;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.model.SquarePayment;
import ai.exemplar.utils.test.DaggerExemplarServicesComponent;
import ai.exemplar.utils.test.ExemplarServicesComponent;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Ignore
public class DataSubsetIngestJob {

    static final Logger log = Logger.getLogger(DataIngestJob.class);

    @Test
    public void dataSubsetIngestJob() {
        ExemplarServicesComponent service = DaggerExemplarServicesComponent
                .create();

        service.spotifyHistoryRepository()
                .list(
                        "8BSTTGBX5Z7VM",
                        LocalDate.parse("2017-08-01").atStartOfDay(),
                        LocalDate.parse("2017-11-01").atStartOfDay()
                )
                .stream()
                .sorted(Comparator
                        .comparing(PlayHistoryItemSchema::getTimestamp))
                .map(playHistoryItem -> new StreamEntry(
                        playHistoryItem.getTimestamp(),
                        EntryType.TRACK,
                        playHistoryItem
                ))
                .forEach(streamEntry -> {
                    if (streamEntry.getType() == EntryType.PAYMENT) {
                        service.streamsAppender()
                                .appendPayment((SquarePayment) streamEntry
                                        .getEntry());
                    } else {
                        service.streamsAppender()
                                .appendTrack((PlayHistoryItemSchema) streamEntry
                                        .getEntry());
                    }
                });
    }

    private enum EntryType {
        PAYMENT,
        TRACK
    }

    private static class StreamEntry {
        private final LocalDateTime timestamp;
        private final EntryType type;
        private final Object entry;
        public StreamEntry(LocalDateTime timestamp, EntryType type, Object entry) {
            this.timestamp = timestamp;
            this.type = type;
            this.entry = entry;
        }
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        public EntryType getType() {
            return type;
        }
        public Object getEntry() {
            return entry;
        }
    }
}

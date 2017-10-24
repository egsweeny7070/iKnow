package ai.exemplar.data;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.model.SquarePayment;
import ai.exemplar.utils.test.DaggerExemplarServicesComponent;
import ai.exemplar.utils.test.ExemplarServicesComponent;
import com.google.common.collect.Streams;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

@Ignore
public class DataIngestJob {

    static final Logger log = Logger.getLogger(DataIngestJob.class);

    @Test
    public void dataIngestJob() {
        ExemplarServicesComponent service = DaggerExemplarServicesComponent
                .create();

        Streams
                .concat(
                        service.spotifyHistoryRepository().scan().stream()
                                .map(playHistoryItem -> new StreamEntry(
                                        playHistoryItem.getTimestamp(),
                                        EntryType.TRACK,
                                        playHistoryItem
                                )),
                        service.squarePaymentsRepository().scan().stream()
                                .map(squarePayment -> new StreamEntry(
                                        squarePayment.getTimestamp(),
                                        EntryType.PAYMENT,
                                        squarePayment
                                ))
                )
                .filter(entry -> entry.getTimestamp()
                        .isBefore(LocalDateTime
                                .of(LocalDate.parse("2017-10-20"), LocalTime.MIDNIGHT)))
                .sorted(Comparator
                        .comparing(StreamEntry::getTimestamp))
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

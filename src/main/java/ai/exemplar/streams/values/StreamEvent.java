package ai.exemplar.streams.values;

import ai.exemplar.persistence.dynamodb.schema.spotify.PlayHistoryItemSchema;
import ai.exemplar.persistence.model.SquarePayment;

public class StreamEvent {

    private EventType type;

    private String key;

    private SquarePayment payment;

    private PlayHistoryItemSchema track;

    public StreamEvent(SquarePayment payment) {
        this.type = EventType.PAYMENT;
        this.key = payment.getLocation();
        this.payment = payment;
    }

    public StreamEvent(PlayHistoryItemSchema track) {
        this.type = EventType.TRACK;
        this.key = track.getKey();
        this.track = track;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SquarePayment getPayment() {
        return payment;
    }

    public void setPayment(SquarePayment payment) {
        this.payment = payment;
    }

    public PlayHistoryItemSchema getTrack() {
        return track;
    }

    public void setTrack(PlayHistoryItemSchema track) {
        this.track = track;
    }
}

package ai.exemplar.persistence.dynamodb.schema.spotify;

import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "SpotifyPlayHistory")
public class PlayHistoryItemSchema {

    private String key;

    private LocalDateTime timestamp;

    private TrackDocumentSchema track;

    private LinkDocumentSchema context;

    public PlayHistoryItemSchema() {
    }

    public PlayHistoryItemSchema(String key, LocalDateTime timestamp, TrackDocumentSchema track, LinkDocumentSchema context) {
        this.key = key;
        this.timestamp = timestamp;
        this.track = track;
        this.context = context;
    }

    @DynamoDBHashKey(attributeName = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBRangeKey(attributeName = "timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = "track")
    public TrackDocumentSchema getTrack() {
        return track;
    }

    public void setTrack(TrackDocumentSchema track) {
        this.track = track;
    }

    @DynamoDBAttribute(attributeName = "context")
    public LinkDocumentSchema getContext() {
        return context;
    }

    public void setContext(LinkDocumentSchema context) {
        this.context = context;
    }
}

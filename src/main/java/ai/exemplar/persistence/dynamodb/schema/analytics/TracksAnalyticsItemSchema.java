package ai.exemplar.persistence.dynamodb.schema.analytics;

import ai.exemplar.persistence.model.TracksAnalyticsItem;
import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "TracksBasicAnalytics")
public class TracksAnalyticsItemSchema {

    private String location;

    private LocalDateTime rowTime;

    private String rangeKey;

    private LocalDateTime timestamp;

    private Integer tracksCount;

    private TrackFeatureAnalyticsDocumentSchema acousticness;

    private TrackFeatureAnalyticsDocumentSchema danceability;

    private TrackFeatureAnalyticsDocumentSchema energy;

    private TrackFeatureAnalyticsDocumentSchema instrumentalness;

    private TrackFeatureAnalyticsDocumentSchema liveness;

    private TrackFeatureAnalyticsDocumentSchema loudness;

    private TrackFeatureAnalyticsDocumentSchema speechiness;

    private TrackFeatureAnalyticsDocumentSchema valence;

    private TrackFeatureAnalyticsDocumentSchema duration;

    private TrackFeatureAnalyticsDocumentSchema tempo;

    public TracksAnalyticsItemSchema() {
    }

    public TracksAnalyticsItemSchema(String location, LocalDateTime rowTime, String rangeKey, LocalDateTime timestamp, Integer tracksCount, TrackFeatureAnalyticsDocumentSchema acousticness, TrackFeatureAnalyticsDocumentSchema danceability, TrackFeatureAnalyticsDocumentSchema energy, TrackFeatureAnalyticsDocumentSchema instrumentalness, TrackFeatureAnalyticsDocumentSchema liveness, TrackFeatureAnalyticsDocumentSchema loudness, TrackFeatureAnalyticsDocumentSchema speechiness, TrackFeatureAnalyticsDocumentSchema valence, TrackFeatureAnalyticsDocumentSchema duration, TrackFeatureAnalyticsDocumentSchema tempo) {
        this.location = location;
        this.rowTime = rowTime;
        this.rangeKey = rangeKey;
        this.timestamp = timestamp;
        this.tracksCount = tracksCount;
        this.acousticness = acousticness;
        this.danceability = danceability;
        this.energy = energy;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.loudness = loudness;
        this.speechiness = speechiness;
        this.valence = valence;
        this.duration = duration;
        this.tempo = tempo;
    }

    private TracksAnalyticsItemSchema(String location) {
        this.location = location;
    }

    @DynamoDBHashKey(attributeName = "location")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "LocationTracksIndex", attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBAttribute(attributeName = "rowTime")
    public LocalDateTime getRowTime() {
        return rowTime;
    }

    public void setRowTime(LocalDateTime rowTime) {
        this.rowTime = rowTime;
    }

    @DynamoDBRangeKey(attributeName = "rangeKey")
    public String getRangeKey() {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey) {
        this.rangeKey = rangeKey;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "LocationTracksIndex", attributeName = "timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = "tracksCount")
    public Integer getTracksCount() {
        return tracksCount;
    }

    public void setTracksCount(Integer tracksCount) {
        this.tracksCount = tracksCount;
    }

    @DynamoDBAttribute(attributeName = "acousticness")
    public TrackFeatureAnalyticsDocumentSchema getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(TrackFeatureAnalyticsDocumentSchema acousticness) {
        this.acousticness = acousticness;
    }

    @DynamoDBAttribute(attributeName = "danceability")
    public TrackFeatureAnalyticsDocumentSchema getDanceability() {
        return danceability;
    }

    public void setDanceability(TrackFeatureAnalyticsDocumentSchema danceability) {
        this.danceability = danceability;
    }

    @DynamoDBAttribute(attributeName = "energy")
    public TrackFeatureAnalyticsDocumentSchema getEnergy() {
        return energy;
    }

    public void setEnergy(TrackFeatureAnalyticsDocumentSchema energy) {
        this.energy = energy;
    }

    @DynamoDBAttribute(attributeName = "instrumentalness")
    public TrackFeatureAnalyticsDocumentSchema getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(TrackFeatureAnalyticsDocumentSchema instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    @DynamoDBAttribute(attributeName = "liveness")
    public TrackFeatureAnalyticsDocumentSchema getLiveness() {
        return liveness;
    }

    public void setLiveness(TrackFeatureAnalyticsDocumentSchema liveness) {
        this.liveness = liveness;
    }

    @DynamoDBAttribute(attributeName = "loudness")
    public TrackFeatureAnalyticsDocumentSchema getLoudness() {
        return loudness;
    }

    public void setLoudness(TrackFeatureAnalyticsDocumentSchema loudness) {
        this.loudness = loudness;
    }

    @DynamoDBAttribute(attributeName = "speechiness")
    public TrackFeatureAnalyticsDocumentSchema getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(TrackFeatureAnalyticsDocumentSchema speechiness) {
        this.speechiness = speechiness;
    }

    @DynamoDBAttribute(attributeName = "valence")
    public TrackFeatureAnalyticsDocumentSchema getValence() {
        return valence;
    }

    public void setValence(TrackFeatureAnalyticsDocumentSchema valence) {
        this.valence = valence;
    }

    @DynamoDBAttribute(attributeName = "duration")
    public TrackFeatureAnalyticsDocumentSchema getDuration() {
        return duration;
    }

    public void setDuration(TrackFeatureAnalyticsDocumentSchema duration) {
        this.duration = duration;
    }

    @DynamoDBAttribute(attributeName = "tempo")
    public TrackFeatureAnalyticsDocumentSchema getTempo() {
        return tempo;
    }

    public void setTempo(TrackFeatureAnalyticsDocumentSchema tempo) {
        this.tempo = tempo;
    }

    private static final String RANGE_KEY_DELIMITER = "_";

    public TracksAnalyticsItem toDomain() {
        return new TracksAnalyticsItem(
                this.getLocation(),
                this.getRowTime(),
                this.getTimestamp(),
                this.getTracksCount(),
                this.getAcousticness(),
                this.getDanceability(),
                this.getEnergy(),
                this.getInstrumentalness(),
                this.getLiveness(),
                this.getLoudness(),
                this.getSpeechiness(),
                this.getValence(),
                this.getDuration(),
                this.getTempo()
        );
    }

    public static TracksAnalyticsItemSchema partitionKey(String location) {
        return new TracksAnalyticsItemSchema(
                location
        );
    }

    public static TracksAnalyticsItemSchema fromDomain(TracksAnalyticsItem domain) {
        return new TracksAnalyticsItemSchema(
                domain.getLocation(),
                domain.getRowTime(),
                new LocalDateTimeTypeConverter().convert(domain.getRowTime())
                        + RANGE_KEY_DELIMITER
                        + new LocalDateTimeTypeConverter().convert(domain.getTimestamp()),
                domain.getTimestamp(),
                domain.getTracksCount(),
                domain.getAcousticness(),
                domain.getDanceability(),
                domain.getEnergy(),
                domain.getInstrumentalness(),
                domain.getLiveness(),
                domain.getLoudness(),
                domain.getSpeechiness(),
                domain.getValence(),
                domain.getDuration(),
                domain.getTempo()
        );
    }
}

package ai.exemplar.persistence.dynamodb.schema.analytics;

import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "TracksBasicAnalytics")
public class TracksAnalyticsItemSchema {

    private String location;

    private LocalDateTime rowTime;

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

    public TracksAnalyticsItemSchema(String location, LocalDateTime rowTime, Integer tracksCount, TrackFeatureAnalyticsDocumentSchema acousticness, TrackFeatureAnalyticsDocumentSchema danceability, TrackFeatureAnalyticsDocumentSchema energy, TrackFeatureAnalyticsDocumentSchema instrumentalness, TrackFeatureAnalyticsDocumentSchema liveness, TrackFeatureAnalyticsDocumentSchema loudness, TrackFeatureAnalyticsDocumentSchema speechiness, TrackFeatureAnalyticsDocumentSchema valence, TrackFeatureAnalyticsDocumentSchema duration, TrackFeatureAnalyticsDocumentSchema tempo) {
        this.location = location;
        this.rowTime = rowTime;
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
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBRangeKey(attributeName = "rowTime")
    public LocalDateTime getRowTime() {
        return rowTime;
    }

    public void setRowTime(LocalDateTime rowTime) {
        this.rowTime = rowTime;
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

    public static TracksAnalyticsItemSchema partitionKey(String location) {
        return new TracksAnalyticsItemSchema(
                location
        );
    }
}

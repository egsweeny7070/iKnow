package ai.exemplar.persistence.model;

import ai.exemplar.persistence.dynamodb.schema.analytics.TrackFeatureAnalyticsDocumentSchema;

import java.time.LocalDateTime;

public class TracksAnalyticsItem {

    private String location;

    private LocalDateTime rowTime;

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

    public TracksAnalyticsItem(String location, LocalDateTime rowTime, LocalDateTime timestamp, Integer tracksCount, TrackFeatureAnalyticsDocumentSchema acousticness, TrackFeatureAnalyticsDocumentSchema danceability, TrackFeatureAnalyticsDocumentSchema energy, TrackFeatureAnalyticsDocumentSchema instrumentalness, TrackFeatureAnalyticsDocumentSchema liveness, TrackFeatureAnalyticsDocumentSchema loudness, TrackFeatureAnalyticsDocumentSchema speechiness, TrackFeatureAnalyticsDocumentSchema valence, TrackFeatureAnalyticsDocumentSchema duration, TrackFeatureAnalyticsDocumentSchema tempo) {
        this.location = location;
        this.rowTime = rowTime;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getRowTime() {
        return rowTime;
    }

    public void setRowTime(LocalDateTime rowTime) {
        this.rowTime = rowTime;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTracksCount() {
        return tracksCount;
    }

    public void setTracksCount(Integer tracksCount) {
        this.tracksCount = tracksCount;
    }

    public TrackFeatureAnalyticsDocumentSchema getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(TrackFeatureAnalyticsDocumentSchema acousticness) {
        this.acousticness = acousticness;
    }

    public TrackFeatureAnalyticsDocumentSchema getDanceability() {
        return danceability;
    }

    public void setDanceability(TrackFeatureAnalyticsDocumentSchema danceability) {
        this.danceability = danceability;
    }

    public TrackFeatureAnalyticsDocumentSchema getEnergy() {
        return energy;
    }

    public void setEnergy(TrackFeatureAnalyticsDocumentSchema energy) {
        this.energy = energy;
    }

    public TrackFeatureAnalyticsDocumentSchema getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(TrackFeatureAnalyticsDocumentSchema instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public TrackFeatureAnalyticsDocumentSchema getLiveness() {
        return liveness;
    }

    public void setLiveness(TrackFeatureAnalyticsDocumentSchema liveness) {
        this.liveness = liveness;
    }

    public TrackFeatureAnalyticsDocumentSchema getLoudness() {
        return loudness;
    }

    public void setLoudness(TrackFeatureAnalyticsDocumentSchema loudness) {
        this.loudness = loudness;
    }

    public TrackFeatureAnalyticsDocumentSchema getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(TrackFeatureAnalyticsDocumentSchema speechiness) {
        this.speechiness = speechiness;
    }

    public TrackFeatureAnalyticsDocumentSchema getValence() {
        return valence;
    }

    public void setValence(TrackFeatureAnalyticsDocumentSchema valence) {
        this.valence = valence;
    }

    public TrackFeatureAnalyticsDocumentSchema getDuration() {
        return duration;
    }

    public void setDuration(TrackFeatureAnalyticsDocumentSchema duration) {
        this.duration = duration;
    }

    public TrackFeatureAnalyticsDocumentSchema getTempo() {
        return tempo;
    }

    public void setTempo(TrackFeatureAnalyticsDocumentSchema tempo) {
        this.tempo = tempo;
    }
}

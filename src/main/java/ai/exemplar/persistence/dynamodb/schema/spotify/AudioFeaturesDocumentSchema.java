package ai.exemplar.persistence.dynamodb.schema.spotify;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class AudioFeaturesDocumentSchema {

    private Integer duration;

    private Integer mode;

    private Integer key;

    private Integer timeSignature;

    private Double tempo;

    private Double acousticness;

    private Double danceability;

    private Double energy;

    private Double instrumentalness;

    private Double liveness;

    private Double loudness;

    private Double speechiness;

    private Double valence;

    public AudioFeaturesDocumentSchema() {
    }

    public AudioFeaturesDocumentSchema(Integer duration, Integer mode, Integer key, Integer timeSignature, Double tempo, Double acousticness, Double danceability, Double energy, Double instrumentalness, Double liveness, Double loudness, Double speechiness, Double valence) {
        this.duration = duration;
        this.mode = mode;
        this.key = key;
        this.timeSignature = timeSignature;
        this.tempo = tempo;
        this.acousticness = acousticness;
        this.danceability = danceability;
        this.energy = energy;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.loudness = loudness;
        this.speechiness = speechiness;
        this.valence = valence;
    }

    @DynamoDBAttribute(attributeName = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @DynamoDBAttribute(attributeName = "mode")
    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @DynamoDBAttribute(attributeName = "key")
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    @DynamoDBAttribute(attributeName = "timeSignature")
    public Integer getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(Integer timeSignature) {
        this.timeSignature = timeSignature;
    }

    @DynamoDBAttribute(attributeName = "tempo")
    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    @DynamoDBAttribute(attributeName = "acousticness")
    public Double getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(Double acousticness) {
        this.acousticness = acousticness;
    }

    @DynamoDBAttribute(attributeName = "danceability")
    public Double getDanceability() {
        return danceability;
    }

    public void setDanceability(Double danceability) {
        this.danceability = danceability;
    }

    @DynamoDBAttribute(attributeName = "energy")
    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    @DynamoDBAttribute(attributeName = "instrumentalness")
    public Double getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(Double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    @DynamoDBAttribute(attributeName = "liveness")
    public Double getLiveness() {
        return liveness;
    }

    public void setLiveness(Double liveness) {
        this.liveness = liveness;
    }

    @DynamoDBAttribute(attributeName = "loudness")
    public Double getLoudness() {
        return loudness;
    }

    public void setLoudness(Double loudness) {
        this.loudness = loudness;
    }

    @DynamoDBAttribute(attributeName = "speechiness")
    public Double getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(Double speechiness) {
        this.speechiness = speechiness;
    }

    @DynamoDBAttribute(attributeName = "valence")
    public Double getValence() {
        return valence;
    }

    public void setValence(Double valence) {
        this.valence = valence;
    }
}

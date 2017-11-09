package ai.exemplar.storage.values;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "locationId",
        "danceability", "speechiness", "instrumentalness",
        "explicitLyrics", "popularity", "duration",
        "loudness", "tempo", "acousticness", "energy",
        "valence", "mode"
})
public class LocationCorrelationsEntry {

    private String locationId;

    private Double danceability;

    private Double speechiness;

    private Double instrumentalness;

    private Double explicitLyrics;

    private Double popularity;

    private Double duration;

    private Double loudness;

    private Double tempo;

    private Double acousticness;

    private Double energy;

    private Double valence;

    private Double mode;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Double getDanceability() {
        return danceability;
    }

    public void setDanceability(Double danceability) {
        this.danceability = danceability;
    }

    public Double getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(Double speechiness) {
        this.speechiness = speechiness;
    }

    public Double getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(Double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public Double getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Double explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getLoudness() {
        return loudness;
    }

    public void setLoudness(Double loudness) {
        this.loudness = loudness;
    }

    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    public Double getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(Double acousticness) {
        this.acousticness = acousticness;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getValence() {
        return valence;
    }

    public void setValence(Double valence) {
        this.valence = valence;
    }

    public Double getMode() {
        return mode;
    }

    public void setMode(Double mode) {
        this.mode = mode;
    }
}

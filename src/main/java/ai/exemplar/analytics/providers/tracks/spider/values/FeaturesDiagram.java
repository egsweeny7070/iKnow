package ai.exemplar.analytics.providers.tracks.spider.values;

public class FeaturesDiagram {

    public FeaturesDiagramItem acousticness;

    private FeaturesDiagramItem danceability;

    private FeaturesDiagramItem energy;

    private FeaturesDiagramItem instrumentalness;

    private FeaturesDiagramItem liveness;

    private FeaturesDiagramItem speechiness;

    private FeaturesDiagramItem valence;

    public FeaturesDiagram(FeaturesDiagramItem acousticness, FeaturesDiagramItem danceability, FeaturesDiagramItem energy, FeaturesDiagramItem instrumentalness, FeaturesDiagramItem liveness, FeaturesDiagramItem speechiness, FeaturesDiagramItem valence) {
        this.acousticness = acousticness;
        this.danceability = danceability;
        this.energy = energy;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.speechiness = speechiness;
        this.valence = valence;
    }

    public FeaturesDiagramItem getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(FeaturesDiagramItem acousticness) {
        this.acousticness = acousticness;
    }

    public FeaturesDiagramItem getDanceability() {
        return danceability;
    }

    public void setDanceability(FeaturesDiagramItem danceability) {
        this.danceability = danceability;
    }

    public FeaturesDiagramItem getEnergy() {
        return energy;
    }

    public void setEnergy(FeaturesDiagramItem energy) {
        this.energy = energy;
    }

    public FeaturesDiagramItem getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(FeaturesDiagramItem instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public FeaturesDiagramItem getLiveness() {
        return liveness;
    }

    public void setLiveness(FeaturesDiagramItem liveness) {
        this.liveness = liveness;
    }

    public FeaturesDiagramItem getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(FeaturesDiagramItem speechiness) {
        this.speechiness = speechiness;
    }

    public FeaturesDiagramItem getValence() {
        return valence;
    }

    public void setValence(FeaturesDiagramItem valence) {
        this.valence = valence;
    }
}

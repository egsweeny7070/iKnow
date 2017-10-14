package ai.exemplar.analytics.providers.tracks.histogram.values;

public class MainFeatures {

    private FeaturesHistogramItem loudness;

    private FeaturesHistogramItem duration;

    private FeaturesHistogramItem tempo;

    public MainFeatures(FeaturesHistogramItem loudness, FeaturesHistogramItem duration, FeaturesHistogramItem tempo) {
        this.loudness = loudness;
        this.duration = duration;
        this.tempo = tempo;
    }

    public FeaturesHistogramItem getLoudness() {
        return loudness;
    }

    public void setLoudness(FeaturesHistogramItem loudness) {
        this.loudness = loudness;
    }

    public FeaturesHistogramItem getDuration() {
        return duration;
    }

    public void setDuration(FeaturesHistogramItem duration) {
        this.duration = duration;
    }

    public FeaturesHistogramItem getTempo() {
        return tempo;
    }

    public void setTempo(FeaturesHistogramItem tempo) {
        this.tempo = tempo;
    }
}

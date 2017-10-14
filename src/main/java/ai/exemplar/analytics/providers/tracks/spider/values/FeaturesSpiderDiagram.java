package ai.exemplar.analytics.providers.tracks.spider.values;

public class FeaturesSpiderDiagram {

    private FeaturesDiagram morning;

    private FeaturesDiagram afternoon;

    public FeaturesSpiderDiagram(FeaturesDiagram morning, FeaturesDiagram afternoon) {
        this.morning = morning;
        this.afternoon = afternoon;
    }

    public FeaturesDiagram getMorning() {
        return morning;
    }

    public void setMorning(FeaturesDiagram morning) {
        this.morning = morning;
    }

    public FeaturesDiagram getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(FeaturesDiagram afternoon) {
        this.afternoon = afternoon;
    }
}

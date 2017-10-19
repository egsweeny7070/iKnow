package ai.exemplar.analytics.providers.tracks.spider.values;

public class FeaturesSpiderDiagram {

    private FeaturesDiagram general;

    private FeaturesDiagram morning;

    private FeaturesDiagram afternoon;

    public FeaturesSpiderDiagram(FeaturesDiagram general, FeaturesDiagram morning, FeaturesDiagram afternoon) {
        this.general = general;
        this.morning = morning;
        this.afternoon = afternoon;
    }

    public FeaturesDiagram getGeneral() {
        return general;
    }

    public void setGeneral(FeaturesDiagram general) {
        this.general = general;
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

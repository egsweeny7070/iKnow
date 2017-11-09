package ai.exemplar.analytics.providers.correlations.values;

public class CorrelationValue {

    private String featureName;

    private Double correlationValue;

    public CorrelationValue(String featureName, Double correlationValue) {
        this.featureName = featureName;
        this.correlationValue = correlationValue;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Double getCorrelationValue() {
        return correlationValue;
    }

    public void setCorrelationValue(Double correlationValue) {
        this.correlationValue = correlationValue;
    }
}

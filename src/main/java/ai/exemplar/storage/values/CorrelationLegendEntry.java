package ai.exemplar.storage.values;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "featureName",
        "include",
        "canonicalName"
})
public class CorrelationLegendEntry {

    private String featureName;

    private Boolean include;

    private String canonicalName;

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Boolean getInclude() {
        return include;
    }

    public void setInclude(Boolean include) {
        this.include = include;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }
}

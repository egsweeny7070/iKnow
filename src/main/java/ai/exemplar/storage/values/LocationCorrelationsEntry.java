package ai.exemplar.storage.values;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "locationId",
        "featureName",
        "correlationValue"
})
public class LocationCorrelationsEntry {

    private String locationId;

    private String featureName;

    private Double correlationValue;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
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

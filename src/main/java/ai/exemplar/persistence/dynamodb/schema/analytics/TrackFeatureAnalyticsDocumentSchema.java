package ai.exemplar.persistence.dynamodb.schema.analytics;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TrackFeatureAnalyticsDocumentSchema {

    private Double min;

    private Double max;

    private Double sum;

    private Double std;

    public TrackFeatureAnalyticsDocumentSchema() {
    }

    public TrackFeatureAnalyticsDocumentSchema(Double min, Double max, Double sum, Double std) {
        this.min = min;
        this.max = max;
        this.sum = sum;
        this.std = std;
    }

    @DynamoDBAttribute(attributeName = "min")
    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    @DynamoDBAttribute(attributeName = "max")
    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    @DynamoDBAttribute(attributeName = "sum")
    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    @DynamoDBAttribute(attributeName = "std")
    public Double getStd() {
        return std;
    }

    public void setStd(Double std) {
        this.std = std;
    }
}

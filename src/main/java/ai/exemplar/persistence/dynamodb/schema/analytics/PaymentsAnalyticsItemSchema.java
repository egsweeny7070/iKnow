package ai.exemplar.persistence.dynamodb.schema.analytics;

import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "PaymentsBasicAnalytics")
public class PaymentsAnalyticsItemSchema {

    private String location;

    private LocalDateTime rowTime;

    private Integer totalPaymentsCount;

    private Integer totalItemsCount;

    private Double totalDiscountPercent;

    private Double collectedAmount;

    public PaymentsAnalyticsItemSchema() {
    }

    public PaymentsAnalyticsItemSchema(String location, LocalDateTime rowTime, Integer totalPaymentsCount, Integer totalItemsCount, Double totalDiscountPercent, Double collectedAmount) {
        this.location = location;
        this.rowTime = rowTime;
        this.totalPaymentsCount = totalPaymentsCount;
        this.totalItemsCount = totalItemsCount;
        this.totalDiscountPercent = totalDiscountPercent;
        this.collectedAmount = collectedAmount;
    }

    private PaymentsAnalyticsItemSchema(String location) {
        this.location = location;
    }

    @DynamoDBHashKey(attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBRangeKey(attributeName = "rowTime")
    public LocalDateTime getRowTime() {
        return rowTime;
    }

    public void setRowTime(LocalDateTime rowTime) {
        this.rowTime = rowTime;
    }

    @DynamoDBAttribute(attributeName = "totalPaymentsCount")
    public Integer getTotalPaymentsCount() {
        return totalPaymentsCount;
    }

    public void setTotalPaymentsCount(Integer totalPaymentsCount) {
        this.totalPaymentsCount = totalPaymentsCount;
    }

    @DynamoDBAttribute(attributeName = "totalItemsCount")
    public Integer getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(Integer totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    @DynamoDBAttribute(attributeName = "totalDiscountPercent")
    public Double getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(Double totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    @DynamoDBAttribute(attributeName = "collectedAmount")
    public Double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public static PaymentsAnalyticsItemSchema partitionKey(String location) {
        return new PaymentsAnalyticsItemSchema(
                location
        );
    }
}

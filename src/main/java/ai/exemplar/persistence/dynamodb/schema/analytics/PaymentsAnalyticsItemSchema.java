package ai.exemplar.persistence.dynamodb.schema.analytics;

import ai.exemplar.persistence.model.PaymentsAnalyticsItem;
import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "PaymentsBasicAnalytics")
public class PaymentsAnalyticsItemSchema {

    private String location;

    private LocalDateTime rowTime;

    private String rangeKey;

    private LocalDateTime timestamp;

    private Integer totalPaymentsCount;

    private Integer totalItemsCount;

    private Double totalDiscountPercent;

    private Double collectedAmount;

    public PaymentsAnalyticsItemSchema() {
    }

    public PaymentsAnalyticsItemSchema(String location, LocalDateTime rowTime, String rangeKey, LocalDateTime timestamp, Integer totalPaymentsCount, Integer totalItemsCount, Double totalDiscountPercent, Double collectedAmount) {
        this.location = location;
        this.rowTime = rowTime;
        this.rangeKey = rangeKey;
        this.timestamp = timestamp;
        this.totalPaymentsCount = totalPaymentsCount;
        this.totalItemsCount = totalItemsCount;
        this.totalDiscountPercent = totalDiscountPercent;
        this.collectedAmount = collectedAmount;
    }

    private PaymentsAnalyticsItemSchema(String location) {
        this.location = location;
    }

    @DynamoDBHashKey(attributeName = "location")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "LocationPaymentsIndex", attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBAttribute(attributeName = "rowTime")
    public LocalDateTime getRowTime() {
        return rowTime;
    }

    public void setRowTime(LocalDateTime rowTime) {
        this.rowTime = rowTime;
    }

    @DynamoDBRangeKey(attributeName = "rangeKey")
    public String getRangeKey() {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey) {
        this.rangeKey = rangeKey;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "LocationPaymentsIndex", attributeName = "timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    private static final String RANGE_KEY_DELIMITER = "_";

    public PaymentsAnalyticsItem toDomain() {
        return new PaymentsAnalyticsItem(
                this.getLocation(),
                this.getRowTime(),
                this.getTimestamp(),
                this.getTotalPaymentsCount(),
                this.getTotalItemsCount(),
                this.getTotalDiscountPercent(),
                this.getCollectedAmount()
        );
    }

    public static PaymentsAnalyticsItemSchema partitionKey(String location) {
        return new PaymentsAnalyticsItemSchema(
                location
        );
    }

    public static PaymentsAnalyticsItemSchema fromDomain(PaymentsAnalyticsItem domain) {
        return new PaymentsAnalyticsItemSchema(
                domain.getLocation(),
                domain.getRowTime(),
                new LocalDateTimeTypeConverter().convert(domain.getRowTime())
                        + RANGE_KEY_DELIMITER
                        + new LocalDateTimeTypeConverter().convert(domain.getTimestamp()),
                domain.getTimestamp(),
                domain.getTotalPaymentsCount(),
                domain.getTotalItemsCount(),
                domain.getTotalDiscountPercent(),
                domain.getCollectedAmount()
        );
    }
}

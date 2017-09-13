package ai.exemplar.persistence.dynamodb.schema.square;

import ai.exemplar.persistence.model.SquarePayment;
import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;
import java.util.List;

@DynamoDBTable(tableName = "SquarePaymentHistory")
public class PaymentSchema {

    private static final String RANGE_KEY_DELIMITER = "$";

    private String location;

    private String id;

    private String device;

    private String url;

    private Double tax;

    private Double tip;

    private Double discount;

    private Double collected;

    private Double fee;

    private Double refunded;

    private List<TenderDocumentSchema> tenders;

    private List<ItemDocumentSchema> items;

    public PaymentSchema() {
    }

    public PaymentSchema(String location, String id, String device, String url, Double tax, Double tip, Double discount, Double collected, Double fee, Double refunded, List<TenderDocumentSchema> tenders, List<ItemDocumentSchema> items) {
        this.location = location;
        this.id = id;
        this.device = device;
        this.url = url;
        this.tax = tax;
        this.tip = tip;
        this.discount = discount;
        this.collected = collected;
        this.fee = fee;
        this.refunded = refunded;
        this.tenders = tenders;
        this.items = items;
    }

    @DynamoDBHashKey(attributeName = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBRangeKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "device")
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @DynamoDBAttribute(attributeName = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DynamoDBAttribute(attributeName = "tax")
    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @DynamoDBAttribute(attributeName = "tip")
    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    @DynamoDBAttribute(attributeName = "discount")
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @DynamoDBAttribute(attributeName = "collected")
    public Double getCollected() {
        return collected;
    }

    public void setCollected(Double collected) {
        this.collected = collected;
    }

    @DynamoDBAttribute(attributeName = "fee")
    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @DynamoDBAttribute(attributeName = "refunded")
    public Double getRefunded() {
        return refunded;
    }

    public void setRefunded(Double refunded) {
        this.refunded = refunded;
    }

    @DynamoDBAttribute(attributeName = "tenders")
    public List<TenderDocumentSchema> getTenders() {
        return tenders;
    }

    public void setTenders(List<TenderDocumentSchema> tenders) {
        this.tenders = tenders;
    }

    @DynamoDBAttribute(attributeName = "items")
    public List<ItemDocumentSchema> getItems() {
        return items;
    }

    public void setItems(List<ItemDocumentSchema> items) {
        this.items = items;
    }

    public static PaymentSchema fromModel(SquarePayment payment) {
        return new PaymentSchema(
                payment.getLocation(),
                new LocalDateTimeTypeConverter().convert(payment.getTimestamp())
                        + RANGE_KEY_DELIMITER
                        + payment.getId(),
                payment.getDevice(),
                payment.getUrl(),
                payment.getTax(),
                payment.getTip(),
                payment.getDiscount(),
                payment.getCollected(),
                payment.getFee(),
                payment.getRefunded(),
                payment.getTenders(),
                payment.getItems()
        );
    }
}

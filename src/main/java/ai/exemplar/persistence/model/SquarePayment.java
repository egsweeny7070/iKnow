package ai.exemplar.persistence.model;

import ai.exemplar.persistence.dynamodb.schema.square.ItemDocumentSchema;
import ai.exemplar.persistence.dynamodb.schema.square.TenderDocumentSchema;

import java.time.LocalDateTime;
import java.util.List;

public class SquarePayment {

    private String location;

    private String id;

    private LocalDateTime timestamp;

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

    public SquarePayment(String location, String id, LocalDateTime timestamp, String device, String url, Double tax, Double tip, Double discount, Double collected, Double fee, Double refunded, List<TenderDocumentSchema> tenders, List<ItemDocumentSchema> items) {
        this.location = location;
        this.id = id;
        this.timestamp = timestamp;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getCollected() {
        return collected;
    }

    public void setCollected(Double collected) {
        this.collected = collected;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getRefunded() {
        return refunded;
    }

    public void setRefunded(Double refunded) {
        this.refunded = refunded;
    }

    public List<TenderDocumentSchema> getTenders() {
        return tenders;
    }

    public void setTenders(List<TenderDocumentSchema> tenders) {
        this.tenders = tenders;
    }

    public List<ItemDocumentSchema> getItems() {
        return items;
    }

    public void setItems(List<ItemDocumentSchema> items) {
        this.items = items;
    }
}

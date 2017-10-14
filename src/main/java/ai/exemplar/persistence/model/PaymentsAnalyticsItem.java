package ai.exemplar.persistence.model;

import java.time.LocalDateTime;

public class PaymentsAnalyticsItem {

    private String location;

    private LocalDateTime rowTime;

    private LocalDateTime timestamp;

    private Integer totalPaymentsCount;

    private Integer totalItemsCount;

    private Double totalDiscountPercent;

    private Double collectedAmount;

    public PaymentsAnalyticsItem(String location, LocalDateTime rowTime, LocalDateTime timestamp, Integer totalPaymentsCount, Integer totalItemsCount, Double totalDiscountPercent, Double collectedAmount) {
        this.location = location;
        this.rowTime = rowTime;
        this.timestamp = timestamp;
        this.totalPaymentsCount = totalPaymentsCount;
        this.totalItemsCount = totalItemsCount;
        this.totalDiscountPercent = totalDiscountPercent;
        this.collectedAmount = collectedAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getRowTime() {
        return rowTime;
    }

    public void setRowTime(LocalDateTime rowTime) {
        this.rowTime = rowTime;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTotalPaymentsCount() {
        return totalPaymentsCount;
    }

    public void setTotalPaymentsCount(Integer totalPaymentsCount) {
        this.totalPaymentsCount = totalPaymentsCount;
    }

    public Integer getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(Integer totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public Double getTotalDiscountPercent() {
        return totalDiscountPercent;
    }

    public void setTotalDiscountPercent(Double totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public Double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }
}

package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Refund {

    private String type;

    private String reason;

    @SerializedName("refunded_money")
    private Money refunded;

    @SerializedName("created_at")
    private LocalDateTime created;

    @SerializedName("processed_at")
    private LocalDateTime processed;

    @SerializedName("payment_id")
    private String paymentId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Money getRefunded() {
        return refunded;
    }

    public void setRefunded(Money refunded) {
        this.refunded = refunded;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getProcessed() {
        return processed;
    }

    public void setProcessed(LocalDateTime processed) {
        this.processed = processed;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}

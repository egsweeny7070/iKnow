package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

public class PaymentTax {

    private String name;

    @SerializedName("applied_money")
    private Money appliedMoney;

    private String rate;

    @SerializedName("inclusion_type")
    private String type;

    @SerializedName("fee_id")
    private String feeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getAppliedMoney() {
        return appliedMoney;
    }

    public void setAppliedMoney(Money appliedMoney) {
        this.appliedMoney = appliedMoney;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }
}

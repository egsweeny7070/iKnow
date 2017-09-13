package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

public class PaymentDiscount {

    private String name;

    @SerializedName("applied_money")
    private Money applied;

    @SerializedName("discount_id")
    private String discount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getApplied() {
        return applied;
    }

    public void setApplied(Money applied) {
        this.applied = applied;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}

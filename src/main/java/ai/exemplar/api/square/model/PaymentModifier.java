package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

public class PaymentModifier {

    private String name;

    @SerializedName("applied_money")
    private Money applied;

    @SerializedName("modifier_option_id")
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

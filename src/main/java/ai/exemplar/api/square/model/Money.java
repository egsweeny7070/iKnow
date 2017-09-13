package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

import java.util.Currency;

public class Money {

    private Integer amount;

    @SerializedName("currency_code")
    private String currencyCode;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double value() {
        return amount.doubleValue() /
                Math.pow(10, Currency.getInstance(getCurrencyCode())
                        .getDefaultFractionDigits());
    }
}

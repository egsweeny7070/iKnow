package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentItemization {

    private String name;

    private Double quantity;

    @SerializedName("itemization_type")
    private String itemization;

    @SerializedName("item_detail")
    private PaymentItemDetail details;

    private String notes;

    @SerializedName("item_variation_name")
    private String itemVariation;

    @SerializedName("total_money")
    private Money total;

    @SerializedName("single_quantity_money")
    private Money singleQuantity;

    @SerializedName("gross_sales_money")
    private Money grossSales;

    @SerializedName("discount_money")
    private Money discount;

    @SerializedName("net_sales_money")
    private Money netSales;

    private List<PaymentTax> taxes;

    private List<PaymentDiscount> discounts;

    private List<PaymentModifier> modifiers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getItemization() {
        return itemization;
    }

    public void setItemization(String itemization) {
        this.itemization = itemization;
    }

    public PaymentItemDetail getDetails() {
        return details;
    }

    public void setDetails(PaymentItemDetail details) {
        this.details = details;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getItemVariation() {
        return itemVariation;
    }

    public void setItemVariation(String itemVariation) {
        this.itemVariation = itemVariation;
    }

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public Money getSingleQuantity() {
        return singleQuantity;
    }

    public void setSingleQuantity(Money singleQuantity) {
        this.singleQuantity = singleQuantity;
    }

    public Money getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(Money grossSales) {
        this.grossSales = grossSales;
    }

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        this.discount = discount;
    }

    public Money getNetSales() {
        return netSales;
    }

    public void setNetSales(Money netSales) {
        this.netSales = netSales;
    }

    public List<PaymentTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<PaymentTax> taxes) {
        this.taxes = taxes;
    }

    public List<PaymentDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<PaymentDiscount> discounts) {
        this.discounts = discounts;
    }

    public List<PaymentModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<PaymentModifier> modifiers) {
        this.modifiers = modifiers;
    }
}

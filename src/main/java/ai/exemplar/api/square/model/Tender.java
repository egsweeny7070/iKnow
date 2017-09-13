package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

public class Tender {

    private String id;

    private String type;

    private String name;

    @SerializedName("employee_id")
    private String employee;

    @SerializedName("receipt_url")
    private String receipt;

    @SerializedName("card_brand")
    private String cardBrand;

    @SerializedName("pan_suffix")
    private String panSuffix;

    @SerializedName("entry_method")
    private String entryMethod;

    @SerializedName("payment_note")
    private String note;

    @SerializedName("total_money")
    private Money total;

    @SerializedName("tendered_money")
    private Money tendered;

    @SerializedName("change_back_money")
    private Money changeBack;

    @SerializedName("refunded_money")
    private Money refunded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getPanSuffix() {
        return panSuffix;
    }

    public void setPanSuffix(String panSuffix) {
        this.panSuffix = panSuffix;
    }

    public String getEntryMethod() {
        return entryMethod;
    }

    public void setEntryMethod(String entryMethod) {
        this.entryMethod = entryMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public Money getTendered() {
        return tendered;
    }

    public void setTendered(Money tendered) {
        this.tendered = tendered;
    }

    public Money getChangeBack() {
        return changeBack;
    }

    public void setChangeBack(Money changeBack) {
        this.changeBack = changeBack;
    }

    public Money getRefunded() {
        return refunded;
    }

    public void setRefunded(Money refunded) {
        this.refunded = refunded;
    }
}

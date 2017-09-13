package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Payment {

    private String id;

    @SerializedName("merchant_id")
    private String merchant;

    @SerializedName("created_at")
    private LocalDateTime created;

    @SerializedName("creator_id")
    private String creatorId;

    private Map<String, String> device;

    @SerializedName("payment_url")
    private String url;

    @SerializedName("receipt_url")
    private String receipt;

    @SerializedName("inclusive_tax_money")
    private Money inclusiveTax;

    @SerializedName("additive_tax_money")
    private Money additiveTax;

    @SerializedName("tax_money")
    private Money tax;

    @SerializedName("tip_money")
    private Money tip;

    @SerializedName("discount_money")
    private Money discount;

    @SerializedName("total_collected_money")
    private Money totalCollected;

    @SerializedName("processing_fee_money")
    private Money processingFee;

    @SerializedName("net_total_money")
    private Money netTotal;

    @SerializedName("refunded_money")
    private Money refunded;

    @SerializedName("inclusive_tax")
    private List<PaymentTax> inclusiveTaxes;

    @SerializedName("additive_tax")
    private List<PaymentTax> additiveTaxes;

    private List<Tender> tender;

    private List<Refund> refunds;

    private List<PaymentItemization> itemizations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Map<String, String> getDevice() {
        return device;
    }

    public void setDevice(Map<String, String> device) {
        this.device = device;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Money getInclusiveTax() {
        return inclusiveTax;
    }

    public void setInclusiveTax(Money inclusiveTax) {
        this.inclusiveTax = inclusiveTax;
    }

    public Money getAdditiveTax() {
        return additiveTax;
    }

    public void setAdditiveTax(Money additiveTax) {
        this.additiveTax = additiveTax;
    }

    public Money getTax() {
        return tax;
    }

    public void setTax(Money tax) {
        this.tax = tax;
    }

    public Money getTip() {
        return tip;
    }

    public void setTip(Money tip) {
        this.tip = tip;
    }

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        this.discount = discount;
    }

    public Money getTotalCollected() {
        return totalCollected;
    }

    public void setTotalCollected(Money totalCollected) {
        this.totalCollected = totalCollected;
    }

    public Money getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(Money processingFee) {
        this.processingFee = processingFee;
    }

    public Money getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Money netTotal) {
        this.netTotal = netTotal;
    }

    public Money getRefunded() {
        return refunded;
    }

    public void setRefunded(Money refunded) {
        this.refunded = refunded;
    }

    public List<PaymentTax> getInclusiveTaxes() {
        return inclusiveTaxes;
    }

    public void setInclusiveTaxes(List<PaymentTax> inclusiveTaxes) {
        this.inclusiveTaxes = inclusiveTaxes;
    }

    public List<PaymentTax> getAdditiveTaxes() {
        return additiveTaxes;
    }

    public void setAdditiveTaxes(List<PaymentTax> additiveTaxes) {
        this.additiveTaxes = additiveTaxes;
    }

    public List<Tender> getTender() {
        return tender;
    }

    public void setTender(List<Tender> tender) {
        this.tender = tender;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public List<PaymentItemization> getItemizations() {
        return itemizations;
    }

    public void setItemizations(List<PaymentItemization> itemizations) {
        this.itemizations = itemizations;
    }

    public String deviceName() {
        return Optional.ofNullable(getDevice()).map(device ->
                device.get("name")).orElse(null);
    }
}

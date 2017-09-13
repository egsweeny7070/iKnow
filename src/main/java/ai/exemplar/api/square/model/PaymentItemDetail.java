package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

public class PaymentItemDetail {

    @SerializedName("category_name")
    private String category;

    private String sku;

    @SerializedName("item_id")
    private String id;

    @SerializedName("item_variation_id")
    private String variationId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }
}

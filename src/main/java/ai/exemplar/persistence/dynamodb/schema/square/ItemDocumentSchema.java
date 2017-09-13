package ai.exemplar.persistence.dynamodb.schema.square;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import java.util.List;

@DynamoDBDocument
public class ItemDocumentSchema {

    private String name;

    private Double quantity;

    private String notes;

    private String variation;

    private String category;

    private String sku;

    private String id;

    private String variationId;

    private Double total;

    private Double singleQuantity;

    private Double grossSales;

    private Double netSales;

    private List<ModifierDocumentSchema> modifiers;

    public ItemDocumentSchema() {
    }

    public ItemDocumentSchema(String name, Double quantity, String notes, String variation, String category, String sku, String id, String variationId, Double total, Double singleQuantity, Double grossSales, Double netSales, List<ModifierDocumentSchema> modifiers) {
        this.name = name;
        this.quantity = quantity;
        this.notes = notes;
        this.variation = variation;
        this.category = category;
        this.sku = sku;
        this.id = id;
        this.variationId = variationId;
        this.total = total;
        this.singleQuantity = singleQuantity;
        this.grossSales = grossSales;
        this.netSales = netSales;
        this.modifiers = modifiers;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "quantity")
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @DynamoDBAttribute(attributeName = "variation")
    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    @DynamoDBAttribute(attributeName = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDBAttribute(attributeName = "sku")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "variationId")
    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    @DynamoDBAttribute(attributeName = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @DynamoDBAttribute(attributeName = "singleQuantity")
    public Double getSingleQuantity() {
        return singleQuantity;
    }

    public void setSingleQuantity(Double singleQuantity) {
        this.singleQuantity = singleQuantity;
    }

    @DynamoDBAttribute(attributeName = "grossSales")
    public Double getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(Double grossSales) {
        this.grossSales = grossSales;
    }

    @DynamoDBAttribute(attributeName = "netSales")
    public Double getNetSales() {
        return netSales;
    }

    public void setNetSales(Double netSales) {
        this.netSales = netSales;
    }

    @DynamoDBAttribute(attributeName = "modifiers")
    public List<ModifierDocumentSchema> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<ModifierDocumentSchema> modifiers) {
        this.modifiers = modifiers;
    }
}

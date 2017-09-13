package ai.exemplar.persistence.dynamodb.schema.square;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class TenderDocumentSchema {

    private String type;

    private Double total;

    private String cardBrand;

    private String panSuffix;

    private String entryMethod;

    public TenderDocumentSchema() {
    }

    public TenderDocumentSchema(String type, Double total, String cardBrand, String panSuffix, String entryMethod) {
        this.type = type;
        this.total = total;
        this.cardBrand = cardBrand;
        this.panSuffix = panSuffix;
        this.entryMethod = entryMethod;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @DynamoDBAttribute(attributeName = "cardBrand")
    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    @DynamoDBAttribute(attributeName = "panSuffix")
    public String getPanSuffix() {
        return panSuffix;
    }

    public void setPanSuffix(String panSuffix) {
        this.panSuffix = panSuffix;
    }

    @DynamoDBAttribute(attributeName = "entryMethod")
    public String getEntryMethod() {
        return entryMethod;
    }

    public void setEntryMethod(String entryMethod) {
        this.entryMethod = entryMethod;
    }
}

package ai.exemplar.persistence.dynamodb.schema.spotify;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class ImageDocumentSchema {

    private Integer height;

    private Integer width;

    private String url;

    public ImageDocumentSchema() {
    }

    public ImageDocumentSchema(Integer height, Integer width, String url) {
        this.height = height;
        this.width = width;
        this.url = url;
    }

    @DynamoDBAttribute(attributeName = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @DynamoDBAttribute(attributeName = "width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @DynamoDBAttribute(attributeName = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

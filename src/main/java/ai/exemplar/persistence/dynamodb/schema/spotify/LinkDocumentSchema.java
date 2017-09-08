package ai.exemplar.persistence.dynamodb.schema.spotify;

import ai.exemplar.utils.dynamodb.converters.StringMapTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.Map;

@DynamoDBDocument
public class LinkDocumentSchema {

    private Map<String, String> externalUrls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;

    public LinkDocumentSchema() {
    }

    public LinkDocumentSchema(Map<String, String> externalUrls, String href, String id, String name, String type, String uri) {
        this.externalUrls = externalUrls;
        this.href = href;
        this.id = id;
        this.name = name;
        this.type = type;
        this.uri = uri;
    }

    @DynamoDBTypeConverted(converter = StringMapTypeConverter.class)
    @DynamoDBAttribute(attributeName = "externalUrls")
    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }

    @DynamoDBAttribute(attributeName = "href")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "uri")
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

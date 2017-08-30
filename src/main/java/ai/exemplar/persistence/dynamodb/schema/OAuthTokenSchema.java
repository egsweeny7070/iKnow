package ai.exemplar.persistence.dynamodb.schema;

import ai.exemplar.persistence.model.OAuthToken;
import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "OAuthToken")
public class OAuthTokenSchema {

    private static final String PRIMARY_KEY_DELIMITER = "_";

    private String key;

    private String token;

    private String refreshToken;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime expiration;

    private String internalId;

    public OAuthTokenSchema() {
    }

    public OAuthTokenSchema(String key, String token, String refreshToken, LocalDateTime created, LocalDateTime updated, LocalDateTime expiration, String internalId) {
        this.key = key;
        this.token = token;
        this.refreshToken = refreshToken;
        this.created = created;
        this.updated = updated;
        this.expiration = expiration;
        this.internalId = internalId;
    }

    @DynamoDBHashKey(attributeName = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @DynamoDBAttribute(attributeName = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @DynamoDBAttribute(attributeName = "refreshToken")
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBAttribute(attributeName = "created")
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBAttribute(attributeName = "updated")
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBAttribute(attributeName = "expiration")
    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    @DynamoDBAttribute(attributeName = "internalId")
    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public static OAuthTokenSchema primaryKey(String username, String provider) {
        return new OAuthTokenSchema(
                username + PRIMARY_KEY_DELIMITER + provider,
                null, null, null, null, null, null
        );
    }

    public static OAuthTokenSchema fromModel(OAuthToken token) {
        return new OAuthTokenSchema(
                token.getUsername() + PRIMARY_KEY_DELIMITER + token.getProvider(),
                token.getToken(),
                token.getRefreshToken(),
                token.getCreated(),
                token.getUpdated(),
                token.getExpiration(),
                token.getInternalId()
        );
    }

    public OAuthToken toModel() {
        return new OAuthToken(
                this.getKey().split(PRIMARY_KEY_DELIMITER)[0],
                this.getKey().split(PRIMARY_KEY_DELIMITER)[1],
                this.getToken(),
                this.getRefreshToken(),
                this.getCreated(),
                this.getUpdated(),
                this.getExpiration(),
                this.getInternalId()
        );
    }
}

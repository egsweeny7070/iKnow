package ai.exemplar.persistence.dynamodb.schema.square;

import ai.exemplar.utils.dynamodb.converters.LocalDateTimeTypeConverter;
import ai.exemplar.utils.dynamodb.converters.StringListTypeConverter;
import ai.exemplar.utils.dynamodb.converters.StringMapTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "SquareLocation")
public class LocationSchema {

    private String account;

    private String id;

    private String name;

    private String businessName;

    private String businessType;

    private String nickname;

    private String email;

    private String phone;

    private String country;

    private String locality;

    private List<String> address;

    private LocalDateTime lastFetched;

    private Map<String, String> playHistoryProviders;

    public LocationSchema() {
    }

    private LocationSchema(String account) {
        this.account = account;
    }

    public LocationSchema(String account, String id, String name, String businessName, String businessType, String nickname, String email, String phone, String country, String locality, List<String> address, Map<String, String> playHistoryProviders, LocalDateTime lastFetched) {
        this.account = account;
        this.id = id;
        this.name = name;
        this.businessName = businessName;
        this.businessType = businessType;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.locality = locality;
        this.address = address;
        this.playHistoryProviders = playHistoryProviders;
        this.lastFetched = lastFetched;
    }

    @DynamoDBHashKey(attributeName = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @DynamoDBRangeKey(attributeName = "id")
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

    @DynamoDBAttribute(attributeName = "businessName")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @DynamoDBAttribute(attributeName = "businessType")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @DynamoDBAttribute(attributeName = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @DynamoDBAttribute(attributeName = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @DynamoDBAttribute(attributeName = "locality")
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @DynamoDBTypeConverted(converter = StringListTypeConverter.class)
    @DynamoDBAttribute(attributeName = "address")
    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeTypeConverter.class)
    @DynamoDBAttribute(attributeName = "lastFetched")
    public LocalDateTime getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(LocalDateTime lastFetched) {
        this.lastFetched = lastFetched;
    }

    @DynamoDBTypeConverted(converter = StringMapTypeConverter.class)
    @DynamoDBAttribute(attributeName = "playHistoryProviders")
    public Map<String, String> getPlayHistoryProviders() {
        return playHistoryProviders;
    }

    public void setPlayHistoryProviders(Map<String, String> playHistoryProviders) {
        this.playHistoryProviders = playHistoryProviders;
    }

    public static LocationSchema hashKey(String account) {
        return new LocationSchema(account);
    }
}

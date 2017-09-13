package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Merchant {

    private String id;

    private String name;

    private String email;

    @SerializedName("account_type")
    private String type;

    @SerializedName("account_capabilities")
    private List<String> capabilities;

    @SerializedName("country_code")
    private String country;

    @SerializedName("language_code")
    private String language;

    @SerializedName("currency_code")
    private String currency;

    @SerializedName("business_name")
    private String businessName;

    @SerializedName("business_address")
    private GlobalAddress address;

    @SerializedName("business_phone")
    private Map<String, String> phone;

    @SerializedName("business_type")
    private String businessType;

    @SerializedName("shipping_address")
    private GlobalAddress shippingAddress;

    @SerializedName("location_details")
    private Map<String, String> locationDetails;

    @SerializedName("market_url")
    private String marketUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public GlobalAddress getAddress() {
        return address;
    }

    public void setAddress(GlobalAddress address) {
        this.address = address;
    }

    public Map<String, String> getPhone() {
        return phone;
    }

    public void setPhone(Map<String, String> phone) {
        this.phone = phone;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public GlobalAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(GlobalAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Map<String, String> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(Map<String, String> locationDetails) {
        this.locationDetails = locationDetails;
    }

    public String getMarketUrl() {
        return marketUrl;
    }

    public void setMarketUrl(String marketUrl) {
        this.marketUrl = marketUrl;
    }

    public String phone() {
        return Optional.ofNullable(getPhone())
                .map(content -> content.get("calling_code") + content.get("number"))
                .orElse(null);
    }

    public String nickname() {
        return Optional.ofNullable(getLocationDetails())
                .map(content -> content.get("nickname"))
                .orElse(null);
    }
}

package ai.exemplar.api.square.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlobalAddress {

    public static class Coordinates {

        private Double latitude;

        private Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

    @SerializedName("address_line_1")
    private String addressLine1;

    @SerializedName("address_line_2")
    private String addressLine2;

    @SerializedName("address_line_3")
    private String addressLine3;

    @SerializedName("address_line_4")
    private String addressLine4;

    @SerializedName("address_line_5")
    private String addressLine5;

    private String locality;

    private String sublocality;

    @SerializedName("sublocality_1")
    private String sublocality1;

    @SerializedName("sublocality_2")
    private String sublocality2;

    @SerializedName("sublocality_3")
    private String sublocality3;

    @SerializedName("sublocality_4")
    private String sublocality4;

    @SerializedName("sublocality_5")
    private String sublocality5;

    @SerializedName("administrative_district_level_1")
    private String administrativeDistrictLevel1;

    @SerializedName("administrative_district_level_2")
    private String administrativeDistrictLevel2;

    @SerializedName("administrative_district_level_3")
    private String administrativeDistrictLevel3;

    @SerializedName("postal_code")
    private String postalCode;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("address_coordinates")
    private Coordinates coordinates;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSublocality() {
        return sublocality;
    }

    public void setSublocality(String sublocality) {
        this.sublocality = sublocality;
    }

    public String getSublocality1() {
        return sublocality1;
    }

    public void setSublocality1(String sublocality1) {
        this.sublocality1 = sublocality1;
    }

    public String getSublocality2() {
        return sublocality2;
    }

    public void setSublocality2(String sublocality2) {
        this.sublocality2 = sublocality2;
    }

    public String getSublocality3() {
        return sublocality3;
    }

    public void setSublocality3(String sublocality3) {
        this.sublocality3 = sublocality3;
    }

    public String getSublocality4() {
        return sublocality4;
    }

    public void setSublocality4(String sublocality4) {
        this.sublocality4 = sublocality4;
    }

    public String getSublocality5() {
        return sublocality5;
    }

    public void setSublocality5(String sublocality5) {
        this.sublocality5 = sublocality5;
    }

    public String getAdministrativeDistrictLevel1() {
        return administrativeDistrictLevel1;
    }

    public void setAdministrativeDistrictLevel1(String administrativeDistrictLevel1) {
        this.administrativeDistrictLevel1 = administrativeDistrictLevel1;
    }

    public String getAdministrativeDistrictLevel2() {
        return administrativeDistrictLevel2;
    }

    public void setAdministrativeDistrictLevel2(String administrativeDistrictLevel2) {
        this.administrativeDistrictLevel2 = administrativeDistrictLevel2;
    }

    public String getAdministrativeDistrictLevel3() {
        return administrativeDistrictLevel3;
    }

    public void setAdministrativeDistrictLevel3(String administrativeDistrictLevel3) {
        this.administrativeDistrictLevel3 = administrativeDistrictLevel3;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> address() {
        return Stream.of(
                getAddressLine1(),
                getAddressLine2(),
                getAddressLine3(),
                getAddressLine4(),
                getAddressLine5()
        ).map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<String> sublocalities() {
        return Stream.of(
                getSublocality1(),
                getSublocality2(),
                getSublocality3(),
                getSublocality4(),
                getSublocality5()
        ).map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<String> administrativeDistrict() {
        return Stream.of(
                getAdministrativeDistrictLevel1(),
                getAdministrativeDistrictLevel2(),
                getAdministrativeDistrictLevel3()
        ).map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}

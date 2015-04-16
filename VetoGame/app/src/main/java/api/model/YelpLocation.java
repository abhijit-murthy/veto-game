package api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Viral on 4/15/2015.
 */
public class YelpLocation {

    @SerializedName("city")
    private String city;

    @SerializedName("state_code")
    private String stateCode;

    @SerializedName("postal_code")
    private String postalCode;

    @SerializedName("address")
    private String[] address;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String toString(){
        if(address.length>0) {
            return address[0] + ", " + city + ", " + stateCode + ", " + postalCode;
        }else{
            return city + ", " + stateCode + ", " + postalCode;
        }
    }
}
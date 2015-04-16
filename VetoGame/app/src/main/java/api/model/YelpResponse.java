package api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Viral on 4/15/2015.
 */
public class YelpResponse {

    @SerializedName("businesses")
    private List<YelpSuggestionResponse> businesses;

    @SerializedName("total")
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<YelpSuggestionResponse> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<YelpSuggestionResponse> businesses) {
        this.businesses = businesses;
    }
}

package api.model;

import com.example.viral.vetogame.Suggestion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Viral on 4/15/2015.
 */
public class YelpSuggestionResponse {
    @SerializedName("id")
    private String suggestionId;

    @SerializedName("name")
    private String suggestionName;

    @SerializedName("mobile_url")
    private String mobileURL;

    @SerializedName("rating_img_url_large")
    private String ratingImg;

    @SerializedName("image_url")
    private String image;

    @SerializedName("review_count")
    private int reviewCount;

    @SerializedName("location")
    private YelpLocation location;

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSuggestionName() {
        return suggestionName;
    }

    public void setSuggestionName(String suggestionName) {
        this.suggestionName = suggestionName;
    }

    public String getMobileURL() {
        return mobileURL;
    }

    public void setMobileURL(String mobileURL) {
        this.mobileURL = mobileURL;
    }

    public String getRatingImg() {
        return ratingImg;
    }

    public void setRatingImg(String ratingImg) {
        this.ratingImg = ratingImg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public YelpLocation getLocation() {
        return location;
    }

    public void setLocation(YelpLocation location) {
        this.location = location;
    }
}

package api.model;

import com.example.viral.vetogame.Suggestion;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by eunkikim on 3/7/15.
 */
public class SuggestionResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("GameId")
    private String gameId;

    @SerializedName("name")
    private String suggestionName;

    @SerializedName("votes")
    private int votes;

    @SerializedName("location")
    private String location;

    @SerializedName("mobile_url")
    private String mobile_url;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("rating_url")
    private String rating_url;

    @SerializedName("createdAt")
    private String createdAt;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getSuggestionName() {
        return suggestionName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVotes() {
        return votes;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getRating_url() {
        return rating_url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    //============= Setters ============================

    public void setId(String suggestionId) {
        this.id = suggestionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setSuggestionName(String suggestionName) {
        this.suggestionName = suggestionName;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setRating_url(String rating_url) {
        this.rating_url = rating_url;
    }
}

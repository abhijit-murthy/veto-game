package api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eunkikim on 3/7/15.
 */
public class UserResponse {
    @SerializedName("id")
    private String userId;

    @SerializedName("name")
    private String userName;

    @SerializedName("wins")
    private int wins;

    @SerializedName("points")
    private int points;

    @SerializedName("extras")
    private int extras;

    public int getExtras() {
        return extras;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getWins() {
        return wins;
    }

    public int getPoints() {
        return points;
    }
}

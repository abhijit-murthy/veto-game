package api.model;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by eunkikim on 3/7/15.
 */
public class GameResponse {
    @SerializedName("id")
    private String userId;

    @SerializedName("eventType")
    private String eventType;

    @SerializedName("eventTime")
    private Date eventTime;

    @SerializedName("timeEnding")
    private Date timeEnding;

    @SerializedName("suggestionTTL")
    private int suggestionTtl;

    @SerializedName("center")
    private String center;

    @SerializedName("radius")
    private int radius;

    public int getRadius() {
        return radius;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventType() {
        return eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public Date getTimeEnding() {
        return timeEnding;
    }

    public int getSuggestionTtl() {
        return suggestionTtl;
    }

    public String getCenter() {
        return center;
    }
}

package api.model;

import android.location.Location;

import com.example.viral.vetogame.Game;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eunkikim on 3/7/15.
 */
public class GameResponse {
    @SerializedName("id")
    private String gameId;

    @SerializedName("name")
    private String gameName;

    @SerializedName("eventType")
    private String eventType;

    @SerializedName("eventTime")
    private String eventTime;

    @SerializedName("timeEnding")
    private String timeEnding;

    @SerializedName("suggestionTTL")
    private int suggestionTtl;

    @SerializedName("center")
    private String center;  // originally Location type, currently DB type is String

    @SerializedName("radius")
    private int radius;

    @SerializedName("currentSuggestion")
    private SuggestionResponse suggestionResponse;

    @SerializedName("userCount")
    private int userCount;

    public int getRadius() {
        return radius;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getTimeEnding() {
        return timeEnding;
    }

    public int getSuggestionTtl() {
        return suggestionTtl;
    }

    public String getCenter() {
        return center;
    }

    public SuggestionResponse getSuggestionResponse() {
        return suggestionResponse;
    }

    public int getUserCount() {
        return userCount;
    }

    //=========== Setters ================================


    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public void setTimeEnding(String timeEnding) {
        this.timeEnding = timeEnding;
    }

    public void setSuggestionTtl(int suggestionTtl) {
        this.suggestionTtl = suggestionTtl;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}

package api.model;

import com.example.viral.vetogame.Suggestion;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by eunkikim on 3/7/15.
 */
public class SuggestionResponse {
    @SerializedName("id")
    private String suggestionId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("game_id")
    private String gameId;

    @SerializedName("name")
    private String suggestionName;

    @SerializedName("count")
    private int count;

    @SerializedName("Unamed")
    private Suggestion currSuggestion;

    public String getSuggestionId() {
        return suggestionId;
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

    public int getCount() {
        return count;
    }

    public Suggestion getCurrSuggestion() {
        return currSuggestion;
    }
}

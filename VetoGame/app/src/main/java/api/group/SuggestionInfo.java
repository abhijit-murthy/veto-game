package api.group;

import java.util.List;

import api.model.SuggestionResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by eunkikim on 3/7/15.
 */
public interface SuggestionInfo {
    /*@POST("/game_data/create")
    public void createGame(@Body Game game, Callback<Game> callback);
*/
    @FormUrlEncoded
    @POST("/suggestion_data/create")
    public void createSuggestion(@Field("user_id") String userId, @Field("game_id") String gameId,
                           @Field("name") int suggestion, @Field("location") String location,
                           Callback<SuggestionResponse> callback);

    @FormUrlEncoded
    @POST("/suggestion_data/upvote")
    public void upvote(@Field("game_id") String gameId, @Field("suggestion_id") String suggestionId,
                         Callback<SuggestionResponse> callback);

    @FormUrlEncoded
    @POST("/suggestion_data/vote")
    public void veto(@Field("game_id") String gameId, @Field("user_id") String userId,
                     @Field("curr_suggestion_id") String currSuggestionId, @Field("new_suggestion_name") String newSuggestion,
                     @Field("new_suggestion_loc") String newLocation,
                     Callback<SuggestionResponse> callback);


    @GET("/suggestion_data/game_history/{id}")
    public void getSuggestionHistory(@Path("id") String gameId, Callback<SuggestionResponse> callback);

    @GET("/suggestion_data/current_suggestion/{id}")
    public void getCurrSuggestion(@Path("id") String gameId, Callback<List<SuggestionResponse>> callback);

    @GET("/suggestion_data/yelp_suggestions/{id}")
    public void getYelpSuggestion(@Path("id") String gameId, Callback<List<SuggestionResponse>> callback);

}

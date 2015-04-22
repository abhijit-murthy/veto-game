package api.group;

import java.util.List;

import api.model.SuggestionResponse;
import api.model.YelpResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

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
                           @Field("name") String suggestion, @Field("location") String location,
                           @Field("mobile_url") String mobile_url, @Field("image_url") String image_url,
                           @Field("rating_url") String rating_url,
                           Callback<SuggestionResponse> callback);

    @FormUrlEncoded
    @POST("/suggestion_data/upvote")
    public void upvote(@Field("game_id") String gameId, @Field("suggestion_id") String suggestionId,
                         Callback<SuggestionResponse> callback);

    @FormUrlEncoded
    @POST("/suggestion_data/veto")
    public void veto(@Field("game_id") String gameId, @Field("user_id") String userId,
                     @Field("curr_suggestion_id") String currSuggestionId, @Field("new_suggestion_name") String newSuggestion,
                     @Field("new_suggestion_loc") String newLocation, @Field("new_suggestion_mobile_url") String newMobileUrl,
                     @Field("new_suggestion_image_url") String newImageUrl, @Field("new_suggestion_rating_url") String newLRatingUrl,
                     Callback<SuggestionResponse> callback);


    @GET("/suggestion_data/game_history/{id}")
    public void getSuggestionHistory(@Path("id") String gameId, Callback<List<SuggestionResponse>> callback);

    @GET("/suggestion_data/current_suggestion/{id}")
    public void getCurrSuggestion(@Path("id") String gameId, Callback<SuggestionResponse> callback);

    @GET("/suggestion_data/yelp_suggestions/{id}")
    public void getYelpSuggestion(@Path("id") String gameId, Callback<List<SuggestionResponse>> callback);

    //@GET("/suggestion_data/yelp_suggestions_initial/{id}")
    @GET("/suggestion_data/yelp_suggestions_initial")
    public void getYelpSuggestionInitial(@Query("center")String center, @Query("radius")String radius,
                                         @Query("event_type")String event_type,
                                         Callback<YelpResponse> callback);

}

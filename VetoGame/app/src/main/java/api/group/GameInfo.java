package api.group;

import com.example.viral.vetogame.Game;

import api.model.GameResponse;

import api.model.UserResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Body;

/**
 * Created by eunkikim on 3/7/15.
 */
public interface GameInfo {
    /*@POST("/game_data/create")
    public void createGame(@Body Game game, Callback<Game> callback);
*/
    @FormUrlEncoded
    @POST("/game_data/create")
    public void createGame(@Field("user_id") String userId, @Field("event_type") String eventType,
                           @Field("suggestion_ttl") int suggestionTtl, @Field("center") String center,
                           @Field("radius") int radius, @Field("game_name") String gameName,
                           @Field("event_time") String eventTime, @Field("time_ending") String timeEnding,
                           Callback<GameResponse> callback);

    @GET("/game_data/{id}")
    public void getGame(@Path("id") String gameId, Callback<GameResponse> callback);
}
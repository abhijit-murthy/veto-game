package api.group;

import com.example.viral.vetogame.Game;

import java.util.ArrayList;
import java.util.List;

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

    @FormUrlEncoded
    @POST("/game_data/add_user_to_game")
    public void addUsers(@Field("user_id") String userId, @Field("game_id") String gameId,
                           Callback<GameResponse> callback);

    @GET("/game_data/{id}")
    public void getGame(@Path("id") String gameId, Callback<GameResponse> callback);

    @GET("/game_data/get_user_games/{id}")
    public void getUserGames(@Path("id") String userId, Callback<List<GameResponse>> callback);

    @GET("/game_data/get_current_games/{id}")
    public void getCurrentGames(@Path("id") String userId, Callback<List<GameResponse>> callback);

    @GET("/game_data/get_past_games/{id}")
    public void getPastGames(@Path("id") String userId, Callback<List<GameResponse>> callback);
}

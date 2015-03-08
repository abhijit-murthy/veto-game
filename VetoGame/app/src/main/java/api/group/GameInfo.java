package api.group;

import com.example.viral.vetogame.Game;

import api.model.GameResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Body;

/**
 * Created by eunkikim on 3/7/15.
 */
public interface GameInfo {
    @POST("/game_data/create")
    public void createGame(@Body Game game, Callback<Game> callback);

    @GET("/game_data/{id}")
    public void getGame(@Path("id") String gameId, Callback<GameResponse> callback);
}

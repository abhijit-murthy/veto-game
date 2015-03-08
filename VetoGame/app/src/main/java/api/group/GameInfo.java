package api.group;

import api.model.GameResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by eunkikim on 3/7/15.
 */
public interface GameInfo {
    @GET("/game_data/{id}")
    public void getGame(@Path("id") String gameId, Callback<GameResponse> callback);
}

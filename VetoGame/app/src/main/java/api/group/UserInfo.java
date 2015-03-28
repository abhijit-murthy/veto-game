package api.group;

import com.example.viral.vetogame.Game;

import api.model.UserResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.FormUrlEncoded;

/**
 * Created by eunkikim on 3/7/15.
 */
public interface UserInfo {
    @FormUrlEncoded
    @POST("/user_data/create")
    public void createUser(@Field("user_id") String userId, @Field("user_name") String userName, Callback<UserResponse> callback);


    @GET("/user_data/{id}")
    public void getUser(@Path("id") String userId, Callback<UserResponse> callback);
}

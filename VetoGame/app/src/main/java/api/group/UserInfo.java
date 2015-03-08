package api.group;

import api.model.UserResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by eunkikim on 3/7/15.
 */
public interface UserInfo {
    @GET("/user_data/{id}")
    public void getUser(@Path("id") String userId, Callback<UserResponse> callback);
}

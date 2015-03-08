package api;

import api.group.GameInfo;
import api.group.UserInfo;

import retrofit.RestAdapter;


/**
 * Created by eunkikim on 3/7/15.
 */
public class RestClient {

    private static final String BASE_URL = "http://173.236.253.103:28080";
    private static GameInfo gameInfo;
    private static UserInfo userInfo;

    public RestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        gameInfo = restAdapter.create(GameInfo.class);
        userInfo = restAdapter.create(UserInfo.class);
    }

    public static GameInfo getGameInfo()
    {
        return gameInfo;
    }

    public static UserInfo getUserInfo()
    {
        return userInfo;
    }
}

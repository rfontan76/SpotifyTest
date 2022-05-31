package api.apps.spotify;
import api.RestResource;
import api.pojos.Playlist;
import api.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.Route.PLAYLISTS;
import static api.Route.USERS;
import static api.managers.TokenManager.getToken;


public class PlaylistAPI {

    @Step
    public static Response post(Playlist playlist){
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken(), playlist );
    }

    @Step
    public static Response post(String token, Playlist playlist){
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS,token, playlist);
    }

    @Step
    public static Response get(String playListId){
        return RestResource.get(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS + "/" + playListId, getToken());
    }

    @Step
    public static Response put(String playListId, Playlist playlist){
        return RestResource.put(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS + "/" + playListId, getToken(), playlist);

    }

}

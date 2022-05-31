package spotify;

import api.apps.spotify.PlaylistAPI;
import api.pojos.Error;
import api.pojos.Playlist;
import api.tests.BaseTest;
import api.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oath 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest {

    @Story("Create playlist")
    @TmsLink("https://www.example.com")
    @Issue("12345")
    @Description("Verify Playlist can be created")
    @Test(description = "Verify Playlist can be created")
    public void ShouldBeAbleToCreatePlaylist(){
        System.out.println();
        Playlist requestPlayList = playListBuilder("New Playlist","Test", false);
        Response response = PlaylistAPI.post(requestPlayList);
        assertStatusCode(response.statusCode(), 201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlayList);
    }

    @Story("Retrieve playlist")
    @TmsLink("https://www.example.com")
    @Issue("12345")
    @Description("Verify Playlist can be retrieved")
    @Test(description = "Verify Playlist can be retrieved")
    public void ShouldBeAbleToGetPlayList(){
        Response response = PlaylistAPI.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), 200);
        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getId(), equalTo(DataLoader.getInstance().getPlaylistId()));
    }

    @Story("Update playlist")
    @TmsLink("https://www.example.com")
    @Issue("12345")
    @Description("Verify Playlist can be updated")
    @Test(description = "Verify Playlist can be updated")
    public void ShouldBeAbleToUpdatePlaylist(){
        Playlist requestPlayList = playListBuilder("Updated New Playlist","Updated Test", false);
        Response response = PlaylistAPI.put(DataLoader.getInstance().getPlaylistId(), requestPlayList);
        assertStatusCode(response.statusCode(), 200);
    }

    @Story("Playlist not created without name")
    @TmsLink("https://www.example.com")
    @Issue("12345")
    @Description("Verify Playlist cannot be created without name")
    @Test(description = "Verify Playlist cannot be created without name")
    public void ShouldNotBeAbleToCreatePlaylistWithOutName(){
        Playlist playlist = playListBuilder(null,"Test", false);
        Response response = PlaylistAPI.post(playlist);
        assertThat(response.statusCode(), equalTo(400));
        assertError(response.as(Error.class), 400, "Missing required field: name");

    }

    @Story("Playlist not created with invalid token")
    @TmsLink("https://www.example.com")
    @Issue("12345")
    @Description("Verify Playlist cannot be created without token")
    @Test(description = "Verify Playlist cannot be created without token")
    public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken(){
        Playlist playlist = playListBuilder("Error Tests","Test", false);
        Response response = PlaylistAPI.post("3456", playlist);
        assertError(response.as(Error.class), 401, "Invalid access token");
    }


    @Step
    public Playlist playListBuilder (String name, String description, boolean _public) {
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();

    }

    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist, Playlist responsePlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    @Step("Test")
    public void assertError(Error responseErr, int expectedErrorCode, String expectedMsg){
        assertThat(responseErr.getError().getStatus(), equalTo(expectedErrorCode));
        assertThat(responseErr.getError().getMessage(), equalTo(expectedMsg));
    }
}

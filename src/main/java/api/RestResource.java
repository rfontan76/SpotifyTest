package api;

import api.apps.spotify.SpecBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static api.Route.API;
import static api.Route.TOKEN;
import static api.apps.spotify.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    @Step
    public static Response post(String path, String token, Object object){
        return given(getRequestSpec())
                .body(object)
                .auth().oauth2(token)
                .when().post(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }


    @Step
    public static Response get(String path, String token){
        return given(getRequestSpec())
                .auth().oauth2(token)
                .when().get(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    @Step
    public static Response put(String path, String token, Object object){
        return given(getRequestSpec())
                .auth().oauth2(token)
                .body(object)
                .when().put(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    @Step
    public static Response postAccount(HashMap<String, String> params){
        return given()
                .spec(getAccountRequestSpec())
                .formParams(params)
                .log().all()
                .when()
                .post(API + TOKEN)
                .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract()
                .response();
    }

}

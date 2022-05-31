package api.managers;

import api.RestResource;
import api.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager {

    private static String accessToken;
    private static Instant expiry_time;

    @Step
    public synchronized static String getToken(){

        try {
            if(accessToken == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token ....");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }else {
                System.out.println("Token is good");
            }

        }catch(Exception e){
            e.printStackTrace();
            throw  new RuntimeException(("Failed to get Token"));
        }
        return accessToken;
    }

    private static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());

        Response response = RestResource.postAccount(formParams);

        if(response.statusCode() != 200){
            throw new RuntimeException("Renew Token failed!!");
        }
        return response;
    }
}

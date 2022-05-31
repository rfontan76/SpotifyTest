package api.utils;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = new PropertyUtils().propertyLoader("src/main/resources/config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null) configLoader = new ConfigLoader();
        return configLoader;
    }

    public String getClientId() {
        String prop = properties.getProperty("client_id");
        if(prop != null){
            return prop;
        }else{
            throw new RuntimeException("No client_id prop found");
        }
    }

    public String getClientSecret() {
        String prop = properties.getProperty("client_secret");
        if(prop != null){
            return prop;
        }else{
            throw new RuntimeException("No client_secret prop found");
        }
    }
    public String getRefreshToken() {
        String prop = properties.getProperty("refresh_token");
        if(prop != null){
            return prop;
        }else{
            throw new RuntimeException("No refresh_token prop found");
        }
    }

    public String getGrantType() {
        String prop = properties.getProperty("grant_type");
        if(prop != null) return prop;
        else throw new RuntimeException("No grant_type prop found");

    }

    public String getUserId() {
        String prop = properties.getProperty("user");
        if(prop != null){
            return prop;
        }else{
            throw new RuntimeException("No user prop found");
        }
    }
}

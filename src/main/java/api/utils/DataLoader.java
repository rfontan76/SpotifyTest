package api.utils;

import java.util.Properties;

public class DataLoader {

    private final Properties properties;
    private static DataLoader configLoader;

    private DataLoader(){
        properties = new PropertyUtils().propertyLoader("src/main/resources/data.properties");
    }

    public static DataLoader getInstance(){
        if(configLoader == null) configLoader = new DataLoader();
        return configLoader;
    }

    public String getPlaylistId() {
        String prop = properties.getProperty("get_playlist_id");
        if(prop != null){
            return prop;
        }else{
            throw new RuntimeException("No playlist_id prop found");
        }
    }

    public String updatePlaylistId() {
        String prop = properties.getProperty("update_playlist_id");
        if(prop != null){
            return prop;
        }else{
            throw new RuntimeException("No update_playlist_id prop found");
        }
    }

}

package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiProperties extends Properties {

    protected static ApiProperties apiProperties;

    public static ApiProperties getApiProperties() {
        if (apiProperties == null) {
            apiProperties = new ApiProperties();
            try {
                apiProperties.load(new FileInputStream("src/main/resources/api.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return apiProperties;
    }
}

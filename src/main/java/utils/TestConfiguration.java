package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


public class TestConfiguration {


    /* This method return test variable by key */
    public static String getProperty(String property){
        try {
            Properties properties = new Properties();
            InputStream config = TestConfiguration.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(config);
            return properties.getProperty(property);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}

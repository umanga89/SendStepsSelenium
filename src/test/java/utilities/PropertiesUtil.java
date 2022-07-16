package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties configProperties = new Properties();

    public static Properties loadProperties(String url) throws IOException {
        try{
            File configFile = new File(url);
            FileInputStream fis = new FileInputStream(configFile);
            configProperties.load(fis);
        }catch (Exception e){
            e.printStackTrace();
            throw new IOException(e);
        }
        return configProperties;
    }
}

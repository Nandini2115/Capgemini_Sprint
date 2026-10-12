package baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonData {

    private static Properties properties = new Properties();
    static {
        try {
            FileInputStream fis = new FileInputStream("./src/main/resources/CommonData.properties");
            properties.load(fis);
            fis.close();
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {

        return properties.getProperty(key);
    }
}
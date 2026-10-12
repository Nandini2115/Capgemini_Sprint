package baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonData {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(
                "src/main/resources/CommonData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(
                "Unable to load CommonData.properties", e
            );
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

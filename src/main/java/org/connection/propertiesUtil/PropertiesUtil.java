package org.connection.propertiesUtil;

import lombok.experimental.UtilityClass;
import org.exceptions.loadPropertiesException.LoadPropertiesException;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }
    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (var resource = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resource);
        } catch (IOException e) {
            throw new LoadPropertiesException(e, " Exception in loadProperties method in PropertiesUtil class.");
        }
    }
}

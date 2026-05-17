package com.frameworkdesign.ui.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("global.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load global.properties!", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

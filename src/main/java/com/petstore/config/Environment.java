package com.petstore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
    private static final Properties properties = new Properties();

    static {
        try {
            // Por defecto, leemos 'dev'
            String environment = System.getProperty("env", "dev"); 
            String filePath = "src/test/resources/config/application-" + environment + ".properties";
            FileInputStream input = new FileInputStream(filePath);
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment properties", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "http://localhost:8080/api/v3");
    }

    public static String getAuthToken() {
        return properties.getProperty("auth.token", "DEFAULT_TOKEN");
    }
}

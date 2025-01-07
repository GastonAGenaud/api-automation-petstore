package com.petstore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
    private static final Properties properties = new Properties();

    static {
        try {
            String environment = System.getProperty("env", "dev");
            String filePath = "src/test/resources/config/application-" + environment + ".properties";
            System.out.println("📚 Loading environment from: " + filePath);

            FileInputStream input = new FileInputStream(filePath);
            properties.load(input);
            input.close();

            System.out.println("🌍 Base URL: " + properties.getProperty("base.url"));
            System.out.println("🔑 Auth Token: " + properties.getProperty("auth.token"));
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load environment properties", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "/pet/1");
    }

    public static String getAuthToken() {
        return properties.getProperty("auth.token", "DEFAULT_TOKEN");
    }
}

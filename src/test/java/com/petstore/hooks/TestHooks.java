package com.petstore.hooks;

import com.petstore.config.Environment;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class TestHooks {

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = Environment.getBaseUrl();
        RestAssured.requestSpecification = RestAssured
                .given()
                .header("Authorization", "Bearer " + Environment.getAuthToken())
                .header("Content-Type", "application/json");

        System.out.println("🌍 Base URL Configured: " + RestAssured.baseURI);
        System.out.println("🔑 Auth Token: " + Environment.getAuthToken());
    }

    @After
    public void teardown() {
        System.out.println("✅ Test Execution Completed!");
        if (RestAssured.baseURI == null) {
            System.err.println("❌ Base URI is not set correctly.");
        }
    }

}

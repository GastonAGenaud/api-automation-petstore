package com.petstore.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class TestHooks {

    @Before
    public void setup() {
        // Ajusta la URL a la que quieras apuntar
        RestAssured.baseURI = System.getProperty("base.url", "http://localhost:8080/api/v3");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        System.out.println("🚀 Test Environment Setup Complete!");
    }

    @After
    public void teardown() {
        System.out.println("✅ Test Execution Completed!");
    }
}

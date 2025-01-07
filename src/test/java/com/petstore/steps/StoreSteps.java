package com.petstore.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

public class StoreSteps {
    private final BaseSteps baseSteps;

    public StoreSteps(BaseSteps baseSteps) {
        this.baseSteps = baseSteps;
    }

    @Given("I have the following order payload:")
    public void i_have_the_following_order_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    @Given("An order with ID {string} exists")
    public void an_order_with_ID_exists(String id) {
        Response response = RestAssured.given()
                .baseUri(baseSteps.getBaseUrl())
                .get("/store/order/" + id);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("The response should contain the order ID")
    public void the_response_should_contain_the_order_ID() {
        Assert.assertNotNull(baseSteps.getResponse().jsonPath().get("id"));
    }

    @Then("The response should have the order ID {string}")
    public void the_response_should_have_the_order_ID(String id) {
        Assert.assertEquals(id, baseSteps.getResponse().jsonPath().get("id").toString());
    }

    @Then("The response should have the order status {string}")
    public void the_response_should_have_the_order_status(String status) {
        Assert.assertEquals(status, baseSteps.getResponse().jsonPath().get("status").toString());
    }

    @Then("The order with ID {string} should no longer exist")
    public void the_order_with_ID_should_no_longer_exist(String id) {
        Response response = RestAssured.given()
                .baseUri(baseSteps.getBaseUrl())
                .get("/store/order/" + id);
        Assert.assertEquals(404, response.getStatusCode());
    }

    @Then("The response should contain inventory details")
    public void the_response_should_contain_inventory_details() {
        Assert.assertNotNull(baseSteps.getResponse().jsonPath().get("available"));
    }

    @Then("I should receive a status code {int} for store operation")
    public void i_should_receive_a_status_code_for_store_operation(Integer statusCode) {
        Assert.assertEquals(statusCode.intValue(), baseSteps.getResponse().getStatusCode());
    }

    @Then("The store response should contain an error message {string}")
    public void the_store_response_should_contain_an_error_message(String errorMessage) {
        Assert.assertTrue(baseSteps.getResponse().getBody().asString().contains(errorMessage));
    }
}

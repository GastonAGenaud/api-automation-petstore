package com.petstore.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;

public class PetSteps {
    private final BaseSteps baseSteps;

    public PetSteps(BaseSteps baseSteps, CommonSteps commonSteps) {
        this.baseSteps = baseSteps;
    }

    @Given("A pet with ID {string} exists")
    public void a_pet_with_ID_exists(String id) {
        Response response = RestAssured.given()
                .baseUri(baseSteps.getBaseUrl())
                .get("/pet/" + id);
        Assert.assertEquals(200, response.getStatusCode());
    }


    @Given("I have an image file {string}")
    public void i_have_an_image_file(String fileName) {
        File imageFile = new File("src/test/resources/" + fileName);
        Assert.assertTrue("Image file does not exist", imageFile.exists());
        baseSteps.setImageFile(imageFile);
    }


    @Then("The response should contain the pet ID")
    public void the_response_should_contain_the_pet_ID() {
        Assert.assertNotNull(baseSteps.getResponse().jsonPath().get("id"));
    }

    @Then("The pet with ID {string} should no longer exist")
    public void the_pet_with_ID_should_no_longer_exist(String id) {
        Response response = RestAssured.given()
                .baseUri(baseSteps.getBaseUrl())
                .get("/pet/" + id);
        Assert.assertEquals(404, response.getStatusCode());
    }

    @Given("The Petstore API is available")
    public void the_petstore_api_is_available() {
        Response response = RestAssured
                .given()
                .baseUri(baseSteps.getBaseUrl())
                .get("/pet");
        Assert.assertEquals(200, response.getStatusCode());
    }


    @Then("The response should have the pet ID {string}")
    public void the_response_should_have_the_pet_ID(String id) {
        Assert.assertEquals(id, baseSteps.getResponse().jsonPath().get("id").toString());
    }

    @Then("The response should have the pet name {string}")
    public void the_response_should_have_the_pet_name(String name) {
        Assert.assertEquals(name, baseSteps.getResponse().jsonPath().get("name").toString());
    }

    @Then("The response should have the status {string}")
    public void the_response_should_have_the_status(String status) {
        Assert.assertEquals(status, baseSteps.getResponse().jsonPath().get("status").toString());
    }

    @Then("The response should contain pets with status {string}")
    public void the_response_should_contain_pets_with_status(String status) {
        List<Object> statuses = baseSteps.getResponse().jsonPath().getList("status");
        Assert.assertTrue(statuses.contains(status));
    }

    @Then("The response should confirm the image upload")
    public void the_response_should_confirm_the_image_upload() {
        Assert.assertTrue(baseSteps.getResponse().jsonPath().getString("message").contains("uploaded"));
    }

    @Then("The response should contain pets with the tag {string}")
    public void the_response_should_contain_pets_with_tag(String tag) {
        Assert.assertTrue(baseSteps.getResponse().getBody().asString().contains(tag));
    }

    @Then("I should receive a status code {int} for pet operation")
    public void i_should_receive_a_status_code_for_pet_operation(Integer statusCode) {
        Assert.assertEquals(statusCode.intValue(), baseSteps.getResponse().getStatusCode());
    }

    @Then("The response should contain an error message {string}")
    public void the_response_should_contain_an_error_message(String errorMessage) {
        Assert.assertTrue(baseSteps.getResponse().getBody().asString().contains(errorMessage));
    }

}

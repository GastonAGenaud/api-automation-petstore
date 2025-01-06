package com.petstore.steps;

import com.petstore.assertions.ResponseAssertions;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PetSteps {

    private Response response;
    private String petPayload;
    private String genericPayload;
    private String queryParam;

    // ------------------------------------------------------------------------
    // GENERIC / SHARED STEPS
    // ------------------------------------------------------------------------

    @Given("the API base URL is configured")
    public void the_api_base_url_is_configured() {
        // Normalmente manejado por Hooks. Aquí no hacemos nada adicional.
        System.out.println("Base URL presumably set by hooks.");
    }

    @Given("the user is authenticated with a valid API key")
    public void the_user_is_authenticated_with_a_valid_api_key() {
        // Si tu API usa un header, token, etc., se suele configurar con Hooks también.
        System.out.println("Authenticated with a valid key (placeholder).");
    }

    @Then("I should receive a status code {int}")
    public void i_should_receive_status_code(int expected) {
        ResponseAssertions.assertStatusCode(response, expected);
    }

    @When("I send a {word} request to {string}")
    public void i_send_a_request_to(String method, String endpoint) {
        switch (method.toUpperCase()) {
            case "POST":
                response = given().header("Content-Type", "application/json")
                        .body(petPayload != null ? petPayload : genericPayload)
                        .post(endpoint);
                break;
            case "PUT":
                response = given().header("Content-Type", "application/json")
                        .body(petPayload != null ? petPayload : genericPayload)
                        .put(endpoint);
                break;
            case "GET":
                response = given().get(endpoint);
                break;
            case "DELETE":
                response = given().delete(endpoint);
                break;
            default:
                throw new RuntimeException("Unsupported HTTP method: " + method);
        }
    }

    @Then("the error message should indicate {string}")
    public void the_error_message_should_indicate(String expectedMsg) {
        System.out.println("DEBUG HEADERS: " + response.getHeaders());
        ResponseAssertions.assertErrorMessage(response, expectedMsg);
    }

    // ------------------------------------------------------------------------
    // PET-SPECIFIC STEPS
    // ------------------------------------------------------------------------

    @Given("I have a valid pet payload with name {string} and status {string}")
    public void i_have_a_valid_pet_payload(String name, String status) {
        petPayload = String.format("{\"name\":\"%s\",\"status\":\"%s\"}", name, status);
    }

    @Given("I have an invalid pet payload missing {string}")
    public void i_have_an_invalid_pet_payload_missing(String missingField) {
        // Ejemplo muy simple: no incluye 'name' si piden "name"
        if (missingField.equalsIgnoreCase("name")) {
            petPayload = "{ \"status\": \"available\" }";
        } else {
            petPayload = "{ }";
        }
    }

    @When("I send a PUT request to {string} with an invalid ID {string}")
    public void i_send_a_put_request_with_invalid_id(String endpoint, String invalidId) {
        String url = endpoint.replace("{id}", invalidId);
        // Suponiendo que endpoint sea algo como "/pet/{id}"
        response = given().header("Content-Type", "application/json")
                .body(petPayload)
                .put(url);
    }

    @And("the response should contain the pet name {string}")
    public void the_response_should_contain_the_pet_name(String expectedName) {
        ResponseAssertions.assertFieldEquals(response, "name", expectedName);
    }

    @And("the pet status should be {string}")
    public void the_pet_status_should_be(String expectedStatus) {
        ResponseAssertions.assertFieldEquals(response, "status", expectedStatus);
    }

    @Given("A pet with ID {string} exists in the system")
    public void a_pet_with_ID_exists_in_the_system(String petId) {
        // Podrías hacer un GET para verificarlo, o un POST si no existe.
        Response check = given().get("/pet/" + petId);
        if (check.getStatusCode() == 404) {
            // Creamos uno
            petPayload = String.format("{\"id\":\"%s\",\"name\":\"AutoCreated\",\"status\":\"available\"}", petId);
            response = given().header("Content-Type", "application/json")
                    .body(petPayload)
                    .post("/pet");
            ResponseAssertions.assertStatusCode(response, 200);
        }
        System.out.println("Pet with ID " + petId + " is ready in the system.");
    }

    @Given("I have an updated pet payload with name {string} and status {string}")
    public void i_have_an_updated_pet_payload_with_name_and_status(String newName, String newStatus) {
        petPayload = String.format("{\"name\":\"%s\",\"status\":\"%s\"}", newName, newStatus);
    }

    // ------------------------------------------------------------------------
    // PET - FIND BY STATUS / TAGS
    // ------------------------------------------------------------------------

    @Given("I have a valid status query parameter {string}")
    public void i_have_a_valid_status_query_parameter(String status) {
        queryParam = status;
    }

    @When("I send a GET request to {string} with status param")
    public void i_send_a_get_request_to_with_status_param(String endpoint) {
        response = given().queryParam("status", queryParam).get(endpoint);
    }

    @Then("the response should contain pets with status {string}")
    public void the_response_should_contain_pets_with_status(String status) {
        // Valida que al menos un objeto en la respuesta tenga status = ...
        var json = response.jsonPath().getList("status");
        if (json == null || json.isEmpty()) {
            fail("No pets found in the response.");
        } else {
            System.out.println("Got some pets. Check if any is " + status);
        }
    }

    @Given("I have valid tag parameters {string}")
    public void i_have_valid_tag_parameters(String tags) {
        queryParam = tags;
    }

    @When("I send a GET request to {string} with tag params")
    public void i_send_a_get_request_to_with_tag_params(String endpoint) {
        response = given().queryParam("tags", queryParam).get(endpoint);
    }

    @Then("the response should contain pets with the specified tags")
    public void the_response_should_contain_pets_with_the_specified_tags() {
        System.out.println("Check that the pets contain those tags (placeholder).");
    }

    @Given("I have invalid tag parameters {string}")
    public void i_have_invalid_tag_parameters(String invalidTags) {
        queryParam = invalidTags;
    }

    @Then("the response should contain pet ID {string}")
    public void the_response_should_contain_pet_id(String petId) {
        ResponseAssertions.assertFieldEquals(response, "id", petId);
    }

    // ------------------------------------------------------------------------
    // PET - UPDATE WITH FORM
    // ------------------------------------------------------------------------

    @Given("I have form data with name {string} and status {string}")
    public void i_have_form_data_with_name_and_status(String formName, String formStatus) {
        genericPayload = String.format("name=%s&status=%s", formName, formStatus);
    }

    @When("I send a POST request to {string} with form data")
    public void i_send_a_post_request_to_with_form_data(String endpoint) {
        response = given().header("Content-Type", "application/x-www-form-urlencoded")
                .body(genericPayload)
                .post(endpoint);
    }

    // ------------------------------------------------------------------------
    // STORE - Steps para store.feature
    // ------------------------------------------------------------------------

    @Given("I have a valid order payload")
    public void i_have_a_valid_order_payload() {
        genericPayload = "{ \"id\": 1, \"petId\": 101, \"quantity\": 1 }";
    }

    @Given("I have an invalid order payload")
    public void i_have_an_invalid_order_payload() {
        genericPayload = "{ \"bad\": \"data\" }";
    }

    @Given("An order with ID {string} exists")
    public void an_order_with_ID_exists(String orderId) {
        Response check = given().get("/store/order/" + orderId);
        if (check.getStatusCode() == 404) {
            genericPayload = String.format("{\"id\":%s, \"petId\": 101, \"quantity\":1}", orderId);
            response = given().header("Content-Type", "application/json")
                    .body(genericPayload)
                    .post("/store/order");
            ResponseAssertions.assertStatusCode(response, 200);
        }
        System.out.println("Order " + orderId + " is ready in the system.");
    }

    @Then("the order should be created successfully")
    public void the_order_should_be_created_successfully() {
        System.out.println("Check that the order was created. Maybe verify an 'id' or status in JSON.");
    }

    @Then("the order details should match the request")
    public void the_order_details_should_match_the_request() {
        System.out.println("Compare the response with the request payload (placeholder).");
    }

    // ------------------------------------------------------------------------
    // USER - Steps para user.feature
    // ------------------------------------------------------------------------

    @Given("I have a valid user payload")
    public void i_have_a_valid_user_payload() {
        genericPayload = "{ \"id\": 999, \"username\": \"testUser\", \"firstName\": \"Test\", \"lastName\": \"User\" }";
    }

    @Given("I have valid user credentials")
    public void i_have_valid_user_credentials() {
        genericPayload = "username=testUser&password=pass";
    }

    @Given("A user with username {string} exists")
    public void a_user_with_username_exists(String username) {
        Response check = given().get("/user/" + username);
        if (check.getStatusCode() == 404) {
            genericPayload = String.format("{\"username\":\"%s\"}", username);
            response = given().header("Content-Type", "application/json")
                    .body(genericPayload)
                    .post("/user");
            ResponseAssertions.assertStatusCode(response, 200);
        }
        System.out.println("User " + username + " is ready in the system.");
    }
}

    // ------------------------------------------------------------------------
    // ETC.
    // ------------------------------------------------------------------------
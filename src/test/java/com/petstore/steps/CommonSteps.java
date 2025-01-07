package com.petstore.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * CommonSteps maneja las acciones comunes de API (POST, PUT, GET, DELETE)
 * y gestiona los payloads, respuestas y archivos compartidos.
 */
public class CommonSteps {
    private final BaseSteps baseSteps;

    public CommonSteps(BaseSteps baseSteps) {
        this.baseSteps = baseSteps;
    }

    @Given("I have the following pet payload:")
    public void i_have_the_following_pet_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    @Given("I have the following updated pet payload:")
    public void i_have_the_following_updated_pet_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    @Given("I have a tag filter {string}")
    public void i_have_a_tag_filter(String tag) {
        baseSteps.setPayload(String.format("{\"tags\": [\"%s\"]}", tag));
    }

    @Given("I have the following invalid pet payload:")
    public void i_have_the_following_invalid_pet_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    @When("I send a {string} request to {string}")
    public void i_send_a_request_to(String method, String endpoint) {
        Response response;
        ObjectMapper objectMapper = new ObjectMapper(); // Para validar el JSON

        try {
            // Configurar Base URI
            RestAssured.baseURI = baseSteps.getBaseUrl();
            var requestSpec = RestAssured.given()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");

            // Incluir Token si existe
            if (baseSteps.getAuthToken() != null && !baseSteps.getAuthToken().isEmpty()) {
                requestSpec.header("Authorization", "Bearer " + baseSteps.getAuthToken());
            }

            // Validar y serializar el payload
            if (baseSteps.getPayload() != null && !baseSteps.getPayload().isEmpty()) {
                try {
                    // Validar si el payload es un JSON válido
                    objectMapper.readTree(baseSteps.getPayload());
                    requestSpec.body(baseSteps.getPayload());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Payload is not a valid JSON: " + baseSteps.getPayload());
                }
            }

            // Manejar distintos métodos HTTP
            switch (method.toUpperCase()) {
                case "POST":
                    if (endpoint.contains("uploadImage")) {
                        requestSpec.contentType("multipart/form-data");
                        requestSpec.multiPart("file", baseSteps.getImageFile());
                    }
                    response = requestSpec.post(endpoint);
                    break;

                case "PUT":
                    response = requestSpec.put(endpoint);
                    break;

                case "GET":
                    response = requestSpec.get(endpoint);
                    break;

                case "DELETE":
                    response = requestSpec.delete(endpoint);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }

            // Guardar la respuesta para validaciones
            baseSteps.setResponse(response);

            // Logs para depuración
            System.out.println("Request Method: " + method);
            System.out.println("Full Endpoint: " + baseSteps.getBaseUrl() + endpoint);
            System.out.println("Payload: " + baseSteps.getPayload());
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
            throw e;
        }
    }

}

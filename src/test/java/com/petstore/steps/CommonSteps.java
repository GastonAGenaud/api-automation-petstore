package com.petstore.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

/**
 * CommonSteps maneja las acciones comunes de API (POST, PUT, GET, DELETE)
 * y gestiona los payloads, respuestas y archivos compartidos.
 */
public class CommonSteps {
    private final BaseSteps baseSteps;

    /**
     * Constructor que recibe BaseSteps
     *
     * @param baseSteps instancia compartida de configuración
     */
    public CommonSteps(BaseSteps baseSteps) {
        this.baseSteps = baseSteps;
    }

    /**
     * Define el payload para crear un pet
     *
     * @param payload JSON String del pet
     */
    @Given("I have the following pet payload:")
    public void i_have_the_following_pet_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    /**
     * Define el payload para actualizar un pet
     *
     * @param payload JSON String del pet actualizado
     */
    @Given("I have the following updated pet payload:")
    public void i_have_the_following_updated_pet_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    /**
     * Define un filtro de tag para buscar pets
     *
     * @param tag Filtro por tag
     */
    @Given("I have a tag filter {string}")
    public void i_have_a_tag_filter(String tag) {
        baseSteps.setPayload(String.format("{\"tags\": [\"%s\"]}", tag));
    }

    /**
     * Define un payload inválido para pruebas negativas
     *
     * @param payload JSON String inválido
     */
    @Given("I have the following invalid pet payload:")
    public void i_have_the_following_invalid_pet_payload(String payload) {
        baseSteps.setPayload(payload);
    }

    /**
     * Envía una solicitud HTTP a una URL con un método específico
     *
     * @param method   Método HTTP (GET, POST, PUT, DELETE)
     * @param endpoint Ruta del recurso
     */
    @When("I send a {string} request to {string}")
    public void i_send_a_request_to(String method, String endpoint) {
        Response response;

        try {
            switch (method.toUpperCase()) {
                case "POST":
                    if (endpoint.contains("uploadImage")) {
                        response = RestAssured.given()
                                .baseUri(baseSteps.getBaseUrl())
                                .header("accept", "application/json")
                                .header("Content-Type", "application/octet-stream") // Cambia el Content-Type
                                .body(baseSteps.getImageFile()) // Envía el archivo como binary
                                .post(endpoint);
                    } else {
                        response = RestAssured.given()
                                .baseUri(baseSteps.getBaseUrl())
                                .contentType("application/json")
                                .body(baseSteps.getPayload())
                                .post(endpoint);
                    }
                    break;



                case "PUT":
                    response = RestAssured.given()
                            .baseUri(baseSteps.getBaseUrl())
                            .contentType("application/json")
                            .body(baseSteps.getPayload())
                            .put(endpoint);
                    break;

                case "GET":
                case "DELETE":
                    response = RestAssured.given()
                            .baseUri(baseSteps.getBaseUrl())
                            .request(method, endpoint);
                    break;

                default:
                    throw new IllegalArgumentException("Método no soportado: " + method);
            }

            baseSteps.setResponse(response);

            // Imprime detalles de la respuesta
            System.out.println("Request Method: " + method);
            System.out.println("Endpoint: " + endpoint);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

        } catch (Exception e) {
            System.err.println("Error al enviar la solicitud: " + e.getMessage());
            throw e; // Lanza nuevamente la excepción para que el test falle correctamente
        }
    }
}

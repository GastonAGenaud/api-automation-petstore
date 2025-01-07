package com.petstore.steps;

import io.restassured.response.Response;
import java.io.File;

/**
 * BaseSteps maneja la configuración compartida para los Steps,
 * incluyendo URL base, payloads, respuestas y archivos.
 */
public class BaseSteps {
    private String baseUrl = System.getProperty("api.baseUrl", "https://petstore3.swagger.io/api/v3");
    private String payload; // Almacena el payload JSON
    private Response response; // Almacena la respuesta HTTP
    private File imageFile; // Almacena el archivo de imagen para subir
    private String authToken; // Token de autenticación

    // Obtener la URL base
    public String getBaseUrl() {
        return baseUrl;
    }

    // Obtener el payload actual
    public String getPayload() {
        return payload;
    }

    // Configurar el payload
    public void setPayload(String payload) {
        this.payload = payload;
    }

    // Obtener la respuesta
    public Response getResponse() {
        return response;
    }

    // Configurar la respuesta
    public void setResponse(Response response) {
        this.response = response;
    }

    // Obtener el archivo de imagen
    public File getImageFile() {
        return imageFile != null ? imageFile : new File("src/test/resources/pet_image.jpg");
    }

    // Configurar el archivo de imagen
    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    // Obtener el token de autenticación
    public String getAuthToken() {
        return authToken;
    }

    // Configurar el token de autenticación
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

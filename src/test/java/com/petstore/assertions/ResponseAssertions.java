package com.petstore.assertions;

import io.restassured.response.Response;

import static org.junit.Assert.*;

public class ResponseAssertions {

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        // Incluir el body en el mensaje ayuda a depurar
        assertEquals("Status code does not match. Body was:\n" + response.asString(),
                expectedStatusCode, response.getStatusCode());
    }

    /**
     * Verifica que la respuesta contenga un "message" si es JSON,
     * o busque la cadena en el body si no es JSON (p.ej. HTML).
     */
    public static void assertErrorMessage(Response response, String expectedMessage) {
        String bodyAsString = response.asString();
        String contentType = response.getHeader("Content-Type");

        // Si no hay content-type o no menciona "json",
        // hacemos un contains() directo.
        if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
            if (!bodyAsString.contains(expectedMessage)) {
                fail("Expected error message '" + expectedMessage
                        + "', but got non-JSON response:\n" + bodyAsString);
            }
            return;
        }

        // De aquí en más, supuestamente es JSON, pero la API manda "Pet not found"
        // sin ser JSON real. Hacemos un try/catch para fallback:
        try {
            String actualMessage = response.jsonPath().getString("message");
            if (actualMessage == null) {
                // Ni siquiera hay un campo "message"
                // Observa si el body es válido JSON pero sin "message"
                // Como fallback, busca la cadena.
                if (!bodyAsString.contains(expectedMessage)) {
                    fail("Expected error message '" + expectedMessage
                            + "', but JSON has no 'message' and body doesn't contain it:\n" + bodyAsString);
                }
            } else {
                // Éxito: compara "message"
                assertEquals("Error message does not match in JSON. Full body:\n" + bodyAsString,
                        expectedMessage, actualMessage);
            }
        } catch (io.restassured.path.json.exception.JsonPathException e) {
            // Significa que el body no parsea como JSON,
            // a pesar de que dice application/json.
            // Buscamos la cadena en el body:
            if (!bodyAsString.contains(expectedMessage)) {
                fail("Expected error message '" + expectedMessage
                        + "', but the body isn't valid JSON and doesn't contain it:\n" + bodyAsString
                        + "\nParsing error was: " + e);
            }
        }
    }

    /**
     * Verifica un campo en JSON usando jsonPath().
     * Asume que la respuesta es JSON.
     */
    public static void assertFieldEquals(Response response, String jsonPath, String expectedValue) {
        String actualValue = response.jsonPath().getString(jsonPath);
        assertEquals("Field value does not match for '" + jsonPath + "'. Body:\n" + response.asString(),
                expectedValue, actualValue);
    }
}

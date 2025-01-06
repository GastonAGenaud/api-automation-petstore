Feature: User Management
  As an API user
  I want to manage user accounts
  So that I can ensure user-related data is accurate

  Background:
    Given the API base URL is configured
    And the user is authenticated with a valid API key

  Scenario: Create a valid user
    Given I have a valid user payload
    When I send a POST request to "/user"
    Then I should receive a status code 200
    And the response should contain pet ID "999"

  Scenario: Login with valid credentials
    Given I have valid user credentials
    When I send a GET request to "/user/login"
    Then I should receive a status code 200
    And the response should contain a valid session token

  Scenario: Get user by username
    Given A user with username "testUser" exists
    When I send a GET request to "/user/testUser"
    Then I should receive a status code 200
    And the response should contain pet ID "999"

  Scenario: Delete a user
    Given A user with username "testUser" exists
    When I send a DELETE request to "/user/testUser"
    Then I should receive a status code 200
    And the response should contain pet ID "999"

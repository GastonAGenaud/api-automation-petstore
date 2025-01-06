Feature: Pet Management
  As an API user
  I want to manage pets in the store
  So that I can ensure proper data integrity

  Background:
    Given the API base URL is configured
    And the user is authenticated with a valid API key

  Scenario: Create a new pet
    Given I have a valid pet payload with name "Tommy" and status "available"
    When I send a POST request to "/pet"
    Then I should receive a status code 200
    And the response should contain the pet name "Tommy"
    And the pet status should be "available"

  Scenario: Update an existing pet
    Given A pet with ID "123" exists in the system
    And I have an updated pet payload with name "TommyUpdated" and status "sold"
    When I send a PUT request to "/pet/123"
    Then I should receive a status code 200
    And the response should contain the pet name "TommyUpdated"
    And the pet status should be "sold"

  Scenario: Retrieve pets by status
    Given I have a valid status query parameter "available"
    When I send a GET request to "/pet/findByStatus" with status param
    Then the response should contain pets with status "available"

  Scenario: Retrieve pets by tags
    Given I have valid tag parameters "cute"
    When I send a GET request to "/pet/findByTags" with tag params
    Then the response should contain pets with the specified tags

  Scenario: Update pet with invalid payload
    Given I have an invalid pet payload missing "name"
    When I send a POST request to "/pet"
    Then I should receive a status code 400
    And the error message should indicate "Invalid input"

  Scenario: Update pet with invalid ID
    Given I have a valid pet payload with name "InvalidPet" and status "available"
    When I send a PUT request to "/pet/{id}" with an invalid ID "invalidId"
    Then I should receive a status code 400
    And the error message should indicate "Invalid ID supplied"

  Scenario: Delete a pet
    Given A pet with ID "123" exists in the system
    When I send a DELETE request to "/pet/123"
    Then I should receive a status code 200
    And the response should contain pet ID "123"

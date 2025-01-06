Feature: Store Order Management
  As an API user
  I want to manage store orders
  So that I can process orders efficiently

  Background:
    Given the API base URL is configured
    And the user is authenticated with a valid API key

  Scenario: Place a valid order
    Given I have a valid order payload
    When I send a POST request to "/store/order"
    Then I should receive a status code 200
    And the order should be created successfully
    And the order details should match the request

  Scenario: Place an invalid order
    Given I have an invalid order payload
    When I send a POST request to "/store/order"
    Then I should receive a status code 400
    And the error message should indicate "Invalid order"

  Scenario: Get order by ID
    Given An order with ID "1" exists
    When I send a GET request to "/store/order/1"
    Then I should receive a status code 200
    And the order details should match the request

  Scenario: Delete an order
    Given An order with ID "1" exists
    When I send a DELETE request to "/store/order/1"
    Then I should receive a status code 200
    And the order should be deleted successfully

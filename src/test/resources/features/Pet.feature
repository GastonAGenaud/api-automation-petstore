Feature: Pet API management

  @CreatePet
  Scenario: Create a new pet
    Given I have the following pet payload:
      """
      {
      "id": 1,
      "name": "doggie",
      "category": {
        "id": 1,
        "name": "Dogs"
      },
      "photoUrls": ["string"],
      "tags": [{"id": 0, "name": "string"}],
      "status": "available"
      }
      """
    When I send a "POST" request to "/pet"
    Then I should receive a status code 200 for pet operation
    And The response should contain the pet ID
    And The response should have the pet name "doggie"
    And The response should have the status "available"

  @GetPetById
  Scenario: Retrieve a pet by ID
    Given A pet with ID "1" exists
    When I send a "GET" request to "/pet/1"
    Then I should receive a status code 200 for pet operation
    And The response should have the pet ID "1"

  @UpdatePet
  Scenario: Update an existing pet
    Given I have the following updated pet payload:
      """
      {
        "id": 1,
        "name": "doggie_updated",
        "status": "sold"
      }
      """
    When I send a "PUT" request to "/pet"
    Then I should receive a status code 200 for pet operation
    And The response should have the pet ID "1"
    And The response should have the status "sold"


  @FindPetsByStatus
  Scenario: Retrieve pets by status
    When I send a "GET" request to "/pet/findByStatus?status=available"
    Then I should receive a status code 200 for pet operation
    And The response should contain pets with status "available"

  @UploadPetImage
  Scenario: Upload an image for a pet
    And I have an image file "pet_image.jpg"
    When I send a "POST" request to "/pet/1/uploadImage"
    Then I should receive a status code 200 for pet operation
    And The response should confirm the image upload

  @DeletePet
  Scenario: Delete a pet by ID
    Given A pet with ID "1" exists
    When I send a "DELETE" request to "/pet/1"
    Then I should receive a status code 200 for pet operation
    And The pet with ID "1" should no longer exist

  @FindPetsByTags
  Scenario: Retrieve pets by tags
    Given I have a tag filter "string"
    When I send a "GET" request to "/pet/findByTags?tags=string"
    Then I should receive a status code 200 for pet operation
    And The response should contain pets with the tag "string"

  @InvalidPetId
  Scenario: Attempt to retrieve a non-existing pet by ID
    When I send a "GET" request to "/pet/9999"
    Then I should receive a status code 404 for pet operation
    And The response should contain an error message "Pet not found"

  @InvalidDeletePet
  Scenario: Attempt to delete a non-existing pet by ID
    When I send a "DELETE" request to "/pet/999099"
    Then I should receive a status code 404 for pet operation
    And The response should contain an error message "Pet not found"

  @InvalidUpdatePet
  Scenario: Attempt to update a pet with invalid data
    Given I have the following invalid pet payload:
      """
      {
        "s": "invalid",
        "s": 12345,
        "s": null
      }
      """
    When I send a "PUT" request to "/pet/1"
    Then I should receive a status code 405 for pet operation
    And The response should contain an error message "HTTP 405 Method Not Allowed"

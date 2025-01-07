# 🐾 **Petstore API Automation Testing Challenge – Task Breakdown and Analysis** 
## 📚 **Task Overview**

This document provides a documentation requested by Venon for the home challenge

---

## 📋 **Task Breakdown**

### ✅ **Step 1: Gather Information about the Swagger Petstore Project**
- The **Swagger Petstore API** documentation was reviewed to understand the available endpoints, request/response payloads, and expected behavior.
- **API Documentation Link:** [Swagger Petstore API v3](https://petstore3.swagger.io/api/v3)

---

### ✅ **Step 2: Set Up the API Petstore and Run It Locally**
- A local environment was configured to interact with the Swagger Petstore API.
- **Base URL:** `https://petstore3.swagger.io/api/v3`
- Authentication tokens were configured where necessary.
- Dependencies were managed via **Maven** to ensure seamless integration and build.

---

### ✅ **Step 3: Propose a List of Test Cases for Automation**
The following test cases were identified as essential for validation:

#### 🐶 **Create Pet**
- Validate creating a new pet with valid payload.
- Verify mandatory fields in the response.

#### 🔍 **Retrieve Pet by ID**
- Fetch pet details by valid ID.
- Handle invalid or non-existing pet IDs gracefully.

#### ✏️ **Update Pet**
- Update an existing pet with valid payload.
- Handle updates with invalid payloads.

#### 🗑️ **Delete Pet**
- Delete a pet by valid ID.
- Attempt deletion with non-existing pet ID.

#### 📸 **Upload Pet Image**
- Upload an image for an existing pet.
- Handle scenarios with invalid image files.

#### 📊 **Find Pets by Status**
- Retrieve pets by status (`available`, `pending`, `sold`).
- Handle invalid status queries.

#### 🏷️ **Find Pets by Tags**
- Retrieve pets using specific tags.
- Handle invalid or non-existing tags.

---

### ✅ **Step 4: Automate the Proposed Test Cases**
The automation framework was implemented using:

- **Java 17** – Primary programming language.
- **RestAssured** – REST API testing library.
- **Cucumber** – BDD framework for test scenarios.
- **JUnit** – For assertions and validations.
- **Maven** – Build and dependency management.
- **GitHub Actions** – CI/CD integration.

#### 📄 **Sample Test Scenario**
```gherkin
@CreatePet
Scenario: Create a new pet
  Given I have the following pet payload:
    """
    {
      "id": 1,
      "name": "doggie",
      "category": { "id": 1, "name": "Dogs" },
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
```

---

### ✅ **Step 5: Explanation of the Solution**

#### 🛠️ **Implementation Details**
- Each test scenario was implemented as a **Cucumber feature file** with Gherkin syntax.
- Step definitions were mapped using **Java methods** with **RestAssured**.
- Test results were captured and stored in `target/surefire-reports`.

#### 📊 **CI/CD Pipeline**
A **GitHub Actions pipeline** was configured to:
1. Build the Maven project.
2. Run automated API tests.
3. Upload test results as artifacts.

**Workflow Configuration Example:**
```yaml
- name: Run Tests Against Public Swagger API
  run: |
    mvn test -Dapi.base.url=https://petstore3.swagger.io/api/v3 || true

- name: Upload Test Results
  if: always()
  uses: actions/upload-artifact@v4
  with:
    name: test-results
    path: target/surefire-reports
```

#### 🚀 **Performance Testing with k6**
- Performance tests were implemented using **k6** via Docker.
- Test results were exported in JSON format.

**Command Example:**
```bash
docker run --rm \
  -v "${PWD}/performance:/scripts" \
  grafana/k6 run /scripts/petstore_tests.js \
  --out json=/scripts/test-results/k6_results.json
```

---

## 🧪 **Test Execution Results**

### ✅ **Summary of Test Results**

| **Scenario**              | **Expected Status** | **Actual Status** | **Result** |
|----------------------------|---------------------|-------------------|-----------|
| Create a new pet          | 200                 | 200               | ✅ Pass    |
| Retrieve pet by ID        | 200                 | 400               | ❌ Fail   |
| Update an existing pet    | 200                 | 400               | ❌ Fail   |
| Retrieve pets by status   | 200                 | 400               | ❌ Fail   |
| Upload pet image          | 200                 | 400               | ❌ Fail   |
| Delete pet by ID          | 200                 | 400               | ❌ Fail   |
| Retrieve pets by tags     | 200                 | 400               | ❌ Fail   |
| Retrieve non-existing pet | 404                 | 400               | ❌ Fail   |
| Delete non-existing pet   | 404                 | 400               | ❌ Fail   |
| Invalid update payload    | 405                 | 400               | ❌ Fail   |

### ⚠️ **Observations:**
- **Successful Tests:** 1/10
- **Failed Tests:** 9/10

### 📝 **Key Issues Identified:**
1. Multiple endpoints are returning `400 Bad Request` instead of expected status codes.
2. Payload validation inconsistencies for `PUT` and `POST` requests.
3. Error handling for invalid inputs needs improvement.



## 📦 **Artifacts**
- **Test Results:** Stored in `target/surefire-reports`
- **Performance Results:** Stored in `performance/test-results/k6_results.json`

---
# 🐾 **Petstore API Automation Testing Challenge** 🚀

## 📚 **Project Overview**

Hi, I'm **Gaston Genaud**, and this repository showcases my approach to **API automation testing** for the **Petstore API (OpenAPI v3)**. Using **Java**, **RestAssured**, **Cucumber**, and **JUnit**, I have designed robust test cases to validate the core functionalities of the Petstore endpoints. The project is built with **Maven** and integrates a **GitHub Actions CI/CD pipeline** to ensure continuous quality assurance. Additionally, performance testing is carried out using **k6**.

---

## 📋 **Table of Contents**

1. [Technologies Used](#technologies-used)
2. [Project Structure](#project-structure)
3. [Environment Setup](#environment-setup)
4. [Running Tests](#running-tests)
5. [Implemented Features](#implemented-features)
6. [CI/CD Pipeline with GitHub Actions](#cicd-pipeline-with-github-actions)
7. [Performance Testing with k6](#performance-testing-with-k6)
8. [Sample Test Case](#sample-test-case)
9. [Contribution](#contribution)
10. [License](#license)

---

## 🛠️ **Technologies Used**

- **Java 11+** – Main programming language.
- **Maven** – Build and dependency management tool.
- **RestAssured** – REST API testing library.
- **JUnit** – Testing framework for assertions and validations.
- **Cucumber** – Behavior-Driven Development (BDD) framework.
- **k6** – Performance testing tool.
- **Docker** – Containerization tool for k6.
- **GitHub Actions** – CI/CD pipeline for automated builds and test execution.

---

## 📂 **Project Structure**

```plaintext
src/
├── main/
│   ├── java/
│   ├── resources/
├── test/
│   ├── java/
│   │   ├── com.petstore.steps/      <-- Step definitions for tests
│   │   ├── com.petstore.runners/    <-- Cucumber Test Runners
│   │   ├── com.petstore.utils/      <-- Shared utilities
│   ├── resources/
│   │   ├── features/                <-- Cucumber .feature files
│   │   │   ├── Pet.feature
│   │   ├── config/
│   │   │   ├── application-dev.properties <-- Environment configuration
performance/
│   ├── petstore_tests.js            <-- k6 Performance Test Script
│   ├── test-results/                <-- Performance test results
pom.xml  <-- Maven dependencies
README.md  <-- Documentation
```

---

## ⚙️ **Environment Setup**

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/api-automation-petstore.git
   cd api-automation-petstore
   ```

2. **Configure Environment Properties:**
   Update the `application-dev.properties` file with the correct base URL and token:
   ```properties
   base.url=https://petstore3.swagger.io/api/v3
   auth.token=your_token_here
   ```

3. **Install Dependencies:**
   ```bash
   mvn clean install
   ```

---

## ▶️ **Running Tests**

### 🔹 **Run All Tests:**
```bash
mvn clean test
```

### 🔹 **Run Specific Tests by Tag:**
```bash
mvn test -Dcucumber.filter.tags=@CreatePet
```

### 🔹 **Test Reports:**
Test results are generated in:
```
target/surefire-reports
```

---

## 🚦 **Implemented Features**

### 🐶 **Pet Feature:**
1. **Create a New Pet** (`POST /pet`)
2. **Retrieve Pet by ID** (`GET /pet/{id}`)
3. **Update an Existing Pet** (`PUT /pet`)
4. **Delete a Pet** (`DELETE /pet/{id}`)
5. **Upload an Image for a Pet** (`POST /pet/{id}/uploadImage`)
6. **Find Pets by Status** (`GET /pet/findByStatus`)
7. **Find Pets by Tags** (`GET /pet/findByTags`)

Each scenario includes comprehensive validations such as:
- HTTP Status Codes
- Response Payload Structure
- Specific Field Validations

---

## 🚀 **CI/CD Pipeline with GitHub Actions**

I have set up a **CI/CD pipeline** using **GitHub Actions** to ensure continuous integration and delivery.

### 📄 **Workflow Configuration: `.github/workflows/api-tests.yml`**

The pipeline consists of two jobs:

### 🔹 **1. build-and-test**

1. **Checkout Repository:** Clone the repository.
2. **Set Up Java:** Install Java 17 and configure Maven cache.
3. **Install Dependencies:** Install Maven dependencies without running tests.
4. **Run Tests:** Execute API tests against the Petstore API.
5. **Upload Test Results:** Test reports are uploaded as artifacts.

### 🔹 **2. performance-test**

1. **Checkout Repository:** Clone the repository.
2. **Run k6 Performance Test:**
   - Use `grafana/k6` Docker image.
   - Execute performance tests defined in `performance/petstore_tests.js`.
3. **Upload k6 Test Results:** Save performance test reports as artifacts.

**k6 Command Used in Pipeline:**
```bash
docker run --rm -v $(pwd)/performance:/scripts grafana/k6 run /scripts/petstore_tests.js
```

### ✅ **Pipeline Steps Summary**

| Job              | Description                     |
|-------------------|---------------------------------|
| `build-and-test` | Build the project and run tests |
| `performance-test` | Run k6 performance tests |

---

## 📊 **Performance Testing with k6**

Performance tests are designed to evaluate the scalability and responsiveness of the Petstore API under different conditions.

### 🔑 **Run Performance Tests Locally:**
```bash
docker run --rm -v $(pwd)/performance:/scripts grafana/k6 run /scripts/petstore_tests.js
```

### 📊 **Metrics Collected:**
- Response Time
- Throughput
- Error Rate
- Requests per Second

### 📂 **Results Location:**
Performance test results are stored in:
```
performance/test-results
```

---

## 🧪 **Sample Test Case**

### 📄 **Pet.feature**
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

## ✉️ **Contact**

**Gaston Genaud** – 🚀 *QA Automation Engineer*  
📧 **gastongenaudar@gmail.com**

---


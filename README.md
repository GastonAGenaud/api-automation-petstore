# 🐾 **Petstore API Automation Testing Challenge** 🚀

## 📚 **Project Overview**

Hi, I'm **Gaston Genaud**, and this repository showcases my approach to **API automation testing** for the **Petstore API (OpenAPI v3)**. Using **Java**, **RestAssured**, **Cucumber**, and **JUnit**, I have designed robust test cases to validate the core functionalities of the Petstore endpoints. The project is built with **Maven** and integrates a **GitHub Actions CI/CD pipeline** to ensure continuous quality assurance.

---

## 📋 **Table of Contents**

1. [Technologies Used](#technologies-used)
2. [Project Structure](#project-structure)
3. [Environment Setup](#environment-setup)
4. [Running Tests](#running-tests)
5. [Implemented Features](#implemented-features)
6. [CI/CD Pipeline with GitHub Actions](#cicd-pipeline-with-github-actions)
7. [Sample Test Case](#sample-test-case)
8. [Contribution](#contribution)
9. [License](#license)

---

## 🛠️ **Technologies Used**

- **Java 11+** – Main programming language.
- **Maven** – Build and dependency management tool.
- **RestAssured** – REST API testing library.
- **JUnit** – Testing framework for assertions and validations.
- **Cucumber** – Behavior-Driven Development (BDD) framework.
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
```yaml
name: API Automation Tests

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout Code
      uses: actions/checkout@v3

    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'

    - name: Install Dependencies
      run: mvn clean install

    - name: Run Tests
      run: mvn test
```

### ✅ **Pipeline Steps:**
1. Clone the Repository
2. Set Up Java Environment
3. Install Dependencies
4. Execute Tests
5. Generate Test Reports

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
gastongenaudar@gmail.com

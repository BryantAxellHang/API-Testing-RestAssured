# Test Plan for TestNG Program

This document outlines detailed test cases for the TestNG Program described in `readme.md`. These tests are designed to ensure the correctness and robustness of the sample Java project using TestNG.

## 1. Project Structure Verification

### Test Case 1.1: Directory Structure Exists

- **Objective:** Ensure the project contains the expected directories and files.
- **Steps:**
  1. Check for the existence of `src/main/`, `src/test/`, `pom.xml`, and `readme.md`.
- **Expected Result:** All listed files and directories exist.

## 2. Build and Dependency Verification

### Test Case 2.1: Maven Build Success

- **Objective:** Verify that the project builds successfully using Maven.
- **Steps:**
  1. Run `mvn clean install` in the project root.
- **Expected Result:** Build completes without errors.

### Test Case 2.2: TestNG Dependency Present

- **Objective:** Ensure TestNG is included as a dependency.
- **Steps:**
  1. Open `pom.xml`.
  2. Check for a `<dependency>` entry for TestNG.
- **Expected Result:** TestNG dependency is present.

## 3. Example TestNG Test Cases

### Test Case 3.1: Example Test Class Exists

- **Objective:** Confirm that at least one TestNG test class exists in `src/test/`.
- **Steps:**
  1. Navigate to `src/test/`.
  2. Check for Java files containing `@Test` annotations.
- **Expected Result:** At least one test class with TestNG tests exists.

### Test Case 3.2: Example Test Runs Successfully

- **Objective:** Ensure the example test(s) pass.
- **Steps:**
  1. Run `mvn test`.
- **Expected Result:** All tests pass.

## 4. IDE Integration

### Test Case 4.1: Import Project into IDE

- **Objective:** Verify the project can be imported into IntelliJ IDEA or Eclipse.
- **Steps:**
  1. Import the project as a Maven project.
- **Expected Result:** No import errors; project structure is recognized.

## 5. Java Version Compatibility

### Test Case 5.1: Java Version Check

- **Objective:** Ensure the project runs on Java 8 or higher.
- **Steps:**
  1. Set JAVA_HOME to Java 8+.
  2. Build and run tests.
- **Expected Result:** No version-related errors.

---

**Note:** Update this file as new features or tests are added to the project.

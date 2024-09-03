# Rekindle Test Automation Portfolio

Welcome to the **Rekindle Test Automation Portfolio**. This repository showcases a collection of test automation modules designed to validate the **[Rekindle Book Store API](https://github.com/amfibolos/rekindle-book-store)**. The tests cover various aspects of the application, leveraging different technologies and frameworks to ensure robustness and reliability.

## Overview

The portfolio includes test modules for the Rekindle Book Store application, which is built with a range of technologies. Each module demonstrates how to perform API-level testing using different programming languages and frameworks:

1. **Kotlin Module**
2. **C# Module**
3. **TypeScript Module**

## Modules

### 1. Kotlin Module

**Technologies Used:**
- **[Kotlin](https://kotlinlang.org/)**: Modern, statically typed programming language.
- **[Gradle](https://gradle.org/)**: Build automation tool.
- **[JUnit 5](https://junit.org/junit5/)**: Testing framework for Java.
- **[Rest Assured](https://rest-assured.io/)**: Library for API testing.
- **[Guice](https://github.com/google/guice)**: Dependency injection framework.
- **[Allure Report](https://allurereport.org/)**: Reporting framework for generating comprehensive test reports.

**Highlights:**
- API tests for bookstore and product services.
- Emphasis on clean and maintainable test code.
- Usage of Kotlin’s features for concise and expressive test definitions.

### 2. C# Module

**Technologies Used:**
- **[C#](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)**: Object-oriented programming language.
- **[.NET 8](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)**: Development platform.
- **[NUnit](https://nunit.org/)**: Testing framework for .NET applications.
- **[RestSharp](https://restsharp.dev/)**: HTTP client library for API testing.
- **[Unity Microsoft Dependency Injection](https://github.com/unitycontainer/microsoft-dependency-injection)**: Dependency injection framework.
- **[Allure Report](https://allurereport.org/)**: Reporting framework for test results.

**Highlights:**
- Comprehensive API tests for various endpoints.
- Focus on ensuring the correct behavior of the Bookstore and Product services.
- Utilizes NUnit’s capabilities for structured and readable test cases.

### 3. TypeScript Module

**Technologies Used:**
- **[TypeScript](https://www.typescriptlang.org/)**: Superset of JavaScript with type safety.
- **[Node.js](https://nodejs.org/en)**: JavaScript runtime environment.
- **[Jest](https://jestjs.io/)**: Testing framework with a focus on simplicity.
- **[Tsyringe](https://github.com/microsoft/tsyringe)**: Dependency injection library.
- **[Axios](https://axios-http.com/docs/intro)**: HTTP client for making API requests.
- **[Allure Report](https://allurereport.org/)**: Reporting framework for test results.

**Highlights:**
- API-level testing with a focus on TypeScript’s type safety and Jest’s test runner.
- Comprehensive coverage of the Bookstore and Product services.
- Integration of Axios for HTTP requests and Tsyringe for dependency injection.

## Code Quality and Best Practices

Test modules emphasize **clean, reusable code** and incorporate **dependency injection** (DI) to enhance **Inversion of Control (IoC)** and reduce **tight coupling**. By leveraging DI frameworks such as Guice for Kotlin, Unity for C#, and Tsyringe for TypeScript, the modules ensure that dependencies are managed efficiently, promoting flexibility and easier maintenance. This approach allows for better test isolation, modularity, and the ability to easily swap out implementations without affecting the test logic, ultimately leading to more maintainable and robust test suites.

## Getting Started

### Prerequisites

For each module, you will need the following:

- **Java Module:** [Java 21](https://corretto.aws/downloads/resources/21.0.1.12.1/amazon-corretto-21.0.1.12.1-windows-x64-jdk.zip), [Gradle 8.5](https://services.gradle.org/distributions/gradle-8.5-bin.zip)
- **C# Module:** [.NET 8](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)
- **TypeScript Module:** [Node.js](https://nodejs.org/en), [npm](https://www.npmjs.com/)

### Running the Tests

Each module has its own instructions for running tests. Below are the general steps:

1. **Kotlin Module**
    - Navigate to the module directory.
    - Use Gradle commands to build and run tests:
      ```bash
      gradle build
      gradle test
      ```

2. **C# Module**
    - Open the solution in Visual Studio or another compatible IDE.
    - Build the solution and run tests using NUnit Test Runner.
      ```bash
      dotnet build
      dotnet test
      ```

3. **TypeScript Module**
    - Navigate to the module directory.
    - Install dependencies and run tests:
      ```bash
      npm install
      npm test
      ```

## Reporting

All modules are configured to generate reports using [Allure Report](https://allurereport.org/). To view the reports:

1. Generate the report with the appropriate command for each module.
2. Open the report in your web browser.
    ```bash
   allure generate
   allure serve
      ```

## License

This project is licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0).

---

# Rule Engine Application

## Overview

This is a simple 3-tier rule engine application developed using Spring Boot and MongoDB. The application determines user eligibility based on attributes like age, department, income, and spending. It utilizes an Abstract Syntax Tree (AST) to represent conditional rules, allowing for dynamic creation, combination, and modification of these rules.

## Features

- **Dynamic Rule Creation**: Users can create rules based on various conditions.
- **Rule Combination**: Combine multiple rules into a single rule using logical operators.
- **Rule Evaluation**: Evaluate rules against user attributes to determine eligibility.
- **RESTful API**: Expose API endpoints for rule management and evaluation.
- **MongoDB Integration**: Store and retrieve rules and user attributes.

## Technologies Used

- **Java**
- **Spring Boot**
- **Maven**
- **MongoDB**


## Getting Started

To get a local copy up and running follow these simple steps:

### Clone the repository
1. Clone
    ```bash
    https://github.com/sujanM14/Zeotap_assign1.git

2. Navigate to the project directory
3. Build the project
    Use Maven to build the project:
        ```bash
        mvn clean install

4. Configure MongoDB
5. Ensure your MongoDB server is running and configure the connection details in src/main/resources/application.properties:
       ```bash
       spring.data.mongodb.uri=mongodb://localhost:27017/your_database_name

### Run the application
1. You can run the application using the following command:

    ```bash
    mvn spring-boot:run


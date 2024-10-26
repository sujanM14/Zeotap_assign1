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

- **Java**: [version]
- **Spring Boot**: [version]
- **Maven**: [version]
- **MongoDB**: [version]
- **Other Dependencies**: [e.g., Spring Data JPA, Lombok, etc.]

## Prerequisites

Before you begin, ensure you have met the following requirements:

- JDK [version] installed on your machine.
- Maven [version] installed on your machine.
- MongoDB server running on your local machine or accessible remotely.

## Getting Started

To get a local copy up and running follow these simple steps:

### Clone the repository

```bash
git clone https://github.com/yourusername/rule-engine.git](https://github.com/sujanM14/Zeotap_assign1.git

Navigate to the project directory
Build the project
Use Maven to build the project:
```bash
mvn clean install

Configure MongoDB
Ensure your MongoDB server is running and configure the connection details in src/main/resources/application.properties:
spring.data.mongodb.uri=mongodb://localhost:27017/your_database_name

### Run the application
You can run the application using the following command:

```bash
mvn spring-boot:run


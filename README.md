# BankFlow

BankFlow is a **Spring Boot-based banking application** that provides REST APIs for managing customers, bank accounts, and transactions. The project demonstrates backend development using Java, Spring Boot, JPA, Hibernate, and MySQL.

## Features

* Customer management
* Bank account creation and management
* Automatic account number generation
* Deposit and withdrawal operations
* Money transfer between accounts
* Transaction management
* Input validation
* Exception handling
* Database persistence
* RESTful API architecture

## Tech Stack

* **Java**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Hibernate**
* **MySQL**
* **Maven**
* **Bean Validation**

## API Endpoints

```text
CUSTOMER APIs
POST   /customers
GET    /customers
GET    /customers/{id}
PUT    /customers/{id}
DELETE /customers/{id}

ACCOUNT APIs
POST   /accounts
GET    /accounts
GET    /accounts/{id}
GET    /accounts/customer/{customerId}
DELETE /accounts/{id}

BANKING OPERATIONS
POST   /accounts/{accountId}/deposit
POST   /accounts/{accountId}/withdraw
POST   /accounts/transfer

TRANSACTION APIs
GET    /transactions
GET    /transactions/{id}
GET    /transactions/account/{accountId}
```

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── ...
│   └── resources/
│       └── application.properties
└── test/
```

The application follows a **layered architecture** with controllers, services, repositories, and entities.

## Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/tusharpauria/BankFlow.git
```

### 2. Configure MySQL

Create a MySQL database and update the database configuration in:

```text
src/main/resources/application.properties
```

### 3. Start the application

Run the Spring Boot application from IntelliJ IDEA or use:

```bash
mvn spring-boot:run
```

The APIs can be tested using **Postman** or any REST API client.

## Purpose

BankFlow was built to practice and demonstrate **Java backend development**, including REST API development, business logic, database integration, JPA/Hibernate, validation, and exception handling.

## Author

**Tushar Pauria**

GitHub: https://github.com/tusharpauria

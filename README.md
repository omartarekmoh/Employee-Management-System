# Demo Spring Boot Employee & Department Management API

## Overview

This project is a Spring Boot RESTful API for managing employees and departments, with user authentication and role-based access control. It supports CRUD operations for employees and departments, user login with JWT authentication, and daily summaries of employee counts per department.

## Features

- User authentication with JWT (JSON Web Token)
- Role-based access control (ADMIN, USER)
- CRUD operations for Employees and Departments
- Daily scheduled summary of employee counts per department
- PostgreSQL database with Flyway migrations and seed data
- Exception handling for validation, authorization, and database errors
- Comprehensive unit and integration tests

## Technologies Used

- Java 21
- Spring Boot 3.5.3
- Spring Data JPA
- Spring Security
- JWT (io.jsonwebtoken)
- PostgreSQL
- Flyway (database migrations)
- Lombok
- JUnit 5, Mockito (testing)

## Getting Started

### Prerequisites

- Java 21+
- Maven
- PostgreSQL

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/omartarekmoh/Employee-Management-System
   cd demo
   ```
2. **Configure the database:**
   Edit `src/main/resources/application.properties` and set your PostgreSQL connection:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
   spring.datasource.username=your_user
   spring.datasource.password=your_password
   jwt.secret=your_very_long_secret_key_at_least_32_chars
   jwt.expiration=3600000 # 1 hour in ms
   ```
3. **Run database migrations:**
   Flyway will auto-run on app startup to create tables and seed data.

4. **Build and run the application:**
   ```bash
   ./mvnw spring-boot:run
   # or
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## API Endpoints

### Authentication

- `POST /api/auth/login` — Login with email and password. Returns JWT token.
  - Request: `{ "email": "user@company.com", "password": "yourpassword" }`
  - Response: `{ "token": "<JWT>" }`

### Departments

- `GET /api/departments` — List all departments (USER, ADMIN)
- `GET /api/departments/{id}` — Get department by ID (USER, ADMIN)
- `POST /api/departments` — Create department (ADMIN)
- `PUT /api/departments/{id}` — Update department (ADMIN)
- `DELETE /api/departments/{id}` — Delete department (ADMIN)

### Employees

- `GET /api/employees` — List all employees (USER, ADMIN)
- `GET /api/employees/{id}` — Get employee by ID (USER, ADMIN)
- `POST /api/employees` — Create employee (ADMIN)
- `PUT /api/employees/{id}` — Update employee (ADMIN)
- `DELETE /api/employees/{id}` — Delete employee (ADMIN)

### Daily Summary

- A scheduled job runs daily at 9:00 AM to log and store the count of employees per department.

## Authentication & Authorization

- All endpoints (except `/api/auth/login`) require a valid JWT in the `Authorization: Bearer <token>` header.
- Roles:
  - **ADMIN**: Full access to all endpoints
  - **USER**: Can only view (GET) departments and employees
- Example request with JWT:
  ```bash
  curl -H "Authorization: Bearer <your_token>" http://localhost:8080/api/employees
  ```

## Error Handling

- Validation errors, unauthorized access, forbidden actions, and not found resources return structured JSON error responses.

## Database

- Uses PostgreSQL. Schema and seed data are managed by Flyway migrations in `src/main/resources/db/migrations/`.

## Testing

- Run all tests:
  ```bash
  ./mvnw test
  # or
  mvn test
  ```
- Tests include unit and integration tests for controllers and services.

## Project Structure

- `src/main/java/com/example/demo/controller/` — REST controllers
- `src/main/java/com/example/demo/model/` — JPA entities
- `src/main/java/com/example/demo/dto/` — Data transfer objects
- `src/main/java/com/example/demo/services/` — Service interfaces and implementations
- `src/main/java/com/example/demo/repository/` — Spring Data JPA repositories
- `src/main/java/com/example/demo/security/` — Security configuration and filters
- `src/main/resources/db/migrations/` — Flyway migration scripts


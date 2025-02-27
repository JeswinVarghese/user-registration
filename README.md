# User Registration API

## Overview

This is a Spring Boot application for user registration with authentication and basic role-based access control.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database** 
- **Docker**
- **Swagger**

## Running the Application with Docker

### Prerequisites

- Docker

### Steps to Run

1. Clone the Repository:

   ```sh
   git clone <repository-url>
   ```
   
2. Build the application JAR file:

   ```sh
   mvn clean package

3. Build the Docker image:

   ```sh
   docker build -t user-registration-app .

4. Run the Docker container:
   ```sh
   docker run -p 8080:8080 user-registration-app
   ```
4. Access the Application: <br>
   <br>
   Swagger UI end-point : 
     ```sh
     http://localhost:8080/swagger-ui.html
     ```
   Admin Credentials :
    ```sh
     Email: admin@example.com 
     Password: admin123
     ```

### API Documentation

Swagger UI end-point
  ```sh
  http://localhost:8080/swagger-ui.html
  ```
## API Endpoints

###  User Registration

**POST** `/api/users/register`

#### Request Body:
```json
{
  "name": "Daniel J",
  "email": "daniel@example.com",
  "gender": "Male",
  "password": "SecurePass123"
}
```
#### Response :
```json
{
  "name": "Daniel J",
  "email": "daniel@example.com",
  "gender": "Male",
  "country": "India"
}
```

###  Validate User

**POST** `/api/users/validate?email=daniel@example.com&password=SecurePass123`

#### Response :
```sh
Valid User
```

###  Get All Users (Admin Only)

**GET** `/api/users/all`

#### Response :
```json
[
  {
    "name": "Daniel J",
    "email": "daniel@example.com",
    "gender": "Male",
    "country": "India"
  },
  {
    "name": "Samuel Smith",
    "email": "samuel@example.com",
    "gender": "Male",
    "country": "USA"
  }
]

```

###  Delete User (Admin Only)

**DELETE** `/api/users/delete/daniel@example.com`

#### Response :
```sh
User with email daniel@example.com has been successfully deleted.
```




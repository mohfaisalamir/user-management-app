# User Management API Documentation

## Overview
A RESTful API built using **Spring Boot** for managing user data, providing CRUD functionalities, pagination, and sorting.

## Features
1. **Create User** (`POST /api/user`)
2. **Get User by ID** (`GET /api/user/{id}`)
3. **Get All Users** (`GET /api/user`)
4. **Update User** (`PUT /api/user`)
5. **Delete User** (`DELETE /api/user/{id}`)

---

## API Endpoints

### 1. Create User
- **Method:** `POST`
- **Endpoint:** `/api/user`
- **Description:** Create a new user.
- **Request Body:**
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```
- **Response:**
```json
{
  "message": "Successfully created user",
  "statusCode": 201,
  "data": {
    "id": "string",
    "name": "string",
    "email": "string"
  }
}
```

### 2. Get User by ID
- **Method:** `GET`
- **Endpoint:** `/api/user/{id}`
- **Description:** Retrieve a user by their ID.
- **Response:**
```json
{
  "message": "Successfully get user by id",
  "statusCode": 200,
  "data": {
    "id": "string",
    "name": "string",
    "email": "string"
  }
}
```

### 3. Get All Users
- **Method:** `GET`
- **Endpoint:** `/api/user`
- **Description:** Retrieve a paginated list of users.
- **Query Parameters:**
    - `page` (default: 1)
    - `size` (default: 10)
    - `direction` (default: asc)
    - `sortBy` (default: name)
- **Response:**
```json
{
  "message": "Successfully get all users",
  "statusCode": 200,
  "data": [
    {
      "id": "string",
      "name": "string",
      "email": "string"
    }
  ]
}
```

### 4. Update User
- **Method:** `PUT`
- **Endpoint:** `/api/user`
- **Description:** Update user details.
- **Request Body:**
```json
{
  "id": "string",
  "name": "string",
  "email": "string"
}
```
- **Response:**
```json
{
  "message": "Successfully updated user",
  "statusCode": 200,
  "data": {
    "id": "string",
    "name": "string",
    "email": "string"
  }
}
```

### 5. Delete User
- **Method:** `DELETE`
- **Endpoint:** `/api/user/{id}`
- **Description:** Delete a user by their ID.
- **Response:**
```json
{
  "message": "Successfully deleted user",
  "statusCode": 200,
  "data": "OK"
}
```

---

## Testing Steps for User/HRD (Using Postman)
1. **Start the Application:** Ensure the backend is running on `http://localhost:8080`.
2. **Open Postman:** Use Postman to interact with the API.
3. **Create a User:** Send a `POST` request to `/api/user` with valid JSON data.
4. **Get User by ID:** Use `GET /api/user/{id}` to verify the created user.
5. **Get All Users:** Use `GET /api/user` to retrieve all users.
6. **Update User:** Send a `PUT` request to `/api/user` with updated user data.
7. **Delete User:** Use `DELETE /api/user/{id}` to remove a user.

---

## Technologies Used
- Spring Boot
- PostgreSQL
- Lombok
- Spring Data JDBC
- Spring Cache
- Redis
- Jedis

---

## Author
- **Name:** Moh. Faisal Amir
- **Email:** mohfaisalamir94@gmail.com
- **GitHub:** [mohfaisalamir](https://github.com/mohfaisalamir)

---

**Happy Testing with Postman! 🚀**

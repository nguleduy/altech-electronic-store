# Electronic Store API

## 1. Overview

The Electronic Store API contains following components:

| Component  | URL                                         | Description                | Language                                                                              |
|------------|---------------------------------------------|----------------------------|---------------------------------------------------------------------------------------|
| API        | http://localhost:8080                       | Electronic Store API.      | Java, Spring, JPA, Flyway, Lombok, Log4j2, Swagger, JUnit, Mokito, Checkstyle, Jacoco |
| Database   |                                             | Electronic Store Database. | Postgres                                                                              |
| Swagger UI | http://localhost:8080/swagger-ui/index.html | API documentation.         | HTML                                                                                  |

## 2. Directory structure

```plaintext
   project-root/
   ├── codequality                                      # Checkstyle configuration
   ├── gradle                                           # Gradle wrapper, manage library versions
   └── src/
      ├── main/java/com/example/altech/                 # Spring Boot application source files
      └── main/resources/
         ├── db/migration/                              # Flyway migration scripts
         ├── postman                                    # Postman collection
         └── application.properties                     # Spring Boot application configuration
         └── log4j2.xml                                 # Log4j2 configuration
   ├── build.gradle                                     # Gradle build script
   ├── Dockerfile                                       # Dockerfile
   ├── docker-compose.yml                               # Docker Compose configuration
   └── README.md                                        # Project documentation
   ```

## 3. Installation

### 3.1. Prerequisites

- JDK >= 17
- Docker
- Gradle >= 7.5

### 3.2. Build

#### 3.2.1. Start project

```shell
  docker compose up --build -d
```

#### 3.2.2. Stop project

```shell
  docker compose down
```
#### 3.2.3. Default credentials
- Admin user: `admin / P@ssw0rd`
- Customer user: `customer / P@ssw0rd`

## 4. API Endpoint
Once the service is up, the following endpoints will be available:
- `Private` endpoints can only be accessed with a `Bearer access token`.
- A user with the role of `ADMIN` or `CUSTOMER` can access `Admin` and `Customer` endpoints.
- A user with the role of `CUSTOMER` can only access `Customer` endpoints.

### Authentication
| Endpoints             | Method | Type    | Description                                                       |
|-----------------------|--------|---------|-------------------------------------------------------------------|
| /api/v1/auth/login    | POST   | Public  | Login to get access token                                         |
| /api/v1/auth/refresh  | POST   | Public  | Use `Referer Header` with refresh token to re-generate access token |
| /api/v1/auth/register | POST   | Public  | Register a new user                                               |

### Admin
| Endpoints                             | Method | Type    | Description                                  |
|-------------------------------------|--------|---------|----------------------------------------------|
| /api/v1/admin/products              | GET    | Private | Return a list of all products in the store   |
| /api/v1/admin/products              | POST   | Private | Create a new product in the store            |
| /api/v1/admin/products/{id}         | GET    | Private | Return the details of a product in the store |
| /api/v1/admin/products/{id}         | DELETE | Private | Remove a product from the store              |
| /api/v1/admin/discounts/{productId} | GET    | Private | Return a list of discounts for a product     |
| /api/v1/admin/discounts/{productId} | POST   | Private | Add a discount to a product                  |

### Customer
| Endpoints                                           | Method | Type    | Description                    |
|---------------------------------------------------|--------|---------|--------------------------------|
| /api/v1/customer/baskets/{customerId}/{productId} | POST   | Private | Add an item to the basket      |
| /api/v1/customer                                  | GET    | Private | Get all customers              |
| /api/v1/customer/baskets/{customerId}             | GET    | Private | Get the basket for a customer  |
| /api/v1/customer/baskets/{customerId}/receipt     | GET    | Private | Get the receipt for a customer |
| /api/v1/customer/baskets/{customerId}/{basketId}  | DELETE | Private | Remove an item from the basket |

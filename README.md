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
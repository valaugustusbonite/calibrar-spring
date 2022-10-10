# Spring Calibrar (Identity Service)

A backend microservice that is responsible for handling users of the system. Built using Spring Boot.
## Table of  Contents
* [Getting Started](#getting-started)
* [Project Structure](#folder-structure)
* [Git Workflow](#git-workflow)
* [Testing](#testing)

## Getting Started
A few resources to get you started if this is your first Spring Boot project:
- Overview: https://spring.io/projects/spring-framework
- Spring Beans: https://www.baeldung.com/spring-bean
- Dependency Injection (DI):
  - https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring
  - https://www.tutorialspoint.com/spring/spring_dependency_injection.htm
  - https://www.mkyong.com/spring/spring-dependency-injection-di/
- Autowiring:
  - https://www.baeldung.com/spring-autowire
  - https://www.tutorialspoint.com/spring/spring_beans_autowiring.html
- Persistence:
  -  https://www.baeldung.com/persistence-with-spring-series
- Spring Security
  - https://www.baeldung.com/spring-security-login
  - https://www.baeldung.com/spring-security-basic-authentication
  - https://spring.io/guides/gs/securing-web/
  - https://www.baeldung.com/get-user-in-spring-security

### Starting Development
- see [Application Properties](#application-properties)
- `mvn clean spring-boot:run` to run development
### Database
- This project uses PostgresQL as its database
- Postgres download: https://www.postgresql.org/download/
- pgAdmin: https://www.pgadmin.org/download/

### ORM
- This project uses Spring Data JPA to communicate with the database
- Overview: https://spring.io/projects/spring-data-jpa
- Other Resources: https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa

### Application Properties
Set the following properties in your application.properties file
```
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.url=                        # url of local db 
spring.datasource.username=                   # username of local db
spring.datasource.password=                   # password of local db
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql= true
```

### Folder Structure

```
identity_service

├───src
│   ├─── main
│   │    ├───java
│   │    │   └───com.calibrar.identityservice
│   │    │       └───com.calibrar.identityservice
│   │    │           ├───common                   
│   │    │           │   ├───constants             # reusable constants
│   │    │           │   ├───entity                # reusable entities
│   │    │           │   ├───enums                 # reusable enums
│   │    │           │   └───exception             # global exception
│   │    │           │          
│   │    │           ├───config                    # global configurations
│   │    │           │   └───security              # spring security configurations
│   │    │           │       ├───filter            # spring security filters
│   │    │           │       └───manager           # spring authentication manager
│   │    │           ├───domain
│   │    │           │       ├───dto               # data transfer object
│   │    │           │       └───entity            # db entity
│   │    │           ├───gateway
│   │    │           │   ├───controller            # rest controller
│   │    │           │   ├───database              # data access layer
│   │    │           │   └───exception             # data exceptions
│   │    │           └───use_cases                 # use case services
│   │    │           
│   │    └───resources
│   │        └───application.properties
│   │   ...
│   └─── test
│        ├───java
│        │    └───com.calibrar.identityservice.gateway
│        │        ├───controller                   # integration tests
│        │        └───database                     # unit tests for D.A.L.
│        │        
│        └───resources
│            └───application.properties
│   
├───target
│   README.md
│   pom.xml  
│   mvnw
```

## Git Workflow

### Feature

1. Checkout from `main` to get the latest codebase
2. Create feature branch ie. `feature/name-of-feature`
3. Ready to merge your changes? Create a Merge Request to `main` branch

### Enhancement / Update

1. Checkout from `main` to get the latest codebase
2. Create feature branch ie. `enhancement/name-of-feature`
3. Ready to merge your changes? Create a Merge Request to `main` branch

### Bugfix / Hotfix

1. Checkout from `main` to get the latest codebase
2. Create `bugfix` branch ie. `bugfix/name-of-feature`
3. Ready to merge your changes? Create a Merge Request to `main` branch

## Commit Message

https://www.freecodecamp.org/news/writing-good-commit-messages-a-practical-guide/

## Testing
  
### Unit testing
As the naming already reveals is a type of testing where units of an application are being tested in isolation. This may involve testing the service layer with mocks, the data access layer (repository/DAO layer).

### Integration testing
Integration testing is a method of testing multiple parts of an application at once. For the context of spring, this involves the whole lifecycle of receiving the request, and returning the appropriate response, which may or may not include the data retrieved from the data persistence layer. Unit tests on their own don't guarantee that your app will work even if those tests pass, because the relationship between the units might be wrong. You should test different features with integration tests.
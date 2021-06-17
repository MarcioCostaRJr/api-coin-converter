# API Coin Converter

This's source code repository for the Coin Converter project. The project has the purpose of converting currencies upon external request, values and other related information are stored in the base embedded, as well as presented as needed.

## Features

### Current

- Process currency conversion from parameters (user ID, target currency, amount)
- Currency conversion for bases: BRL, USD, EUR, JPY
- Persist transaction of currency conversion after operation performed
- View transaction of currency conversion after operation performed

### Future

- Configure a server for Sonar
- Implement the oauth2 authentication concept


## Technology

### Main technologies

- Java 1.8
- Maven
- Spring Boot Web
- Spring Boot Data JPA
- H2 (Database Embedded)
- Lombok
- JUnit and Mockito
- Swagger-UI
- Sl4j

### Layers

The concept used was 'Package for Layer'. In this way, the implementation will be based on its technical purpose. For example, the repository layer will be responsible for enabling communication with the database.

- **Model** - Class of business
- **Enums** - Enums for business
- **DTO** - Data Transfer Object
- **Validator** - Validator of business   
- **Repository** - Communication with database
- **Service** - Services to meet business specifications
- **Security** - Personalized exception for context
- **Controller** - Requisition interceptor
- **Configuration** - Beans of configuration
- **Util** - Utility for business

### Build / Development

#### Build Maven
```
# Simple build
mvn clean install
```

#### Tests
```
# Unit Tests
mvn clean test
```

#### Run
```
# Run project
mvn spring-boot:run
```

#### Importing to IDE
```
# IDE
- Just import the root pom.xml
```

#### Access Swagger
```
# Swagger-UI
- Just acess the endpoint /swagger-ui/# 
```


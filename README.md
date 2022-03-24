## Spring Accounts

### About

### Tools

* Java
* Spring Boot
* Spring Security
* Thymeleaf
* PostgreSQL
* Liquibase
* Hibernate Validation
* Google Guava

### Features

* AES CBC Encryption with random Initialization vector (IV)

### Requirements

* Java 11 with Maven
* Node Package Manager
* PostgreSQL

### Tests

Configure database connection for tests: src/main/resources/application-tests.properties

```
$> mvn --batch-mode --update-snapshots verify
```

### Start up

Configure database connection: src/main/resources/application.properties

```
$> mvn clean package
$> java -jar target/spring-accounts-0.0.1-SNAPSHOT.jar
```

# CRUD Framework with Plugin Architecture

This project demonstrates a modular Spring Boot application designed with a generic CRUD framework and a specific implementation for managing `Person` entities. It showcases a plugin-based architecture where different data stores can be used for different entity types.

## Microkernel/Plugin Architecture

This project is structured around a microkernel (or plugin) architecture, which promotes extensibility and flexibility. The core idea is to keep the central system (the microkernel) small and focused on essential functionalities, while allowing additional features and business logic to be added as independent plugins.

In this architecture:

*   **Microkernel (Core System)**: Represented by the `crud-abstraction` and `rest-abstraction` modules, along with the `plugin-host-app`.
    *   **`crud-abstraction`**: Provides the core contracts (interfaces) for CRUD operations, defining how plugins should interact with the system for data management. It's the abstract foundation.
    *   **`rest-abstraction`**: Offers a standardized way for plugins to expose RESTful APIs and handle responses, ensuring consistency across all integrated plugins.
    *   **`plugin-host-app`**: Acts as the main application that loads and orchestrates these plugins. It provides the runtime environment and integrates the functionalities exposed by various plugins.
*   **Plugins**: Independent modules that extend the functionality of the microkernel.
    *   **`people-crud-plugin`**: This module serves as a concrete example of a plugin. It implements the `crud-abstraction` interfaces for a specific entity (`Person`) and integrates with a particular data store (MongoDB). This demonstrates how new entity types and their respective data handling can be "plugged in" without modifying the core system.

This design allows for:
*   **Modularity**: Features are isolated into distinct plugins, making the system easier to understand, develop, and maintain.
*   **Extensibility**: New functionalities (e.g., CRUD for other entities like `Product`, `Order`) can be added by simply creating new plugins that adhere to the defined abstractions, without altering the existing core or other plugins.
*   **Flexibility**: Different plugins can utilize different technologies or data stores (e.g., one plugin uses MongoDB, another could use PostgreSQL) while still conforming to the common CRUD interfaces.
*   **Scalability**: Individual plugins can potentially be scaled independently if deployed as separate services, although in this example, they are integrated within a single host application.

## Project Structure

The project is organized into several Maven modules:

*   **`crud-framework` (Parent POM)**: The parent project that defines common dependencies, properties, and manages the sub-modules.
*   **`crud-abstraction`**: This module defines generic interfaces for CRUD operations (Controller, Service, Repository) and a `BaseEntity`. It provides a database-agnostic abstraction layer for CRUD functionalities.
    *   [`CrudController.java`](crud-framework/crud-abstraction/src/main/java/com/jejakin/crud/abstraction/controller/CrudController.java): Generic REST controller interface.
    *   [`CrudService.java`](crud-framework/crud-abstraction/src/main/java/com/jejakin/crud/abstraction/service/CrudService.java): Generic service interface for business logic.
    *   [`CrudRepository.java`](crud-framework/crud-abstraction/src/main/java/com/jejakin/crud/abstraction/repository/CrudRepository.java): Generic repository interface extending Spring Data JPA's `JpaRepository`.
    *   [`BaseEntity.java`](crud-framework/crud-abstraction/src/main/java/com/jejakin/crud/abstraction/model/BaseEntity.java): Abstract base class for entities with an auto-generated `Long id`.
*   **`rest-abstraction`**: This module provides standard REST request/response structures and common HTTP response handling mechanisms.
*   **`people-crud-plugin`**: A concrete implementation of the CRUD abstraction specifically for a `Person` entity. This plugin uses Spring Data MongoDB for persistence, demonstrating the flexibility of the framework to integrate different data stores.
    *   [`Person.java`](crud-framework/people-crud-plugin/src/main/java/com/jejakin/crud/people/model/Person.java): The entity class representing a person, extending `BaseEntity` and annotated as a MongoDB document.
    *   [`PersonController.java`](crud-framework/people-crud-plugin/src/main/java/com/jejakin/crud/people/controller/PersonController.java): REST controller for `Person` entities.
    *   [`PersonService.java`](crud-framework/people-crud-plugin/src/main/java/com/jejakin/crud/people/service/PersonService.java): Service layer for `Person` business logic.
    *   [`PersonRepository.java`](crud-framework/people-crud-plugin/src/main/java/com/jejakin/crud/people/repository/PersonRepository.java): Repository for `Person` entities, extending `MongoRepository`.
*   **`plugin-host-app`**: The main Spring Boot application that integrates the `rest-abstraction` and `people-crud-plugin` modules. It acts as the host for the CRUD plugins and exposes the REST API for `Person` entities. It also includes SpringDoc OpenAPI for API documentation (Swagger UI).

## Key Technologies

*   **Spring Boot**: 3.2.0
*   **Spring Data JPA**: Used in `crud-abstraction` for generic repository definition.
*   **Spring Data MongoDB**: Used in `people-crud-plugin` for `Person` entity persistence.
*   **Lombok**: Reduces boilerplate code.
*   **Maven**: Build automation tool.
*   **SpringDoc OpenAPI (Swagger UI)**: For automatic API documentation.

## Application Flow Diagram

```mermaid
graph TD
    A[Client] -->|HTTP Request| B(Plugin Host Application);
    B --> C{PersonController};
    C --> D[PersonService];
    D --> E[PersonRepository];
    E --> F[MongoDB Database];
    F -->|Data| E;
    E -->|Data| D;
    D -->|Data| C;
    C -->|HTTP Response| B;
    B --> A;
```

**Explanation:**

1.  A client sends an HTTP request (e.g., `POST /api/people` to create a new person) to the `plugin-host-app`.
2.  The `plugin-host-app` routes the request to the `PersonController` within the `people-crud-plugin`.
3.  The `PersonController` delegates the business logic to the `PersonService`.
4.  The `PersonService` interacts with the `PersonRepository` to perform the necessary database operations.
5.  The `PersonRepository` communicates with the MongoDB database to store or retrieve `Person` data.
6.  The data flows back from the database, through the `PersonRepository`, `PersonService`, and `PersonController`.
7.  Finally, the `PersonController` constructs an HTTP response (potentially using `BaseResponse` from `rest-abstraction`) and sends it back to the client via the `plugin-host-app`.

## Class Diagram

```mermaid
classDiagram
    direction LR
    class BaseEntity {
        +Long id
        +getId(): Long
        +setId(Long id): void
    }

    interface CrudRepository<T, ID> {
        +save(T entity): T
        +findById(ID id): Optional<T>
        +findAll(): List<T>
        +deleteById(ID id): void
    }

    interface CrudService<T, ID> {
        +save(T entity): T
        +findById(ID id): Optional<T>
        +findAll(): List<T>
        +deleteById(ID id): void
        +update(ID id, T entity): T
    }

    interface CrudController<T, ID> {
        +create(T entity): ResponseEntity<T>
        +getById(ID id): ResponseEntity<T>
        +getAll(): ResponseEntity<List<T>>
        +update(ID id, T entity): ResponseEntity<T>
        +delete(ID id): ResponseEntity<Void>
    }

    class Person {
        +String nik
        +String firstName
        +String lastName
        +String phoneNumber
        +String address1
        +String address2
    }

    class PersonRepository {
        // Extends MongoRepository<Person, Long>
    }

    class PersonService {
        -PersonRepository repository
        +save(Person person): Person
        +findById(Long id): Optional<Person>
        +findAll(): List<Person>
        +deleteById(Long id): void
        +update(Long id, Person person): Person
    }

    class PersonController {
        -PersonService personService
        +createPerson(Person person): ResponseEntity<Person>
        +getPersonById(Long id): ResponseEntity<Person>
        +getAllPeople(): ResponseEntity<List<Person>>
        +updatePerson(Long id, Person person): ResponseEntity<Person>
        +deletePerson(Long id): ResponseEntity<Void>
    }

    BaseEntity <|-- Person : extends
    CrudRepository <|-- PersonRepository : implements
    CrudService <|-- PersonService : implements
    CrudController <|-- PersonController : implements

    PersonService --> PersonRepository : uses
    PersonController --> PersonService : uses
```
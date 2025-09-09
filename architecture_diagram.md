# Diagram Arsitektur

```mermaid
graph TD
    subgraph "Client"
        A[Web Browser/Mobile App] --> B(REST API Endpoints)
    end

    subgraph "Plugin Host Application"
        B --> C[PluginHostApplication]
        C --> D[PeopleCrudPluginConfig]
    end

    subgraph "People CRUD Plugin"
        D --> E[PersonController]
        E --> F[PersonService]
        F --> G[PersonRepository]
        G --> H[Database]
        F --> I[Person Model]
        E --> J[REST Abstraction]
    end

    subgraph "CRUD Abstraction"
        F -- extends --> K[BaseCrudService]
        E -- extends --> L[BaseCrudController]
        G -- extends --> M[CrudRepository]
        I -- extends --> N[BaseEntity]
    end

    subgraph "REST Abstraction"
        J --> O[GlobalExceptionHandler]
        J --> P[BaseResponse/ErrorResponse]
        J --> Q[ResponseUtil]
    end

    subgraph "Database"
        H[Database]
    end

    style A fill:#f9f,stroke:#333,stroke-width:2px
    style B fill:#bbf,stroke:#333,stroke-width:2px
    style C fill:#bbf,stroke:#333,stroke-width:2px
    style D fill:#bbf,stroke:#333,stroke-width:2px
    style E fill:#ccf,stroke:#333,stroke-width:2px
    style F fill:#ccf,stroke:#333,stroke-width:2px
    style G fill:#ccf,stroke:#333,stroke-width:2px
    style H fill:#fcf,stroke:#333,stroke-width:2px
    style I fill:#ccf,stroke:#333,stroke-width:2px
    style J fill:#ccf,stroke:#333,stroke-width:2px
    style K fill:#afa,stroke:#333,stroke-width:2px
    style L fill:#afa,stroke:#333,stroke-width:2px
    style M fill:#afa,stroke:#333,stroke-width:2px
    style N fill:#afa,stroke:#333,stroke-width:2px
    style O fill:#ffc,stroke:#333,stroke-width:2px
    style P fill:#ffc,stroke:#333,stroke-width:2px
    style Q fill:#ffc,stroke:#333,stroke-width:2px

    linkStyle 0 stroke-width:2px,fill:none,stroke:blue;
    linkStyle 1 stroke-width:2px,fill:none,stroke:blue;
    linkStyle 2 stroke-width:2px,fill:none,stroke:green;
    linkStyle 3 stroke-width:2px,fill:none,stroke:green;
    linkStyle 4 stroke-width:2px,fill:none,stroke:green;
    linkStyle 5 stroke-width:2px,fill:none,stroke:green;
    linkStyle 6 stroke-width:2px,fill:none,stroke:green;
    linkStyle 7 stroke-width:2px,fill:none,stroke:purple;
    linkStyle 8 stroke-width:2px,fill:none,stroke:purple;
    linkStyle 9 stroke-width:2px,fill:none,stroke:purple;
    linkStyle 10 stroke-width:2px,fill:none,stroke:purple;
    linkStyle 11 stroke-width:2px,fill:none,stroke:orange;
    linkStyle 12 stroke-width:2px,fill:none,stroke:orange;
    linkStyle 13 stroke-width:2px,fill:none,stroke:orange;
    linkStyle 14 stroke-width:2px,fill:none,stroke:orange;
```

# Penjelasan Arsitektur

Aplikasi ini mengikuti arsitektur berbasis plugin, di mana `Plugin Host Application` bertindak sebagai wadah untuk `People CRUD Plugin` dan plugin lainnya yang mungkin ditambahkan di masa mendatang.

**Komponen Utama:**

1.  **Client:** Mewakili antarmuka pengguna (misalnya, browser web atau aplikasi seluler) yang berinteraksi dengan aplikasi melalui REST API.
2.  **Plugin Host Application:**
    *   `PluginHostApplication`: Aplikasi Spring Boot utama yang memulai konteks aplikasi dan menghosting plugin.
    *   `PeopleCrudPluginConfig`: Kelas konfigurasi untuk plugin CRUD People, yang kemungkinan mendaftarkan bean atau komponen spesifik plugin.
3.  **People CRUD Plugin:** Ini adalah plugin inti yang menyediakan fungsionalitas CRUD untuk entitas `Person`.
    *   `PersonController`: Menangani permintaan HTTP yang terkait dengan entitas `Person` (misalnya, membuat, membaca, memperbarui, menghapus). Ini mendelegasikan logika bisnis ke `PersonService`.
    *   `PersonService`: Berisi logika bisnis untuk operasi `Person`. Ini berinteraksi dengan `PersonRepository` untuk persistensi data.
    *   `PersonRepository`: Antarmuka repositori yang bertanggung jawab untuk berinteraksi dengan database untuk operasi CRUD `Person`.
    *   `Person Model`: Kelas entitas yang merepresentasikan data `Person`.
4.  **CRUD Abstraction:** Modul ini menyediakan kerangka kerja abstrak untuk operasi CRUD, mempromosikan penggunaan kembali kode dan konsistensi di seluruh plugin.
    *   `BaseCrudController`: Kelas dasar abstrak untuk pengontrol CRUD, menyediakan implementasi umum untuk operasi REST.
    *   `CrudController`: Antarmuka yang mendefinisikan operasi pengontrol CRUD.
    *   `BaseCrudService`: Kelas dasar abstrak untuk layanan CRUD, menyediakan implementasi umum untuk logika bisnis.
    *   `CrudService`: Antarmuka yang mendefinisikan operasi layanan CRUD.
    *   `CrudRepository`: Antarmuka dasar untuk repositori, kemungkinan memperluas antarmuka Spring Data JPA.
    *   `BaseEntity`: Kelas dasar abstrak untuk entitas model, menyediakan bidang umum seperti ID.
5.  **REST Abstraction:** Modul ini menyediakan utilitas dan penanganan kesalahan standar untuk REST API.
    *   `GlobalExceptionHandler`: Menangani pengecualian secara global di seluruh aplikasi dan mengembalikan respons kesalahan yang diformat.
    *   `BaseResponse`: Kelas respons generik untuk mengembalikan data yang berhasil.
    *   `ErrorResponse`: Kelas respons khusus untuk mengembalikan detail kesalahan.
    *   `ResponseUtil`: Kelas utilitas untuk membuat objek respons standar.
6.  **Database:** Lapisan persistensi tempat data `Person` disimpan.

# Diagram Alur (Contoh: Membuat Person Baru)

```mermaid
graph TD
    A[Client] -->|POST /persons| B[PersonController]
    B -->|Call create(person)| C[PersonService]
    C -->|Call save(person)| D[PersonRepository]
    D -->|Persist data| E[Database]
    E -->|Return saved Person| D
    D -->|Return saved Person| C
    C -->|Return saved Person| B
    B -->|Return BaseResponse(success, message, Person)| A

    style A fill:#f9f,stroke:#333,stroke-width:2px
    style B fill:#ccf,stroke:#333,stroke-width:2px
    style C fill:#ccf,stroke:#333,stroke-width:2px
    style D fill:#ccf,stroke:#333,stroke-width:2px
    style E fill:#fcf,stroke:#333,stroke-width:2px

    linkStyle 0 stroke-width:2px,fill:none,stroke:blue;
    linkStyle 1 stroke-width:2px,fill:none,stroke:green;
    linkStyle 2 stroke-width:2px,fill:none,stroke:green;
    linkStyle 3 stroke-width:2px,fill:none,stroke:red;
    linkStyle 4 stroke-width:2px,fill:none,stroke:green;
    linkStyle 5 stroke-width:2px,fill:none,stroke:green;
    linkStyle 6 stroke-width:2px,fill:none,stroke:green;
    linkStyle 7 stroke-width:2px,fill:none,stroke:blue;
```

# Penjelasan Alur (Contoh: Membuat Person Baru)

1.  **Client Mengirim Permintaan:** Klien (misalnya, aplikasi web) mengirimkan permintaan HTTP POST ke endpoint `/persons` dengan data `Person` baru di badan permintaan.
2.  **PersonController Menerima Permintaan:** `PersonController` menerima permintaan, memvalidasi data, dan memanggil metode `create` di `PersonService`.
3.  **PersonService Memproses Logika Bisnis:** `PersonService` menerapkan logika bisnis apa pun (misalnya, validasi tambahan, transformasi data) dan kemudian memanggil metode `save` di `PersonRepository`.
4.  **PersonRepository Berinteraksi dengan Database:** `PersonRepository` bertanggung jawab untuk berinteraksi dengan database (misalnya, menggunakan Spring Data JPA) untuk menyimpan objek `Person` baru.
5.  **Database Menyimpan Data:** Database menyimpan data `Person` dan mengembalikan objek `Person` yang disimpan (dengan ID yang dihasilkan) ke `PersonRepository`.
6.  **PersonRepository Mengembalikan Hasil:** `PersonRepository` mengembalikan objek `Person` yang disimpan ke `PersonService`.
7.  **PersonService Mengembalikan Hasil:** `PersonService` mengembalikan objek `Person` yang disimpan ke `PersonController`.
8.  **PersonController Mengembalikan Respons:** `PersonController` membuat `BaseResponse` yang menunjukkan keberhasilan operasi, menyertakan objek `Person` yang baru dibuat, dan mengirimkannya kembali ke klien.

# Diagram Kelas UML

```mermaid
classDiagram
    direction LR

    class BaseEntity {
        +ID id
    }

    class CrudRepository {
        <<interface>>
        +save(T entity)
        +findById(ID id)
        +findAll()
        +deleteById(ID id)
    }

    class CrudService {
        <<interface>>
        +create(T entity)
        +findById(ID id)
        +findAll()
        +update(ID id, T entity)
        +delete(ID id)
    }

    class BaseCrudService {
        +BaseCrudService(CrudRepository repository)
        +create(T entity)
        +findById(ID id)
        +findAll()
        +update(ID id, T entity)
        +delete(ID id)
    }

    class CrudController {
        <<interface>>
        +create(T entity)
        +findById(ID id)
        +findAll()
        +update(ID id, T entity)
        +delete(ID id)
    }

    class BaseCrudController {
        +BaseCrudController(CrudService service)
        +create(T entity)
        +findById(ID id)
        +findAll()
        +update(ID id, T entity)
        +delete(ID id)
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
        +PersonRepository(EntityManager entityManager)
    }

    class PersonService {
        +PersonService(PersonRepository repository)
        +update(Long id, Person person)
    }

    class PersonController {
        +PersonController(PersonService personService)
        +createPerson(Person person)
        +getPersonById(Long id)
        +getAllPersons()
        +updatePerson(Long id, Person person)
        +deletePerson(Long id)
    }

    class PluginHostApplication {
        +main(String[] args)
    }

    class PeopleCrudPluginConfig {
        +personRepository(EntityManager entityManager)
        +personService(PersonRepository personRepository)
        +personController(PersonService personService)
    }

    class GlobalExceptionHandler {
        +handleAllExceptions(Exception ex)
    }

    class BaseResponse {
        +boolean success
        +String message
        +T data
    }

    class ErrorResponse {
        +HttpStatus status
        +String message
        +String errorCode
        +List~String~ errors
    }

    class ResponseUtil {
        +buildSuccessResponse(T data, String message)
        +buildErrorResponse(HttpStatus status, String message, String errorCode, List~String~ errors)
    }

    BaseEntity <|-- Person
    CrudRepository <|-- PersonRepository
    CrudService <|-- BaseCrudService
    BaseCrudService <|-- PersonService
    CrudController <|-- BaseCrudController
    BaseCrudController <|-- PersonController
    BaseResponse <|-- ErrorResponse

    PersonController ..> PersonService : uses
    PersonService ..> PersonRepository : uses
    PersonRepository ..> Person : manages
    PersonService ..> Person : manages
    PersonController ..> Person : manages

    PluginHostApplication ..> PeopleCrudPluginConfig : loads
    PeopleCrudPluginConfig ..> PersonController : creates
    PeopleCrudPluginConfig ..> PersonService : creates
    PeopleCrudPluginConfig ..> PersonRepository : creates

    PersonController ..> GlobalExceptionHandler : handles exceptions
    PersonController ..> ResponseUtil : builds responses
    GlobalExceptionHandler ..> ErrorResponse : creates
    ResponseUtil ..> BaseResponse : creates
    ResponseUtil ..> ErrorResponse : creates
```

Ini adalah gambaran umum arsitektur dan alur. Apakah Anda ingin saya menambahkan detail lebih lanjut atau memodifikasi diagram ini?
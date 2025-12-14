# Library Management System

# 1. Book Management

- Add, remove, and update books in the library inventory
- Search books by title, author, or ISBN
- Track book availability status

# 2. Patron Management

- Add new patrons and update their information
- Track patron borrowing history
- Manage patron records

# 3. Lending Process

- Checkout books to patrons
- Return books from patrons
- Validate availability before checkout

# 4. Inventory Management

- Track available books
- Track borrowed books
- Maintain real-time inventory status

# Project Structure

```
src/main/java/com/library/
├── model/          # Data models (Book, Patron, Loan)
├── repository/     # Data access layer (BookRepository, PatronRepository)
├── service/        # Business logic layer (BookService, PatronService, LoanService)
├── factory/        # Factory pattern (BookFactory)
├── strategy/       # Strategy pattern (SearchStrategy implementations)
├── util/           # Utility classes (LoggerUtil)
└── Main.java       # Entry point and demonstration
```

# Class Diagram

The following diagram illustrates the relationships between all classes in the system:

```mermaid
classDiagram
    class Book {
        -String title
        -String author
        -String isbn
        -int publicationYear
        -boolean isAvailable
        +getTitle() String
        +setTitle(String)
        +getAuthor() String
        +setAuthor(String)
        +getIsbn() String
        +setIsbn(String)
        +isAvailable() boolean
        +setAvailable(boolean)
    }

    class Patron {
        -int patronId
        -String name
        -String email
        -String phoneNumber
        -List~Loan~ borrowingHistory
        +getPatronId() int
        +getName() String
        +getEmail() String
        +getBorrowingHistory() List~Loan~
        +addToBorrowingHistory(Loan)
    }

    class Loan {
        -Book book
        -Patron patron
        -LocalDate checkoutDate
        -LocalDate returnDate
        +getBook() Book
        +getPatron() Patron
        +getCheckoutDate() LocalDate
        +getReturnDate() LocalDate
        +isReturned() boolean
    }

    class BookRepository {
        -Map~String,Book~ books
        +addBook(Book) boolean
        +removeBook(String) boolean
        +updateBook(String, Book) boolean
        +findByISBN(String) Book
        +getAllBooks() List~Book~
    }

    class PatronRepository {
        -Map~Integer,Patron~ patrons
        -int nextPatronId
        +addPatron(Patron) boolean
        +updatePatron(int, Patron) boolean
        +findById(int) Patron
        +getAllPatrons() List~Patron~
        +getNextPatronId() int
    }

    class BookService {
        -BookRepository bookRepository
        -SearchStrategy searchStrategy
        -Logger logger
        +setSearchStrategy(SearchStrategy)
        +addBook(Book) boolean
        +removeBook(String) boolean
        +updateBook(String, Book) boolean
        +searchBooks(String) List~Book~
        +getBookByISBN(String) Book
    }

    class PatronService {
        -PatronRepository patronRepository
        -Logger logger
        +addPatron(Patron) boolean
        +updatePatron(int, Patron) boolean
        +getBorrowingHistory(int) List~Loan~
        +getPatronById(int) Patron
    }

    class LoanService {
        -BookRepository bookRepository
        -PatronRepository patronRepository
        -List~Loan~ activeLoans
        -Logger logger
        +checkoutBook(String, int) boolean
        +returnBook(String, int) boolean
        +getAvailableBooks() List~Book~
        +getBorrowedBooks() List~Book~
        +getActiveLoans() List~Loan~
    }

    class BookFactory {
        +createBook(String, String, String, int)$ Book
    }

    class SearchStrategy {
        <<interface>>
        +search(List~Book~, String) List~Book~
    }

    class SearchByTitle {
        +search(List~Book~, String) List~Book~
    }

    class SearchByAuthor {
        +search(List~Book~, String) List~Book~
    }

    class SearchByISBN {
        +search(List~Book~, String) List~Book~
    }

    class LoggerUtil {
        +getLogger()$ Logger
    }

    class Main {
        +main(String[]) void
    }

    %% Relationships
    Loan --> Book : uses
    Loan --> Patron : uses
    Patron --> Loan : "1..* borrowingHistory"

    BookService --> BookRepository : depends on
    BookService --> SearchStrategy : uses
    PatronService --> PatronRepository : depends on
    LoanService --> BookRepository : depends on
    LoanService --> PatronRepository : depends on
    LoanService --> Loan : manages

    SearchByTitle ..|> SearchStrategy : implements
    SearchByAuthor ..|> SearchStrategy : implements
    SearchByISBN ..|> SearchStrategy : implements

    BookFactory ..> Book : creates

    BookService --> LoggerUtil : uses
    PatronService --> LoggerUtil : uses
    LoanService --> LoggerUtil : uses

    Main --> BookService : uses
    Main --> PatronService : uses
    Main --> LoanService : uses
    Main --> BookFactory : uses
    Main --> SearchByTitle : uses
    Main --> SearchByAuthor : uses
    Main --> SearchByISBN : uses
```

### Diagram Legend

- **Solid arrows (-->)**: Dependency/Association - one class uses or depends on another
- **Dashed arrows (..>)**: Creation - one class creates instances of another
- **Dashed arrows with | (..|>)**: Implementation - a class implements an interface
- **Attributes**: Private fields are prefixed with `-`, public methods with `+`, static methods with `$`
- **Multiplicity**: `1..*` indicates one-to-many relationship

## Design Principles

### OOP Concepts

- **Encapsulation**: All model classes use private fields with public getters/setters
- **Inheritance**: Strategy pattern uses interface inheritance
- **Polymorphism**: SearchStrategy interface allows different search implementations
- **Abstraction**: SearchStrategy interface abstracts search behavior

### SOLID Principles

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: BookService is open for extension (new search strategies) but closed for modification
- **Liskov Substitution**: All SearchStrategy implementations are interchangeable
- **Interface Segregation**: SearchStrategy interface is focused and minimal
- **Dependency Inversion**: Services depend on abstractions (SearchStrategy interface) not concrete implementations

### Design Patterns

- **Factory Pattern**: `BookFactory` creates Book objects with validation
- **Strategy Pattern**: Different search algorithms (by title, author, ISBN) encapsulated in separate classes

### Java Collections

- `Map<String, Book>` in BookRepository (ISBN as key)
- `Map<Integer, Patron>` in PatronRepository (patronId as key)
- `List<Book>` for search operations
- `List<Loan>` for tracking borrowing history

### Logging

- Uses `java.util.logging` for logging important events
- Logs book operations, patron operations, and lending transactions
- Centralized logging through `LoggerUtil`

## How to Run

1. Compile all Java files:

   ```bash
   javac -d out src/main/java/com/library/**/*.java
   ```

2. Run the Main class:
   ```bash
   java -cp out com.library.Main
   ```

Alternatively, if using an IDE like IntelliJ IDEA or Eclipse:

- Import the project
- Run the `Main.java` file

## Usage Example

```java
// Create repositories
BookRepository bookRepository = new BookRepository();
PatronRepository patronRepository = new PatronRepository();

// Create services
BookService bookService = new BookService(bookRepository);
PatronService patronService = new PatronService(patronRepository);
LoanService loanService = new LoanService(bookRepository, patronRepository);

// Create a book using Factory pattern
Book book = BookFactory.createBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", 1925);
bookService.addBook(book);

// Search by title using Strategy pattern
bookService.setSearchStrategy(new SearchByTitle());
List<Book> results = bookService.searchBooks("Gatsby");

// Create a patron
Patron patron = new Patron(1, "John Doe", "john@email.com", "123-456-7890");
patronService.addPatron(patron);

// Checkout a book
loanService.checkoutBook("978-0-7432-7356-5", 1);

// Return a book
loanService.returnBook("978-0-7432-7356-5", 1);
```

package com.library;

import com.library.factory.BookFactory;
import com.library.model.Book;
import com.library.model.Patron;
import com.library.repository.BookRepository;
import com.library.repository.PatronRepository;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.PatronService;
import com.library.strategy.SearchByAuthor;
import com.library.strategy.SearchByISBN;
import com.library.strategy.SearchByTitle;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===\n");

        // Initialize repositories
        BookRepository bookRepository = new BookRepository();
        PatronRepository patronRepository = new PatronRepository();

        // Initialize services
        BookService bookService = new BookService(bookRepository);
        PatronService patronService = new PatronService(patronRepository);
        LoanService loanService = new LoanService(bookRepository, patronRepository);

        // ========== Book Management ==========
        System.out.println("--- Book Management ---");
        
        // Create books using Factory pattern
        Book book1 = BookFactory.createBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", 1925);
        Book book2 = BookFactory.createBook("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4", 1960);
        Book book3 = BookFactory.createBook("1984", "George Orwell", "978-0-452-28423-4", 1949);
        Book book4 = BookFactory.createBook("Pride and Prejudice", "Jane Austen", "978-0-14-143951-8", 1813);

        // Add books
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        bookService.addBook(book4);

        System.out.println("\nTotal books in library: " + bookRepository.getBookCount());

        // Search by Title using Strategy pattern
        System.out.println("\n--- Searching by Title ---");
        bookService.setSearchStrategy(new SearchByTitle());
        var titleResults = bookService.searchBooks("Gatsby");
        System.out.println("Search results for 'Gatsby':");
        titleResults.forEach(book -> System.out.println("  - " + book.getTitle() + " by " + book.getAuthor()));

        // Search by Author using Strategy pattern
        System.out.println("\n--- Searching by Author ---");
        bookService.setSearchStrategy(new SearchByAuthor());
        var authorResults = bookService.searchBooks("Orwell");
        System.out.println("Search results for author 'Orwell':");
        authorResults.forEach(book -> System.out.println("  - " + book.getTitle() + " by " + book.getAuthor()));

        // Search by ISBN using Strategy pattern
        System.out.println("\n--- Searching by ISBN ---");
        bookService.setSearchStrategy(new SearchByISBN());
        var isbnResults = bookService.searchBooks("978-0-14-143951-8");
        System.out.println("Search results for ISBN '978-0-14-143951-8':");
        isbnResults.forEach(book -> System.out.println("  - " + book.getTitle() + " by " + book.getAuthor()));

        // Update a book
        System.out.println("\n--- Updating Book ---");
        Book updatedBook = BookFactory.createBook("The Great Gatsby (Updated)", "F. Scott Fitzgerald", "978-0-7432-7356-5", 1925);
        bookService.updateBook("978-0-7432-7356-5", updatedBook);
        System.out.println("Book updated: " + bookService.getBookByISBN("978-0-7432-7356-5").getTitle());

        // ========== Patron Management ==========
        System.out.println("\n--- Patron Management ---");
        
        // Create patrons
        Patron patron1 = new Patron(patronService.getNextPatronId(), "John Doe", "john.doe@email.com", "123-456-7890");
        Patron patron2 = new Patron(patronService.getNextPatronId(), "Jane Smith", "jane.smith@email.com", "987-654-3210");
        Patron patron3 = new Patron(patronService.getNextPatronId(), "Bob Johnson", "bob.johnson@email.com", "555-123-4567");

        // Add patrons
        patronService.addPatron(patron1);
        patronService.addPatron(patron2);
        patronService.addPatron(patron3);

        System.out.println("\nTotal patrons: " + patronRepository.getPatronCount());

        // ========== Lending Process ==========
        System.out.println("\n--- Lending Process ---");
        
        // Checkout books
        System.out.println("\nChecking out books:");
        loanService.checkoutBook("978-0-7432-7356-5", 1); // The Great Gatsby to John Doe
        loanService.checkoutBook("978-0-06-112008-4", 2); // To Kill a Mockingbird to Jane Smith
        loanService.checkoutBook("978-0-452-28423-4", 1); // 1984 to John Doe

        // Try to checkout unavailable book
        System.out.println("\nTrying to checkout already borrowed book:");
        loanService.checkoutBook("978-0-7432-7356-5", 3); // Should fail

        // ========== Inventory Management ==========
        System.out.println("\n--- Inventory Management ---");
        
        System.out.println("\nAvailable books (" + loanService.getAvailableBooks().size() + "):");
        loanService.getAvailableBooks().forEach(book -> 
            System.out.println("  - " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")")
        );

        System.out.println("\nBorrowed books (" + loanService.getBorrowedBooks().size() + "):");
        loanService.getBorrowedBooks().forEach(book -> 
            System.out.println("  - " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")")
        );

        // ========== Borrowing History ==========
        System.out.println("\n--- Borrowing History ---");
        
        System.out.println("\nBorrowing history for John Doe (ID: 1):");
        patronService.getBorrowingHistory(1).forEach(loan -> 
            System.out.println("  - " + loan.getBook().getTitle() + " (Checked out: " + loan.getCheckoutDate() + ")")
        );

        // ========== Return Process ==========
        System.out.println("\n--- Return Process ---");
        
        System.out.println("\nReturning books:");
        loanService.returnBook("978-0-7432-7356-5", 1); // John Doe returns The Great Gatsby
        loanService.returnBook("978-0-06-112008-4", 2); // Jane Smith returns To Kill a Mockingbird

        System.out.println("\nAvailable books after returns (" + loanService.getAvailableBooks().size() + "):");
        loanService.getAvailableBooks().forEach(book -> 
            System.out.println("  - " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")")
        );

        System.out.println("\nBorrowed books after returns (" + loanService.getBorrowedBooks().size() + "):");
        loanService.getBorrowedBooks().forEach(book -> 
            System.out.println("  - " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")")
        );

        // ========== Final Summary ==========
        System.out.println("\n=== System Summary ===");
        System.out.println("Total Books: " + bookRepository.getBookCount());
        System.out.println("Total Patrons: " + patronRepository.getPatronCount());
        System.out.println("Available Books: " + loanService.getAvailableBooks().size());
        System.out.println("Borrowed Books: " + loanService.getBorrowedBooks().size());
        System.out.println("Active Loans: " + loanService.getActiveLoans().size());

        System.out.println("\n=== Library Management System Demo Complete ===");
    }
}

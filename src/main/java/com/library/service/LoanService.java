package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Patron;
import com.library.repository.BookRepository;
import com.library.repository.PatronRepository;
import com.library.util.LoggerUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for managing lending operations and inventory.
 * Handles checkout, return, and inventory management.
 */
public class LoanService {
    private BookRepository bookRepository;
    private PatronRepository patronRepository;
    private List<Loan> activeLoans;
    private static final Logger logger = LoggerUtil.getLogger();

    public LoanService(BookRepository bookRepository, PatronRepository patronRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.activeLoans = new ArrayList<>();
    }

    /**
     * Checks out a book to a patron.
     * @param isbn The ISBN of the book to checkout
     * @param patronId The ID of the patron
     * @return true if checkout successful, false otherwise
     */
    public boolean checkoutBook(String isbn, int patronId) {
        Book book = bookRepository.findByISBN(isbn);
        Patron patron = patronRepository.findById(patronId);

        if (book == null) {
            logger.log(Level.WARNING, "Checkout failed: Book with ISBN " + isbn + " not found");
            return false;
        }

        if (patron == null) {
            logger.log(Level.WARNING, "Checkout failed: Patron with ID " + patronId + " not found");
            return false;
        }

        if (!book.isAvailable()) {
            logger.log(Level.WARNING, "Checkout failed: Book " + book.getTitle() + " is not available");
            return false;
        }

        // Create loan record
        Loan loan = new Loan(book, patron, LocalDate.now());
        activeLoans.add(loan);
        patron.addToBorrowingHistory(loan);

        // Update book availability
        book.setAvailable(false);

        logger.log(Level.INFO, "Book checked out successfully: " + book.getTitle() + " to " + patron.getName());
        return true;
    }

    /**
     * Returns a book from a patron.
     * @param isbn The ISBN of the book to return
     * @param patronId The ID of the patron
     * @return true if return successful, false otherwise
     */
    public boolean returnBook(String isbn, int patronId) {
        Book book = bookRepository.findByISBN(isbn);
        Patron patron = patronRepository.findById(patronId);

        if (book == null) {
            logger.log(Level.WARNING, "Return failed: Book with ISBN " + isbn + " not found");
            return false;
        }

        if (patron == null) {
            logger.log(Level.WARNING, "Return failed: Patron with ID " + patronId + " not found");
            return false;
        }

        // Find the active loan
        Loan loanToReturn = null;
        for (Loan loan : activeLoans) {
            if (loan.getBook().getIsbn().equals(isbn) && 
                loan.getPatron().getPatronId() == patronId && 
                !loan.isReturned()) {
                loanToReturn = loan;
                break;
            }
        }

        if (loanToReturn == null) {
            logger.log(Level.WARNING, "Return failed: No active loan found for book " + isbn + " and patron " + patronId);
            return false;
        }

        // Update loan record
        loanToReturn.setReturnDate(LocalDate.now());

        // Update book availability
        book.setAvailable(true);

        logger.log(Level.INFO, "Book returned successfully: " + book.getTitle() + " from " + patron.getName());
        return true;
    }

    /**
     * Gets all available books in the library.
     * @return List of available books
     */
    public List<Book> getAvailableBooks() {
        List<Book> allBooks = bookRepository.getAllBooks();
        List<Book> availableBooks = new ArrayList<>();
        
        for (Book book : allBooks) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        
        return availableBooks;
    }

    /**
     * Gets all currently borrowed books.
     * @return List of borrowed books
     */
    public List<Book> getBorrowedBooks() {
        List<Book> allBooks = bookRepository.getAllBooks();
        List<Book> borrowedBooks = new ArrayList<>();
        
        for (Book book : allBooks) {
            if (!book.isAvailable()) {
                borrowedBooks.add(book);
            }
        }
        
        return borrowedBooks;
    }

    /**
     * Gets all active loans.
     * @return List of active (not yet returned) loans
     */
    public List<Loan> getActiveLoans() {
        List<Loan> active = new ArrayList<>();
        for (Loan loan : activeLoans) {
            if (!loan.isReturned()) {
                active.add(loan);
            }
        }
        return active;
    }
}

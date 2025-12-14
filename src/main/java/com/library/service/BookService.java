package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.strategy.SearchStrategy;
import com.library.util.LoggerUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for book-related business logic.
 * Demonstrates Dependency Inversion Principle - depends on SearchStrategy abstraction.
 * Demonstrates Open/Closed Principle - open for extension (new search strategies) but closed for modification.
 */
public class BookService {
    private BookRepository bookRepository;
    private SearchStrategy searchStrategy;
    private static final Logger logger = LoggerUtil.getLogger();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Sets the search strategy to use.
     * @param searchStrategy The search strategy implementation
     */
    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    /**
     * Adds a new book to the library.
     * @param book The book to add
     * @return true if added successfully, false otherwise
     */
    public boolean addBook(Book book) {
        boolean result = bookRepository.addBook(book);
        if (result) {
            logger.log(Level.INFO, "Book added successfully: " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
        } else {
            logger.log(Level.WARNING, "Failed to add book: Book with ISBN " + book.getIsbn() + " already exists");
        }
        return result;
    }

    /**
     * Removes a book from the library.
     * @param isbn The ISBN of the book to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeBook(String isbn) {
        boolean result = bookRepository.removeBook(isbn);
        if (result) {
            logger.log(Level.INFO, "Book removed successfully: ISBN " + isbn);
        } else {
            logger.log(Level.WARNING, "Failed to remove book: Book with ISBN " + isbn + " not found");
        }
        return result;
    }

    /**
     * Updates an existing book in the library.
     * @param isbn The ISBN of the book to update
     * @param updatedBook The updated book object
     * @return true if updated successfully, false otherwise
     */
    public boolean updateBook(String isbn, Book updatedBook) {
        boolean result = bookRepository.updateBook(isbn, updatedBook);
        if (result) {
            logger.log(Level.INFO, "Book updated successfully: ISBN " + isbn);
        } else {
            logger.log(Level.WARNING, "Failed to update book: Book with ISBN " + isbn + " not found");
        }
        return result;
    }

    /**
     * Searches for books using the current search strategy.
     * @param query The search query
     * @return List of matching books
     */
    public List<Book> searchBooks(String query) {
        if (searchStrategy == null) {
            logger.log(Level.SEVERE, "Search strategy not set!");
            return List.of();
        }
        
        List<Book> allBooks = bookRepository.getAllBooks();
        List<Book> results = searchStrategy.search(allBooks, query);
        logger.log(Level.INFO, "Search performed with query: " + query + ", found " + results.size() + " results");
        return results;
    }

    /**
     * Gets a book by ISBN.
     * @param isbn The ISBN to search for
     * @return The book if found, null otherwise
     */
    public Book getBookByISBN(String isbn) {
        return bookRepository.findByISBN(isbn);
    }

    /**
     * Gets all books in the library.
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}

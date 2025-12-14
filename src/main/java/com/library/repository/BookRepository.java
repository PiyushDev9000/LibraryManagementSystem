package com.library.repository;

import com.library.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Book data.
 * Demonstrates Single Responsibility Principle - only handles data storage/retrieval.
 * Uses Map collection with ISBN as key for efficient lookups.
 */
public class BookRepository {
    private Map<String, Book> books; // Key: ISBN, Value: Book

    public BookRepository() {
        this.books = new HashMap<>();
    }

    /**
     * Adds a book to the repository.
     * @param book The book to add
     * @return true if added successfully, false if book with same ISBN already exists
     */
    public boolean addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            return false; // Book with this ISBN already exists
        }
        books.put(book.getIsbn(), book);
        return true;
    }

    /**
     * Removes a book from the repository.
     * @param isbn The ISBN of the book to remove
     * @return true if removed successfully, false if book not found
     */
    public boolean removeBook(String isbn) {
        if (books.containsKey(isbn)) {
            books.remove(isbn);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing book in the repository.
     * @param isbn The ISBN of the book to update
     * @param updatedBook The updated book object
     * @return true if updated successfully, false if book not found
     */
    public boolean updateBook(String isbn, Book updatedBook) {
        if (books.containsKey(isbn)) {
            books.put(isbn, updatedBook);
            return true;
        }
        return false;
    }

    /**
     * Finds a book by ISBN.
     * @param isbn The ISBN to search for
     * @return The book if found, null otherwise
     */
    public Book findByISBN(String isbn) {
        return books.get(isbn);
    }

    /**
     * Gets all books in the repository.
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    /**
     * Gets the total number of books in the repository.
     * @return The count of books
     */
    public int getBookCount() {
        return books.size();
    }
}

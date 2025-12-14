package com.library.factory;

import com.library.model.Book;

/**
 * Factory class for creating Book objects.
 * Demonstrates Factory Design Pattern - encapsulates object creation logic.
 * Can be extended to create different types of books in the future.
 */
public class BookFactory {
    
    /**
     * Creates a new Book instance with the provided details.
     * @param title The title of the book
     * @param author The author of the book
     * @param isbn The ISBN of the book
     * @param publicationYear The publication year
     * @return A new Book object
     */
    public static Book createBook(String title, String author, String isbn, int publicationYear) {
        // Validation can be added here
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (publicationYear < 0 || publicationYear > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Invalid publication year");
        }
        
        return new Book(title, author, isbn, publicationYear);
    }
}

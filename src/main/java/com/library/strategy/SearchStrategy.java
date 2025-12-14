package com.library.strategy;

import com.library.model.Book;
import java.util.List;

/**
 * Strategy interface for different search algorithms.
 * Demonstrates Strategy Design Pattern - allows different search behaviors
 * to be used interchangeably.
 */
public interface SearchStrategy {
    /**
     * Searches for books based on a query string.
     * @param books The list of books to search through
     * @param query The search query
     * @return List of books matching the query
     */
    List<Book> search(List<Book> books, String query);
}

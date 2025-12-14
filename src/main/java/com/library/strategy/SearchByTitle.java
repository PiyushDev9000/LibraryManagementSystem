package com.library.strategy;

import com.library.model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete strategy for searching books by title.
 * Implements SearchStrategy interface.
 */
public class SearchByTitle implements SearchStrategy {
    
    @Override
    public List<Book> search(List<Book> books, String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerQuery)) {
                results.add(book);
            }
        }
        
        return results;
    }
}

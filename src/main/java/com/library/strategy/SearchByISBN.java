package com.library.strategy;

import com.library.model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete strategy for searching books by ISBN.
 * Implements SearchStrategy interface.
 */
public class SearchByISBN implements SearchStrategy {
    
    @Override
    public List<Book> search(List<Book> books, String query) {
        List<Book> results = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(query)) {
                results.add(book);
            }
        }
        
        return results;
    }
}

package com.library.model;

import java.time.LocalDate;

/**
 * Represents a lending transaction between a Patron and a Book.
 */
public class Loan {
    private Book book;
    private Patron patron;
    private LocalDate checkoutDate;
    private LocalDate returnDate;

    // Constructor for checkout
    public Loan(Book book, Patron patron, LocalDate checkoutDate) {
        this.book = book;
        this.patron = patron;
        this.checkoutDate = checkoutDate;
        this.returnDate = null; // Will be set when book is returned
    }

    // Getters and Setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book.getTitle() +
                ", patron=" + patron.getName() +
                ", checkoutDate=" + checkoutDate +
                ", returnDate=" + (returnDate != null ? returnDate : "Not returned") +
                '}';
    }
}

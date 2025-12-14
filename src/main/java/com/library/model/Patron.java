package com.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Patron (library member) in the system.
 * Tracks borrowing history using a List of Loan objects.
 */
public class Patron {
    private int patronId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Loan> borrowingHistory;

    // Constructor
    public Patron(int patronId, String name, String email, String phoneNumber) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowingHistory = new ArrayList<>();
    }

    // Getters and Setters
    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Loan> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void addToBorrowingHistory(Loan loan) {
        this.borrowingHistory.add(loan);
    }

    @Override
    public String toString() {
        return "Patron{" +
                "patronId=" + patronId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", totalLoans=" + borrowingHistory.size() +
                '}';
    }
}

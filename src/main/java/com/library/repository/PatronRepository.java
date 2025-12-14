package com.library.repository;

import com.library.model.Patron;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Patron data.
 * Demonstrates Single Responsibility Principle - only handles data storage/retrieval.
 * Uses Map collection with patronId as key for efficient lookups.
 */
public class PatronRepository {
    private Map<Integer, Patron> patrons; // Key: patronId, Value: Patron
    private int nextPatronId;

    public PatronRepository() {
        this.patrons = new HashMap<>();
        this.nextPatronId = 1;
    }

    /**
     * Adds a new patron to the repository.
     * @param patron The patron to add
     * @return true if added successfully, false if patron with same ID already exists
     */
    public boolean addPatron(Patron patron) {
        if (patrons.containsKey(patron.getPatronId())) {
            return false; // Patron with this ID already exists
        }
        patrons.put(patron.getPatronId(), patron);
        return true;
    }

    /**
     * Updates an existing patron in the repository.
     * @param patronId The ID of the patron to update
     * @param updatedPatron The updated patron object
     * @return true if updated successfully, false if patron not found
     */
    public boolean updatePatron(int patronId, Patron updatedPatron) {
        if (patrons.containsKey(patronId)) {
            patrons.put(patronId, updatedPatron);
            return true;
        }
        return false;
    }

    /**
     * Finds a patron by ID.
     * @param patronId The ID to search for
     * @return The patron if found, null otherwise
     */
    public Patron findById(int patronId) {
        return patrons.get(patronId);
    }

    /**
     * Gets all patrons in the repository.
     * @return List of all patrons
     */
    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }

    /**
     * Generates and returns the next available patron ID.
     * @return The next patron ID
     */
    public int getNextPatronId() {
        return nextPatronId++;
    }

    /**
     * Gets the total number of patrons in the repository.
     * @return The count of patrons
     */
    public int getPatronCount() {
        return patrons.size();
    }
}

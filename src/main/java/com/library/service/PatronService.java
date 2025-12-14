package com.library.service;

import com.library.model.Loan;
import com.library.model.Patron;
import com.library.repository.PatronRepository;
import com.library.util.LoggerUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for patron-related business logic.
 * Demonstrates Single Responsibility Principle - handles only patron operations.
 */
public class PatronService {
    private PatronRepository patronRepository;
    private static final Logger logger = LoggerUtil.getLogger();

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    /**
     * Adds a new patron to the system.
     * @param patron The patron to add
     * @return true if added successfully, false otherwise
     */
    public boolean addPatron(Patron patron) {
        boolean result = patronRepository.addPatron(patron);
        if (result) {
            logger.log(Level.INFO, "Patron added successfully: " + patron.getName() + " (ID: " + patron.getPatronId() + ")");
        } else {
            logger.log(Level.WARNING, "Failed to add patron: Patron with ID " + patron.getPatronId() + " already exists");
        }
        return result;
    }

    /**
     * Updates an existing patron's information.
     * @param patronId The ID of the patron to update
     * @param updatedPatron The updated patron object
     * @return true if updated successfully, false otherwise
     */
    public boolean updatePatron(int patronId, Patron updatedPatron) {
        boolean result = patronRepository.updatePatron(patronId, updatedPatron);
        if (result) {
            logger.log(Level.INFO, "Patron updated successfully: ID " + patronId);
        } else {
            logger.log(Level.WARNING, "Failed to update patron: Patron with ID " + patronId + " not found");
        }
        return result;
    }

    /**
     * Gets a patron by ID.
     * @param patronId The ID to search for
     * @return The patron if found, null otherwise
     */
    public Patron getPatronById(int patronId) {
        return patronRepository.findById(patronId);
    }

    /**
     * Gets the borrowing history of a patron.
     * @param patronId The ID of the patron
     * @return List of loans (borrowing history)
     */
    public List<Loan> getBorrowingHistory(int patronId) {
        Patron patron = patronRepository.findById(patronId);
        if (patron != null) {
            logger.log(Level.INFO, "Retrieved borrowing history for patron: " + patron.getName() + " (ID: " + patronId + ")");
            return patron.getBorrowingHistory();
        } else {
            logger.log(Level.WARNING, "Patron not found: ID " + patronId);
            return List.of();
        }
    }

    /**
     * Gets all patrons in the system.
     * @return List of all patrons
     */
    public List<Patron> getAllPatrons() {
        return patronRepository.getAllPatrons();
    }

    /**
     * Gets the next available patron ID.
     * @return The next patron ID
     */
    public int getNextPatronId() {
        return patronRepository.getNextPatronId();
    }
}

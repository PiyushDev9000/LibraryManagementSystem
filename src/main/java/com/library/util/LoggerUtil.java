package com.library.util;

import java.util.logging.Logger;

/**
 * Utility class for centralized logging configuration.
 * Provides a single logger instance for the entire application.
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger("LibraryManagementSystem");

    public static Logger getLogger() {
        return logger;
    }
}

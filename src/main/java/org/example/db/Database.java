package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ========================= DO-178C MODULE HEADER =========================
// Module Name    : Database.java
// Application    : Aircraft Maintenance Tracker
// Description    : Provides centralized database connection logic.
// Safety Level   : DAL C or higher (Data Integrity is critical)
// Developed By   : Rafail
// Last Modified  : 2025-04-28
// Notes          : Hardcoded credentials pose a security risk in aviation applications
// ========================================================================

/**
 * Database: Utility class for managing PostgreSQL connections.
 *
 * ⚠️ SECURITY WARNING:
 * Credentials are currently hardcoded. This violates DO-178C and secure software practices.
 * For production or certifiable systems:
 *   - Externalize credentials to a secure config or keystore
 *   - Use environment variables or encryption mechanisms
 */

public class Database {
    //JDBC connection to the database using the 5432 port for the database
    private static final String URL = "jdbc:postgresql://localhost:5432/aircraftdb";

    //Credentials for the User
    private static final String USER = "postgres";

    //Credentials for the Password
    private static final String PASSWORD = "2552085124rR!";

    //Established the connection to the database
    //@return Connection object representing an active session with the database.
    //@throws SQLException if a database access error occurs or the connection cannot be established.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

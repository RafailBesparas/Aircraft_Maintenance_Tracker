package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility classes responsible for managing database connections to the PostgreSQL server
 * Provides a centralized mechanism to establish connections required by the application
 *
 * Security note the credentials are hardcoded so for a production level application it is adviced
 * that the creadentials must be externalized
 * @author Rafail
 * @version 1.0
 * @since 2025-04-28
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

package db;

import org.example.db.Database;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


public class DatabaseTest {

    @Test
    public void testConnectionIsNotNull() {
        try (Connection conn = Database.getConnection()) {
            assertNotNull(conn);
        } catch (Exception e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testCriticalTablesExist() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs1 = stmt.executeQuery("SELECT 1 FROM aircraft LIMIT 1;");
            assertTrue(rs1.next());

            ResultSet rs2 = stmt.executeQuery("SELECT 1 FROM maintenance_task LIMIT 1;");
            assertTrue(rs2.next());

        } catch (Exception e) {
            fail("Critical table missing or query failed: " + e.getMessage());
        }
    }
}
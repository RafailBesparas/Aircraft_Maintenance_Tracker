package integration;

import org.example.db.Database;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AircraftCrudIntegrationTest {

    private static int testAircraftId;

    @Test
    @Order(1)
    public void testInsertAircraft() throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO aircraft (model, tail_number) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "TestPlane");
            stmt.setString(2, "T-1234");
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            assertTrue(rs.next());
            testAircraftId = rs.getInt(1);
            assertTrue(testAircraftId > 0);
        }
    }

    @Test
    @Order(2)
    public void testUpdateAircraft() throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE aircraft SET model = ?, tail_number = ? WHERE id = ?")) {

            stmt.setString(1, "UpdatedPlane");
            stmt.setString(2, "U-9999");
            stmt.setInt(3, testAircraftId);
            int updatedRows = stmt.executeUpdate();

            assertEquals(1, updatedRows);
        }
    }

    @Test
    @Order(3)
    public void testDeleteAircraft() throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM aircraft WHERE id = ?")) {

            stmt.setInt(1, testAircraftId);
            int deletedRows = stmt.executeUpdate();

            assertEquals(1, deletedRows);
        }
    }
}

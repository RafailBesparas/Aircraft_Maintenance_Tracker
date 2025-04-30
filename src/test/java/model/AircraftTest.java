package model;

import org.example.model.Aircraft;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AircraftTest {

    @Test
    public void testAircraftCreation() {
        Aircraft aircraft1 = new Aircraft(1, "Airbus A320", "D-AIPX");
        Aircraft aircraft2 = new Aircraft(1, "Boeing 747", "N373BA");

        assertEquals(1, aircraft1.getId());
        assertEquals("Airbus A320", aircraft1.getModel());
        assertEquals("D-AIPX", aircraft1.getTailNumber());

        assertEquals(2, aircraft2.getId());
        assertEquals("Boeing 747", aircraft2.getModel());
        assertEquals("N373BA", aircraft2.getTailNumber());

    }


}

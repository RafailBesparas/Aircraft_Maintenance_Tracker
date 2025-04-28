package org.example.model;

/**
 *Aircraft Model class representing the basic information for the Aircraft
 *Attempt to comply with the DO-178C Standard
 * The Aircraft entity will contain an Identification and Basic Information
 *<p>
 *Each aircraft has a unique identifier, model name like Airbus, Boeing 747 and a tail number which
 * is the registration number like a licence plate for a car  </p>

 *@author Rafail
 *@version 1.0
 *@since 2025-04-28
 */

public class Aircraft {

    // Attributes of the Aircraft Model

    // Unique internal identifier for the aircraft
    private int id;

    //Model name of the aircraft like boeing 747
    private String model;

    //Registration number for identifying the aircraft externally like licence plate
    private String tailNumber;

    // Constructor for the class, for creating an Aircraft instance
    /**
     * @param id         Internal database identifier for the aircraft.
     * @param model      Manufacturer's model name of the aircraft.
     * @param tailNumber Unique tail number (registration number) of the aircraft.
     */
    public Aircraft(int id, String model, String tailNumber){
        this.id = id;
        this.model = model;
        this.tailNumber = tailNumber;
    }

    //Getters for the Aircraft
    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getTailNumber() {
        return tailNumber;
    }
}

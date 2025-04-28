package org.example.model;

public class Aircraft {

    // Attributes of the Aircraft Model
    private int id;
    private String model;
    private String tailNumber;

    //First Question why do we use tail number?

    // Constructor for the class
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

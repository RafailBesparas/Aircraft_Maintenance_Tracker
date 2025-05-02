package org.example.app;

import org.example.presenter.AircraftPresenter;
import org.example.view.AircraftViewImplementation;
import org.example.view.MaintenanceDashboardPanel;

// Used for logging the honey trap attempts into the security-audit.log
import java.io.FileWriter;

//Used for catching exceptions
import java.io.IOException;

// ========================= DO-178C MODULE HEADER =========================
// Module Name    : Main.java
// Application    : Aircraft Maintenance Tracker
// Description    : Entry point for the application. Initializes login,
//                  and launches real or decoy mode for aviation security.
// Safety Level   : DAL C (assumed - confirm with system safety assessment)
// Developed By   : Rafail
// Last Modified  : 2025-05-02
// ========================================================================

//Use this to check the stealth mode
//coleopter1!
//with admin123 we get to the real application

/**
 * Main class: Application entry point.
 * It supports launching the system in normal operational mode or a honeypot decoy.
 */
public class Main {

    /**
     * Main method - invoked when the application starts.
     * Launches the login interface.
     */
    public static void main(String[] args) {
        //Launching the login window (authentication entry point)
        new org.example.auth.LoginWindow();
    }

    /**
     * Launches the real operational mode of the Aircraft Maintenance application.
     * This is the standard execution path.
     */
    public static void launchRealMode() {
        //Create a presenter object with a null view
        //If in the function there is not null then the software will throw a NullPointerException if the view has not
        //Constructed yer
        AircraftPresenter presenter = new AircraftPresenter(null);
        //Create the real view and pass false to not activate the honey trap
        AircraftViewImplementation view = new AircraftViewImplementation(presenter, false);
        //Connect the view to the presenter from the MVP pattern
        presenter.setView(view);
        //Load the actual aircraft data from the aircraft model
        presenter.loadAircraft();
        //Show the Maintenace Dashboard with details on the aircrafts
        MaintenanceDashboardPanel dashboard = new MaintenanceDashboardPanel(presenter);

    }

    /**
     * Launches the honey trap (decoy) mode.
     * Used for detecting and handling unauthorized access.
     */
    public static void launchHoneyTrapMode() {
        //Log into the security-audit file that there is an infiltrator
        logHoneyTrap();
        //Create the decoy presenter
        AircraftPresenter presenter = new AircraftPresenter(null);
        //Show the decoy view and create the decoy list
        AircraftViewImplementation view = new AircraftViewImplementation(presenter, true);
        //Set the view
        presenter.setView(view);
        //Load mock aircraft data to deceive the attacker and
        view.loadFakeAircraft();
    }

    /**
     * Logs honey trap activation to the security audit file.
     * Supports incident analysis and cybersecurity compliance.
     */
    private static void logHoneyTrap() {
        // Open the file in append mode to log the infiltrator
        try (FileWriter fw = new FileWriter("security-audit.log", true)) {
            //Log and write the timestamp to the system
            fw.write("Honey trap login detected at " + new java.util.Date() + System.lineSeparator());
        } catch (IOException e) {
            //In production I have to change that to a certified logging mechanism
            e.printStackTrace();
        }
    }
}

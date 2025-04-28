package org.example.view;

import org.example.model.Aircraft;

import java.util.List;

/**
 * Interface for the Aircraft Model, using the MVC Architecture.
 * It defines the contract or implementation needed to show aircraft related information to the user
 *
 *
 * Montag Day 1 of the project Version 1.0
 * @author Rafail
 * @version 1.0
 * @since 2025-04-28
 */

public interface AircraftView {

    //Displays the list of Aircraft entries to the user
    //@param aircraftList List of Aircraft objects to be shown in the user interface.
    void showAircraftList(List<Aircraft> aircraftList);

    //Displays a system message to the user
    //@param message Textual message to be displayed (e.g., notifications, error messages).
    void showMessage(String message);
}

package org.example.app;

import org.example.presenter.AircraftPresenter;
import org.example.view.AircraftViewImplementation;

//Entry point for the Application
//Responsible for initiating the presenter and the view
//@author Rafail
//@version 1.0
//@since 2025-04-28

public class Main {
    public static void main(String[] args) {
        // Pass null to the presenter object because we have not created a view yet
        AircraftPresenter presenter = new AircraftPresenter(null);

        //Create the view and pass it to presenter object
        AircraftViewImplementation view = new AircraftViewImplementation(presenter);

        //Connect view to presenter object
        presenter.setView(view);

        //Kickstart the program
        //Load the existing aircraft data
        presenter.loadAircraft();
    }
}

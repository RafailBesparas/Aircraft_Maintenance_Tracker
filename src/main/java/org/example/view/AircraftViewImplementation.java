package org.example.view;

import org.example.model.Aircraft;
import org.example.presenter.AircraftPresenter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Implementation of the AircraftView interface
 * This class also provides a GUI to interact with the Aircraft Maintenance Tracker System.
 * First is to allow users to add new aircrafts and view the aircrafts stored in the database
 * @author Rafail
 * @version 1.0
 * @since 2025-04-28
 */

public class AircraftViewImplementation extends JFrame implements AircraftView {

    // Class for managing the business logic of the application
    private AircraftPresenter presenter;

    // List model from SWING Library to manage aircraft list entries to the GUI
    private DefaultListModel<String> listModel;

    // Initializing the GUI Components
    //@param presenter The AircraftPresenter instance of the business logic between model and view.
    public AircraftViewImplementation(AircraftPresenter presenter) {
        this.presenter = presenter;
        setupUI();
    }

    // Sets the graphical user interface components including the aircraft list and the Add Button
    // The user can input a new aircraft model and tail number through the prompt
    // The data is forwarded to the presenter  for validation and storage
    private void setupUI() {
        //Title to be displayed in the GUI
        setTitle("Aircraft Maintenance Tracker Power By Rafael Besparas");
        //Size of GUI
        setSize(500, 400);
        //Exit the QUI. When the user clicks the button X close the application
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Create a data container for a lsit of items, add or remove items to automatically update
        listModel = new DefaultListModel<>();

        //Create a visual component that shows what is inside the List Model
        //Show the list of added aircraft models
        JList<String> aircraftList = new JList<>(listModel);

        // Button to add an aircraft and also the logic on Event handling and what to do in this button
        // Ask the user to provide Model and Tail Number
        // Pass the data to Presenter and use the add aircraft method to add the aircraft
        JButton addButton = new JButton("Add Aircraft");
        addButton.addActionListener((ActionEvent e) -> {
            String model = JOptionPane.showInputDialog("Enter Model:");
            String tailNumber = JOptionPane.showInputDialog("Enter Tail Number:");
            //Check if both fields have been completed and have a value
            if (model != null && tailNumber != null) {
                presenter.addAircraft(model, tailNumber);
            }
        });

        // Show where the Aircraft list will be showed. In the Center.
        getContentPane().add(new JScrollPane(aircraftList), BorderLayout.CENTER);
        getContentPane().add(addButton, BorderLayout.SOUTH);

        //presenter.loadAircraft();
        //Show the window on the screen
        setVisible(true);
    }

    //Override this from the AircraftView and also show the list with Aircrafts.
    //@param aircraftList List of Aircraft objects to be shown to the user.
    @Override
    public void showAircraftList(List<Aircraft> aircraftList) {
        listModel.clear();
        for (Aircraft aircraft : aircraftList) {
            //Concatenate the model and the tail number in the list to be placed in a single entry
            listModel.addElement(aircraft.getModel() + " (" + aircraft.getTailNumber() + ")");
        }
    }

    //Display the pop-up message to the User
    //@param message Textual information or alert to be displayed.
    @Override
    public void showMessage(String message) {
        //Show the message to the application Pane/GUI
        JOptionPane.showMessageDialog(this, message);
    }

}

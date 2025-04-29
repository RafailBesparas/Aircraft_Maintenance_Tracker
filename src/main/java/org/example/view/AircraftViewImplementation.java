package org.example.view;

import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.presenter.AircraftPresenter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private JList<String> aircraftList;

    private List<Aircraft> aircraftData;

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
        setSize(600, 500);
        //Exit the QUI. When the user clicks the button X close the application
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Create a data container for a lsit of items, add or remove items to automatically update
        listModel = new DefaultListModel<>();

        aircraftList = new JList<>(listModel);

        aircraftData = new ArrayList<>();

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

        JButton updateButton = new JButton("Update Aircraft");
        updateButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = aircraftList.getSelectedIndex();
            if (selectedIndex != -1) {
                Aircraft selectedAircraft = aircraftData.get(selectedIndex);
                String newModel = JOptionPane.showInputDialog("Enter new Model:", selectedAircraft.getModel());
                String newTailNumber = JOptionPane.showInputDialog("Enter new Tail Number:", selectedAircraft.getTailNumber());
                if (newModel != null && newTailNumber != null) {
                    presenter.updateAircraft(selectedAircraft.getId(), newModel, newTailNumber);
                }
            } else {
                showMessage("Please select an aircraft to update.");
            }
        });

        JButton deleteButton = new JButton("Delete Aircraft");
        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = aircraftList.getSelectedIndex();
            if (selectedIndex != -1) {
                Aircraft selectedAircraft = aircraftData.get(selectedIndex);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this aircraft?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    presenter.deleteAircraft(selectedAircraft.getId());
                }
            } else {
                showMessage("Please select an aircraft to delete.");
            }
        });

        JButton addTaskButton = new JButton("Add Maintenance Task");
        addTaskButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = aircraftList.getSelectedIndex();
            if (selectedIndex != -1) {
                Aircraft selectedAircraft = aircraftData.get(selectedIndex);
                String description = JOptionPane.showInputDialog("Enter Task Description:");
                String dueDateStr = JOptionPane.showInputDialog("Enter Due Date (YYYY-MM-DD):");
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    formatter.setLenient(false);
                    java.util.Date utilDate = formatter.parse(dueDateStr);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Correct
                    presenter.addMaintenanceTask(selectedAircraft.getId(), description, sqlDate);
                } catch (Exception ex) {
                    showMessage("Invalid date format. Please enter as YYYY-MM-DD.");
                }
            } else {
                showMessage("Please select an aircraft first.");
            }
        });

        JButton viewTasksButton = new JButton("View Maintenance Tasks");
        viewTasksButton.addActionListener((ActionEvent e) -> {
            List<MaintenanceTask> tasks = presenter.loadMaintenanceTasks();
            new MaintenanceTaskViewer(tasks);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(viewTasksButton);



        // Show where the Aircraft list will be showed. In the Center.
        getContentPane().add(new JScrollPane(aircraftList), BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        //presenter.loadAircraft();
        //Show the window on the screen
        setVisible(true);
    }

    //Override this from the AircraftView and also show the list with Aircrafts.
    //@param aircraftList List of Aircraft objects to be shown to the user.
    @Override
    public void showAircraftList(List<Aircraft> aircraftList) {
        this.aircraftData = aircraftList;
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

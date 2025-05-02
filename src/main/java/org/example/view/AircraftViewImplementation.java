package org.example.view;

import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.presenter.AircraftPresenter;
import org.example.util.AircraftImportHandler;
import org.example.util.MaintenanceTaskImportHandler;

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
 *
 * New Changes in the version 2
 * The user can update and delete aircrafts
 *
 * GUI behavior is driven by event listeners on various buttons, enabling full CRUD
 * functionality and maintenance scheduling in an MVP-compliant architecture
 *
 * @author Rafail
 * @version 2.0
 * @since 2025-04-30
 */

public class AircraftViewImplementation extends JFrame implements AircraftView {

    // Class for managing the business logic of the application
    private AircraftPresenter presenter;

    // List model from SWING Library to manage aircraft list entries to the GUI
    private DefaultListModel<String> listModel;

    // This diplays the list of strings which actually are the list of Aircrafts
    private JList<String> aircraftList;

    private MaintenanceDashboardPanel dashboard;

    // This hidden data structure holds actually the list of aircrafts
    // It is used internally
    // This allows to connect the GUI selection with the real Aircraft record later in the code
    private List<Aircraft> aircraftData;

    private boolean honeyTrapMode;

    // Initializing the GUI Components
    //@param presenter The AircraftPresenter instance of the business logic between model and view.
    public AircraftViewImplementation(AircraftPresenter presenter, boolean honeyTrapMode) {
        this.presenter = presenter;
        this.honeyTrapMode = honeyTrapMode;
        setupUI();
    }

    // Sets the graphical user interface components including the aircraft list and the Add Button
    // The user can input a new aircraft model and tail number through the prompt
    // The user also can update and delete aircrafts.
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

        // List of aircrafts
        aircraftList = new JList<>(listModel);

        // Hidden data structure holding the actual aircraft objects
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

        // Button to update an aircraft. Select it in the GUI and update the information
        // Ask the user to provide new Model and new Tail Number
        // Pass the data to Presenter and use the add aircraft method to update the aircraft information
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

        // Button to delete an aircraft. Select it in the GUI and delete the information
        // Pass the data to Presenter that will delete the selected Aircraft from the Database.
        JButton deleteButton = new JButton("Delete Aircraft");
        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = aircraftList.getSelectedIndex();
            if (selectedIndex != -1) {
                Aircraft selectedAircraft = aircraftData.get(selectedIndex);
                // Show a message to the user if wants to delete the aircraft
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this aircraft?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    presenter.deleteAircraft(selectedAircraft.getId());
                }
            } else {
                showMessage("Please select an aircraft to delete.");
            }
        });

        // Button to add a task
        // Click on the selected aircraft then click on the add maintenance task
        // Enter the description and due data
        // The data is passed into the Presented which will help to add the task into the database
        // The task is then visible into the Maintenance task GUI not the Aircraft GUI
        JButton addTaskButton = new JButton("Add Maintenance Task");
        addTaskButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = aircraftList.getSelectedIndex();
            if (selectedIndex != -1) {
                Aircraft selectedAircraft = aircraftData.get(selectedIndex);
                String description = JOptionPane.showInputDialog("Enter Task Description:");
                String dueDateStr = JOptionPane.showInputDialog("Enter Due Date (YYYY-MM-DD):");
                // This was created to check the data passed and because I have encoutered a lot of bugs to get the
                // appropriate date format
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

        // The button that when clikced will show the Maintenance Task GUI
        JButton viewTasksButton = new JButton("View Maintenance Tasks");
        viewTasksButton.addActionListener((ActionEvent e) -> {
            List<MaintenanceTask> tasks = presenter.loadMaintenanceTasks();
            new MaintenanceTaskViewer(tasks);
        });

        JButton importButton = new JButton("Import Aircraft");
        importButton.addActionListener(e -> {
            AircraftImportHandler.importAircraftFromFile(this);
            presenter.loadAircraft();
        });

        JButton importTaskButton = new JButton("Import Maintenance Tasks");
        importTaskButton.addActionListener(e -> {
            MaintenanceTaskImportHandler.importTasksFromFile(this);
        });

        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = aircraftList.getSelectedIndex();
            if (selectedIndex != -1) {
                Aircraft selectedAircraft = aircraftData.get(selectedIndex);
                List<MaintenanceTask> allTasks = presenter.loadMaintenanceTasks();
                new AircraftDetailView(this, selectedAircraft, allTasks);
            } else {
                showMessage("Please select an aircraft to view details.");
            }
        });

        if (honeyTrapMode) {
            addButton.setEnabled(false);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
            addTaskButton.setEnabled(false);
            viewTasksButton.setEnabled(false);
            viewDetailsButton.setEnabled(false);
            importButton.setEnabled(false);
            importTaskButton.setEnabled(false);
        }

        // The button panel is an object that will contain all the buttons
        // A single layout like a pane with the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.add(importButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(viewTasksButton);
        buttonPanel.add(importTaskButton);
        buttonPanel.add(viewDetailsButton);

        dashboard = new MaintenanceDashboardPanel(presenter);




        // Show where the Aircraft list will be showed. In the Center.
        getContentPane().add(new JScrollPane(aircraftList), BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(dashboard, BorderLayout.NORTH);


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

    public void loadFakeAircraft() {
        List<Aircraft> fakeList = List.of(
                new Aircraft(9991, "FakeJet 1000", "F-JET1"),
                new Aircraft(9992, "Phantom 200", "P-200"),
                new Aircraft(9993, "GhostRider X", "G-RDX")
        );
        showAircraftList(fakeList);
    }

}

package org.example.presenter;

import org.example.db.Database;
import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.view.AircraftView;

// For the GUI and Java Queries to work
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// ========================= DO-178C MODULE HEADER =========================
// Module Name    : AircraftPresenter.java
// Application    : Aircraft Maintenance Tracker
// Description    : Presenter layer in MVP architecture. Manages logic flow between
//                  view and model layers including database access, data mutation,
//                  and user feedback.
// Safety Level   : DAL C (data processing and user command interpretation)
// Developed By   : Rafail
// Last Modified  : 2025-05-02
// ========================================================================


/**
 * AircraftPresenter is the central coordinator in the MVP design pattern.
 *
 * Responsibilities:
 *  - Mediates between AircraftView and database models
 *  - Implements business logic
 *  - Ensures traceable flow of actions and updates
 */
public class AircraftPresenter {

    //It references tto the Aircraft view component that has the role of displaying information to the user
    private AircraftView view;

    //Constructor of the AircraftPresenter using the Aircraft view, updates hte view in the GUI
    //@param view The view component to be updated by the presenter.
    public AircraftPresenter(AircraftView view) {
        this.view = view;
    }

    //Load all the entries for Aircrafts from the Database and updates the view
    //Establishes a connection to PostgreSQL and retrieve all the records.
    // Populates the view with the aircrafts in the database
    // If there is a database access problem. then an appropriate error message occurs
    public void loadAircraft() {
        //List of aircrafts
        List<Aircraft> aircraftList = new ArrayList<>();
        //Create the connection
        //Create the statement
        // Execute the Query to the database and retrieve results
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM aircraft")) {

            //Read all the results from the query
            while (rs.next()) {
                Aircraft aircraft = new Aircraft(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("tail_number")
                );
                //Add the results to the list
                aircraftList.add(aircraft);
            }
            // Show the results from the query
            view.showAircraftList(aircraftList);
        //If needed throw and exception
        } catch (SQLException e) {
            view.showMessage("Error loading aircraft: " + e.getMessage());
        }
    }

    /**
     * Updates the view reference for delayed initialization.
     *
     * @param view AircraftView instance to bind
     */
    public void setView(AircraftView view) {
        this.view = view;
    }

    // Add a new aircraft entry to the database based on the user input
    //Refresh the table when a new aircraft is added
    //If a database insertion error occurs please throw an appropriate error
    //@param model The model name of the new aircraft.
    //@param tailNumber The unique registration number (tail number) of the new aircraft.
    public void addAircraft(String model, String tailNumber) {
        //Initiate the connection to the database
        try (Connection conn = Database.getConnection();
             //Create the statement and excecute the statement
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO aircraft (model, tail_number) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            //Based on the user input in the first ? add the model and in the second ? add the tail number
            //Excecute the statement to update the database
            pstmt.setString(1, model);
            pstmt.setString(2, tailNumber);
            pstmt.executeUpdate();

            //Show the appropriate message
            view.showMessage("Aircraft added successfully.");
            loadAircraft();
        //Throw an error when needed
        } catch (SQLException e) {
            view.showMessage("Error adding aircraft: " + e.getMessage());
        }
    }

    // Updates the model and tail number for the Aircraft
    public void updateAircraft(int id, String newModel, String newTailNumber){
        // Establish the connection with the database and also query the database to update the information
        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("UPDATE aircraft SET model = ?, tail_number = ? WHERE id = ?"))
        {
         // Get the data from the user from the GUI
         // In the first ? add the model provided by the user
         // In the second ? add the tailnumber provided by the user
         pstmt.setString(1, newModel);
         pstmt.setString(2, newTailNumber);
         pstmt.setInt(3, id);
         // Check if the update has happened to show the appropriate message
         int rowsUpdated = pstmt.executeUpdate();

         if(rowsUpdated > 0){
             view.showMessage("Aircraft updated successfully.");
         } else{
             view.showMessage("Aircraft not found for update.");
          }
         // load the list of aircrafts and update the view
         loadAircraft();
        } catch (SQLException e){
            view.showMessage("Error updating aircraft " + e.getMessage());
        }
    }

    /**
     * Deletes an aircraft from the database by ID.
     * Triggers full view reload to reflect the change.
     *
     * @param id Aircraft ID to delete
     */
    public void deleteAircraft(int id){
        //Establish the connection and Query the database to delete the aircraft
        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM aircraft WHERE id = ?"))
        {
            // Based on the Aircraft chosen in the GUI delete it from the Database and View
            pstmt.setInt(1, id);
            //Check if the action was implemented
            int rowsDeleted = pstmt.executeUpdate();

            // Show the appropriate message
            if (rowsDeleted > 0) {
                view.showMessage("Aircraft deleted successfully.");
            } else {
                view.showMessage("Aircraft not found for deletion.");
            }
            loadAircraft();
        } catch (SQLException e) {
            view.showMessage("Error deleting aircraft: " + e.getMessage());
        }
    }

    // ==================== MAINTENANCE TASK OPERATIONS ====================
    // ==================== MAINTENANCE TASK OPERATIONS ====================
    // ==================== MAINTENANCE TASK OPERATIONS ====================

    ///  View for the Maintenance Tasks of Aircrafts

    /**
     * Add a maintenance task for a given aircraft.
     * Choose it from the GUI, click on it and then press Add Maintenance Task
     *
     * @param aircraftId ID of the aircraft to associate the task with
     * @param taskDescription The description of the task
     * @param dueDate Due date for the task (as java.sql.Date)
     */
 public void addMaintenanceTask(int aircraftId, String taskDescription, Date dueDate) {
     // Establish connection to the database
     // Query the database to insert the task information and create a new task
     try (Connection conn = Database.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(
                  "INSERT INTO maintenance_task (aircraft_id, task_description, due_date, status) VALUES (?, ?, ?, 'Pending')")) {
         //Based on the User input in the first ? add the aircraftId, based on the 2 ? add the task description
         //For the due date we have a certain day format to be used
         pstmt.setInt(1, aircraftId);
         pstmt.setString(2, taskDescription);
         pstmt.setDate(3, new java.sql.Date(dueDate.getTime()));
         pstmt.executeUpdate();
         view.showMessage("Maintenance task added successfully.");
     } catch (SQLException e) {
         view.showMessage("Error adding maintenance task: " + e.getMessage());
     }
 }

    /**
     * Retrieves and returns all maintenance tasks from the database, joined with aircraft info.
     *
     * @return List of MaintenanceTask objects sorted by due date
     */
    public List<MaintenanceTask> loadMaintenanceTasks() {
        // Create a list of the maintenance Tasks based on the Data Strucutre ArrayList
        List<MaintenanceTask> taskList = new ArrayList<>();
        //Establish connection and also create a query
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             // This query retrieves all maintenance tasks and their linked aircraft information (model + tail number),
             // ordered by task due date from earliest to latest.
             ResultSet rs = stmt.executeQuery(
                     "SELECT mt.id, mt.aircraft_id, a.model, a.tail_number, mt.task_description, mt.due_date, mt.status " +
                             "FROM maintenance_task mt JOIN aircraft a ON mt.aircraft_id = a.id ORDER BY mt.due_date ASC")) {

            // Show all the tasks in the database
            while (rs.next()) {
                // Concatenate the Model and the tail number to create the display name
                String displayName = rs.getString("model") + " (" + rs.getString("tail_number") + ")";
                MaintenanceTask task = new MaintenanceTask(
                        rs.getInt("id"),
                        rs.getInt("aircraft_id"),
                        displayName,
                        rs.getString("task_description"),
                        rs.getDate("due_date"),
                        rs.getString("status")
                );
                // Add the task to the list of tasks
                taskList.add(task);
            }
        } catch (SQLException e) {
            view.showMessage("Error loading maintenance tasks: " + e.getMessage());
        }
        return taskList;
    }

    /**
     * Loads and returns all aircrafts without directly displaying them.
     * Used in dropdowns, data selectors, views and GUIs.
     *
     * @return List of aircraft from database
     */
    public List<Aircraft> getAircraftList() {
        // Initialize a list to store the aircraft objects returned from the database
        List<Aircraft> aircraftList = new ArrayList<>();
        // Establish connection in the database, use a statement/query and run it in the database
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             // Query to get results
             ResultSet rs = stmt.executeQuery("SELECT * FROM aircraft")) {
            // For all the resutls from the query, read them and Construct an Aircraft object from the current row's
            while (rs.next()) {
                aircraftList.add(new Aircraft(
                        rs.getInt("id"), // Fetch the aircraft id
                        rs.getString("model"), // Fetch the aircraft model
                        rs.getString("tail_number") // Fetsch the tail number
                ));
            }
            // If a database error occurs, display the error message to the user
        } catch (SQLException e) {
            view.showMessage("Error loading aircraft list: " + e.getMessage());
        }
        // Return the complete list of Aircraft
        return aircraftList;
    }

}// End of class
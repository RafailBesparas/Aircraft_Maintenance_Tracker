package org.example.presenter;

import org.example.db.Database;
import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.view.AircraftView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter class of the MVP model Model View Presenter
 * Responsible for handling the business logic
 * Is the coordinator between the Aircraft model and the AircraftView
 *
 * This class performs the database operations , queries, process the user inputs and update the view accordingly
 * @author Rafail
 * @version 1.0
 * @since 2025-04-28
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

    //Set the view component after
    //@param view The AircraftView implementation to associate with the presenter.
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

    public void updateAircraft(int id, String newModel, String newTailNumber){
        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("UPDATE aircraft SET model = ?, tail_number = ? WHERE id = ?"))
        {
         pstmt.setString(1, newModel);
         pstmt.setString(2, newTailNumber);
         pstmt.setInt(3, id);
         int rowsUpdated = pstmt.executeUpdate();

         if(rowsUpdated > 0){
             view.showMessage("Aircraft updated successfully.");
         } else{
             view.showMessage("Aircraft not found for update.");
          }
         loadAircraft();
        } catch (SQLException e){
            view.showMessage("Error updating aircraft " + e.getMessage());
        }
    }

    public void deleteAircraft(int id){
        try(Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM aircraft WHERE id = ?"))
        {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

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

 ///  View for the Maintenance Tasks of Aircrafts

 public void addMaintenanceTask(int aircraftId, String taskDescription, Date dueDate) {
     try (Connection conn = Database.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(
                  "INSERT INTO maintenance_task (aircraft_id, task_description, due_date, status) VALUES (?, ?, ?, 'Pending')")) {
         pstmt.setInt(1, aircraftId);
         pstmt.setString(2, taskDescription);
         pstmt.setDate(3, new java.sql.Date(dueDate.getTime()));
         pstmt.executeUpdate();
         view.showMessage("Maintenance task added successfully.");
     } catch (SQLException e) {
         view.showMessage("Error adding maintenance task: " + e.getMessage());
     }
 }

    public List<MaintenanceTask> loadMaintenanceTasks() {
        List<MaintenanceTask> taskList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM maintenance_task ORDER BY due_date ASC")) {
            while (rs.next()) {
                MaintenanceTask task = new MaintenanceTask(
                        rs.getInt("id"),
                        rs.getInt("aircraft_id"),
                        rs.getString("task_description"),
                        rs.getDate("due_date"),
                        rs.getString("status")
                );
                taskList.add(task);
            }
        } catch (SQLException e) {
            view.showMessage("Error loading maintenance tasks: " + e.getMessage());
        }
        return taskList;
    }

}// End of class
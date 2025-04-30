package org.example.model;

import java.util.Date;

/**
* Represents the scheduled maintenance task associated with a specific aircraft
 * This class helps to act as a model for what a maintenance task must as data
 * Each task is uniquely indentified and linked to an aircraft, defined by a description, due date and current status.
 *
 * This class is used to display and manage maintenance tasks with the appropriate information in the UI and
 * for Storage in PostegreSQL.
 *
 * Status Values should be limited to Pending or Complete
 *
 * @author Rafail
 * @version 2.0
 * @since 2025-04-28
 *
 */

public class MaintenanceTask {

    // Unique Identifier for the task(primary key)
    private int id;

    // Foreign Key for reference to the Aircrafts
    private int aircraftId;

    // Description for the maintenance activity
    private String taskDescription;

    // Deadline for the maintenance task
    private Date dueDate;

    // Combined model and tail number for UI
    private String aircraftDisplayName;

    // Task Status : "Pending" or "Complete"
    private String status;

    /**
     *Constructor for the main Task with all attributes Specified
     *
     * @param id unique task identifier
     * @param aircraftId ID of the aircraft this task belongs to
     * @param aircraftDisplayName a readable identifier for the aircraft
     * @param taskDescription the textual description of the task
     * @param dueDate when the task is due
     * @param status the status of the task: Pending or Completed
     */
    public MaintenanceTask(int id, int aircraftId,String aircraftDisplayName, String taskDescription, Date dueDate, String status) {
        this.id = id;
        this.aircraftDisplayName = aircraftDisplayName;
        this.aircraftId = aircraftId;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getter for the Maintenance Tasks

    public int getId() {
        return id;
    }

    public String getAircraftDisplayName() {
        return aircraftDisplayName;
    }

    public int getAircraftId() {
        return aircraftId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}

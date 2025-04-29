package org.example.model;

import java.util.Date;

public class MaintenanceTask {

    private int id;
    private int aircraftId;
    private String taskDescription;
    private Date dueDate;

    // The status can only be complete or Pending
    private String status;

    public MaintenanceTask(int id, int aircraftId, String taskDescription, Date dueDate, String status) {
        this.id = id;
        this.aircraftId = aircraftId;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() {
        return id;
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

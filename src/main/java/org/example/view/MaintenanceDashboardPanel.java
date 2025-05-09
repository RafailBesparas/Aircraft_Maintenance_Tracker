package org.example.view;


import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.presenter.AircraftPresenter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Dashboard panel displaying maintenance KPIs:
 * - Total aircraft
 * - Aircraft with pending maintenance
 * - Completed maintenance tasks this month
 */
public class MaintenanceDashboardPanel extends JPanel {

    // The label that will show how many aircrafts in the system
    private JLabel totalAircraftLabel;
    // The label with the aircrafts in maintenance
    private JLabel dueMaintenanceLabel;
    // The label with the completed tasks this month
    private JLabel completedThisMonthLabel;

    // Instantiate the presenter object
    private AircraftPresenter presenter;

    // Constructor and how the view starts
    public MaintenanceDashboardPanel(AircraftPresenter presenter) {
        this.presenter = presenter;
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(new TitledBorder("Maintenance Dashboard"));

        // Initialize KPI labels
        totalAircraftLabel = createStyledLabel("Total Aircraft: 0");
        dueMaintenanceLabel = createStyledLabel("Aircraft Due for Maintenance: 0");
        completedThisMonthLabel = createStyledLabel("Completed Maintenance This Month: 0");

        // Add labels to the panel
        add(totalAircraftLabel);
        add(dueMaintenanceLabel);
        add(completedThisMonthLabel);

        // Load the initial numbers from the database
        refreshDashboard();
    }

    // Creates a styled label for visual consistency
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(0, 70, 140));
        return label;
    }

    // Updates the KPI values from the presenter
    public void refreshDashboard() {
        List<Aircraft> aircraftList = presenter.getAircraftList();
        List<MaintenanceTask> taskList = presenter.loadMaintenanceTasks();

        totalAircraftLabel.setText("Total Aircraft: " + aircraftList.size());

        long dueOrPending = taskList.stream()
                .filter(task -> "Pending".equalsIgnoreCase(task.getStatus()))
                .count();
        dueMaintenanceLabel.setText("Aircraft Due for Maintenance: " + dueOrPending);

        long completedThisMonth = taskList.stream()
                .filter(task -> "Completed".equalsIgnoreCase(task.getStatus()))
                .filter(task -> {
                    LocalDate taskDate = new java.sql.Date(task.getDueDate().getTime()).toLocalDate();
                    LocalDate now = LocalDate.now();
                    return taskDate.getMonth() == now.getMonth() && taskDate.getYear() == now.getYear();
                })
                .count();
        completedThisMonthLabel.setText("Completed Maintenance This Month: " + completedThisMonth);
    }
}

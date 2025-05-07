package org.example.view;

import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.presenter.AircraftPresenter;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * AircraftDetailView displays summary information about a selected aircraft,
 * including model, tail number, current date, and a breakdown of its maintenance tasks.
 *
 * This dialog supports traceability by summarizing status-relevant maintenance data per aircraft.
 */
public class AircraftDetailView extends JDialog {

    /**
     * Constructs the modal dialog showing aircraft details and task stats.
     *
     * @param parent the parent frame
     * @param aircraft the selected Aircraft object
     * @param allTasks list of all maintenance tasks for filtering
     */
    public AircraftDetailView(JFrame parent, Aircraft aircraft, List<MaintenanceTask> allTasks) {
        super(parent, "Individual Aircraft Details", true); // It is the title of the Window
        setSize(400, 300); // Size of the window
        setLocationRelativeTo(parent); // Center the box on the screen

        // Create a basic panel with a vertical layout
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        // add the necessary padding
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Name the button and display th necessary information
        JLabel modelLabel = new JLabel("Aircraft Model: " + aircraft.getModel());
        // Display the tailNumberLabel and also the tail number
        JLabel tailNumberLabel = new JLabel("Tail Number: " + aircraft.getTailNumber());

        // Simulate current date as "Date of Entry"
        JLabel dateOfEntryLabel = new JLabel("Date of Entry: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        // Filter and count tasks related to this aircraft
        long total = allTasks.stream().filter(t -> t.getAircraftId() == aircraft.getId()).count();
        long completed = allTasks.stream().filter(t -> t.getAircraftId() == aircraft.getId() && "Completed".equalsIgnoreCase(t.getStatus())).count();
        long pending = total - completed;

        // Display maintenance task counts
        JLabel taskTotalLabel = new JLabel("Total Maintenance Tasks: " + total);
        JLabel statusSummaryLabel = new JLabel("Status Summary: " + pending + " Pending / " + completed + " Completed");

        // Add the User Interface components to the panel
        panel.add(modelLabel);
        panel.add(tailNumberLabel);
        panel.add(dateOfEntryLabel);
        panel.add(taskTotalLabel);
        panel.add(statusSummaryLabel);

        // Close the button and dismiss the dialog
        JButton closeButton = new JButton("Close");
        // Dispose the dialog on click
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);

        // Attach the panel to dialog and display
        add(panel);
        setVisible(true);
    }
}
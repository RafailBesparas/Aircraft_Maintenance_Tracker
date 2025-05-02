package org.example.view;

import org.example.model.Aircraft;
import org.example.model.MaintenanceTask;
import org.example.presenter.AircraftPresenter;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AircraftDetailView extends JDialog {

    public AircraftDetailView(JFrame parent, Aircraft aircraft, List<MaintenanceTask> allTasks) {
        super(parent, "Individual Aircraft Details", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel modelLabel = new JLabel("Aircraft Model: " + aircraft.getModel());
        JLabel tailNumberLabel = new JLabel("Tail Number: " + aircraft.getTailNumber());

        // Simulate current date as "Date of Entry"
        JLabel dateOfEntryLabel = new JLabel("Date of Entry: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        // Task filtering
        long total = allTasks.stream().filter(t -> t.getAircraftId() == aircraft.getId()).count();
        long completed = allTasks.stream().filter(t -> t.getAircraftId() == aircraft.getId() && "Completed".equalsIgnoreCase(t.getStatus())).count();
        long pending = total - completed;

        JLabel taskTotalLabel = new JLabel("Total Maintenance Tasks: " + total);
        JLabel statusSummaryLabel = new JLabel("Status Summary: " + pending + " Pending / " + completed + " Completed");

        panel.add(modelLabel);
        panel.add(tailNumberLabel);
        panel.add(dateOfEntryLabel);
        panel.add(taskTotalLabel);
        panel.add(statusSummaryLabel);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);

        add(panel);
        setVisible(true);
    }
}
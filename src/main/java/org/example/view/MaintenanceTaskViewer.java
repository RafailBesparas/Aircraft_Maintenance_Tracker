package org.example.view;

import org.example.model.MaintenanceTask;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class MaintenanceTaskViewer extends JFrame {

    private JTable taskTable;
    private DefaultTableModel tableModel;

    public MaintenanceTaskViewer(List<MaintenanceTask> taskList) {
        setTitle("Maintenance Task Viewer");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Aircraft ID");
        tableModel.addColumn("Description");
        tableModel.addColumn("Due Date");
        tableModel.addColumn("Status");

        taskTable = new JTable(tableModel);

        fillTaskTable(taskList);

        JScrollPane scrollPane = new JScrollPane(taskTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void fillTaskTable(List<MaintenanceTask> taskList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (MaintenanceTask task : taskList) {
            Object[] row = {
                    task.getId(),
                    task.getAircraftId(),
                    task.getTaskDescription(),
                    sdf.format(task.getDueDate()),
                    task.getStatus()
            };
            tableModel.addRow(row);
        }
    }
}
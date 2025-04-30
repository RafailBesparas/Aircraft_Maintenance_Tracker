package org.example.view;

import org.example.model.MaintenanceTask;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Swing GUI window for displaying scheduled maintenance tasks.
 *
 * This view presents a list of maintenance tasks including:
 * Task ID, linked Aircraft (Model+Tail Number), Description, Due Date, and Status.
 *
 * This class provides a read only visual interface for monitoring the
 * current maintenance schedule and status of all aircraft in the system.
 *
 *It uses JTable with a DefaultTableModel for dynamic row updates and
 * formats dates for human readability.
 *
 * @author Rafail
 * @version 2.0
 * @since 2025-04-28
 */

public class MaintenanceTaskViewer extends JFrame {

    //This is the Swing table to visually display the tasks
    private JTable taskTable;

    // This is the dataModel, it contains the actual content, it allows to dynamically add rows
    private DefaultTableModel tableModel;

    /**
     * Maintenance Task Viewer GUI and loads task data into a table.
     *
     * @param taskList A list of MaintenanceTask objects to display
     */
    public MaintenanceTaskViewer(List<MaintenanceTask> taskList) {
        setTitle("Maintenance Task Viewer");
        setSize(700, 400);
        //Centers the window on the screen when it opens.
        setLocationRelativeTo(null);
        //Specifies what happens when the user clicks the X button on the window.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Initialize the table model with the column headers to appear in the GUI
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Aircraft Name & TailNumber");
        tableModel.addColumn("Description");
        tableModel.addColumn("Due Date");
        tableModel.addColumn("Status");

        // Add the columns in the GUI
        taskTable = new JTable(tableModel);

        // Load the data of the tasks and show it in the GUI
        fillTaskTable(taskList);

        JScrollPane scrollPane = new JScrollPane(taskTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Populates the table with maintenance task data.
     *
     * @param taskList List of MaintenanceTask objects
     */
    private void fillTaskTable(List<MaintenanceTask> taskList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (MaintenanceTask task : taskList) {
            Object[] row = {
                    task.getId(),
                    task.getAircraftDisplayName(),
                    task.getTaskDescription(),
                    sdf.format(task.getDueDate()),
                    task.getStatus()
            };
            tableModel.addRow(row);
        }
    }
}
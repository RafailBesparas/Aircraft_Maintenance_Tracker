package org.example.util;

// Excel Handling
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.db.Database;

// UI for file dialog
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.w3c.dom.*;

// ========================= DO-178C MODULE HEADER =========================
// Module Name    : MaintenanceTaskImportHandler.java
// Application    : Aircraft Maintenance Tracker
// Description    : Handles bulk import of maintenance tasks from CSV, Excel, and XML files.
// Safety Level   : DAL C (task status impacts operational safety)
// Developed By   : Rafail
// Last Modified  : 2025-05-02
// Certification Notes:
//   - File parsing and database insertion must ensure data validity.
//   - Logging should support audit trail for certification traceability.
// ========================================================================


/**
 * MaintenanceTaskImportHandler:
 * Allows users to import maintenance tasks for aircraft from CSV, Excel, and XML files.
 * - Provides extension-specific routing
 * - Stores imported data into the PostgreSQL maintenance_task table
 * - Validates aircraft association via aircraft_id (assumed to exist)
 */
public class MaintenanceTaskImportHandler {
    /**
     * Opens a file chooser dialog and selects the appropriate import method
     * based on file extension (.csv, .xml, .xlsx).
     */
    public static void importTasksFromFile(JFrame parentFrame) {
        // Open file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        // Show the window dialog
        int result = fileChooser.showOpenDialog(parentFrame);

        // If user selects a file proceed
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the file choosen by the user
            File file = fileChooser.getSelectedFile();
            // Convert it into lowecase for extention to check
            String fileName = file.getName().toLowerCase();

            // For each type of file delegate it into the appropriate importer function
            try {
                if (fileName.endsWith(".csv")) {
                    importFromCSV(file);
                } else if (fileName.endsWith(".xml")) {
                    importFromXML(file);
                } else if (fileName.endsWith(".xlsx")) {
                    importFromExcel(file);
                } else {
                    // If the file format is not CSV, XML or XLSX write this message
                    JOptionPane.showMessageDialog(parentFrame, "Unsupported file format.");
                }
            } catch (Exception e) {
                // When the task fails and the file cannot be imported write
                JOptionPane.showMessageDialog(parentFrame, "Task import failed: " + e.getMessage());
            }
        }
    }

    /**
     * Reads tasks from CSV file and inserts them into the database.
     *
     * Format: aircraftId,description,dueDate,status
     */
    private static void importFromCSV(File file) throws Exception {
        // Open the file for reading
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             // Establish the database connection
             Connection conn = Database.getConnection()) {

            String line; // This variable will store each line from the file
            int lineNum = 0; // Counter and tracker the line number

            while ((line = reader.readLine()) != null) {
                if (++lineNum == 1) continue; // Skip header
                String[] parts = line.split(","); // Split the rows by a comma
                if (parts.length != 4) continue; // Skip the wrongly formed lines

                int aircraftId = Integer.parseInt(parts[0].trim()); // take the aircraft id
                String description = parts[1].trim(); // take from the file the task description
                String dueDateStr = parts[2].trim(); // take the due date
                String status = parts[3].trim(); // take the status

                // Insert them into the necessary database
                insertTask(conn, aircraftId, description, dueDateStr, status);
            }
        }

        JOptionPane.showMessageDialog(null, "CSV task import complete.");
    }

    /**
     * Reads tasks from Excel (.xlsx) file using Apache POI and inserts them into the database.
     * Expects same format as CSV.
     */
    private static void importFromExcel(File file) throws Exception {
        // Open the input stream
        try (FileInputStream fis = new FileInputStream(file);
             // Apache Poi workbook object
             Workbook workbook = new XSSFWorkbook(fis);
             // Initiate the database connection
             Connection conn = Database.getConnection()) {

            // Read the first sheet from the file
            Sheet sheet = workbook.getSheetAt(0);
            // Varialbe to check if the header is skipped
            boolean headerSkipped = false;

            // Skip the first row header
            for (Row row : sheet) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                // Get the aircraft id
                int aircraftId = (int) row.getCell(0).getNumericCellValue();
                // Get the description
                String description = row.getCell(1).getStringCellValue().trim();
                // Get the dueDate
                String dueDateStr = row.getCell(2).getStringCellValue().trim();
                // Get the status
                String status = row.getCell(3).getStringCellValue().trim();

                // Insert all of them into the database
                insertTask(conn, aircraftId, description, dueDateStr, status);
            }
        }

        // Show this message to the user for the successfull process
        JOptionPane.showMessageDialog(null, "Excel task import complete.");
    }

    /**
     * Reads tasks from XML file and inserts them into the database.
     * Expected XML format:
     * <task>
     *   <aircraftId>1</aircraftId>
     *   <description>Check engine</description>
     *   <dueDate>2025-06-01</dueDate>
     *   <status>Pending</status>
     * </task>
     */
    private static void importFromXML(File file) throws Exception {
        // Create a factory from the XML parser
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Build the document parser from the factory
        Document doc = builder.parse(file);
        // Get all the task elements
        NodeList taskNodes = doc.getElementsByTagName("task");

        // Establish a connection with the database
        try (Connection conn = Database.getConnection()) {
            // Iterate all the tasks and return the number of task elements
            for (int i = 0; i < taskNodes.getLength(); i++) {
                // Get the current task node and cast it into an Element so its children can be accessed by the tag name
                Element task = (Element) taskNodes.item(i);

                // retrieve the aircraft id and the content of this item
                int aircraftId = Integer.parseInt(task.getElementsByTagName("aircraftId").item(0).getTextContent().trim());
                // Extract the description from the file and trim the whitespace
                String description = task.getElementsByTagName("description").item(0).getTextContent().trim();
                // Extract the dueDateStr and trim the whitespace
                String dueDateStr = task.getElementsByTagName("dueDate").item(0).getTextContent().trim();
                // Extract the status and trim the whitespace
                String status = task.getElementsByTagName("status").item(0).getTextContent().trim();

                // Insert the task into the database
                insertTask(conn, aircraftId, description, dueDateStr, status);
            }
        }

        // Show this message if the transaction is completed and the XML is imported successfully
        JOptionPane.showMessageDialog(null, "XML task import complete.");
    }

    /**
     * Inserts a single maintenance task record into the database.
     *
     * @param conn         Active DB connection
     * @param aircraftId   Foreign key ID of the aircraft
     * @param description  Maintenance task description
     * @param dueDateStr   Date string (YYYY-MM-DD format)
     * @param status       Task status ("Pending", "Complete")
     */
    private static void insertTask(Connection conn, int aircraftId, String description, String dueDateStr, String status) throws Exception {
        // Statement to use for inserting the data into the database
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO maintenance_task (aircraft_id, task_description, due_date, status) VALUES (?, ?, ?, ?)"
        );
        // Where the first ? place the value of the aircraftID
        stmt.setInt(1, aircraftId);
        // Where the second ? place the value of the description
        stmt.setString(2, description);
        // Where the third ? is place the value of the date
        stmt.setDate(3, java.sql.Date.valueOf(dueDateStr));
        // Where the fourth ? is place the value of status
        stmt.setString(4, status);
        // Excecute the query and update the database
        stmt.executeUpdate();
    }
}
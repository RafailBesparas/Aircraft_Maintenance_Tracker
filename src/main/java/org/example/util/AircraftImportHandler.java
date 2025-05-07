package org.example.util;

// For Excel file handling (Apache POI)
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.db.Database;
import org.example.model.Aircraft;

// For JFileChooser and dialogs
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// ========================= DO-178C MODULE HEADER =========================
// Module Name    : AircraftImportHandler.java
// Application    : Aircraft Maintenance Tracker
// Description    : Handles import of aircraft data from CSV, XML, and Excel files.
// Safety Level   : DAL D or C (Data integrity, audit relevance, traceable ingestion)
// Developed By   : Rafail
// Last Modified  : 2025-05-02
// Notes          : Prevents duplicates using tail number checks; provides file I/O interface
// ========================================================================


/**
 * AircraftImportHandler: Handles aircraft import from external files.
 * Supported formats: .csv, .xml, .xlsx
 *
 * Key responsibilities:
 *  - Read and parse files selected by the user
 *  - Avoid importing duplicate aircraft using tail number
 *  - Insert new entries to database
 *
 * Security and DO-178C Notes:
 *  - Prevents duplicate insertions with tail number constraints
 *  - Lacks file schema validation (improvement point)
 *  - Inputs from users must be sanitized and logged in higher DAL systems
 */
public class AircraftImportHandler {

    /**
     * Launches a file picker and routes to format-specific import logic.
     * Only allows import from known extensions (csv, xml, xlsx).
     * The user is allowed to choose only between these formats
     */
    public static void importAircraftFromFile(JFrame parentFrame) {
        //Open the file to choose a dialog
        JFileChooser fileChooser = new JFileChooser();
        //Show the dialog
        int result = fileChooser.showOpenDialog(parentFrame);

        // If the user selects a file
        if (result == JFileChooser.APPROVE_OPTION) {
            //Get the selected file the user has choosen
            File file = fileChooser.getSelectedFile();
            //Convert the file name to lowercase to get an extention check
            String fileName = file.getName().toLowerCase();

            // based on the file extention the algorithm chooses the right function
            try {
                // Checks if the extention is csv
                if (fileName.endsWith(".csv")) {
                    importFromCSV(file); // launches the function for csv and passes the file there
                } else if (fileName.endsWith(".xml")) { // checks if the extention is xml
                    importFromXML(file); // launches the function for xml and passes the file there
                } else if (fileName.endsWith(".xlsx")) { // checks if the extention is xlsx
                    importFromExcel(file); // launches the excel import function and passes the file there
                } else {
                    // If the file has another extention there this message appears to the user
                    JOptionPane.showMessageDialog(parentFrame, "Unsupported file format.");
                }
             // Throw an error if there is any problem with importing this file content
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "Import failed: " + e.getMessage());
            }
        }
    }

    // ----------------------- CSV Import -----------------------

    /**
     * Imports aircraft entries from a CSV file with header.
     *
     * @param file CSV file containing aircraft data
     * @throws Exception if file or database operations fail
     */
    private static void importFromCSV(File file) throws Exception {
        // open the file for reading
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Connection conn = Database.getConnection()) { // establish a database connection

            // Load the current tail number because there cannot be an aircraft with identical tail number
            Set<String> existingTailNumbers = fetchExistingTailNumbers(conn);
            String line; // This variable will store each line from the file
            int lineNum = 0; // Counter and tracke the line number

            // Read each line from the csv file
            while ((line = reader.readLine()) != null) {
                lineNum++; // increment the line number
                if (lineNum == 1) continue; // Skip header row
                String[] parts = line.split(","); // Split the line into parts by comma
                if (parts.length != 2) continue; // Those rows without this design skip them

                // Extract and trim the aircraft model
                String model = parts[0].trim();
                //Extract and trim the tail number
                String tailNumber = parts[1].trim();

                // If the tailnumber is not in the DB then insert the aircraft
                if (!existingTailNumbers.contains(tailNumber)) {
                    insertAircraft(conn, model, tailNumber); // Insert aircraft into the db
                    existingTailNumbers.add(tailNumber); // Track the inserted tail numbers to avoid re-adding
                }
            }

            // Notify the user that the import is completed
            JOptionPane.showMessageDialog(null, "CSV import complete.");
        }
    }

    /**
     * Imports aircraft entries from a XML file with header.
     *
     * @param file XML file containing aircraft data
     * @throws Exception if file or database operations fail
     */
    private static void importFromXML(File file) throws Exception {
        // Create a factory for the xml parser
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        // build the document parser from the factory
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        // Parse the document  and create the DOM representation
        Document doc = dBuilder.parse(file);
        // Normalize the structure of the XML document
        doc.getDocumentElement().normalize();

        // Find all aircraft elements in the Document
        NodeList aircraftNodes = doc.getElementsByTagName("aircraft");
        // Open the database connection using the try with resources for safe closure
        try (Connection conn = Database.getConnection()) {
            // Load the tail numbers from the DB to check for dublicates inside the database
            Set<String> existingTailNumbers = fetchExistingTailNumbers(conn);

            // Loop over each element in the XML file
            for (int i = 0; i < aircraftNodes.getLength(); i++) {
                // Cast a node to an Element to access child tags
                Element element = (Element) aircraftNodes.item(i);
                // Extract model tag content and trim whitespace
                String model = element.getElementsByTagName("model").item(0).getTextContent().trim();
                // Extract tailNumber tag content
                String tailNumber = element.getElementsByTagName("tailNumber").item(0).getTextContent().trim();

                // Insert in the database if only the tailnumber is not already present in the database
                if (!existingTailNumbers.contains(tailNumber)) {
                    // Insert aircraft in the database
                    insertAircraft(conn, model, tailNumber);
                    // Track duplicates to not re-insert
                    existingTailNumbers.add(tailNumber);
                }
            }
        }

        // Show confirmation dialog to user
        JOptionPane.showMessageDialog(null, "XML import complete.");
    }

    /**
     * Imports aircraft entries from a Excel file with header.
     *
     * @param file Excel file containing aircraft data
     * @throws Exception if file or database operations fail
     */
    private static void importFromExcel(File file) throws Exception {
        // Open the input stream, initialize Excel workbook and Database connection
        try (FileInputStream fis = new FileInputStream(file);
             // Apache POI workbook object
             Workbook workbook = new XSSFWorkbook(fis);
             // Connect to the database
             Connection conn = Database.getConnection()) {

            // Load the first worksheet
            Sheet sheet = workbook.getSheetAt(0);

            // Load the tailnumbers from the database to avoid duplicates
            Set<String> existingTailNumbers = fetchExistingTailNumbers(conn);
            // Used to skip the first row
            boolean headerSkipped = false;

            // Iterate over each row in the Excel sheet
            for (Row row : sheet) {
                // Skip the header row
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                // Read the cell for model and tail number
                Cell modelCell = row.getCell(0);
                Cell tailNumberCell = row.getCell(1);

                // If either value is missing skip the row
                if (modelCell == null || tailNumberCell == null) continue;

                // Extract and clean the values
                String model = modelCell.getStringCellValue().trim();
                String tailNumber = tailNumberCell.getStringCellValue().trim();

                // Only insert if tail number is new
                if (!existingTailNumbers.contains(tailNumber)) {
                    insertAircraft(conn, model, tailNumber);
                    existingTailNumbers.add(tailNumber);
                }
            }
        }

        // Notify the user that import is completed
        JOptionPane.showMessageDialog(null, "Excel import complete.");
    }


    private static Set<String> fetchExistingTailNumbers(Connection conn) throws Exception {
        // Set to store unique tail numbers
        Set<String> tailNumbers = new HashSet<>();
        // Excecute SQL SELECT query to retrieve all existing tail numbers
        ResultSet rs = conn.createStatement().executeQuery("SELECT tail_number FROM aircraft");
        // Populate set from result set
        while (rs.next()) {
            tailNumbers.add(rs.getString("tail_number"));
        }
        // return the set of tail numbers
        return tailNumbers;
    }

    private static void insertAircraft(Connection conn, String model, String tailNumber) throws Exception {
        // Prepare an SQL statement to add aircraft data
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO aircraft (model, tail_number) VALUES (?, ?)");
        // Set model and tail number parameters
        stmt.setString(1, model);
        stmt.setString(2, tailNumber);
        // Excecute the insert operation
        stmt.executeUpdate();
    }
}
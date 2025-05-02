package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.db.Database;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.w3c.dom.*;

public class MaintenanceTaskImportHandler {

    public static void importTasksFromFile(JFrame parentFrame) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(parentFrame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName().toLowerCase();

            try {
                if (fileName.endsWith(".csv")) {
                    importFromCSV(file);
                } else if (fileName.endsWith(".xml")) {
                    importFromXML(file);
                } else if (fileName.endsWith(".xlsx")) {
                    importFromExcel(file);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Unsupported file format.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "Task import failed: " + e.getMessage());
            }
        }
    }

    private static void importFromCSV(File file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Connection conn = Database.getConnection()) {

            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                if (++lineNum == 1) continue; // Skip header
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                int aircraftId = Integer.parseInt(parts[0].trim());
                String description = parts[1].trim();
                String dueDateStr = parts[2].trim();
                String status = parts[3].trim();

                insertTask(conn, aircraftId, description, dueDateStr, status);
            }
        }

        JOptionPane.showMessageDialog(null, "CSV task import complete.");
    }

    private static void importFromExcel(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis);
             Connection conn = Database.getConnection()) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean headerSkipped = false;

            for (Row row : sheet) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                int aircraftId = (int) row.getCell(0).getNumericCellValue();
                String description = row.getCell(1).getStringCellValue().trim();
                String dueDateStr = row.getCell(2).getStringCellValue().trim();
                String status = row.getCell(3).getStringCellValue().trim();

                insertTask(conn, aircraftId, description, dueDateStr, status);
            }
        }

        JOptionPane.showMessageDialog(null, "Excel task import complete.");
    }

    private static void importFromXML(File file) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(file);
        NodeList taskNodes = doc.getElementsByTagName("task");

        try (Connection conn = Database.getConnection()) {
            for (int i = 0; i < taskNodes.getLength(); i++) {
                Element task = (Element) taskNodes.item(i);

                int aircraftId = Integer.parseInt(task.getElementsByTagName("aircraftId").item(0).getTextContent().trim());
                String description = task.getElementsByTagName("description").item(0).getTextContent().trim();
                String dueDateStr = task.getElementsByTagName("dueDate").item(0).getTextContent().trim();
                String status = task.getElementsByTagName("status").item(0).getTextContent().trim();

                insertTask(conn, aircraftId, description, dueDateStr, status);
            }
        }

        JOptionPane.showMessageDialog(null, "XML task import complete.");
    }

    private static void insertTask(Connection conn, int aircraftId, String description, String dueDateStr, String status) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO maintenance_task (aircraft_id, task_description, due_date, status) VALUES (?, ?, ?, ?)"
        );
        stmt.setInt(1, aircraftId);
        stmt.setString(2, description);
        stmt.setDate(3, java.sql.Date.valueOf(dueDateStr));
        stmt.setString(4, status);
        stmt.executeUpdate();
    }
}
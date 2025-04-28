package org.example.presenter;

import org.example.db.Database;
import org.example.model.Aircraft;
import org.example.view.AircraftView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AircraftPresenter {
    private AircraftView view;

    public AircraftPresenter(AircraftView view) {
        this.view = view;
    }

    public void loadAircraft() {
        List<Aircraft> aircraftList = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM aircraft")) {

            while (rs.next()) {
                Aircraft aircraft = new Aircraft(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("tail_number")
                );
                aircraftList.add(aircraft);
            }

            view.showAircraftList(aircraftList);

        } catch (SQLException e) {
            view.showMessage("Error loading aircraft: " + e.getMessage());
        }
    }

    public void setView(AircraftView view) {
        this.view = view;
    }

    public void addAircraft(String model, String tailNumber) {
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO aircraft (model, tail_number) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, model);
            pstmt.setString(2, tailNumber);
            pstmt.executeUpdate();

            view.showMessage("Aircraft added successfully.");
            loadAircraft();
        } catch (SQLException e) {
            view.showMessage("Error adding aircraft: " + e.getMessage());
        }
    }
}
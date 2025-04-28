package org.example.view;

import org.example.model.Aircraft;

import java.util.List;

public interface AircraftView {
    void showAircraftList(List<Aircraft> aircraftList);
    void showMessage(String message);
}

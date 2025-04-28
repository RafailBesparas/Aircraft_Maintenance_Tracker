package org.example.app;

import org.example.presenter.AircraftPresenter;
import org.example.view.AircraftViewImplementation;

public class Main {
    public static void main(String[] args) {
        AircraftPresenter presenter = new AircraftPresenter(null);
        AircraftViewImplementation view = new AircraftViewImplementation(presenter);
        presenter.setView(view);
        presenter.loadAircraft();
    }
}

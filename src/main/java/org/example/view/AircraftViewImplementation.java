package org.example.view;

import org.example.model.Aircraft;
import org.example.presenter.AircraftPresenter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AircraftViewImplementation extends JFrame implements AircraftView {

    private AircraftPresenter presenter;

    private DefaultListModel<String> listModel;

    public AircraftViewImplementation(AircraftPresenter presenter) {
        this.presenter = presenter;
        setupUI();
    }

    private void setupUI() {
        setTitle("Aircraft Maintenance Tracker Power By Rafael Besparas");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        listModel = new DefaultListModel<>();
        JList<String> aircraftList = new JList<>(listModel);

        JButton addButton = new JButton("Add Aircraft");
        addButton.addActionListener((ActionEvent e) -> {
            String model = JOptionPane.showInputDialog("Enter Model:");
            String tailNumber = JOptionPane.showInputDialog("Enter Tail Number:");
            if (model != null && tailNumber != null) {
                presenter.addAircraft(model, tailNumber);
            }
        });

        getContentPane().add(new JScrollPane(aircraftList), BorderLayout.CENTER);
        getContentPane().add(addButton, BorderLayout.SOUTH);

        //presenter.loadAircraft();
        setVisible(true);
    }

    @Override
    public void showAircraftList(List<Aircraft> aircraftList) {
        listModel.clear();
        for (Aircraft aircraft : aircraftList) {
            listModel.addElement(aircraft.getModel() + " (" + aircraft.getTailNumber() + ")");
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}

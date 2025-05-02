package org.example.auth;

// GUI and some event needed imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// ========================= DO-178C MODULE HEADER =========================
// Module Name    : LoginWindow.java
// Application    : Aircraft Maintenance Tracker
// Description    : Provides GUI-based password authentication for the application.
// Safety Level   : DAL D or C (depending on its role in access control)
// Developed By   : Rafail
// Last Modified  : 2025-05-02
// Notes          : Triggers secure or decoy mode based on password entry
// ========================================================================

/**
 * LoginWindow: A Swing-based GUI for user authentication.
 * Launches either the real operational environment or a honeytrap decoy based on the entered password.
 */
public class LoginWindow extends JFrame {

    /**
     * Constructor: Initializes and displays the login interface.
     */
    public LoginWindow(){
        // Provide the title of the Window
        setTitle("Aircraft Maintenance Tracker - Login Powered By Rafael");
        //Set the window size width height
        setSize(400, 200);
        //Ensure the application exists when the window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Center the window in the screen
        setLocationRelativeTo(null);

        //Creating the label that the user will See asking for password
        JLabel label = new JLabel("Enter Access Password:");
        //Password field for the user to input the password
        JPasswordField passwordField = new JPasswordField();
        //Creating the button for submiting the user input
        JButton loginButton = new JButton("Login");

        //Create a panel with a grid layout 3 rows, 1 column and spacing of 10
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        //Add padding around the panel contents
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        //Add User Interface components to the panel
        // Like label, passwordfield, loginButton
        panel.add(label);
        panel.add(passwordField);
        panel.add(loginButton);

        // Add the panel to the window
        add(panel);

        // Here define the action involved when the login button will be pressed
        loginButton.addActionListener((ActionEvent e) -> {
            //Get from the user the password input
            String password = new String(passwordField.getPassword());
            //Close the login window
            dispose();
            //Check which password has been provided
            if ("coleopter1!".equals(password)) { // with this passowrd activate the honey trap mode
                org.example.app.Main.launchHoneyTrapMode(); // launch the honeytrap mode
            } else if ("admin123".equals(password)) { // with this password activate the normal mode
                org.example.app.Main.launchRealMode(); // launch the real application
            } else {// Else deny the access thus the password is incorrect
                JOptionPane.showMessageDialog(this, "Invalid password. Access denied.");
                System.exit(1);// Ensure the application will shut down
            }
        });
        // make the login window visible
        setVisible(true);

    }

}

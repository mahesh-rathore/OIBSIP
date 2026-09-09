package gui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("Online Reservation System - Login");

        setSize(450, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Components
        JLabel usernameLabel = new JLabel("Username:");

        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");

        passwordField = new JPasswordField();

        loginButton = new JButton("Login");

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(new JLabel());
        panel.add(loginButton);

        // Add panel to frame
        add(panel);

        // Login button action
        loginButton.addActionListener(e -> loginUser());

        setVisible(true);
    }

    private void loginUser() {

        String username = usernameField.getText();

        String password = new String(passwordField.getPassword());

        // Check empty fields
        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password!"
            );

            return;
        }

        // Check credentials from database
        UserDAO userDAO = new UserDAO();

        boolean isValid =
                userDAO.validateUser(username, password);

        if (isValid) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!"
            );

            new MainMenuFrame();

            dispose();

        }else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!"
            );
        }
    }
}
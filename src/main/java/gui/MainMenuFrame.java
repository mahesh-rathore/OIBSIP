package gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    private JButton bookTicketButton;
    private JButton cancelTicketButton;
    private JButton exitButton;

    public MainMenuFrame() {

        setTitle("Online Reservation System");

        setSize(400, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(4, 1, 15, 15)
        );

        // Title
        JLabel titleLabel =
                new JLabel(
                        "ONLINE RESERVATION SYSTEM",
                        SwingConstants.CENTER
                );

        // Buttons
        bookTicketButton =
                new JButton("Book Ticket");

        cancelTicketButton =
                new JButton("Cancel Ticket");

        exitButton =
                new JButton("Exit");

        // Add components
        panel.add(titleLabel);

        panel.add(bookTicketButton);

        panel.add(cancelTicketButton);

        panel.add(exitButton);

        add(panel);

        // Button actions

        bookTicketButton.addActionListener(
                e -> new ReservationFrame()
        );

        cancelTicketButton.addActionListener(
                e -> new CancellationFrame()
        );

        exitButton.addActionListener(
                e -> System.exit(0)
        );

        setVisible(true);
    }
}

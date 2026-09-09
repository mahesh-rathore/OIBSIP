package gui;

import dao.ReservationDAO;

import javax.swing.*;
import java.awt.*;

public class CancellationFrame extends JFrame {

    private JTextField pnrField;
    private JTextArea detailsArea;
    private JButton searchButton;
    private JButton cancelButton;
    private JButton backButton;

    public CancellationFrame() {

        setTitle("Cancel Ticket");

        setSize(500, 400);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        // Top Panel for PNR
        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Enter PNR:"));

        pnrField = new JTextField(15);
        topPanel.add(pnrField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        // Reservation Details Area
        detailsArea = new JTextArea();

        detailsArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(detailsArea);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();

        cancelButton = new JButton("Cancel Ticket");
        backButton = new JButton("Back to Main Menu");

        bottomPanel.add(cancelButton);
        bottomPanel.add(backButton);

        // Add panels
        panel.add(topPanel, BorderLayout.NORTH);

        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Button actions
        searchButton.addActionListener(e -> searchReservation());

        cancelButton.addActionListener(e -> cancelTicket());
        backButton.addActionListener(e -> {

            new MainMenuFrame();

            dispose();
        });

        setVisible(true);
    }


    private void searchReservation() {

        String pnrText = pnrField.getText();

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR!"
            );

            return;
        }

        try {

            long pnr = Long.parseLong(pnrText);

            ReservationDAO reservationDAO =
                    new ReservationDAO();

            String details =
                    reservationDAO.getReservationDetails(pnr);

            if (details != null) {

                detailsArea.setText(details);

            } else {

                detailsArea.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation not found!"
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid PNR number!"
            );
        }
    }


    private void cancelTicket() {

        String pnrText = pnrField.getText();

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR first!"
            );

            return;
        }

        try {

            long pnr = Long.parseLong(pnrText);

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel this ticket?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                ReservationDAO reservationDAO =
                        new ReservationDAO();

                boolean success =
                        reservationDAO.cancelReservation(pnr);

                if (success) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Ticket Cancelled Successfully!"
                    );

                    pnrField.setText("");
                    detailsArea.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Cancellation Failed! Reservation not found."
                    );
                }
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid PNR number!"
            );
        }
    }
}

package gui;

import dao.ReservationDAO;
import dao.TrainDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservationFrame extends JFrame {

    private JTextField passengerNameField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JComboBox<String> classTypeComboBox;
    private JTextField journeyDateField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JButton bookButton;
    private JButton backButton;


    public ReservationFrame() {

        setTitle("Online Reservation System");

        setSize(500, 500);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10));

        // Passenger Name
        panel.add(new JLabel("Passenger Name:"));

        passengerNameField = new JTextField();
        panel.add(passengerNameField);

        // Train Number
        panel.add(new JLabel("Train Number:"));

        trainNumberField = new JTextField();

        trainNumberField.addActionListener(e -> loadTrainName());

        panel.add(trainNumberField);

        // Train Name
        panel.add(new JLabel("Train Name:"));

        trainNameField = new JTextField();
        trainNameField.setEditable(false);

        panel.add(trainNameField);

        // Class Type
        panel.add(new JLabel("Class Type:"));

        String[] classes = {
                "First Class",
                "Second Class",
                "Sleeper",
                "AC"
        };

        classTypeComboBox = new JComboBox<>(classes);
        panel.add(classTypeComboBox);

        // Journey Date
        panel.add(new JLabel("Journey Date (YYYY-MM-DD):"));

        journeyDateField = new JTextField();
        panel.add(journeyDateField);

        // Source
        panel.add(new JLabel("Source Station:"));

        sourceField = new JTextField();
        panel.add(sourceField);

        // Destination
        panel.add(new JLabel("Destination Station:"));

        destinationField = new JTextField();
        panel.add(destinationField);

        // Book Button
        panel.add(new JLabel());

        bookButton = new JButton("Book Ticket");
        panel.add(bookButton);
        // Back Button
        panel.add(new JLabel());

        backButton = new JButton("Back to Main Menu");
        panel.add(backButton);



        // Book button action
        bookButton.addActionListener(e -> bookTicket());
        backButton.addActionListener(e -> {

            new MainMenuFrame();

            dispose();
        });


        add(panel);

        setVisible(true);
    }


    private void loadTrainName() {

        try {

            int trainNumber =
                    Integer.parseInt(trainNumberField.getText());

            TrainDAO trainDAO = new TrainDAO();

            String trainName =
                    trainDAO.getTrainName(trainNumber);

            if (trainName != null) {

                trainNameField.setText(trainName);

            } else {

                trainNameField.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "Train not found!"
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid train number!"
            );
        }
    }


    private void bookTicket() {

        // Get values from fields
        String passengerName = passengerNameField.getText().trim();
        String trainNumberText = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();

        String classType =
                (String) classTypeComboBox.getSelectedItem();

        String journeyDate = journeyDateField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();


        // Check empty fields
        if (passengerName.isEmpty()
                || trainNumberText.isEmpty()
                || trainName.isEmpty()
                || journeyDate.isEmpty()
                || source.isEmpty()
                || destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all the fields!"
            );

            return;
        }


        // Validate journey date
        try {

            LocalDate.parse(journeyDate);

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter date in YYYY-MM-DD format!"
            );

            return;
        }


        try {

            int trainNumber =
                    Integer.parseInt(trainNumberText);

            // Generate unique PNR
            long pnr = System.currentTimeMillis();


            // Create DAO object
            ReservationDAO reservationDAO =
                    new ReservationDAO();


            // Save reservation
            boolean success =
                    reservationDAO.saveReservation(
                            pnr,
                            passengerName,
                            trainNumber,
                            trainName,
                            classType,
                            journeyDate,
                            source,
                            destination
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket Booked Successfully!\n\n" +
                                "PNR: " + pnr +
                                "\nPassenger Name: " + passengerName +
                                "\nTrain Number: " + trainNumber +
                                "\nTrain Name: " + trainName +
                                "\nClass: " + classType +
                                "\nJourney Date: " + journeyDate +
                                "\nSource: " + source +
                                "\nDestination: " + destination
                );

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking Failed!"
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid train number!"
            );
        }
    }




    private void clearFields() {

        passengerNameField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        journeyDateField.setText("");
        sourceField.setText("");
        destinationField.setText("");
    }
}
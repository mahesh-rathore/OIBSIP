package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDAO {

    public boolean saveReservation(
            long pnr,
            String passengerName,
            int trainNumber,
            String trainName,
            String classType,
            String journeyDate,
            String source,
            String destination
    ) {

        String query = "INSERT INTO reservations " +
                "(pnr, passenger_name, train_number, train_name, " +
                "class_type, journey_date, source_station, destination_station) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setLong(1, pnr);
            statement.setString(2, passengerName);
            statement.setInt(3, trainNumber);
            statement.setString(4, trainName);
            statement.setString(5, classType);
            statement.setString(6, journeyDate);
            statement.setString(7, source);
            statement.setString(8, destination);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    public String getReservationDetails(long pnr) {

        String query = "SELECT * FROM reservations WHERE pnr = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setLong(1, pnr);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return "PNR: " + resultSet.getLong("pnr") +
                        "\nPassenger Name: " +
                        resultSet.getString("passenger_name") +
                        "\nTrain Number: " +
                        resultSet.getInt("train_number") +
                        "\nTrain Name: " +
                        resultSet.getString("train_name") +
                        "\nClass: " +
                        resultSet.getString("class_type") +
                        "\nJourney Date: " +
                        resultSet.getDate("journey_date") +
                        "\nSource: " +
                        resultSet.getString("source_station") +
                        "\nDestination: " +
                        resultSet.getString("destination_station");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    public boolean cancelReservation(long pnr) {

        String query = "DELETE FROM reservations WHERE pnr = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setLong(1, pnr);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}
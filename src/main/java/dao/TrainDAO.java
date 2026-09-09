package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainDAO {

    public String getTrainName(int trainNumber) {

        String query =
                "SELECT train_name FROM trains WHERE train_number = ?";

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, trainNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getString("train_name");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }
}
package com.example.hotelproject;

import java.sql.*;

public class RoomCheckIn_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomCheckIns";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static String showCheckInInformationWithID(int checkInID) {
        String checkInTime = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT CheckInTime FROM RoomCheckIns WHERE CheckInID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, checkInID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                checkInTime = rs.getString("CheckInTime");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkInTime;
    }

    //Thai Bao them code
    public static int geCustomerIDWithCheckInID(int checkInID) {
        int customerID = 0;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT CustomerID FROM RoomCheckIns WHERE CheckInID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, checkInID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                customerID = rs.getInt("CustomerID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerID;
    }

    public static int countCheckIns() {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM RoomCheckIns WHERE Deleted = 0";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static int sumNOP() {
        int sum = 0;
        try {
            String query = "SELECT COUNT(NOP) FROM RoomCheckIns WHERE Deleted = 0";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public static RoomCheckIn getCheckInByRoomID(int roomID) throws SQLException {
        String query = "SELECT * FROM RoomCheckIns WHERE RoomID = ?";
        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int checkInID = resultSet.getInt("CheckInID");
                Time checkInTime = resultSet.getTime("CheckInTime");
                int userID = resultSet.getInt("UserID");

                return new RoomCheckIn(checkInID, checkInTime, userID);
            } else {
                return null;
            }
        }
    }
    public static void updateRoomID(int checkInID, int newRoomID) throws SQLException {
        try {
            String sql = "UPDATE RoomCheckIns SET RoomID = ? WHERE CheckInID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newRoomID);
            preparedStatement.setInt(2, checkInID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

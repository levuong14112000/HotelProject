package com.example.hotelproject;

import java.sql.*;

public class RoomService_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomServices";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void saveRoomService(RoomService roomService) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Conect.getInstance();

            String query = "INSERT INTO RoomServices (RoomID, CheckInID, ServiceID, Quantity, ServicePrice, UserID) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, roomService.getRoomID());
            statement.setInt(2, roomService.getCheckInID());
            statement.setInt(3, roomService.getServiceID());
            statement.setInt(4, roomService.getQuantity());
            statement.setDouble(5, roomService.getServicePrice());
            statement.setInt(6,roomService.getUserID());
//            statement.setInt(5, roomService.getTimes());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteRoomServicesByRoomID(int roomID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Conect.getInstance();
            String query = "DELETE FROM RoomServices WHERE RoomID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isRoomServiceExist(int roomID, int serviceID, String tenMatHang) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Conect.getInstance();

            String query = "SELECT COUNT(*) AS Count FROM RoomServices WHERE RoomID = ? AND ServiceID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            statement.setInt(2, serviceID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("Count");
                return count > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static ResultSet getRoomServicesByRoomIDAndCheckInID(int roomID, int checkInID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT * FROM RoomServices WHERE RoomID = ? AND CheckInID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            statement.setInt(2, checkInID);
            resultSet = statement.executeQuery();

            return resultSet;
        } catch (SQLException e) {
            System.out.println("Failed to fetch room services from the database.");
            e.printStackTrace();
            throw e;
        }
    }
}

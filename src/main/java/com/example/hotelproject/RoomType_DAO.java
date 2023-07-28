package com.example.hotelproject;

import java.sql.*;

public class RoomType_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomTypes WHERE Deleted = 0";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static int calculateTotalRoomType() {
        int totalRoom = 0;
        try {
            String query = "SELECT COUNT(RoomTypeID) FROM roomtypes";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalRoom = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRoom;
    }

    public static double getBasePriceByRoomID(int roomID) {
        double basePrice = 0;

        try {
            String sqlRoom = "SELECT RoomTypeID FROM Rooms WHERE RoomID = ?";
            PreparedStatement stmtRoom = connection.prepareStatement(sqlRoom);
            stmtRoom.setInt(1, roomID);

            ResultSet rsRoom = stmtRoom.executeQuery();

            if (rsRoom.next()) {
                int roomTypeID = rsRoom.getInt("RoomTypeID");

                String sqlRoomType = "SELECT BasePrice FROM RoomTypes WHERE RoomTypeID = ?";
                PreparedStatement stmtRoomType = connection.prepareStatement(sqlRoomType);
                stmtRoomType.setInt(1, roomTypeID);

                ResultSet rsRoomType = stmtRoomType.executeQuery();

                if (rsRoomType.next()) {
                    basePrice = rsRoomType.getDouble("BasePrice");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return basePrice;
    }
    public static void insertRoomtype(String RoomTypeName, Double roomPrice) {
        try {
            String query = "INSERT INTO roomtypes (RoomTypeName, BasePrice) " +
                    "VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, RoomTypeName);
            stmt.setDouble(2, roomPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateRoomtype(int RoomTypeID ,String RoomTypeName , Double BasePrice) {
        try {
            String query = "UPDATE RoomTypes " +
                    "SET RoomTypeName = ? , BasePrice = ? " +
                    "WHERE RoomTypeID =  ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, RoomTypeName);
            stmt.setDouble(2, BasePrice);
            stmt.setInt(3, RoomTypeID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteRoomtype(int  RoomTypeID) {
        try {
            String query = "DELETE FROM RoomTypes WHERE RoomTypeID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, RoomTypeID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

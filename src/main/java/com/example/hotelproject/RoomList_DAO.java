package com.example.hotelproject;

import com.example.hotelproject.Conect;

import java.sql.*;

public class RoomList_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet Read(){
        ResultSet rs ;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Rooms WHERE Deleted = 0";
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet showRooms() {
        ResultSet rs;
        try {
            String query = "SELECT rooms.RoomID, rooms.RoomNumber, rooms.Status,rooms.RoomTypeID,roomtypes.BasePrice, roomtypes.RoomTypeName " +
                    "FROM rooms " +
                    "JOIN roomtypes ON rooms.RoomTypeID = roomtypes.RoomTypeID WHERE rooms.Deleted = 0 ORDER BY RoomID ASC " ;
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet showRoomTKs() {
            ResultSet  rs;
        try {
            String query = "SELECT rooms.RoomID, rooms.RoomNumber, rooms.Status,rooms.RoomTypeID,roomtypes.BasePrice, roomtypes.RoomTypeName " +
                    "FROM rooms " +
                    "JOIN roomtypes ON rooms.RoomTypeID = roomtypes.RoomTypeID WHERE STATUS = 0 AND rooms.Deleted = 0 ORDER BY RoomID ASC ";
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet ReadRoomType(){
        ResultSet rs ;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Roomtypes";
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return rs;
    }
    public static int calculateTotalRoom() {
        int totalRoom = 0;
        try {
            String query = "SELECT COUNT(RoomID) FROM rooms WHERE Deleted = 0";
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
    public static void insertRoom(String roomNumber, String roomTypeName) {
        try {
            String query = "INSERT INTO rooms (RoomNumber, RoomTypeID) " +
                    "VALUES (?, (SELECT RoomTypeID FROM roomtypes WHERE RoomTypeName = ? AND Deleted = 0))";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomNumber);
            stmt.setString(2, roomTypeName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateRoom(int roomid,String roomNumber, String roomTypeName) {
        try {
            String query = "UPDATE rooms " +
                    "SET RoomTypeID = (SELECT RoomTypeID FROM roomtypes WHERE RoomTypeName = ?), RoomNumber = ? " +
                    "WHERE ROOMID = ? AND Deleted = 0";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomTypeName);
            stmt.setString(2, roomNumber);
            stmt.setInt(3, roomid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteRoom(int  roomID) {
        try {
            String query = "UPDATE rooms SET DELETED = 1 WHERE RoomID = ? AND Deleted = 0";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, roomID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

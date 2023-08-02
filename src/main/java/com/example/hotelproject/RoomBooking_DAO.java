package com.example.hotelproject;

import com.example.hotelproject.Conect;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoomBooking_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet ReadCus(){
        ResultSet rs ;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT  * FROM customer WHERE Deleted = 0 ";
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
            String query = "SELECT roombookings.BookingID,roombookings.CustomerID, customers.FullName," +
                    "customers.IDNumber,customers.PhoneNumber," +
                    "roombookings.RoomID,roombookings.CheckInDate ,"+
                    "roombookings.CheckOutDate,roombookings.UserID ,customers.Deleted,roombookings.BookingTime" +
                    " FROM customers " +
                    "JOIN roombookings ON roombookings.CustomerID = customers.CustomerID WHERE roombookings.Deleted = 0";
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet Status(String RoomNumber) {
        ResultSet rs;
        try {
            String query = "SELECT rooms.Status , rooms.RoomID" +
                    " FROM rooms WHERE RoomID = (SELECT RoomID FROM rooms WHERE RoomNumber = ? AND Deleted = 0) AND Deleted = 0";
            PreparedStatement stmt =  connection.prepareStatement(query);
            stmt.setString(1,RoomNumber);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet showCus() {
        ResultSet rs;
        try {
            String query = "SELECT * FROM customers WHERE Deleted = 0";
            PreparedStatement stmt =  connection.prepareStatement(query);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet searchData(String option, String searchText) {
        ResultSet rs;
        try {
            String query = "SELECT * FROM roombookings " +
                    "JOIN customers ON roombookings.CustomerID = customers.CustomerID " +
                    "WHERE " + option + " = ? AND roombookings.Deleted = 0";
            PreparedStatement stmt =  connection.prepareStatement(query);
            stmt.setString(1, searchText);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void insertBooking(LocalDate BookingTime, LocalDate CheckinDate, LocalDate CheckOutDate, String RoomID, int UserID, String FullName, String PhoneNumber, String IDNumber) {
        try {
            String customerQuery = "INSERT INTO customers (FullName, PhoneNumber, IDNumber) VALUES (?, ?, ?)";
            PreparedStatement customerStmt = connection.prepareStatement(customerQuery, Statement.RETURN_GENERATED_KEYS);
            customerStmt.setString(1, FullName);
            customerStmt.setString(2, PhoneNumber);
            customerStmt.setString(3, IDNumber);
            customerStmt.executeUpdate();
            ResultSet generatedKeys = customerStmt.getGeneratedKeys();
            int generatedId;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get generated key for customer");
            }
            String bookingQuery = "INSERT INTO roombookings (CustomerID,RoomID, BookingTime, CheckInDate, CheckOutDate, UserID) VALUES (?,(SELECT RoomID FROM rooms WHERE RoomNumber = ?), ?, ?, ?, ?)";
            PreparedStatement bookingStmt = connection.prepareStatement(bookingQuery);
            bookingStmt.setInt(1, generatedId);
            bookingStmt.setString(2,RoomID);
            bookingStmt.setDate(3, java.sql.Date.valueOf(BookingTime));
            bookingStmt.setDate(4, java.sql.Date.valueOf(CheckinDate));
            bookingStmt.setDate(5, java.sql.Date.valueOf(CheckOutDate));
            bookingStmt.setInt(6, UserID);
            bookingStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBooking( LocalDate BookingTime,LocalDate checkinRoom, LocalDate checkoutRoom , String FullName  , String PhoneNumber , String IDNumber,int CustomerID,String RoomID,int BookID) {
        try {
            String query = "UPDATE roombookings SET BookingTime = '" + BookingTime + "', CheckInDate = '" + checkinRoom + "', CheckOutDate = '" + checkoutRoom + "', RoomID = (SELECT RoomID FROM rooms WHERE RoomNumber = ?) WHERE BookingID = " + BookID;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,RoomID);
            stmt.executeUpdate();
            String query1 = "UPDATE customers SET FullName = '" + FullName + "', IDNumber = '" + IDNumber + "', PhoneNumber = '" + PhoneNumber +  "' WHERE CustomerID = " + CustomerID;
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateStatus(int RoomID) {
        try {
            String query = "UPDATE rooms SET Status = -1 WHERE RoomID  =" +RoomID;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteBooking(int BookingID) {
        try {
            String query = "UPDATE roombookings SET Deleted = 1 WHERE BookingID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, BookingID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int countBookings() {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM RoomBookings WHERE Deleted = 0";
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

    public static int checkBookingID(int bookingID) throws SQLException {
        ResultSet rs;
        int id = 0;
        try {
            String query = "SELECT BookingID FROM RoomBookings WHERE Deleted = 0 AND BookingID = ?";
            PreparedStatement stmt =  connection.prepareStatement(query);
            stmt.setInt(1, bookingID);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (rs.next()) {
            return rs.getInt("BookingID");
        } else {
            return -1;
        }
    }
}

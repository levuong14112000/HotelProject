package com.example.hotelproject;

import java.sql.*;

public class RoomPayment_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomPayments WHERE Status = 0";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet showBillDeleted() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomPayments WHERE Status = 1";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static void createRoomPayment(int customerID, int roomID, int checkInID, int checkOutID, double roomCharge,
                                         double extraCharge, double discount, int userID) {
        String sql = "INSERT INTO RoomPayments (CustomerID, RoomID, CheckInID, CheckOutID, RoomCharge, ExtraCharge, Discount, UserID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            statement.setInt(2, roomID);
            statement.setInt(3, checkInID);
            statement.setInt(4, checkOutID);
            statement.setDouble(5, roomCharge);
            statement.setDouble(6, extraCharge);
            statement.setDouble(7, discount);
            statement.setInt(8, userID);

            statement.executeUpdate();
            System.out.println("Lưu bill thành công !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet showRoomPaymentInformationWithID(int paymentID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = Conect.getInstance();
            String sql = "SELECT * FROM RoomPayments WHERE PaymentID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, paymentID);

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static void updatePaymentStatus(int paymentID, int status) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Conect.getInstance();
            String query = "UPDATE RoomPayments SET Status = ? WHERE PaymentID = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, status);
            statement.setInt(2, paymentID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int countBills() {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM RoomPayments WHERE Status = 0";
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
    public static double sumRoomCharge() {
        double sum = 0;
        try {
            String sql = "SELECT SUM(RoomCharge) AS TotalRoomCharge FROM RoomPayments WHERE Status = 0";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getDouble("TotalRoomCharge");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public static double sumExtraCharge() {
        double sum = 0;
        try {
            String sql = "SELECT SUM(ExtraCharge) AS Total FROM RoomPayments WHERE Status = 0";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getDouble("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public static double sumDiscount() {
        double sum = 0;
        try {
            String sql = "SELECT SUM(Discount) AS Total FROM RoomPayments WHERE Status = 0";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getDouble("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

}

package com.example.hotelproject;

import java.sql.*;

public class Service_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Services WHERE Deleted = 0";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static double getDonGiaByTenMatHang(String tenMatHang) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double donGia = 0;

        try {
            connection = Conect.getInstance();
            String query = "SELECT ServicePrice FROM Services WHERE ServiceName = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, tenMatHang);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                donGia = resultSet.getDouble("ServicePrice");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return donGia;
    }

    public static double getIDByTenMatHang(String tenMatHang) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double id = 0;

        try {
            connection = Conect.getInstance();
            String query = "SELECT ServiceID FROM Services WHERE ServiceName = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, tenMatHang);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("ServiceID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static String getTenMatHangByID(String serviceID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String tenMatHang = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT ServiceName FROM Services WHERE ServiceID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, serviceID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tenMatHang = resultSet.getString("ServiceName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tenMatHang;
    }
    public static void createService(String serviceName, double servicePrice) {
        String sql = "INSERT INTO Services (ServiceName, ServicePrice) VALUES ( ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, serviceName);
            statement.setDouble(2, servicePrice);
            statement.executeUpdate();
            System.out.println("Thêm thành công !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean deletedService(int serviceID) {
        try {
            String query = "UPDATE Services SET Deleted = 1 WHERE ServiceID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, serviceID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean updateService(ServiceController.ServiceItem service) {
        try {
            String query = "UPDATE Services SET ServiceName = ?, ServicePrice = ? WHERE ServiceID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, service.getServiceName());
            stmt.setDouble(2, service.getServicePrice());
            stmt.setInt(3, service.getServiceID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

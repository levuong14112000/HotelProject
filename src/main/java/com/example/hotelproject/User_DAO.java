package com.example.hotelproject;

import java.sql.*;

public class User_DAO {
    private static Connection connection = Conect.getInstance();
    public static boolean checkLogin(String username, String password) {
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static ResultSet getIDbyUsernamePassword(String username, String password){
        ResultSet resultSet;
        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public static ResultSet getNameByUserID(int userID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Conect.getInstance();
            String query = "SELECT * FROM Users WHERE UserID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userID);
            resultSet = statement.executeQuery();

        } catch (SQLException e) {
            throw new SQLException("Failed to fetch room check-in data from the database.", e);
        }

        return resultSet;
    }
    public static void changePassword(int userID) {
        try {
            String query = "UPDATE Users SET Password = ? WHERE UserID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public static String checkPassword(int userID){
//        String password;
//        try {
//            String query = "SELECT Password FROM Users WHERE UserID = ?";
//            PreparedStatement stmt = connection.prepareStatement(query);
//            stmt.setInt(1, userID);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

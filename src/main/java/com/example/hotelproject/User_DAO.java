package com.example.hotelproject;

import java.sql.*;
import java.util.ArrayList;

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
            String query = "SELECT * FROM Users WHERE Deleted = 0 AND username = ? AND password = ?";
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
            String query = "SELECT * FROM Users WHERE Deleted = 0 AND UserID = ?";
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

    private static User_DAO instance = null;
    private static ArrayList<User> listUser;
    private User_DAO(){
        listUser = new ArrayList<>();
    }

    public static User_DAO getInstance(){
        if (instance == null){
            instance = new User_DAO();
        }
        return instance;
    }

    public ArrayList<User> getListUser(){
        return listUser;
    }
    private static  Connection conectDAO = Conect.getInstance();

    public static ResultSet readAll(){
        Statement statement;
        ResultSet result;
        String readSQL="select * from users WHERE Deleted = 0 ORDER BY UserID ASC";
        try {
            statement = conectDAO.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            result = statement.executeQuery(readSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static ResultSet find2(String loaiTimKiem, String textTimKiem){
        Statement statement;
        ResultSet result;
        String readSQL="select * FROM Users WHERE Deleted = 0 AND "+ loaiTimKiem +" LIKE '%"+ textTimKiem +"%' ORDER BY UserID ASC";
        try {
            statement = conectDAO.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            result = statement.executeQuery(readSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static int addNV(String fullName, String userName, String password, boolean isAdmin){
        String readSQL = "INSERT INTO Users VALUES (default,?,?,?,?,0)";
        try {
            PreparedStatement ppsm = conectDAO.prepareStatement(readSQL);
//            ppsm.setInt(1, userID);
            ppsm.setString(1, fullName);
            ppsm.setString(2, userName);
            ppsm.setString(3, password);
            ppsm.setBoolean(4,isAdmin);
            return ppsm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int updateNV(int userID, String fullName, String userName, String password, boolean isAdmin){
        String readSQL = "UPDATE Users SET FullName = ?, UserName= ?, Password= ?, IsAdmin=? WHERE userID= ?";
        try {
            PreparedStatement ppsm = conectDAO.prepareStatement(readSQL);

            ppsm.setString(1, fullName);
            ppsm.setString(2, userName);
            ppsm.setString(3, password);
            ppsm.setBoolean(4,isAdmin);
            ppsm.setInt(5, userID);
            return ppsm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int del(int userID){
        String readSQL = "UPDATE Users SET Deleted = 1 WHERE  UserID= ?";
        try {
            PreparedStatement ppsm = conectDAO.prepareStatement(readSQL);
            ppsm.setInt(1, userID);

            return ppsm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int tranPassword(String pass1, String pass2,int UserID){
        String readSQL = "UPDATE Users SET Password = ? WHERE Password = ? AND UserID = ?";
        try {
            PreparedStatement ppsm = conectDAO.prepareStatement(readSQL);
            ppsm.setString(1, pass1);
            ppsm.setString(2, pass2);
            ppsm.setInt(3, UserID);
            return ppsm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

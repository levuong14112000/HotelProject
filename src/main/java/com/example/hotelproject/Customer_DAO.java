package com.example.hotelproject;

import com.example.hotelproject.Conect;

import java.sql.*;
import java.time.LocalDate;

public class Customer_DAO {
    private static Connection connection = Conect.getInstance();
    public static ResultSet ReadCus(){
        ResultSet rs ;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT  * FROM customers ";
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return rs;
    }
    public static ResultSet searchData(String option, String searchText) {
        ResultSet rs;
        try {
            String query = "SELECT * FROM customers " +
                    "WHERE " + option + " = ?";
            PreparedStatement stmt =  connection.prepareStatement(query);
            stmt.setString(1, searchText);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    public static void insertCustomer(String FullName , String PhoneNumber , String IDNumber){
        try {
            String customerQuery = "INSERT INTO customers (FullName, PhoneNumber, IDNumber) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(customerQuery);
            stmt.setString(1, FullName);
            stmt.setString(2, PhoneNumber);
            stmt.setString(3, IDNumber);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteCustomer(int CustomerID) {
        try {
            String query = "DELETE FROM customers WHERE CustomerID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, CustomerID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateCustomer(String FullName , String PhoneNumber , String IDNumber , int CustomerID) {
        try {
            String query = "UPDATE customers SET FullName = '" + FullName + "', PhoneNumber = '" + PhoneNumber + "', IDNumber = '" + IDNumber +"' WHERE CustomerID = " + CustomerID;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

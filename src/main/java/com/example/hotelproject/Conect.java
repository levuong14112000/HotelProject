package com.example.hotelproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conect {
    private static Connection instance;
    private Conect(){};
    public static Connection getInstance(){
        if (instance == null) {
            try {
                instance = DriverManager.getConnection("jdbc:mysql://localhost:3306/HotelProject", "root", "");
                System.out.println("Success!");
            }
            catch (SQLException e){
                System.out.println("Failed");
            }
        }
        return instance;
    }
}

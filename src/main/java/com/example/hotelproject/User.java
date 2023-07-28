package com.example.hotelproject;

public class User {
    private int userID;
    private String fullName;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(int userID, String fullName, String username, String password, boolean isAdmin) {
        this.userID = userID;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

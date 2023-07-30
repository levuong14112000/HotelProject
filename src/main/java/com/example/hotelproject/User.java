package com.example.hotelproject;

public class User {
    private int userID;
    private String fullName;
    private String username;
    private String password;
    private String isAdmin;
    private boolean del;

    public User(int userID, String fullName, String username, String password, String isAdmin, boolean del) {
        this.userID = userID;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.del = del;
    }

    public User() {}

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public void setAdmin(String admin) {
        isAdmin = admin;
    }
    public void setDel(boolean del) {
        this.del = del;
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

    public String getIsAdmin() {
        return isAdmin;
    }

    public boolean isDel() {
        return del;
    }

    @Override
    public String toString() {
        return "User{userID=" + userID + ", fullName='" + fullName + '\'' +", username='" + username + '\'' +", password='" + password + '\'' +", isAdmin='" + isAdmin + '\'' +
                ", del=" + del + '}'+"\n";
    }
}

package com.example.hotelproject;

public class Customer {
    private int customerID;
    private String FullName;
    private String idNumber;
    private String phoneNumber;
    private Boolean Deleted;
    private int UserID;
    private int RoomID;

    public Customer(int customerID, String fullName, String idNumber, String phoneNumber) {
        this.customerID=customerID;
        this.FullName=fullName;
        this.idNumber=idNumber;
        this.phoneNumber=phoneNumber;
    }

    public Customer(String fullName, String idNumber, String phoneNumber) {
        this.FullName = fullName;
        this.idNumber =idNumber;
        this.phoneNumber =  phoneNumber;
    }


    public Boolean getDeleted() {
        return Deleted;
    }

    public void setDeleted(Boolean deleted) {
        Deleted = deleted;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public Customer(int customerID, String FullName, String idNumber, String phoneNumber, Boolean Deleted) {
        this.customerID = customerID;
        this.FullName = FullName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.Deleted = Deleted;
    }

    public Customer(int RoomID, String FullName, int UserID) {
        this.RoomID=RoomID;
        this.FullName=FullName;
        this.UserID=UserID;
    }


    // Getters and setters

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}


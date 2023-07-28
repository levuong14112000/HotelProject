package com.example.hotelproject;

public class RoomPayment {
    private int paymentID;
    private int customerID;
    private int roomID;
    private int checkInID;
    private int checkOutID;
    private double roomCharge;
    private Double extraCharge;
    private Double discount;
    private int userID;
    private boolean isDeleted;
    private int status;

    public RoomPayment(int paymentID, int customerID, int roomID, int checkInID, int checkOutID, double roomCharge, Double extraCharge, Double discount, int userID, boolean isDeleted) {
        this.paymentID = paymentID;
        this.customerID = customerID;
        this.roomID = roomID;
        this.checkInID = checkInID;
        this.checkOutID = checkOutID;
        this.roomCharge = roomCharge;
        this.extraCharge = extraCharge;
        this.discount = discount;
        this.userID = userID;
        this.isDeleted = isDeleted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }

    public int getCheckOutID() {
        return checkOutID;
    }

    public void setCheckOutID(int checkOutID) {
        this.checkOutID = checkOutID;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
    }

    public Double getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(Double extraCharge) {
        this.extraCharge = extraCharge;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

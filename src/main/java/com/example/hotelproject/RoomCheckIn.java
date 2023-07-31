package com.example.hotelproject;

import java.sql.Time;
import java.sql.Timestamp;

public class RoomCheckIn {
    private int checkInID;
    private int roomID;
    private int customerID;
    private int bookingID;
    private Time checkInTime;
    private int nop;
    private boolean isDeleted;
    private int userID;

    public RoomCheckIn(int roomID, int bookingID, int nop, int userID) {
        this.checkInID = checkInID;
        this.roomID = roomID;
        this.customerID = customerID;
        this.bookingID = bookingID;
        this.checkInTime = checkInTime;
        this.isDeleted = false;
        this.nop =nop;
        this.userID = userID;
    }

    public RoomCheckIn(int roomID, int nop, int userID) {
        this.checkInID = checkInID;
        this.roomID = roomID;
        this.customerID = customerID;
        this.bookingID = bookingID;
        this.checkInTime = checkInTime;
        this.isDeleted = false;
        this.nop =nop;
        this.userID = userID;
    }

    public RoomCheckIn(int checkInID, int roomID, int customerID, int bookingID, Time checkInTime, int nop, int userID) {
        this.checkInID = checkInID;
        this.roomID = roomID;
        this.customerID = customerID;
        this.bookingID = bookingID;
        this.checkInTime = checkInTime;
        this.isDeleted = false;
        this.nop =nop;
        this.userID = userID;
    }

    public RoomCheckIn(int checkInID, Time checkInTime, int userID) {
        this.checkInID =checkInID;
        this.checkInTime = checkInTime;
        this.userID = userID;
    }

    // Getters and setters

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getNop() {
        return nop;
    }

    public void setNop(int nop) {
        this.nop = nop;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}


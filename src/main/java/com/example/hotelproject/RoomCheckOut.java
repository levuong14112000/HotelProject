package com.example.hotelproject;

import java.sql.Timestamp;

public class RoomCheckOut {
    private int checkOutID;
    private int roomID;
    private int checkInID;
    private Timestamp checkOutTime;
    private boolean isDeleted;
    private int userID;

    public RoomCheckOut(){}

    public RoomCheckOut(int checkOutID, int roomID, int checkInID, Timestamp checkOutTime, boolean isDeleted) {
        this.checkOutID = checkOutID;
        this.checkInID =checkInID;
        this.roomID = roomID;
        this.checkOutTime = checkOutTime;
        this.isDeleted = isDeleted;
    }

    // Getters and setters

    public int getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCheckOutID() {
        return checkOutID;
    }

    public void setCheckOutID(int checkOutID) {
        this.checkOutID = checkOutID;
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Timestamp  checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}


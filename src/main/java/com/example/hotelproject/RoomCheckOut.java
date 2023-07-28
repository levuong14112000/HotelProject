package com.example.hotelproject;

import java.sql.Time;
import java.sql.Timestamp;

public class RoomCheckOut {
    private int checkOutID;
    private int roomID;
    private int checkInID;
    private Time checkOutTime;
    private boolean isDeleted;
    private int userID;

    public RoomCheckOut(){}

    public RoomCheckOut(int checkOutID, int roomID, int checkInID, Time checkOutTime, boolean isDeleted) {
        this.checkOutID = checkOutID;
        this.checkInID =checkInID;
        this.roomID = roomID;
        this.checkOutTime = checkOutTime;
        this.isDeleted = isDeleted;
    }

    public RoomCheckOut(int CheckOutID, int roomID, int checkInID1, Time checkOutTime, int userID) {
        this.checkOutID = CheckOutID;
        this.checkInID =checkInID1;
        this.roomID = roomID;
        this.checkOutTime = checkOutTime;
        this.userID = userID;
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

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time  checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}


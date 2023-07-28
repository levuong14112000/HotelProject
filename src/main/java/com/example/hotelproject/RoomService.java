package com.example.hotelproject;

import java.sql.Timestamp;

public class RoomService {
    private int roomServiceID;
    private int roomID;
    private int checkInID;
    private int serviceID;
    private int quantity;
    private double servicePrice;
    private Timestamp time;
    private int userID;
    private boolean deleted;

    // Constructor
    public RoomService(int roomServiceID, int roomID,int checkInID, int serviceID, int quantity, double servicePrice,Timestamp time, boolean deleted) {
        this.roomServiceID = roomServiceID;
        this.roomID = roomID;
        this.checkInID = checkInID;
        this.serviceID = serviceID;
        this.quantity = quantity;
        this.servicePrice = servicePrice;
        this.time = time;
        this.deleted = deleted;
    }
    public RoomService(int roomID,int checkInID, int serviceID, int quantity, double servicePrice, int userID) {
        this.roomServiceID = roomServiceID;
        this.roomID = roomID;
        this.checkInID =checkInID;
        this.serviceID = serviceID;
        this.quantity = quantity;
        this.servicePrice = servicePrice;
        this.time = time;
        this.userID = userID;
        this.deleted = deleted;
    }

    public int getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getRoomServiceID() {
        return roomServiceID;
    }

    public void setRoomServiceID(int roomServiceID) {
        this.roomServiceID = roomServiceID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}


package com.example.hotelproject;

import javafx.beans.property.*;

public class RoomType {
    private int roomTypeID;
    private String roomTypeName;
    private double basePrice;
    private boolean isDeleted;

    // Constructor
    public RoomType(int roomTypeID, String roomTypeName, double basePrice, boolean isDeleted) {
        this.roomTypeID = roomTypeID;
        this.roomTypeName = roomTypeName;
        this.basePrice = basePrice;
        this.isDeleted = isDeleted;
    }

    public RoomType(int roomtypeID, String roomTypeName, Double roomPrice) {
        this.roomTypeID =  roomtypeID;
        this.roomTypeName = roomTypeName;
        this.basePrice = roomPrice;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public StringProperty roomTypeNameProperty() {
        return new SimpleStringProperty(roomTypeName);
    }
    public DoubleProperty roomPriceProperty() {
        return new SimpleDoubleProperty(basePrice);
    }
    public IntegerProperty roomIDProperty() {
        return new SimpleIntegerProperty(roomTypeID);
    }
}

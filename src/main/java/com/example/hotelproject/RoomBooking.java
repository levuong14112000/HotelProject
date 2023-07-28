package com.example.hotelproject;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class RoomBooking {
    private int bookingID;
    private int customerID;
    private int roomID;
    private LocalDate bookingTime;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int userID;
    private boolean isDeleted;
    private String FullName;
    private String IDNumber ;
    private String PhoneNumber;
    private String RoomNumber;

    public RoomBooking(int bookingID,int CustomerID, String FullName, String IDNumber, String PhoneNumber, int roomID, LocalDate BookingTime,LocalDate checkInDate, LocalDate checkOutDate, int userID) {
        this.bookingID = bookingID;
        this.customerID=CustomerID;
        this.FullName = FullName;
        this.IDNumber = IDNumber;
        this.bookingTime = BookingTime;
        this.PhoneNumber = PhoneNumber;
        this.roomID = roomID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.userID = userID;
    }

    public RoomBooking(int bookingID, String FullName, String IDNumber, String PhoneNumber, int roomID) {
        this.bookingID = bookingID;
        this.FullName = FullName;
        this.IDNumber = IDNumber;
        this.PhoneNumber = PhoneNumber;
        this.roomID = roomID;
    }
    public RoomBooking(int roomID, String FullName , int userID) {
        this.roomID = roomID;
        this.FullName = FullName;
        this.userID = userID;
    }
    public RoomBooking(int bookID, int idCustomer, int roomID, LocalDate bookingTime, LocalDate checkinRoom, LocalDate checkoutRoom, int userID) {
        this.bookingID = bookID;
        this.customerID = idCustomer;
        this.roomID = roomID;
        this.bookingTime = bookingTime;
        this.checkInDate = checkinRoom;
        this.checkOutDate = checkoutRoom;
        this.userID = userID;
    }

    public RoomBooking(int bookingID, int customerID, String fullName, String idNumber, String phoneNumber, String roomNumber, LocalDate bookingTime, LocalDate checkInDate, LocalDate checkOutDate, int userID) {
        this.bookingID = bookingID;
        this.customerID=customerID;
        this.FullName = fullName;
        this.IDNumber = idNumber;
        this.bookingTime = bookingTime;
        this.PhoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.userID = userID;
        this.RoomNumber=roomNumber;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }
    private String PhoneNum;
    public RoomBooking(int bookingID, int customerID, int roomID, LocalDate bookingTime, LocalDate checkInDate, LocalDate checkOutDate, int userID, boolean isDeleted) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.roomID = roomID;
        this.bookingTime = bookingTime;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.userID = userID;
        this.isDeleted = isDeleted;
    }
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public String getPhoneNum() {
        return PhoneNum;
    }
    public int getRoomID() {
        return roomID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public LocalDate getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDate bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
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
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }
    public String getIDNumber() {
        return IDNumber;
    }
    public void setIDNumber(String idNumber) {
        this.IDNumber = idNumber;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public IntegerProperty roomIDProperty() {
        return new SimpleIntegerProperty(roomID);
    }
    public IntegerProperty bookingIDProperty() {
        return new SimpleIntegerProperty(bookingID);
    }
    public StringProperty CusNameProperty() {
        return new SimpleStringProperty(FullName);
    }
    public StringProperty IDcusProperty() {
        return new SimpleStringProperty(IDNumber);
    }
    public StringProperty PhoneProperty() {
        return new SimpleStringProperty(PhoneNum);
    }
    public ObjectProperty<LocalDate> CheckinProperty() {
        return new SimpleObjectProperty<>(checkInDate);
    }
    public ObjectProperty<LocalDate> CheckoutProperty() {
        return new SimpleObjectProperty<>(checkOutDate);
    }
    public IntegerProperty UserID(){return  new SimpleIntegerProperty(userID);}

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}


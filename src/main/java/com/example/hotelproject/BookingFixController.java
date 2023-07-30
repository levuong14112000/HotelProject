package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingFixController implements Initializable {
    private RoomBookingController customerController;

    public void setCustomerController(RoomBookingController customerController) {
        this.customerController = customerController;
    }
    @FXML
    ComboBox<Integer> c_roomid;

    @FXML Label customer_lb , l_bookid;
    @FXML
    private TextField i_bookingid1 , i_customer , i_roomid , i_userid ,I_Phonenumber , I_fullname , i_IDNumber;
    @FXML
    private DatePicker b_checkin1, b_checkout1 ,i_booktime1;
    @FXML
    protected void FixBookingBtn(){
        LocalDate bookingtime = i_booktime1.getValue();
        LocalDate checkin = b_checkin1.getValue();
        LocalDate checkout = b_checkout1.getValue();
        String FullName = I_fullname.getText();
        String PhoneNumber = I_Phonenumber.getText();
        String IDNumber = i_IDNumber.getText();
        Integer roomIDValue = c_roomid.getValue();
        int RoomID = roomIDValue != null ? roomIDValue.intValue() : 0;
        int CustomerID = Integer.parseInt(customer_lb.getText());
        int BookID =Integer.parseInt(l_bookid.getText());
        if (bookingtime == null || checkin == null || checkout == null || FullName.isEmpty() || PhoneNumber.isEmpty() || IDNumber.isEmpty() || RoomID == 0) {
            showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!validatePhoneNumber(PhoneNumber)) {
            showErrorMessage("Số điện thoại không hợp lệ!");
            return;
        }
        if (!validateIDNumber(IDNumber)) {
            showErrorMessage("Số CMND không hợp lệ!");
            return;
        }
        if (!validateCheckout(checkout,checkin)){
            showErrorMessage("Thời Gian CheckOut Không Hợp Lệ!");
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn cập nhật ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        String RoomNumber = String.valueOf(RoomID);
        ResultSet resultSet = RoomBooking_DAO.Status(RoomNumber);
        try {
            while (resultSet.next()) {
                int Status = resultSet.getInt("Status");
                Room roomz = new Room(Status);
                System.out.println(roomz);
                if (roomz.getStatus() == 0) {
                    showSuccessMessage("Update Thành Công");
                    Room_DAO.updateRoomStatus(RoomNumber, -1);
                    RoomBooking_DAO.updateBooking(bookingtime,checkin,checkout,FullName,PhoneNumber,IDNumber,CustomerID,RoomNumber,BookID);
                }
                else{
                    showErrorMessage("Phòng Đã Được Booking Trước Đó!");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage currentStage = (Stage) I_fullname.getScene().getWindow();
            customerController.updateTableView();
            currentStage.close();
        }
    }
    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() < 8) {
            return false;
        }
        if (!phoneNumber.startsWith("0")) {
            return false;
        }
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateIDNumber(String idNumber) {
        int length = idNumber.length();
        if (length != 9 && length != 12) {
            return false;
        }
        for (char c : idNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private boolean validateCheckout(LocalDate date, LocalDate CHECKIN) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(CHECKIN)) {
            return false;
        }
        else {
            return true;
        }
    }
    public void setBooking(RoomBooking rb){
        String bookingtime = String.valueOf(rb.getBookingTime());
        i_booktime1.setValue(LocalDate.parse(bookingtime));
        String Checkin = String.valueOf(rb.getCheckInDate());
        b_checkin1.setValue(LocalDate.parse(Checkin));
        String CheckOut = String.valueOf(rb.getCheckOutDate());
        b_checkout1.setValue(LocalDate.parse(CheckOut));
        I_fullname.setText(rb.getFullName());
        I_Phonenumber.setText(rb.getPhoneNumber());
        i_IDNumber.setText(rb.getIDNumber());
        int CustomerID = rb.getCustomerID();
        int BookID = rb.getBookingID();
        customer_lb.setText(String.valueOf(CustomerID));
        l_bookid.setText(String.valueOf(BookID));
        ObservableList<Integer> flag1 = FXCollections.observableArrayList();
        ResultSet resultSet1 = RoomList_DAO.showRooms();
        try {
            while (resultSet1.next()) {
                int roomID = resultSet1.getInt("RoomID");
                String RoomNumber = Room_DAO.getRoomNumberByRoomID(roomID);
                flag1.add(Integer.valueOf(RoomNumber));
                c_roomid.setItems(flag1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setBooking1(RoomBooking rb){
        String bookingtime = String.valueOf(rb.getBookingTime());
        i_booktime1.setValue(LocalDate.parse(bookingtime));
        String Checkin = String.valueOf(rb.getCheckInDate());
        b_checkin1.setValue(LocalDate.parse(Checkin));
        String CheckOut = String.valueOf(rb.getCheckOutDate());
        b_checkout1.setValue(LocalDate.parse(CheckOut));
        I_fullname.setText(rb.getFullName());
        I_Phonenumber.setText(rb.getPhoneNumber());
        i_IDNumber.setText(rb.getIDNumber());
        int CustomerID = rb.getCustomerID();
        int BookID = rb.getBookingID();
        customer_lb.setText(String.valueOf(CustomerID));
        l_bookid.setText(String.valueOf(BookID));
        ObservableList<Integer> flag1 = FXCollections.observableArrayList();
        ResultSet resultSet1 = RoomList_DAO.showRooms();
        try {
            while (resultSet1.next()) {
                int roomID = resultSet1.getInt("RoomID");
                String RoomNumber = Room_DAO.getRoomNumberByRoomID(roomID);
                flag1.add(Integer.valueOf(RoomNumber));
                c_roomid.setItems(flag1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    private void onHuyButtonClick(){
        Stage currenStage = (Stage) i_IDNumber.getScene().getWindow();
        currenStage.close();
    }
}

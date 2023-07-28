package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingInsertController implements Initializable {
    private RoomBookingController customerController;
    private int userId;

    public void setCustomerController(RoomBookingController customerController) {
        this.customerController = customerController;
    }

    @FXML
    private ComboBox<String> b_roomid;
    @FXML
    private TextField i_fullname, i_number, i_phone;
    @FXML
    private DatePicker b_checkin, b_checkout, i_booktime;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }
    public void loadData(){
        ObservableList<String> flag1 = FXCollections.observableArrayList();
        ObservableList<Integer> flag2 = FXCollections.observableArrayList();
        ObservableList<Integer> flag3 = FXCollections.observableArrayList();
        ObservableList<Customer> Cus = FXCollections.observableArrayList();
        ObservableList<Room> roomlist = FXCollections.observableArrayList();
        ResultSet resultSet1 = RoomList_DAO.showRooms();
        try {
            while (resultSet1.next()) {
                int roomtypeID = resultSet1.getInt("RoomTypeID");
                String roomNum = resultSet1.getString("RoomNumber");
                String roomTypeName = resultSet1.getString("RoomTypeName");
                String roomPrice = resultSet1.getString("BasePrice");
                Room room = new Room( roomNum, roomTypeName, roomPrice, roomtypeID);
                roomlist.add(room);
                flag1.add(roomNum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet2 = RoomBooking_DAO.showCus();
            while (resultSet2.next()) {
                int CustomerID = resultSet2.getInt("CustomerID");
                String FullName = resultSet2.getString("FullName");
                String IDNumber = resultSet2.getString("IDNumber");
                String PhoneNumber = resultSet2.getString("PhoneNumber");
                Boolean Deleted = resultSet2.getBoolean("Deleted");
                Customer cus = new Customer(CustomerID, FullName, IDNumber, PhoneNumber, Deleted);
                Cus.add(cus);
                flag2.add(CustomerID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ResultSet resultSet = RoomBooking_DAO.showRooms();
            while (resultSet.next()) {
                int cbbuser = userId;
                flag3.add(cbbuser);
            }
            b_roomid.setItems(flag1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
     loadData();
    }
    @FXML
    protected void InsertBookingBtn() {
        LocalDate BookingTime = i_booktime.getValue();
        LocalDate CheckInDate = b_checkin.getValue();
        LocalDate CheckOutDay = b_checkout.getValue();
        String roomIDValue = b_roomid.getValue();
        String RoomID = String.valueOf(roomIDValue);
        int UserID = userId;
        String FullName = i_fullname.getText();
        String PhoneNum = i_phone.getText();
        String IDNum = i_number.getText();
        if (BookingTime == null || CheckInDate == null || CheckOutDay == null || FullName.isEmpty() || PhoneNum.isEmpty() || IDNum.isEmpty() || RoomID.isEmpty()) {
            showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!validatePhoneNumber(PhoneNum)) {
            showErrorMessage("Số điện thoại không hợp lệ!");
            return;
        }

        if (!validateIDNumber(IDNum)) {
            showErrorMessage("Số CMND không hợp lệ!");
            return;
        }
        if (!validateDate(BookingTime)) {
            showErrorMessage("Thời Gian Booking Không Hợp Lệ!");
            return;
        }
        if (!validateCheckin(CheckInDate)) {
            showErrorMessage("Thời Gian CheckIn Không Hợp Lệ!");
            return;
        }
        if (!validateCheckout(CheckOutDay, CheckInDate)) {
            showErrorMessage("Thời Gian CheckOut Không Hợp Lệ!");
            return;
        }
        System.out.println("kk");
        ResultSet resultSet = RoomBooking_DAO.Status(RoomID);
        try {
            while (resultSet.next()) {
                int Status = resultSet.getInt("Status");
                Room roomz = new Room(Status);
                System.out.println(roomz);
                if (roomz.getStatus() == 0) {
                    showSuccessMessage("Insert Thành Công");
                    Room_DAO.updateRoomStatus(RoomID, -1);
                    RoomBooking_DAO.insertBooking(BookingTime, CheckInDate, CheckOutDay, RoomID, UserID, FullName, PhoneNum, IDNum);
                }
                else{
                    showErrorMessage("Phòng Đã Được Booking Trước Đó!");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage currentStage = (Stage) i_fullname.getScene().getWindow();
        customerController.updateTableView();
        currentStage.close();
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

    private boolean validateDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            return false;
        }
        return true;
    }

    private boolean validateCheckin(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            return false;
        }
        return true;
    }

    private boolean validateCheckout(LocalDate date, LocalDate CHECKIN) {
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(CHECKIN)) {
            return false;
        } else {
            return true;
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

}

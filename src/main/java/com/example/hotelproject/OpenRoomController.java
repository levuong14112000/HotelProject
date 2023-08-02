package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.ResourceBundle;

public class OpenRoomController implements Initializable {
    @FXML
    private Label roomPrice, roomNumber, timeField;
    @FXML
    private RadioButton fKhachVangLai;
    @FXML
    private String userData;
    @FXML
    private TextField fcustomerName, fnumberID, fphoneNumber, fnop, fbookingID;
    private StackPane rightPane;
    private int roomID;
    private int userId;
    private int checkInID;

    public void setRightPane(StackPane rightPane) {
        this.rightPane = rightPane;
    }
    public void setUserData(String userData) {
        this.userData = userData;
    }
    public void setDataAndInitialize(String userData) {
        this.userData = userData;
        initialize(null, null);
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }
    public void errorAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleChooseRadioButton(){
        if (fKhachVangLai.isSelected() == true){
            fbookingID.setDisable(true);
        }else {
            fbookingID.setDisable(false);
        }
    }
    @FXML
    private void onCancelButtonClick() {
        roomPrice.getScene().getWindow().hide();
    }
    @FXML
    private void handleOKButtonAction(ActionEvent event) {
        // Lấy thông tin từ FXML
        String fullName = fcustomerName.getText();
        String idNumber = fnumberID.getText();
        String phoneNumber = fphoneNumber.getText();
        String nopText = fnop.getText();

        Customer customer = new Customer(fullName, idNumber, phoneNumber);
        roomID = Integer.parseInt(userData);

        if (fullName.isEmpty() || idNumber.isEmpty() || phoneNumber.isEmpty() || nopText.isEmpty()){
            errorAlert("Vui lòng nhập đầy đủ thông tin !");
            if (fullName.isEmpty()){
                fcustomerName.requestFocus();
            } else if (idNumber.isEmpty()) {
                fnumberID.requestFocus();
            }  else if (phoneNumber.isEmpty()){
                fphoneNumber.requestFocus();
            }   else {
                fnop.requestFocus();;
            }
            return;
        }

        if (!idNumber.matches("\\d{9,}")) {
            errorAlert("CMND/Passport phải là số và có ít nhất 9 chữ số!");
            fnumberID.requestFocus();
            return;
        }
        if (!phoneNumber.matches("\\d{10,}")) {
            errorAlert("Số điện thoại là số và có ít nhất 10 chữ số!");
            fnumberID.requestFocus();
            return;
        }
        int nop;
        try {
            nop = Integer.parseInt(nopText);
            if (nop < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            errorAlert("Số khách không hợp lệ !");
            fnop.requestFocus();
            return;
        }

        try {
            if (!fKhachVangLai.isSelected()) {
                int bookingID = Integer.parseInt(fbookingID.getText());
                if (RoomBooking_DAO.checkBookingID(bookingID) != bookingID){
                    errorAlert("Mã booking không tồn tại !");
                }
                RoomCheckIn roomCheckIn = new RoomCheckIn(roomID, bookingID, nop, userId);
                checkInID = Room_DAO.createCustomer_CheckIn(customer, roomCheckIn);
            } else {
                RoomCheckIn roomCheckIn = new RoomCheckIn(roomID, nop, userId);
                checkInID = Room_DAO.createCustomer_CheckIn_NotBooking(customer, roomCheckIn);
            }

            roomPrice.getScene().getWindow().hide();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDetailView.fxml"));
                Parent roomDetail = loader.load();

                RoomDetailController roomDetailController = loader.getController();
                roomDetailController.setUserData(userData);
                roomDetailController.setDataAndInitialize(userData);
                roomDetailController.setTransData(userId, checkInID);
                roomDetailController.setTransDataAndInitialize(userId, checkInID);
                System.out.println("OpenRoom " + checkInID);
//                roomDetailController.setCheckInID(checkInID);
//                roomDetailController.setCheckInIDAndInitialize(checkInID);

                rightPane.getChildren().setAll(roomDetail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {

        }
        Room_DAO.updateRoomStatus(roomID, checkInID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ResultSet resultSet = Room_DAO.showRoomInformationWithRoomID(userData);
            while (resultSet.next()) {
                String giaPhong = resultSet.getString("BasePrice");
                String soPhong = resultSet.getString("RoomNumber");
                roomPrice.setText(giaPhong);
                roomNumber.setText(soPhong);
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Ngày giờ hiện tại
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        String formattedDateTime = now.format(formatter);
//        timeField.setText(formattedDateTime);

        System.out.println("openrooom" + checkInID);
    }
}

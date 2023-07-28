package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {
    @FXML
    private TableView<RoomCheckIn> table;
    @FXML
    private TableColumn<RoomCheckIn,Integer> checkin , room , customer , booking  ,nop , userid;
    @FXML
    private TableColumn<RoomCheckIn, Timestamp> checkin_time;
    @FXML
    public void showCheckIn(){
        ResultSet resultSet = RoomCheckIn_DAO.read();
        ObservableList<RoomCheckIn> Check = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int CheckInID = resultSet.getInt("CheckInID");
                int RoomID = resultSet.getInt("RoomID");
                int CustomerID = resultSet.getInt("CustomerID");
                int BookingID = resultSet.getInt("BookingID");
                Time CheckInTime = resultSet.getTime("CheckInTime");
                int NOP = resultSet.getInt("NOP");
                int UserID = resultSet.getInt("UserID");
                RoomCheckIn room = new RoomCheckIn(CheckInID,RoomID,CustomerID,BookingID,CheckInTime,NOP,UserID);
                Check.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkin.setCellValueFactory(new PropertyValueFactory<>("checkInID"));
        room.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        customer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        booking.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        checkin_time.setCellValueFactory(new PropertyValueFactory<>("checkInTime"));
        nop.setCellValueFactory(new PropertyValueFactory<>("nop"));
        userid.setCellValueFactory(new PropertyValueFactory<>("userID"));
        table.setItems(Check);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCheckIn();
    }
}

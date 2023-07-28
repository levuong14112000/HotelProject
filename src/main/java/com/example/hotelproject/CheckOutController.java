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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckOutController implements Initializable {

    @FXML
    private TableView<RoomCheckOut>  table1;
    @FXML
    private TableColumn<RoomCheckOut ,Integer> checkout1 , room1 , checkin1  , userid1;
    @FXML
    private TableColumn<RoomCheckOut, Time> checkout_time1;
    public CheckOutController() {
    }

    public void showCheckOut(){
        ResultSet resultSet = RoomCheckOut_DAO.read();
        ObservableList<RoomCheckOut> Check1 = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int CheckOutID = resultSet.getInt("CheckOutID");
                int RoomID = resultSet.getInt("RoomID");
                int CheckInID = resultSet.getInt("CheckInID");
                Time CheckOutTime = resultSet.getTime("CheckOutTime");
                int UserID = resultSet.getInt("UserID");
                RoomCheckOut room = new RoomCheckOut(CheckOutID,RoomID,CheckInID,CheckOutTime,UserID);
                Check1.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkout1.setCellValueFactory(new PropertyValueFactory<>("checkOutID"));
        room1.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        checkin1.setCellValueFactory(new PropertyValueFactory<>("checkInID"));
        userid1.setCellValueFactory(new PropertyValueFactory<>("userID"));
        checkout_time1.setCellValueFactory(new PropertyValueFactory<>("checkOutTime"));
        table1.setItems(Check1);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCheckOut();
    }
}

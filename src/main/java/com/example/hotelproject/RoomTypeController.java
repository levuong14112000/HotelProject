package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RoomTypeController implements Initializable {
    @FXML
    private TableView<RoomType> tableView;
    @FXML
    private TableColumn<RoomType, Integer> roomIDColumn;
    @FXML
    private TableColumn<RoomType, String> roomTypeNameColumn;
    @FXML
    private TableColumn<RoomType, Double> roomPriceColumn;
    @FXML
    private Label CountRoom;
    @FXML
    private TextField t_status, t_numRoom;

    private void handleRowClick() {
        RoomType selectedRoom = tableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            t_numRoom.setText(selectedRoom.getRoomTypeName());
            String flag = String.valueOf(selectedRoom.getBasePrice());
            t_status.setText(flag);
        }
    }

    public void  show() {
        ResultSet resultSet = RoomType_DAO.read();
        String count = String.valueOf(RoomType_DAO.calculateTotalRoomType());
        CountRoom.setText("Tổng " + count + " Phòng");
        ObservableList<RoomType> rooms = FXCollections.observableArrayList();
        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<String> uniqueItems = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int roomtypeID = resultSet.getInt("RoomTypeID");
                String roomTypeName = resultSet.getString("RoomTypeName");
                Double roomPrice = resultSet.getDouble("BasePrice");
                RoomType room = new RoomType(roomtypeID,roomTypeName, roomPrice);
                list.add(roomTypeName);
                for (String item : list) {
                    if (!uniqueItems.contains(item)) {
                        uniqueItems.add(item);
                    }
                }
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        roomIDColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
        roomTypeNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeNameProperty());
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty().asObject());
        tableView.setItems(rooms);
    }
    @FXML
    protected void insertButton() {
        String RoomTypeName = t_numRoom.getText();
        Double BasicPrice = Double.valueOf(t_status.getText());
        try {
            if (RoomTypeName.isEmpty() || BasicPrice == null) {
                showErrorMessage("Tất cả dữ liệu phải được nhập đầy đủ !");
                return;
            }
            if (BasicPrice <= 0) {
                showErrorMessage("Basic Price phải là một số dương!");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Basic Price phải là một số hợp lệ!");
            return;
        }
        RoomType_DAO.insertRoomtype( RoomTypeName,BasicPrice);

        if (!RoomTypeName.isEmpty()){
            showSuccessMessage("Thành Công");
        }
        show();
        t_numRoom.clear();
        t_status.clear();
    }
    @FXML
    protected void updateButton() {

        RoomType selectedRoom = tableView.getSelectionModel().getSelectedItem();
        System.out.println("Test");
        int RoomTypeID = selectedRoom.getRoomTypeID();
        String RoomTypeName = String.valueOf(t_numRoom.getText());
        Double BasicPrice = Double.valueOf(t_status.getText());
        try {
            if (RoomTypeName.isEmpty() || BasicPrice == null) {
                showErrorMessage("Tất cả dữ liệu phải được nhập đầy đủ !");
                return;
            }
            if (BasicPrice <= 0) {
                showErrorMessage("Basic Price phải là một số dương!");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Basic Price phải là một số hợp lệ!");
            return;
        }
        if (!RoomTypeName.isEmpty()){
            showSuccessMessage("Thành Công");
        }
        RoomType_DAO.updateRoomtype(RoomTypeID,RoomTypeName,BasicPrice);

        show();
        t_numRoom.clear();
        t_status.clear();
    }
    @FXML
    protected void deleteButton() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            RoomType selectedRoom = tableView.getSelectionModel().getSelectedItem();
            int RoomTypeID = selectedRoom.getRoomTypeID();
            RoomType_DAO.deleteRoomtype(RoomTypeID);
            show();
            t_numRoom.clear();
            t_status.clear();
        }
    }
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
        tableView.setOnMouseClicked(event -> handleRowClick());
        Pattern validDoubleText = Pattern.compile("-?\\d*(\\.\\d*)?");
        TextFormatter<String> doubleTextFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (validDoubleText.matcher(newText).matches()) {
                return change;
            } else {
                return null;
            }
        });
        t_status.setTextFormatter(doubleTextFormatter);

    }
}

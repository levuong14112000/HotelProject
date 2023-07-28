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

public class RoomsController_1 implements Initializable {
    @FXML
    private TableView<Room> tableView;
    @FXML
    private TableColumn<Room, Integer> roomIDColumn;
    @FXML
    private TableColumn<Room, String> roomNumColumn;
    @FXML
    private TableColumn<Room, Boolean> statusColumn;

    @FXML
    private TableColumn<Room, String> roomTypeNameColumn;
    @FXML
    private TableColumn<Room, String> roomPriceColumn;
    @FXML
    private Label CountRoom;
    @FXML
    private TextField t_idroom, t_status, t_numRoom, t_loai;
    @FXML
    private ComboBox c_roo;

    public RoomsController_1() {
    }


    private void handleRowClick() {
        Room selectedRoom = tableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            t_numRoom.setText(selectedRoom.getRoomNumber());
            String flag = String.valueOf(selectedRoom.getRoomTypeID());
            c_roo.setValue(selectedRoom.getRoomTypeName());
        }
    }

    public void  show() {
        ResultSet resultSet = RoomList_DAO.showRoomTKs();
        String count = String.valueOf(RoomList_DAO.calculateTotalRoom());
        CountRoom.setText("Tổng " + count + " Phòng");
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<String> list1 = FXCollections.observableArrayList();
        ObservableList<String> uniqueItems = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                int roomtypeID = resultSet.getInt("RoomTypeID");
                String roomNum = resultSet.getString("RoomNumber");
                String roomTypeName = resultSet.getString("RoomTypeName");
                String roomPrice = resultSet.getString("BasePrice");
                Room room = new Room(roomID, roomNum, roomTypeName, roomPrice, roomtypeID);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet1 = RoomList_DAO.ReadRoomType();
        try {
            while (resultSet1.next()) {
                String roomTypeName = resultSet1.getString("RoomTypeName");
                RoomType rp = new RoomType(roomTypeName);
                list1.add(roomTypeName);
                for (String item : list1) {
                    if (!uniqueItems.contains(item)) {
                        uniqueItems.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        c_roo.setItems(uniqueItems);
        roomNumColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty());
        roomIDColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
        roomTypeNameColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeNameProperty());
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty());

        tableView.setItems(rooms);
    }
    public void ifQuerry() {
        String roomNumberText = t_numRoom.getText();
        String roomTypeIDText = (String) c_roo.getValue();
        if (roomNumberText.isEmpty() || roomTypeIDText.isEmpty()) {
            showErrorMessage("Tất cả dữ liệu phải được nhập đầy đủ !");
            return;
        }
        if (!roomNumberText.matches("\\d+")) {
            showErrorMessage("Thông tin nhập chưa chính xác !");
            return;
        }
    }
    @FXML
    protected void insertButton() {
        ifQuerry();
        String roomNumber = String.valueOf(t_numRoom.getText());
        String TypeName = (String) c_roo.getValue();
        if (!roomNumber.matches("\\d+")) {
            showErrorMessage("Thất Bại !");
            return;
        }
        RoomList_DAO.insertRoom( roomNumber, TypeName);

        if (!roomNumber.isEmpty()){
            showSuccessMessage("Thành Công");
        }
        show();
        t_numRoom.clear();
    }
    @FXML
    protected void updateButton() {
        ifQuerry();
        Room selectedRoom = tableView.getSelectionModel().getSelectedItem();
        int roomid = selectedRoom.getRoomID();
        String roomNumber = String.valueOf(t_numRoom.getText());
        String TypeName = (String) c_roo.getValue();
        if (!roomNumber.matches("\\d+")) {
            showErrorMessage("Thất Bại !");
            return;
        }
        RoomList_DAO.updateRoom( roomid,roomNumber, TypeName);
        if (!roomNumber.isEmpty()){
            showSuccessMessage("Thành Công");
        }
        show();
        t_numRoom.clear();
    }
    @FXML
    protected void deleteButton() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Room selectedRoom = tableView.getSelectionModel().getSelectedItem();
            int roomid = selectedRoom.getRoomID();
            RoomList_DAO.deleteRoom(roomid);
            show();
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

    }
}

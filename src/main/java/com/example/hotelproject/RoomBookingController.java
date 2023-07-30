package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomBookingController implements Initializable {
    @FXML
    private TableView<RoomBooking> tableView, tableView1;
    @FXML
    private TableColumn<RoomBooking, Integer> IDc, IDc1;
    @FXML
    private TableColumn<RoomBooking, Integer> bookIDColumn, bookIDColumn1;
    @FXML
    private TableColumn<RoomBooking, Integer> roomIDColumn, roomIDColumn1;
    @FXML
    private TableColumn<RoomBooking, String> nameCustomer, nameCustomer1;
    @FXML
    private TableColumn<RoomBooking, String> BookingTimeColumn, BookingTimeColumn1;
    @FXML
    private TableColumn<RoomBooking, String> CustomerIDColumn, CustomerIDColumn1;
    @FXML
    private TableColumn<RoomBooking, String> PhoneCustomerColumn, PhoneCustomerColumn1;
    @FXML
    private TableColumn<RoomBooking, String> CheckinColumn, CheckinColumn1;
    @FXML
    private TableColumn<RoomBooking, String> CheckOutColumn, CheckOutColumn1;
    @FXML
    private TableColumn<RoomBooking, String> UserID, UserID1;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField t_search;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }
    public void showinl() {
        ResultSet resultSet = RoomBooking_DAO.showRooms();
        ObservableList<RoomBooking> roomBookings = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int bookingID = resultSet.getInt("BookingID");
                int CustomerID = resultSet.getInt("CustomerID");
                String fullName = resultSet.getString("FullName");
                String IDNumber = resultSet.getString("IDNumber");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int roomID = resultSet.getInt("RoomID");
                String RoomNumber = Room_DAO.getRoomNumberByRoomID(roomID);
                LocalDate BookingTime = resultSet.getDate("BookingTime").toLocalDate();
                LocalDate checkInDate = resultSet.getDate("CheckInDate").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("CheckOutDate").toLocalDate();
                int userID = resultSet.getInt("UserID");
                RoomBooking roomBooking = new RoomBooking(bookingID,CustomerID, fullName, IDNumber, phoneNumber, RoomNumber,BookingTime, checkInDate, checkOutDate, userID);
                roomBookings.add(roomBooking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        IDc.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCustomer.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
        PhoneCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        BookingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));
        CheckinColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        CheckOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        tableView.setItems(roomBookings);
    }
    @FXML
    protected void SearchButton() {
        String selectedOption = (String) comboBox.getValue();
        String searchText = t_search.getText();
        if (selectedOption != null && !searchText.isEmpty()) {
            performSearch(selectedOption, searchText);
        } else {
            showErrorMessage("Chưa Chọn Giá Trị Cần Tìm !");
        }
    }
    private void performSearch(String option, String searchText) {
        if (option.equals("CMND & PARTPOSS")){
            option = "IDNumber";
        }
        if (option.equals("SỐ ĐIỆN THOẠI")){
            option = "PhoneNumber";
        }
        if (option.equals("HỌ VÀ TÊN")){
            option = "FullName";
        }
        ResultSet resultSet = RoomBooking_DAO.searchData(option, searchText);
        ObservableList<RoomBooking> roomBookings1 = FXCollections.observableArrayList();

        try {
            if (resultSet.next()) {
                int bookingID = resultSet.getInt("BookingID");
                int CustomerID = resultSet.getInt("CustomerID");
                String fullName = resultSet.getString("FullName");
                String IDNumber = resultSet.getString("IDNumber");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int roomID = resultSet.getInt("RoomID");
                LocalDate BookingTime = resultSet.getDate("BookingTime").toLocalDate();
                LocalDate checkInDate = resultSet.getDate("CheckInDate").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("CheckOutDate").toLocalDate();
                int userID = resultSet.getInt("UserID");
                String RoomNumber = Room_DAO.getRoomNumberByRoomID(roomID);
                RoomBooking roomBooking = new RoomBooking(bookingID,CustomerID, fullName, IDNumber, phoneNumber, RoomNumber,BookingTime ,checkInDate, checkOutDate, userID);
                roomBookings1.add(roomBooking);
                bookIDColumn1.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
                IDc1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
                nameCustomer1.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                CustomerIDColumn1.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
                PhoneCustomerColumn1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                roomIDColumn1.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
                BookingTimeColumn1.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));
                CheckinColumn1.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
                CheckOutColumn1.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
                UserID1.setCellValueFactory(new PropertyValueFactory<>("UserID"));
                tableView1.setItems(roomBookings1);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy dữ liệu phù hợp!");
                alert.showAndWait();
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

    @FXML
    protected void addButtonClicked() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("insertBooking.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            BookingInsertController controller = loader.getController();
            controller.setUserId(userId);
            controller.setUserIDAndInitialize(userId);
            controller.setCustomerController(this);
            stage.setScene(scene);
            stage.setTitle("Thao Tác");
            stage.show();
            showinl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void DeleteBtn() {
        RoomBooking selectedRoom = tableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int BookID = selectedRoom.getBookingID();
                RoomBooking_DAO.deleteBooking(BookID);
                if (BookID != 0) {
                    showSuccessMessage("Xóa Thành Công!");
                    showinl();
                } else {
                    showErrorMessage("Xóa thất bại!");
                    showinl();
                }
            }
        }
    }
    @FXML
    protected void FixButtonClicked() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fixBooking.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            BookingFixController controller = loader.getController();
            controller.setCustomerController(this);
            RoomBooking selected = tableView.getSelectionModel().getSelectedItem();
            RoomBooking selected1 = tableView1.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.setBooking(selected);
            } else {
                controller.setBooking1(selected1);
            }
            stage.setScene(scene);
            stage.setTitle("Thao Tác");
            stage.show();
            showinl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showinl();
        ObservableList<String> options = FXCollections.observableArrayList("CMND & PARTPOSS", "SỐ ĐIỆN THOẠI", "HỌ VÀ TÊN");
        comboBox.setItems(options);
    }
    public void updateTableView() {
        showinl();
    }
}


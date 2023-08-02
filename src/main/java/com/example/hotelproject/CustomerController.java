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
import java.util.Optional;
import java.util.ResourceBundle;


public class CustomerController implements Initializable {
    @FXML
    private TableView<Customer> tableView, tableView1;
    @FXML
    private TableColumn<Customer, Integer> CustomerColumn, CustomerColumn1;
    @FXML
    private TableColumn<Customer, String> NameColumn, NameColumn1;
    @FXML
    private TableColumn<Customer, String> IDNumberColumn, IDNumberColumn1;
    @FXML
    private TableColumn<Customer, String> PhoneColumn, PhoneColumn1;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField t_search;
    public void showinl() {
        ResultSet resultSet = Customer_DAO.ReadCus();
        ObservableList<Customer> Customer = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                int CustomerID = resultSet.getInt("CustomerID");
                String FullName = resultSet.getString("FullName");
                String IDNumber = resultSet.getString("IDNumber");
                String PhoneNumber = resultSet.getString("PhoneNumber");
                Customer customer = new Customer(CustomerID,FullName,IDNumber,PhoneNumber);
                Customer.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        IDNumberColumn.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableView.setItems(Customer);
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
        if (option.equals("CMND/Passport")){
            option = "IDNumber";
        }
        if (option.equals("Số điện thoại")){
            option = "PhoneNumber";
        }
        if (option.equals("Họ tên")){
            option = "FullName";
        }
        ResultSet resultSet = Customer_DAO.searchData(option, searchText);
        ObservableList<Customer> Customer1 = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                int CustomerID = resultSet.getInt("CustomerID");
                String FullName = resultSet.getString("FullName");
                String IDNumber = resultSet.getString("IDNumber");
                String PhoneNumber = resultSet.getString("PhoneNumber");
                Customer customer1 = new Customer(CustomerID,FullName,IDNumber,PhoneNumber);
                Customer1.add(customer1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CustomerColumn1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        NameColumn1.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        IDNumberColumn1.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        PhoneColumn1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableView1.setItems(Customer1);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showinl();
        ObservableList<String> options = FXCollections.observableArrayList("CMND/Passport", "Số điện thoại", "Họ tên");
        comboBox.setItems(options);
    }
    @FXML
    protected void addButtonClicked() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Thao Tác");
            CustomerInsertController controller = loader.getController();
            controller.setCustomerController(this);
            stage.show();
            showinl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void DeleteBtn() {
        Customer selectedRoom = tableView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int CustomerID = selectedRoom.getCustomerID();
                Customer_DAO.deleteCustomer(CustomerID);
                if (CustomerID != 0) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fixCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            CustomerFixController controller = loader.getController();
            controller.setCustomerController(this);
            Customer selected = tableView.getSelectionModel().getSelectedItem();
            Customer selected1 = tableView1.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.setCustomer(selected);
            } else {
                controller.setCustomer1(selected1);
            }
            stage.setScene(scene);
            stage.setTitle("Thao Tác");
            stage.show();
            showinl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void updateTableView() {
        showinl();
    }
}

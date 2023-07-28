package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {
    @FXML private TableView<ServiceItem> serviceTable;
    @FXML private TableColumn<ServiceItem, String> colSTT;
    @FXML private TableColumn<ServiceItem, Integer> colMaHang;
    @FXML private TableColumn<ServiceItem, String> colTenHang;
    @FXML private TableColumn<ServiceItem, Double> colDonGia;
    @FXML private Label lbTongSo;
    @FXML private TextField txtTenHang, txtDonGia;

    private ServiceItem selectedRow;
    private int selectedIndex;
    private ServiceItem selectedService;
    private ObservableList<ServiceItem> serviceList = FXCollections.observableArrayList();
    public class ServiceItem {
        private String stt;
        private int ServiceID;
        private String ServiceName;
        private double ServicePrice;

        public ServiceItem(String stt, int serviceID, String serviceName, double servicePrice) {
            this.stt = stt;
            ServiceID = serviceID;
            ServiceName = serviceName;
            ServicePrice = servicePrice;
        }

        public String getStt() {
            return stt;
        }

        public void setStt(String stt) {
            this.stt = stt;
        }

        public int getServiceID() {
            return ServiceID;
        }

        public void setServiceID(int serviceID) {
            ServiceID = serviceID;
        }

        public String getServiceName() {
            return ServiceName;
        }

        public void setServiceName(String serviceName) {
            ServiceName = serviceName;
        }

        public double getServicePrice() {
            return ServicePrice;
        }

        public void setServicePrice(double servicePrice) {
            ServicePrice = servicePrice;
        }

    }

    @FXML
    private void onChinhSuaButtonClick() {
        ServiceItem selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showWarningAlert("Vui lòng chọn một dịch vụ để chỉnh sửa.");
            return;
        }

        txtTenHang.setText(selectedService.getServiceName());
        txtDonGia.setText(String.valueOf(selectedService.getServicePrice()));
    }
    @FXML
    private void onLuuButtonClick() {
        selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showWarningAlert("Vui lòng chọn một dịch vụ để lưu thay đổi.");
            return;
        }

        String serviceName = txtTenHang.getText().trim();
        String servicePriceStr = txtDonGia.getText().trim();

        if (serviceName.isEmpty() || servicePriceStr.isEmpty()) {
            showWarningAlert("Vui lòng nhập đủ thông tin dịch vụ.");
            return;
        }

        double servicePrice;
        try {
            servicePrice = Double.parseDouble(servicePriceStr);
        } catch (NumberFormatException e) {
            showWarningAlert("Giá dịch vụ không hợp lệ. Vui lòng nhập giá là một số hợp lệ.");
            return;
        }

        selectedService.setServiceName(serviceName);
        selectedService.setServicePrice(servicePrice);

        serviceTable.refresh();

        Service_DAO.updateService(selectedService);

        showInformationAlert("Đã lưu thay đổi thành công.");
    }

    @FXML
    private void onThemMoiButtonClick(){
        String serviceName = txtTenHang.getText().trim();
        String servicePriceStr = txtDonGia.getText().trim();

        if (serviceName.isEmpty() || servicePriceStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Vui lòng nhập đủ thông tin");
            alert.setContentText("Vui lòng nhập tên dịch vụ và giá dịch vụ.");
            alert.showAndWait();
            return;
        }

        double servicePrice;
        try {
            servicePrice = Double.parseDouble(servicePriceStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Giá dịch vụ không hợp lệ");
            alert.setContentText("Vui lòng nhập giá dịch vụ là một số hợp lệ.");
            alert.showAndWait();
            return;
        }
        Service_DAO.createService(serviceName, servicePrice);
        serviceList.clear();
        loadDataFromDatabase();
        serviceTable.refresh();
        txtTenHang.clear();
        txtDonGia.clear();
    }
    @FXML
    private void onXoaButtonClick() {
        ServiceItem selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showWarningAlert("Vui lòng chọn một dịch vụ để xóa.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận xóa");
        confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa dịch vụ này?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean deleteResult = Service_DAO.deletedService(selectedService.getServiceID());
            if (deleteResult) {
                serviceList.remove(selectedService);
                serviceTable.setItems(serviceList);
                serviceTable.refresh();

                showInformationAlert("Đã xóa dịch vụ thành công.");
            }
        }
    }


    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông tin");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSTT.setCellValueFactory(new PropertyValueFactory<>("stt"));
        colMaHang.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colTenHang.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");

        loadDataFromDatabase();
        serviceTable.setItems(serviceList);
    }

    private void loadDataFromDatabase() {
        ResultSet resultSet = Service_DAO.read();
        int i =1;
        String stt = String.format("0%d", i);
         try {
             while (resultSet.next()) {
                 int serviceID = resultSet.getInt("ServiceID");
                 String serviceName = resultSet.getString("ServiceName");
                 double servicePrice = resultSet.getDouble("ServicePrice");
                 ServiceItem serviceItem = new ServiceItem(stt,serviceID, serviceName, servicePrice);
                 serviceList.add(serviceItem);
                 i++;
                 stt = String.format("0%d", i);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         lbTongSo.setText(String.valueOf(i-1));
    }
    private void selectNextRow() {
        int rowCount = serviceTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex < rowCount - 1) {
                selectedIndex++;
            } else {
                selectedIndex = 0;
            }
            selectedRow = serviceTable.getItems().get(selectedIndex);
            serviceTable.getSelectionModel().select(selectedRow);
        }
    }

    private void selectPreviousRow() {
        int rowCount = serviceTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex > 0) {
                selectedIndex--;
            } else {
                selectedIndex = rowCount - 1;
            }
            selectedRow = serviceTable.getItems().get(selectedIndex);
            serviceTable.getSelectionModel().select(selectedRow);
        }
    }

    @FXML
    private void onLenButtonClick() {
        selectPreviousRow();
    }

    @FXML
    private void onXuongButtonClick() {
        selectNextRow();
    }
}

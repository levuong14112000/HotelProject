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
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class BillDeletedController implements Initializable {
    @FXML
    private TableView<ReportItem> billDeletedTable;
    @FXML
    private TableColumn<ReportItem, Integer> colSoBill;
    @FXML
    private TableColumn<ReportItem, Integer> colMaBill;
    @FXML
    private TableColumn<ReportItem, String> colTenPhong;
    @FXML
    private TableColumn<ReportItem, Timestamp> colGioCheckIn;
    @FXML
    private TableColumn<ReportItem, Timestamp> colGioCheckOut;
    @FXML
    private TableColumn<ReportItem, Double> colTriGia;
    public class ReportItem {
        private int paymentID;
        private String maBill;
        private String tenPhong;
        private String gioCheckIn;
        private String gioCheckOut;
        private double triGia;

        public ReportItem(int paymentID, String maBill, String tenPhong, String gioCheckIn, String gioCheckOut, double triGia) {
            this.paymentID =paymentID;
            this.maBill = maBill;
            this.tenPhong = tenPhong;
            this.gioCheckIn = gioCheckIn;
            this.gioCheckOut = gioCheckOut;
            this.triGia = triGia;
        }

        public int getPaymentID() {
            return paymentID;
        }

        public void setPaymentID(int paymentID) {
            this.paymentID = paymentID;
        }

        public String getMaBill() {
            return maBill;
        }

        public void setMaBill(String maBill) {
            this.maBill = maBill;
        }

        public String getTenPhong() {
            return tenPhong;
        }

        public void setTenPhong(String tenPhong) {
            this.tenPhong = tenPhong;
        }

        public String getGioCheckIn() {
            return gioCheckIn;
        }

        public void setGioCheckIn(String gioCheckIn) {
            this.gioCheckIn = gioCheckIn;
        }

        public String getGioCheckOut() {
            return gioCheckOut;
        }

        public void setGioCheckOut(String gioCheckOut) {
            this.gioCheckOut = gioCheckOut;
        }

        public double getTriGia() {
            return triGia;
        }

        public void setTriGia(double triGia) {
            this.triGia = triGia;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet resultSet = RoomPayment_DAO.showBillDeleted();
        ObservableList<ReportItem> reportItems = FXCollections.observableArrayList();
        int stt = 1;
        String maBill = String.format("00%d", stt);
        double tongTien = 0;
        try {
            while (resultSet.next()) {
                int paymentID = resultSet.getInt("PaymentID");
                int roomID = resultSet.getInt("RoomID");
                String tenPhong = Room_DAO.getRoomNumberByRoomID(roomID);
                int checkInID = resultSet.getInt("CheckInID");
                String gioCheckIn = RoomCheckIn_DAO.showCheckInInformationWithID(checkInID);
                int checkOutID = resultSet.getInt("CheckOutID");
                String gioCheckOut = RoomCheckOut_DAO.showCheckOutInformationWithID(checkInID);
                double tienPhong = resultSet.getDouble("RoomCharge");
                double phuThu = resultSet.getDouble("ExtraCharge");
                double giamGia = resultSet.getDouble("Discount");
                double triGia = tienPhong - giamGia + phuThu;


                ReportItem reportItem = new ReportItem(paymentID, maBill, tenPhong, gioCheckIn, gioCheckOut, triGia);
                reportItems.add(reportItem);
                stt++;
                maBill = String.format("00%d", stt);
                tongTien = tongTien + triGia;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colMaBill.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colSoBill.setCellValueFactory(new PropertyValueFactory<>("maBill"));
        colTenPhong.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));
        colGioCheckIn.setCellValueFactory(new PropertyValueFactory<>("gioCheckIn"));
        colGioCheckOut.setCellValueFactory(new PropertyValueFactory<>("gioCheckOut"));
        colTriGia.setCellValueFactory(new PropertyValueFactory<>("triGia"));
        billDeletedTable.setItems(reportItems);

        colSoBill.setStyle("-fx-alignment: CENTER-RIGHT;");
        colMaBill.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTenPhong.setStyle("-fx-alignment: CENTER-RIGHT;");
        colGioCheckIn.setStyle("-fx-alignment: CENTER-RIGHT;");
        colGioCheckOut.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTriGia.setStyle("-fx-alignment: CENTER-RIGHT;");
    }
}

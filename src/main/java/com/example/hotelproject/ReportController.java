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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    private int maHoaDon;
    @FXML
    private TableView<ReportItem> reportTable;
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

    @FXML
    private TableView<ReportDetailItem> reportDetailTable;
    @FXML
    private TableColumn<ReportDetailItem, String> colMatHang;
    @FXML
    private TableColumn<ReportDetailItem, Integer> colSoLuong;
    @FXML
    private TableColumn<ReportDetailItem, Double> colDonGia;
    @FXML
    private TableColumn<ReportDetailItem, Double> colThanhTien;

    @FXML
    private Label tongBillLabel, tongTienLabel;
    private ReportItem selectedRow1;
    private ReportDetailItem selectedRow2;
    private int selectedIndex1, selectedIndex2;
    @FXML
    private void selectNextRow1() {
        int rowCount = reportTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex1 < rowCount - 1) {
                selectedIndex1++;
            } else {
                selectedIndex1 = 0;
            }
            selectedRow1 = reportTable.getItems().get(selectedIndex1);
            reportTable.getSelectionModel().select(selectedRow1);
        }
    }
    @FXML
    private void selectPreviousRow1() {
        int rowCount = reportTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex1 > 0) {
                selectedIndex1--;
            } else {
                selectedIndex1 = rowCount - 1;
            }
            selectedRow1 = reportTable.getItems().get(selectedIndex1);
            reportTable.getSelectionModel().select(selectedRow1);
        }
    }
    @FXML
    private void selectNextRow2() {
        int rowCount = reportDetailTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex2 < rowCount - 1) {
                selectedIndex2++;
            } else {
                selectedIndex2 = 0;
            }
            selectedRow2 = reportDetailTable.getItems().get(selectedIndex2);
            reportDetailTable.getSelectionModel().select(selectedRow2);
        }
    }
    @FXML
    private void selectPreviousRow2() {
        int rowCount = reportDetailTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex2 > 0) {
                selectedIndex2--;
            } else {
                selectedIndex2 = rowCount - 1;
            }
            selectedRow2 = reportDetailTable.getItems().get(selectedIndex2);
            reportDetailTable.getSelectionModel().select(selectedRow2);
        }
    }

    public class ReportItem{
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
    public class ReportDetailItem{
        private String tenMatHang;
        private int soLuong;
        private double donGia;
        private double thanhTien;

        public ReportDetailItem(String tenMatHang, int soLuong, double donGia, double thanhTien) {
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = thanhTien;
        }

        public String getTenMatHang() {
            return tenMatHang;
        }

        public void setTenMatHang(String tenMatHang) {
            this.tenMatHang = tenMatHang;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public double getDonGia() {
            return donGia;
        }

        public void setDonGia(double donGia) {
            this.donGia = donGia;
        }

        public double getThanhTien() {
            return thanhTien;
        }

        public void setThanhTien(double thanhTien) {
            this.thanhTien = thanhTien;
        }
    }

    @FXML
    private void onHuyBillButtonClick() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Xác nhận");
        confirmation.setHeaderText("Bạn có chắc chắn muốn huỷ?");

        if (confirmation.showAndWait().orElse(null) == ButtonType.OK) {
            if (maHoaDon > 0) {
                System.out.println("PaymentID đã chọn: " + maHoaDon);
                RoomPayment_DAO.updatePaymentStatus(maHoaDon, 1);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Không có hàng nào được chọn");
                alert.showAndWait();
            }
        }

    }
    @FXML
    private void onBillDaHuyButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BillDeletedView.fxml"));
            Parent root = loader.load();

            Stage billDeletedStage = new Stage();
            billDeletedStage.initModality(Modality.APPLICATION_MODAL);
            billDeletedStage.setTitle("Bill Deleted View");

            Scene scene = new Scene(root);
            billDeletedStage.setScene(scene);
            billDeletedStage.initStyle(StageStyle.UTILITY);
            billDeletedStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet resultSet = RoomPayment_DAO.read();
        ObservableList<ReportItem> reportItems = FXCollections.observableArrayList();
        int stt = 1;
        String maBill = String.format("0%d", stt);
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
                maBill = String.format("0%d", stt);
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
        reportTable.setItems(reportItems);

        colSoBill.setStyle("-fx-alignment: CENTER-RIGHT;");
        colMaBill.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTenPhong.setStyle("-fx-alignment: CENTER-RIGHT;");
        colGioCheckIn.setStyle("-fx-alignment: CENTER-RIGHT;");
        colGioCheckOut.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTriGia.setStyle("-fx-alignment: CENTER-RIGHT;");

        tongBillLabel.setText(String.valueOf(stt - 1));
        tongTienLabel.setText(String.valueOf(tongTien));


        reportTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                maHoaDon = newValue.getPaymentID();
                System.out.println("mã chọn : " +maHoaDon);
                ResultSet resultSetPayment = RoomPayment_DAO.showRoomPaymentInformationWithID(maHoaDon);
                ObservableList<ReportDetailItem> reportDetailItems = FXCollections.observableArrayList();
                try {
                    if (resultSetPayment.next()) {
                        int roomID = resultSetPayment.getInt("RoomID");
                        System.out.println("RoomID "+roomID);
                        int checkInID = resultSetPayment.getInt("CheckInID");
                        System.out.println("Checkin ID "+checkInID);
                        ResultSet roomServiceRs = RoomService_DAO.getRoomServicesByRoomIDAndCheckInID(roomID,checkInID);
                        try {
                            if (roomServiceRs.next()){
                                int serviceID = roomServiceRs.getInt("ServiceID");
                                System.out.println("Service ID "+ serviceID);
                                String tenMatHang = Service_DAO.getTenMatHangByID(String.valueOf(serviceID));
                                int soLuong = roomServiceRs.getInt("Quantity");
                                double donGia = roomServiceRs.getDouble("ServicePrice");
                                double thanhTien = soLuong * donGia;

                                ReportDetailItem reportDetailItem = new ReportDetailItem(tenMatHang,soLuong,donGia,thanhTien);
                                reportDetailItems.add(reportDetailItem);

                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                colMatHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
                colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
                colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
                colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
                reportDetailTable.setItems(reportDetailItems);

                colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");
                colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");
                colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");
            } else {
                reportDetailTable.getItems().clear();
            }
        });
    }
}

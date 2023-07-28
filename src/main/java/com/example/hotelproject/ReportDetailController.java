//package com.example.hotelproject;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//public class ReportDetailController implements Initializable {
//    @FXML
//    private TableView<ReportDetailItem> reportDetailTable;
//    @FXML
//    private TableColumn<ReportDetailItem, String> colMatHang;
//    @FXML
//    private TableColumn<ReportDetailItem, Integer> colSoLuong;
//    @FXML
//    private TableColumn<ReportDetailItem, Double> colDonGia;
//    @FXML
//    private TableColumn<ReportDetailItem, Double> colThanhTien;
//    private ObservableList<ReportDetailItem> reportDetailItems = FXCollections.observableArrayList();
//    public class ReportDetailItem{
//        private String tenMatHang;
//        private int soLuong;
//        private double donGia;
//        private double thanhTien;
//
//        public ReportDetailItem(String tenMatHang, int soLuong, double donGia, double thanhTien) {
//            this.tenMatHang = tenMatHang;
//            this.soLuong = soLuong;
//            this.donGia = donGia;
//            this.thanhTien = thanhTien;
//        }
//
//        public String getTenMatHang() {
//            return tenMatHang;
//        }
//
//        public void setTenMatHang(String tenMatHang) {
//            this.tenMatHang = tenMatHang;
//        }
//
//        public int getSoLuong() {
//            return soLuong;
//        }
//
//        public void setSoLuong(int soLuong) {
//            this.soLuong = soLuong;
//        }
//
//        public double getDonGia() {
//            return donGia;
//        }
//
//        public void setDonGia(double donGia) {
//            this.donGia = donGia;
//        }
//
//        public double getThanhTien() {
//            return thanhTien;
//        }
//
//        public void setThanhTien(double thanhTien) {
//            this.thanhTien = thanhTien;
//        }
//    }
//    private int paymentID;
//    public void setPaymentID(int paymentID){
//        this.paymentID = paymentID;
//    }
//    public void setPaymentIDAndInitialize(int paymentID) {
//        this.paymentID = paymentID;
//        loadDataReportTable();
//    }
//
//    public void loadDataReportTable(){
//
//        ResultSet resultSet = RoomPayment_DAO.read();
//        try {
//            while (resultSet.next()) {
//                int roomID = resultSet.getInt("RoomID");
//                int checkInID = resultSet.getInt("CheckInID");
//                ResultSet roomServiceRs = RoomService_DAO.getRoomServicesByRoomIDAndCheckInID(roomID,checkInID);
//                try {
//                    while (roomServiceRs.next()){
//                        int serviceID = roomServiceRs.getInt("ServiceID");
//                        String tenMatHang = Service_DAO.getTenMatHangByID(String.valueOf(serviceID));
//                        int soLuong = roomServiceRs.getInt("Quantity");
//                        double donGia = roomServiceRs.getDouble("ServicePrice");
//                        double thanhTien = soLuong * donGia;
//
//                        ReportDetailItem reportDetailItem = new ReportDetailItem(tenMatHang,soLuong,donGia,thanhTien);
//                        reportDetailItems.add(reportDetailItem);
//                    }
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("yyyyy" + paymentID);
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("Mã hoá đơn : " + paymentID);
//        colMatHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
//        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
//        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
//        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
//
//        loadDataReportTable();
//        reportDetailTable.setItems(reportDetailItems);
//
//    }
//}
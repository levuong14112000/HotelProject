//package com.example.hotelproject;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class OrderDetailController implements Initializable {
//    @FXML
//    private TableView<Item> tableView;
//    @FXML
//    private TableColumn<Item, String> colTenMatHang;
//    @FXML
//    private TableColumn<Item, Integer> colSoLuong;
//    @FXML
//    private TableColumn<Item, Double> colDonGia;
//    @FXML
//    private TableColumn<Item, Double> colThanhTien;
//    @FXML
//    private Button btnThoat;
//    @FXML
//    private Button btnLen;
//    @FXML
//    private Button btnXuong;
//
//    private ObservableList<Item> itemList;
//
//    public static class Item {
//        private String tenMatHang;
//        private int soLuong;
//        private double donGia;
//        private double thanhTien;
//
//        public Item(String tenMatHang, int soLuong, double donGia, double thanhTien) {
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
//
//    @FXML
//    private void onThoatButtonClick() {
//        // Xử lý sự kiện khi nút "Thoát" được nhấn
//    }
//
//    @FXML
//    private void onLenButtonClick() {
//        // Xử lý sự kiện khi nút "Lên" được nhấn
//    }
//
//    @FXML
//    private void onXuongButtonClick() {
//        // Xử lý sự kiện khi nút "Xuống" được nhấn
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // Khởi tạo TableView và các cột
//        itemList = FXCollections.observableArrayList();
//        colTenMatHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
//        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
//        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
//        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
//
//        // Cấu hình dữ liệu cho TableView
//        tableView.setItems(itemList);
//    }
//
//    public void setData(List<Item> items) {
//        itemList.clear();
//        itemList.addAll(items);
//    }
//}

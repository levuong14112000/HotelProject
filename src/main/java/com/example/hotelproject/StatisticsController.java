package com.example.hotelproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private Label lbPhongTrong;
    @FXML
    private Label lbCheckIn;
    @FXML
    private Label lbCheckOut;
    @FXML
    private Label lbBooking;
    @FXML
    private Label lbTongBill;
    @FXML
    private Label lbDoanhSo;
    @FXML
    private Label lbChietKhau;
    @FXML
    private Label lbPhuThu;
    @FXML
    private Label lbDoanhThu;
    @FXML
    private Label lbTongSoKhach;
    @FXML
    private Label lbBinhQuanMoiNguoi;
    @FXML
    private Label lbBinhQuanMoiPhong;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int tongBill = RoomPayment_DAO.countBills();
        lbTongBill.setText(String.valueOf(tongBill));
        int phongTrong = Room_DAO.countRoomss();
        lbPhongTrong.setText(String.valueOf(phongTrong));
        int checkIn = RoomCheckIn_DAO.countCheckIns();
        lbCheckIn.setText(String.valueOf(checkIn));
        int checkOut = RoomCheckOut_DAO.countCheckOuts();
        lbCheckOut.setText(String.valueOf(checkOut));
        int booking = RoomBooking_DAO.countBookings();
        lbBooking.setText(String.valueOf(booking));
        double doanhThu = RoomPayment_DAO.sumRoomCharge();
        lbDoanhThu.setText(String.valueOf(doanhThu));
        double phuThu = RoomPayment_DAO.sumExtraCharge();
        lbPhuThu.setText(String.valueOf(phuThu));
        double chietKhau = RoomPayment_DAO.sumDiscount();
        lbChietKhau.setText(String.valueOf(chietKhau));
        double doanhSo = doanhThu + phuThu -chietKhau;
        lbDoanhSo.setText(String.valueOf(doanhSo));
        int tongSoKhach = RoomCheckIn_DAO.sumNOP();
        lbTongSoKhach.setText(String.valueOf(tongSoKhach));
        double binhQuanMoiNguoi = doanhThu / tongSoKhach;
        lbBinhQuanMoiNguoi.setText(String.valueOf(binhQuanMoiNguoi));
        double binhQuanMoiPhong = doanhThu / tongBill;
        lbBinhQuanMoiPhong.setText(String.valueOf(binhQuanMoiPhong));
    }
}

package com.example.hotelproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
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

        double phuThu = RoomPayment_DAO.sumExtraCharge();
        String formattedPhuThu = decimalFormat.format(phuThu);
        lbPhuThu.setText(String.valueOf(formattedPhuThu));

        double chietKhau = RoomPayment_DAO.sumDiscount();
        String formattedChietKhau = decimalFormat.format(chietKhau);
        lbChietKhau.setText(String.valueOf(formattedChietKhau));

        double doanhThu = RoomPayment_DAO.sumRoomCharge() + phuThu -chietKhau;
        String formattedDoanhThu = decimalFormat.format(doanhThu);
        lbDoanhThu.setText(String.valueOf(formattedDoanhThu));

        double doanhSo = doanhThu + phuThu + chietKhau;
        String formattedDoanhSo = decimalFormat.format(doanhSo);
        lbDoanhSo.setText(String.valueOf(formattedDoanhSo));

        int tongSoKhach = RoomCheckIn_DAO.sumNOP();
        lbTongSoKhach.setText(String.valueOf(tongSoKhach));
        double binhQuanMoiNguoi = doanhThu / tongSoKhach;
        String formattedBinhQuanMoiNguoi = decimalFormat.format(binhQuanMoiNguoi);
        lbBinhQuanMoiNguoi.setText(String.valueOf(formattedBinhQuanMoiNguoi));
        double binhQuanMoiPhong = doanhThu / tongBill;
        String formattedBinhQuanMoiPhong = decimalFormat.format(binhQuanMoiPhong);
        lbBinhQuanMoiPhong.setText(String.valueOf(formattedBinhQuanMoiPhong));
    }

    public void PhongTrongActionOnclick(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ttpThongKe.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Danh Sách Phòng Trống");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CheckoutActionOnclick(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOutView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Danh Sách Check Out");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void BookingActionOnclick(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Danh Sách Booking");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void BillActionOnclick(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BaoCaoView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Danh Sách Bill");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CheckinctionOnclick(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckInView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Danh Sách Check In");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.hotelproject;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class RoomPaymentController implements Initializable {
    private String userData;
    private int userId;
    private int customerID;
    private int checkInID;
    private int checkOutID;
    private int roomID;
    private int serviceID;
    private double roomCharge;

    private double convertedAmount;
    private RoomDetailController.Item selectedRow;
    private int selectedIndex;
    private Scene currentScene;
    private Scene previousScene;
    List<RoomDetailController.Item> newItems = new ArrayList<>();
    @FXML
    private Button returnMain;
    @FXML
    private TableView paymentTable;
    @FXML
    private TableColumn<RoomDetailController.Item, String> colTenMatHang;
    @FXML
    private TableColumn<RoomDetailController.Item, Integer> colSoLuong;
    @FXML
    private TableColumn<RoomDetailController.Item, Double> colDonGia;
    @FXML
    private TableColumn<RoomDetailController.Item, Double> colThanhTien;
    @FXML
    private Label txtThanhTien, txtPhuThu,txtGiamGia, txtThanhToan;
    private ObservableList<RoomDetailController.Item> itemList;
    private HashMap<String, ItemPayment> itemQuantityMap = new HashMap<>();

    public class ItemPayment {
        private String tenMatHang;
        private int tongSoLuong;
        private double donGia;
        private double thanhTien;

        public ItemPayment(String tenMatHang, int tongSoLuong, double donGia, double thanhTien) {
            this.tenMatHang = tenMatHang;
            this.tongSoLuong = tongSoLuong;
            this.donGia = donGia;
            this.thanhTien = thanhTien;
        }

        public ItemPayment(String tenMatHang, int soLuong) {
            this.tenMatHang = tenMatHang;
            this.tongSoLuong = soLuong;
        }

        public String getTenMatHang() {
            return tenMatHang;
        }

        public void setTenMatHang(String tenMatHang) {
            this.tenMatHang = tenMatHang;
        }

        public int getTongSoLuong() {
            return tongSoLuong;
        }

        public void setTongSoLuong(int tongSoLuong) {
            this.tongSoLuong = tongSoLuong;
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


    //    public void setPreviousScene(Scene scene) {
//        this.previousScene = scene;
//    }
    public void setTransData(int userId, int checkInID, String userData, Scene scene) {
        this.userId = userId;
        this.checkInID = checkInID;
        this.userData = userData;
        this.previousScene = scene;
    }
    public void setTransDataAndInitialize(int userId, int checkInID, String userData, Scene scene) {
        this.userId = userId;
        this.checkInID = checkInID;
        this.userData = userData;
        this.previousScene = scene;
        initialize(null, null);
    }
    public void setRoomData(ObservableList<RoomDetailController.Item> itemList, String totalAmount) {
        paymentTable.setItems(itemList);

        colTenMatHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("tongSoLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));


        colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");
        colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");
        colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        txtThanhTien.setText(totalAmount);
        convertedAmount = convertAmount(totalAmount);
        txtPhuThu.setText("0");
        txtGiamGia.setText("0");
        txtThanhToan.setText(totalAmount);

        itemQuantityMap.clear();
        for (RoomDetailController.Item item : itemList) {
            String tenMatHang = item.getTenMatHang();
            int soLuong = item.getSoLuong();
            double donGia = item.getDonGia();
            double thanhTien = soLuong * donGia;

            if (itemQuantityMap.containsKey(tenMatHang)) {
                int existingQuantity = itemQuantityMap.get(tenMatHang).getTongSoLuong();
                itemQuantityMap.get(tenMatHang).setTongSoLuong(existingQuantity + soLuong);
                double existingTotalAmount = itemQuantityMap.get(tenMatHang).getThanhTien();
                itemQuantityMap.get(tenMatHang).setThanhTien(existingTotalAmount + thanhTien);
            } else {
                itemQuantityMap.put(tenMatHang, new ItemPayment(tenMatHang, soLuong, donGia, thanhTien));
            }
        }

        ObservableList<ItemPayment> paymentItemList = FXCollections.observableArrayList(itemQuantityMap.values());
        paymentTable.setItems(paymentItemList);
    }

    private void switchToMainScreen() {
        try {
            Scene currentScene = returnMain.getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            currentScene.setRoot(root);
            checkInID =0;
            MainController mainController = loader.getController();
            mainController.initializeWithData(userId, checkInID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void selectNextRow() {
        int rowCount = paymentTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex < rowCount - 1) {
                selectedIndex++;
            } else {
                selectedIndex = 0;
            }
            selectedRow = (RoomDetailController.Item) paymentTable.getItems().get(selectedIndex);
            paymentTable.getSelectionModel().select(selectedRow);
        }
    }
    private void selectPreviousRow() {
        int rowCount = paymentTable.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex > 0) {
                selectedIndex--;
            } else {
                selectedIndex = rowCount - 1;
            }
            selectedRow = (RoomDetailController.Item) paymentTable.getItems().get(selectedIndex);
            paymentTable.getSelectionModel().select(selectedRow);
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


    @FXML
    private void onChietKhauButtonClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nhập phần trăm chiết khấu");
        dialog.setHeaderText(null);
        dialog.setContentText("Nhập phần trăm chiết khấu:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(discountPercentage -> {
            try {
                double discount = Double.parseDouble(discountPercentage);

                if (discount >= 0 && discount <= 100) {
                    double totalAmount = convertedAmount;
                    double discountAmount = totalAmount * (discount / 100);
                    double finalAmount = totalAmount - discountAmount;

                    txtGiamGia.setText(formatNumberWithCommas(discountAmount));
                    txtThanhToan.setText(formatNumberWithCommas(finalAmount));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Phần trăm chiết khấu không hợp lệ. Vui lòng nhập lại.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void onPhuThuButtonClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nhập số tiền phụ thu");
        dialog.setHeaderText(null);
        dialog.setContentText("Nhập số tiền phụ thu:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(phuThuString -> {
            try {
                double phuThu = Double.parseDouble(phuThuString);

                if (phuThu >= 0) {
                    double finalAmount = convertAmount(txtThanhToan.getText()) + phuThu;

                    txtPhuThu.setText(formatNumberWithCommas(phuThu));
                    txtThanhToan.setText(formatNumberWithCommas(finalAmount));

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Số tiền phụ thu không hợp lệ. Vui lòng nhập lại.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void onExportPDFButtonClick() {
        try {
            // Tạo đối tượng Document với kích thước khổ giấy A5
            Document document = new Document(PageSize.A5);

            // Sử dụng font chữ tiếng Việt (times.ttf) từ file
            BaseFont bf = BaseFont.createFont("src/main/resources/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFont = new Font(bf, 12, Font.NORMAL, BaseColor.BLACK);

            // Ghi dữ liệu vào tài liệu PDF
            PdfWriter.getInstance(document, new FileOutputStream("payment_data.pdf"));
            document.open();

            // Tiêu đề của bảng
            Paragraph title = new Paragraph("Dữ liệu thanh toán phòng", vietnameseFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Tạo bảng để xuất dữ liệu từ TableView
            PdfPTable table = new PdfPTable(4); // 4 cột trong TableView
            table.setWidthPercentage(100); // Chiếm toàn bộ trang
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Đặt các tiêu đề cột cho bảng
            PdfPCell cell = new PdfPCell(new Phrase("Tên Mặt Hàng", vietnameseFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Số Lượng", vietnameseFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Đơn Giá", vietnameseFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Thành Tiền", vietnameseFont));
            table.addCell(cell);

            // Lặp qua các dòng trong TableView và thêm dữ liệu vào bảng
            for (Object obj : paymentTable.getItems()) {
                if (obj instanceof ItemPayment) {
                    ItemPayment item = (ItemPayment) obj;
                    table.addCell(new Phrase(item.getTenMatHang(), vietnameseFont));
                    table.addCell(new Phrase(String.valueOf(item.getTongSoLuong()), vietnameseFont));
                    table.addCell(new Phrase(String.valueOf(item.getDonGia()), vietnameseFont));
                    table.addCell(new Phrase(String.valueOf(item.getThanhTien()), vietnameseFont));
                }
            }

            // Thêm bảng vào document
            document.add(table);

            document.close();
            System.out.println("Xuất dữ liệu thành công vào file payment_data.pdf");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double convertAmount(String inputAmount) {
        String formattedString = inputAmount.replace(".", "");

        try {
            // Chuyển đổi chuỗi thành số
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
            double number = decimalFormat.parse(formattedString).doubleValue();
            return number;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    public String formatNumberWithCommas(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');

        decimalFormat.setDecimalFormatSymbols(symbols);

        return decimalFormat.format(number);
    }

    @FXML
    private void onCASHButtonClick(){
        //(int customerID, int roomID, int checkInID, int checkOutID, double roomCharge, double extraCharge, double discount, int userID)
        customerID = RoomCheckIn_DAO.geCustomerIDWithCheckInID(checkInID);
        roomID = Integer.parseInt(userData);
        checkOutID = RoomCheckOut_DAO.getCheckOutIDWithCheckInID(checkInID);

        roomCharge = convertAmount(txtThanhTien.getText());
        double extraCharge = convertAmount(txtPhuThu.getText());
        double discount = convertAmount(txtGiamGia.getText());

        RoomPayment_DAO.createRoomPayment(customerID, roomID, checkInID, checkOutID, roomCharge, extraCharge, discount, userId);
        onExportPDFButtonClick();
        Room_DAO.updateRoomStatus(roomID, 0);
        switchToMainScreen();
    }
    @FXML
    private void onTroVeTruocButtonClick() {
        if (previousScene != null) {
            Stage currentStage = (Stage) returnMain.getScene().getWindow();
            currentStage.setScene(previousScene);
        } else {
            System.out.println("Không có giao diện trước đó");
        }
    }
    @FXML
    private void onTroVeSoDoPhongButtonClick(){
        switchToMainScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize RoomDetail \nUser " +userId+"\nCheckInID " +checkInID +"\nUserData " +userData);
    }

}

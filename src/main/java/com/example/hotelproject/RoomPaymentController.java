package com.example.hotelproject;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
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
    private Button returnMain, btnTroVeTruoc;
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
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
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
    private void onExportPDFButtonClick(double roomCharge, double extraCharge, double discount) {
        try {
            // Tạo đối tượng Document với kích thước khổ giấy A5
            Document document = new Document(PageSize.A6);

            // Sử dụng font chữ tiếng Việt (times.ttf) từ file
            BaseFont bf = BaseFont.createFont("src/main/resources/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFont = new Font(bf, 7, Font.NORMAL, BaseColor.BLACK);
            Font titleFont = new Font(bf, 12, Font.BOLD, BaseColor.BLACK);
            Font baseItalicFont = new Font(bf, 7,Font.ITALIC, BaseColor.BLACK);
            Font baseBoldFont = new Font(bf,7, Font.BOLD, BaseColor.BLACK);

            // Ghi dữ liệu vào tài liệu PDF
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("payment_data.pdf"));
            document.open();

            // Tiêu đề của bảng
            Paragraph header = new Paragraph("PORT COLN Hotel\n", titleFont);
            header.setAlignment(Element.ALIGN_LEFT);
            document.add(header);
            Paragraph address = new Paragraph("Địa chỉ: 01 Lê Duẩn - Phường Bến Nghé - Quận 1 - TPHCM", baseItalicFont);
            address.setAlignment(Element.ALIGN_LEFT);
            document.add(address);
            Paragraph phone = new Paragraph("Điện thoại: (083) 3456 789\n\n", baseItalicFont);
            phone.setAlignment(Element.ALIGN_LEFT);
            document.add(phone);
            Paragraph title = new Paragraph("HOÁ ĐƠN THANH TOÁN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(BaseColor.BLACK);
            lineSeparator.setLineWidth(1f);
            Chunk lineChunk = new Chunk(lineSeparator);
            document.add(lineChunk);

            int paymentID = RoomPayment_DAO.showIDWithCheckInID(checkInID);
            Paragraph id = new Paragraph("Mã hoá đơn : " + paymentID, baseBoldFont);
            document.add(id);

            String checkInStr = RoomCheckIn_DAO.showCheckInInformationWithID(checkInID);
            Paragraph checkInTime = new Paragraph("Check in : " +checkInStr, vietnameseFont);
            document.add(checkInTime);

            String checkOutStr = RoomCheckOut_DAO.showCheckOutInformationWithID(checkInID);
            Paragraph checkOutTime = new Paragraph("Check out : " +checkOutStr, vietnameseFont);
            document.add(checkOutTime);

            String name = User_DAO.showNameByUserID(userId);
            Paragraph empoyee = new Paragraph("Nhân viên : " + name, vietnameseFont);
            document.add(empoyee);

            // Tạo bảng để xuất dữ liệu từ TableView
            PdfPTable table = new PdfPTable(4); // 4 cột trong TableView
            table.setWidthPercentage(100); // Chiếm toàn bộ trang
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Đặt các tiêu đề cột cho bảng
            PdfPCell cell = new PdfPCell(new Phrase("Tên dịch vụ", baseBoldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Số lượng", baseBoldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Đơn giá", baseBoldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Thành tiền", baseBoldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Lặp qua các dòng trong TableView và thêm dữ liệu vào bảng
            for (Object obj : paymentTable.getItems()) {
                if (obj instanceof ItemPayment) {
                    ItemPayment item = (ItemPayment) obj;

                    table.addCell(new Phrase(item.getTenMatHang(), vietnameseFont));
                    PdfPCell cellSoLuong = new PdfPCell(new Phrase(String.valueOf(item.getTongSoLuong()), vietnameseFont));
                    cellSoLuong.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(cellSoLuong);

                    PdfPCell cellDonGia = new PdfPCell(new Phrase(String.valueOf(item.getDonGia()), vietnameseFont));
                    cellDonGia.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(cellDonGia);

                    PdfPCell cellThanhTien = new PdfPCell(new Phrase(String.valueOf(item.getThanhTien()), vietnameseFont));
                    cellThanhTien.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(cellThanhTien);
                }
            }
            document.add(table);

//            Paragraph tongCong = new Paragraph("Tổng cộng :\t\t\t" + roomCharge, baseBoldFont);
//            tongCong.setAlignment(Element.ALIGN_RIGHT);
//            document.add(tongCong);
//            Paragraph chietKhau = new Paragraph("Chiết khấu :\t\t\t" + discount, baseBoldFont);
//            chietKhau.setAlignment(Element.ALIGN_RIGHT);
//            document.add(chietKhau);

            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(60);
            PdfPCell cellCenter2 = new PdfPCell();
            Paragraph centerParagraph2 = new Paragraph("\t\t\tTổng cộng : ", baseBoldFont);
            centerParagraph2.setAlignment(Element.ALIGN_LEFT);
            cellCenter2.addElement(centerParagraph2);
            cellCenter2.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight2 = new PdfPCell();
            Paragraph rightParagraph2 = new Paragraph(String.valueOf(roomCharge), baseBoldFont);
            rightParagraph2.setAlignment(Element.ALIGN_RIGHT);
            cellRight2.addElement(rightParagraph2);
            cellRight2.setBorder(Rectangle.NO_BORDER);

            PdfPCell cellCenter3 = new PdfPCell();
            Paragraph centerParagraph3 = new Paragraph("\t\t\tChiết khấu : ", baseBoldFont);
            centerParagraph3.setAlignment(Element.ALIGN_LEFT);
            cellCenter3.addElement(centerParagraph3);
            cellCenter3.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight3 = new PdfPCell();
            Paragraph rightParagraph3 = new Paragraph(String.valueOf(discount), baseBoldFont);
            rightParagraph3.setAlignment(Element.ALIGN_RIGHT);
            cellRight3.addElement(rightParagraph3);
            cellRight3.setBorder(Rectangle.NO_BORDER);

            PdfPCell cellCenter4 = new PdfPCell();
            Paragraph centerParagraph4 = new Paragraph("\t\t\tPhụ thu : ", baseBoldFont);
            centerParagraph4.setAlignment(Element.ALIGN_LEFT);
            cellCenter4.addElement(centerParagraph4);
            cellCenter4.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight4 = new PdfPCell();
            Paragraph rightParagraph4 = new Paragraph(String.valueOf(extraCharge), baseBoldFont);
            rightParagraph4.setAlignment(Element.ALIGN_RIGHT);
            cellRight4.addElement(rightParagraph4);
            cellRight4.setBorder(Rectangle.NO_BORDER);

            double total = roomCharge - discount + extraCharge;
            PdfPCell cellCenter5 = new PdfPCell();
            Paragraph centerParagraph5 = new Paragraph("\t\t\tThanh toán : ", baseBoldFont);
            centerParagraph5.setAlignment(Element.ALIGN_LEFT);
            cellCenter5.addElement(centerParagraph5);
            cellCenter5.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight5 = new PdfPCell();
            Paragraph rightParagraph5 = new Paragraph(String.valueOf(total), baseBoldFont);
            rightParagraph5.setAlignment(Element.ALIGN_RIGHT);
            cellRight5.addElement(rightParagraph5);
            cellRight5.setBorder(Rectangle.NO_BORDER);

            table2.addCell(cellCenter2);
            table2.addCell(cellRight2);
            table2.addCell(cellCenter3);
            table2.addCell(cellRight3);
            table2.addCell(cellCenter4);
            table2.addCell(cellRight4);
            table2.addCell(cellCenter5);
            table2.addCell(cellRight5);

            document.add(table2);
            table2.setSpacingAfter(0f);

            Paragraph thanks = new Paragraph("\n\n\nCảm ơn và hẹn gặp lại quý khách !", baseItalicFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);

            document.close();
            System.out.println("Xuất dữ liệu thành công vào file payment_data.pdf");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    private void openPDFFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("File not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        onExportPDFButtonClick(roomCharge, extraCharge, discount);
        openPDFFile("payment_data.pdf");
        Room_DAO.updateRoomStatus(roomID, 0);
        switchToMainScreen();
    }
    @FXML
    private void onTroVeTruocButtonClick() {
        System.out.println("Đẫ click");
        System.out.println(previousScene);
        if (previousScene != null) {
            Stage currentStage = (Stage) btnTroVeTruoc.getScene().getWindow();
            currentStage.setScene(previousScene);
            System.out.println("Không null");
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

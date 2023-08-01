package com.example.hotelproject;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.awt.Desktop;

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
    private int userID;

    public void setUserID(int userID){
        this.userID = userID;
    }
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
    public static long tinhSoDem(int checkInID) {
        String checkInTime = RoomCheckIn_DAO.showCheckInInformationWithID(checkInID);
        String checkOutTime = RoomCheckOut_DAO.showCheckOutInformationWithID(checkInID);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date startDate = dateFormat.parse(checkInTime);
            Date endDate = dateFormat.parse(checkOutTime);

            Calendar calStart = Calendar.getInstance();
            calStart.setTime(startDate);
            calStart.set(Calendar.HOUR_OF_DAY, 0);
            calStart.set(Calendar.MINUTE, 0);
            calStart.set(Calendar.SECOND, 0);
            calStart.set(Calendar.MILLISECOND, 0);

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.SECOND, 0);
            calEnd.set(Calendar.MILLISECOND, 0);

            if (calEnd.before(calStart) || calEnd.equals(calStart)) {
                return 1;
            } else {
                long timeDifference = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
                long numberOfNights = timeDifference / 86400000;
                return numberOfNights;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
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
    private void onInLaiButtonClick() {
        ReportItem selectedReportItem = reportTable.getSelectionModel().getSelectedItem();
        if (selectedReportItem == null) {
            return;
        }
        exportPDF(selectedReportItem);
        openPDFFile("payment_data.pdf");

    }
    private void exportPDF(ReportItem reportItem) {
        try {
            Document document = new Document(PageSize.A6);

            BaseFont bf = BaseFont.createFont("src/main/resources/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font vietnameseFont = new Font(bf, 7, Font.NORMAL, BaseColor.BLACK);
            Font titleFont = new Font(bf, 12, Font.BOLD, BaseColor.BLACK);
            Font baseItalicFont = new Font(bf, 7, Font.ITALIC, BaseColor.BLACK);
            Font baseBoldFont = new Font(bf, 7, Font.BOLD, BaseColor.BLACK);

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

            Paragraph id = new Paragraph("Mã hoá đơn : " + maHoaDon, baseBoldFont);
            document.add(id);

            Paragraph checkInTime = new Paragraph("Check in : " +reportItem.getGioCheckIn(), vietnameseFont);
            document.add(checkInTime);

            Paragraph checkOutTime = new Paragraph("Check out : " +reportItem.getGioCheckOut(), vietnameseFont);
            document.add(checkOutTime);

            String name = User_DAO.showNameByUserID(userID);
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

            double chietKhauValue = 0;
            double phuThuValue = 0;
            double tongTien = 0;

            // Lặp qua các dòng trong TableView và thêm dữ liệu vào bảng
            for (Object obj : reportDetailTable.getItems()) {
                if (obj instanceof ReportController.ReportDetailItem) {
                    ReportController.ReportDetailItem item = (ReportController.ReportDetailItem) obj;

                    if (item.getTenMatHang().equals("Chiết khấu")) {
                        chietKhauValue = item.getThanhTien();
                    }else if (item.getTenMatHang().equals("Phụ thu")){
                        phuThuValue = item.getThanhTien();
                    } else if (!item.getTenMatHang().equals("Chiết khấu") && !item.getTenMatHang().equals("Phụ thu")) {
                        table.addCell(new Phrase(item.getTenMatHang(), vietnameseFont));
                        PdfPCell cellSoLuong = new PdfPCell(new Phrase(String.valueOf(item.getSoLuong()), vietnameseFont));
                        cellSoLuong.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellSoLuong);

                        PdfPCell cellDonGia = new PdfPCell(new Phrase(String.valueOf(item.getDonGia()), vietnameseFont));
                        cellDonGia.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellDonGia);

                        PdfPCell cellThanhTien = new PdfPCell(new Phrase(String.valueOf(item.getThanhTien()), vietnameseFont));
                        cellThanhTien.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cellThanhTien);
                        tongTien += item.getThanhTien();
                    }
                }
            }

            document.add(table);

            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(60);
            PdfPCell cellCenter2 = new PdfPCell();
            Paragraph centerParagraph2 = new Paragraph("\t\t\tTổng cộng : ", baseBoldFont);
            centerParagraph2.setAlignment(Element.ALIGN_LEFT);
            cellCenter2.addElement(centerParagraph2);
            cellCenter2.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight2 = new PdfPCell();
            Paragraph rightParagraph2 = new Paragraph(String.valueOf(tongTien), baseBoldFont);
            rightParagraph2.setAlignment(Element.ALIGN_RIGHT);
            cellRight2.addElement(rightParagraph2);
            cellRight2.setBorder(Rectangle.NO_BORDER);

            PdfPCell cellCenter3 = new PdfPCell();
            Paragraph centerParagraph3 = new Paragraph("\t\t\tChiết khấu : ", baseBoldFont);
            centerParagraph3.setAlignment(Element.ALIGN_LEFT);
            cellCenter3.addElement(centerParagraph3);
            cellCenter3.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight3 = new PdfPCell();
            Paragraph rightParagraph3 = new Paragraph(String.valueOf(chietKhauValue*-1), baseBoldFont);
            rightParagraph3.setAlignment(Element.ALIGN_RIGHT);
            cellRight3.addElement(rightParagraph3);
            cellRight3.setBorder(Rectangle.NO_BORDER);

            PdfPCell cellCenter4 = new PdfPCell();
            Paragraph centerParagraph4 = new Paragraph("\t\t\tPhụ thu : ", baseBoldFont);
            centerParagraph4.setAlignment(Element.ALIGN_LEFT);
            cellCenter4.addElement(centerParagraph4);
            cellCenter4.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight4 = new PdfPCell();
            Paragraph rightParagraph4 = new Paragraph(String.valueOf(phuThuValue), baseBoldFont);
            rightParagraph4.setAlignment(Element.ALIGN_RIGHT);
            cellRight4.addElement(rightParagraph4);
            cellRight4.setBorder(Rectangle.NO_BORDER);

            double thanhToan = tongTien - chietKhauValue*-1 + phuThuValue;
            PdfPCell cellCenter5 = new PdfPCell();
            Paragraph centerParagraph5 = new Paragraph("\t\t\tThanh toán : ", baseBoldFont);
            centerParagraph5.setAlignment(Element.ALIGN_LEFT);
            cellCenter5.addElement(centerParagraph5);
            cellCenter5.setBorder(Rectangle.NO_BORDER);
            PdfPCell cellRight5 = new PdfPCell();
            Paragraph rightParagraph5 = new Paragraph(String.valueOf(thanhToan), baseBoldFont);
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        //Table Bill detail
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

                            int soDem = (int) tinhSoDem(checkInID);
                            //Lấy đơn giá phòng
                            double donGiaPhong = RoomType_DAO.getBasePriceByRoomID(roomID);
                            //Tiền phòng
                            double tienPhong = soDem * donGiaPhong;

                            ReportDetailItem reportDetailItem = new ReportDetailItem("Số đêm", soDem, donGiaPhong, tienPhong);
                            reportDetailItems.add(reportDetailItem);

                            double chietKhau = resultSetPayment.getDouble("Discount");
                            if (chietKhau > 0){
                                ReportDetailItem reportDetailItemChietKhau = new ReportDetailItem("Chiết khấu", 1, chietKhau*-1, chietKhau*-1);
                                reportDetailItems.add(reportDetailItemChietKhau);
                            }
                            double phuThu = resultSetPayment.getDouble("ExtraCharge");
                            if (phuThu > 0){
                                ReportDetailItem reportDetailItemGiamGia = new ReportDetailItem("Phụ thu", 1, phuThu, phuThu);
                                reportDetailItems.add(reportDetailItemGiamGia);
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

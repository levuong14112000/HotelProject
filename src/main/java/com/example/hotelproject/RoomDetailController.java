package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RoomDetailController implements Initializable {
    @FXML
    private Label roomNumber, tongTienLabel;
    @FXML
    private ComboBox<String> fTenMatHang;
    @FXML
    private TextField fSoLuong;
    @FXML
    private TableView<Item> tableViewRoomDetail;
    @FXML
    private TableColumn<Item, String> colTenMatHang;
    @FXML
    private TableColumn<Item, Integer> colSoLuong;
    @FXML
    private TableColumn<Item, Double> colDonGia;
    @FXML
    private TableColumn<Item, Double> colThanhTien;
    private int roomID;
    private int serviceID;
    private int checkInID;
    List<Item> newItems = new ArrayList<>();
    private String userData;
    private int userId;
    private Item selectedRow;
    private int selectedIndex;
    private StackPane rightPane;
    private ObservableList<RoomDetailController.Item> itemList;

    public ObservableList<RoomDetailController.Item> getItemList() {
        return itemList;
    }


    public static class Item {
        private String tenMatHang;
        private int soLuong;
        private double donGia;
        private double thanhTien;
        private int soLanLuu;
        private String serialNumber;


        public Item(String tenMatHang, int soLuong, double donGia, double thanhTien) {
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = thanhTien;
        }

        public Item(String tenMatHang, int soLuong, double donGia) {
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
        }

        public Item(String serialNumber, String tenMatHang, int soLuong, double donGia, double thanhTien) {
            this.serialNumber = serialNumber;
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = thanhTien;
        }

        public Item(String serialNumber, String tenMatHang, int soLuong, double donGia) {
            this.serialNumber = serialNumber;
            this.tenMatHang = tenMatHang;
            this.soLuong = soLuong;
            this.donGia = donGia;
        }


        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
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
    }

    public void setRightPane(StackPane rightPane) {
        this.rightPane = rightPane;
    }

    public void setRightPaneAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    //    public void setCheckInID(int checkInID) {
//        this.checkInID = checkInID;
//    }
//    public void setCheckInIDAndInitialize(int checkInID) {
//        this.checkInID = checkInID;
//        initialize(null, null);
//    }
    public void setDataAndInitialize(String userData) {
        this.userData = userData;
        initialize(null, null);
    }

    public void setTransData(int userId, int checkInID) {
        this.userId = userId;
        this.checkInID = checkInID;
    }

    public void setTransDataAndInitialize(int userId, int checkInID) {
        this.userId = userId;
        this.checkInID = checkInID;
        initialize(null, null);
    }

    private void switchToMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.initializeWithData(userId, checkInID);
//            mainController.setCheckInID(checkInID);
//            mainController.setCheckInIDAndInitialize(checkInID);

//            System.out.println("roomdetail, trước khi qua main " +checkInID);


            Scene scene = new Scene(root);
            Stage stage = (Stage) roomNumber.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onThemButtonClick() {
        String tenMatHang = fTenMatHang.getValue();
        String soLuongText = fSoLuong.getText();

        if (tenMatHang.isEmpty()) {
            showErrorMessage("Vui lòng chọn mặt hàng !");
            return;
        }
        if (soLuongText.isEmpty()) {
            showErrorMessage("Vui lòng nhập số lượng !");
            return;
        }

        try {
            int soLuong = Integer.parseInt(soLuongText);
            double donGia = Service_DAO.getDonGiaByTenMatHang(tenMatHang);
            double thanhTien = soLuong * donGia;

            Item item = new Item(tenMatHang, soLuong, donGia, thanhTien);
            itemList.add(item);

            fTenMatHang.getSelectionModel().clearSelection();
            fSoLuong.clear();
            String totalAmount = calculateTotalAmount();
            tongTienLabel.setText(String.valueOf(totalAmount) + "   VND");
        } catch (SQLException e) {
            System.out.println("Kết nối dữ liệu thất bại");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            showErrorMessage("Số lượng phải là số !");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onXoaButtonClick() {
        Item selectedItem = tableViewRoomDetail.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemList.remove(selectedItem);
        }
    }

    @FXML
    private void onThoatButtonClick() {
        switchToMainScreen();
    }

    @FXML
    private void onLuuButtonClick() throws SQLException {
        // Lưu thông tin từ itemList vào bảng RoomServices

        for (Item item : itemList) {
            String tenMatHang = item.getTenMatHang();
            serviceID = (int) Service_DAO.getIDByTenMatHang(item.getTenMatHang());
            int soLuong = item.getSoLuong();
            double donGia = item.getDonGia();

            if (!RoomService_DAO.isRoomServiceExist(roomID, serviceID, tenMatHang)) {
                newItems.add(new Item(tenMatHang, soLuong, donGia));
            }
        }

        for (Item newItem : newItems) {
            String tenMatHang = newItem.getTenMatHang();
            serviceID = (int) Service_DAO.getIDByTenMatHang(newItem.getTenMatHang());
            int soLuong = newItem.getSoLuong();
            double donGia = newItem.getDonGia();

            // Tạo đối tượng RoomService và lưu thông tin vào cơ sở dữ liệu
            RoomService roomService = new RoomService(roomID, checkInID, serviceID, soLuong, donGia, userId);
            System.out.println("test" + userId);
            RoomService_DAO.saveRoomService(roomService);
        }

        // Xóa dữ liệu cũ trong cơ sở dữ liệu
        RoomService_DAO.deleteRoomServicesByRoomID(roomID);

        // Lưu dữ liệu mới từ itemList vào cơ sở dữ liệu
        for (Item item : itemList) {
            String tenMatHang = item.getTenMatHang();
            serviceID = (int) Service_DAO.getIDByTenMatHang(item.getTenMatHang());
            int soLuong = item.getSoLuong();
            double donGia = item.getDonGia();

            // Tạo đối tượng RoomService và lưu thông tin vào cơ sở dữ liệu
            RoomService roomService = new RoomService(roomID, checkInID, serviceID, soLuong, donGia, userId);
            RoomService_DAO.saveRoomService(roomService);
        }

        loadRoomData();
        switchToMainScreen();
    }


    public void showDataRoomDetail() {
        // Truyền số phòng
        try {
            ResultSet resultSet = Room_DAO.showRoomInformationWithRoomID(userData);
            if (resultSet.next()) {
                String soPhong = resultSet.getString("RoomNumber");
                int maPhong = resultSet.getInt("RoomID");
                roomNumber.setText(soPhong);
                roomID = maPhong;
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Truyền Tên mặt hàng/Services
        try {
            ResultSet resultSet = Service_DAO.read();
            ObservableList<String> tenMatHangList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String tenMatHang = resultSet.getString("ServiceName");
                tenMatHangList.add(tenMatHang);
                fTenMatHang.setItems(tenMatHangList);
            }
            fTenMatHang.setItems(tenMatHangList);
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Khởi tạo TableView và các cột
        itemList = FXCollections.observableArrayList();
        colTenMatHang.setCellValueFactory(new PropertyValueFactory<>("tenMatHang"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        tableViewRoomDetail.setItems(itemList);
        // Căn phải các cột
        colSoLuong.setStyle("-fx-alignment: CENTER-RIGHT;");
        colDonGia.setStyle("-fx-alignment: CENTER-RIGHT;");
        colThanhTien.setStyle("-fx-alignment: CENTER-RIGHT;");

        itemList = FXCollections.observableArrayList();
        tableViewRoomDetail.setItems(itemList);
    }

    private void loadRoomData() {
        try {
            ResultSet resultSet = Room_DAO.showRoomInformationWithRoomID(userData);
            if (resultSet.next()) {
                // Cập nhật thông tin phòng từ cơ sở dữ liệu
                String soPhong = resultSet.getString("RoomNumber");
                int maPhong = resultSet.getInt("RoomID");
                roomNumber.setText(soPhong);
                roomID = maPhong;

                // Hiển thị thông tin mặt hàng đã lưu
                itemList.clear();
                ResultSet roomServiceResultSet = RoomService_DAO.getRoomServicesByRoomIDAndCheckInID(roomID, checkInID);
                while (roomServiceResultSet.next()) {
                    String serviceIDD = roomServiceResultSet.getString("ServiceID");//
                    String serviceNameResultSet = Service_DAO.getTenMatHangByID(serviceIDD);

                    int soLuong = roomServiceResultSet.getInt("Quantity");
                    double donGia = roomServiceResultSet.getDouble("ServicePrice");
                    double thanhTien = soLuong * donGia;
                    itemList.add(new Item(serviceNameResultSet, soLuong, donGia, thanhTien));
                }
            }
        } catch (SQLException e) {
            System.out.println("Kết nối dữ liệu thất bại.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String calculateTotalAmount() {
        double totalAmount = 0;
        for (Item item : itemList) {
            totalAmount += item.getThanhTien();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedTotalAmount = decimalFormat.format(totalAmount);

        return formattedTotalAmount;
    }


    //Button lên/xuoonsg
    private void selectNextRow() {
        int rowCount = tableViewRoomDetail.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex < rowCount - 1) {
                selectedIndex++;
            } else {
                selectedIndex = 0;
            }
            selectedRow = tableViewRoomDetail.getItems().get(selectedIndex);
            tableViewRoomDetail.getSelectionModel().select(selectedRow);
        }
    }

    private void selectPreviousRow() {
        int rowCount = tableViewRoomDetail.getItems().size();
        if (rowCount > 0) {
            if (selectedIndex > 0) {
                selectedIndex--;
            } else {
                selectedIndex = rowCount - 1;
            }
            selectedRow = tableViewRoomDetail.getItems().get(selectedIndex);
            tableViewRoomDetail.getSelectionModel().select(selectedRow);
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
    private void onThanhToanButtonClick() {

        try {
            RoomCheckOut roomCheckOut = new RoomCheckOut();
            RoomCheckOut_DAO.createRoomCheckOut(roomCheckOut, checkInID, userId, roomID);

            //Tính số ngày qua đêm và truyền qua paymentTable
            int soDem = (int) tinhSoDem(checkInID);
            //Lấy đơn giá phòng
            double donGiaPhong = RoomType_DAO.getBasePriceByRoomID(roomID);
            //Tiền phòng
            double tienPhong = soDem * donGiaPhong;

            RoomDetailController.Item newItem = new RoomDetailController.Item("Số đêm", soDem, donGiaPhong, tienPhong);
            itemList.add(newItem);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomPaymentView.fxml"));
            Parent roomPaymentView = loader.load();

            Scene currentScene = roomNumber.getScene();
            RoomPaymentController roomPaymentController = loader.getController();
            roomPaymentController.setTransData(userId, checkInID, userData, currentScene);
            roomPaymentController.setTransDataAndInitialize(userId, checkInID, userData, currentScene);
            roomPaymentController.setRoomData(itemList, calculateTotalAmount());

            currentScene.setRoot(roomPaymentView);
            // Ẩn cửa sổ chính (Main)
            // mainStage.hide();


        } catch (IOException e) {
            e.printStackTrace();
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDataRoomDetail();
        loadRoomData();
        String totalAmount = calculateTotalAmount();
        tongTienLabel.setText(String.valueOf(totalAmount) + "   VND");

        selectedRow = null;
        selectedIndex = -1;

        System.out.println("Initialize RoomDetail \nUser " + userId + "\nCheckInID " + checkInID + "\nUserData " + userData);

    }
}

package com.example.hotelproject;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private StackPane rightPane;
    @FXML
    private Label fullNameLabel, sysTime;
    private GridPane gridPane = new GridPane();
    private int userId;
    private int checkInID;
    public void setCheckInID(int checkInID) {
        this.checkInID = checkInID;
    }
    public void setCheckInIDAndInitialize(int checkInID) {
        this.checkInID = checkInID;
        initialize(null, null);
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserIDAndInitialize(int userId) {
        this.userId = userId;
        initialize(null, null);
    }
    public void initializeWithData(int userId, int checkInID) {
        setUserId(userId);
        setUserIDAndInitialize(userId);
        setCheckInID(checkInID);
        setCheckInIDAndInitialize(checkInID);
    }
    private void updateSysTime() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        sysTime.setText(formattedDateTime);
    }

    private void openRoom(String userData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenRoom.fxml"));
            Parent root = loader.load();

            OpenRoomController openRoomController = loader.getController();
            openRoomController.setUserData(userData);
            openRoomController.setDataAndInitialize(userData);
            openRoomController.setRightPane(rightPane);
            openRoomController.setUserId(userId);
            openRoomController.setUserIDAndInitialize(userId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openRoomDetail(String userData){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomDetailView.fxml"));
            Parent roomDetail = loader.load();

            RoomDetailController roomDetailController = loader.getController();
            roomDetailController.setUserData(userData);
            roomDetailController.setDataAndInitialize(userData);
            roomDetailController.setTransData(userId, checkInID);
            roomDetailController.setTransDataAndInitialize(userId, checkInID);

            rightPane.getChildren().setAll(roomDetail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPhongButtonClick() {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(gridPane);

        gridPane.setVgap(100);
        gridPane.setHgap(115);
        double newButtonWidth = 150;
        double newButtonHeight = 80;
        Font timesNewRomanFont = new Font("Times New Roman", 24);
//        Scene scene = sysTime.getScene();
//        scene.getStylesheets().add(getClass().getResource("CSS.css").toExternalForm());

        try {
            int columnCount = 5;
            int rowCount = 0;
            int columnIndex = 0;
            int rowIndex = 0;

            ResultSet resultSet = Room_DAO.read();
            while (resultSet.next()) {
                String tenPhong = resultSet.getString("RoomNumber");
                String id = resultSet.getString("RoomID");
                int status = resultSet.getInt("Status");
                Button button = new Button(tenPhong);
                if (status > 0){
                    button.setStyle("-fx-background-color: #FF0000");
                }
                if (status < 0){
                    button.setStyle("-fx-background-color: #FF9900");
                }
                button.setPrefWidth(newButtonWidth);
                button.setPrefHeight(newButtonHeight);
                button.setFont(timesNewRomanFont);
                // Chú ý: Trích dẫn tên của kiểu CSS bằng dấu nháy kép "roomsbookingbutton"
                // Đảm bảo bạn đã khai báo tên "roomsbookingbutton" trong file CSS.css
//                if (checkInID < 0) {
//                    button.getStyleClass().add("roomsbookingbutton");
//                }
                button.setOnAction(event -> {
                    System.out.println("Đã nhấn vào phòng: " + tenPhong);
                    System.out.println(id);
                    if (status > 0) {
                        checkInID = status;
                        openRoomDetail(id);
                    } else {
                        openRoom(id);
                    }
                });

                GridPane.setHalignment(button, HPos.CENTER);
                gridPane.add(button, columnIndex, rowIndex);

                columnIndex++;
                if (columnIndex == columnCount) {
                    columnIndex = 0;
                    rowIndex++;
                    rowCount++;
                }
            }
            gridPane.setPadding(new Insets(30, 0, 0, 30));
        } catch (SQLException e) {
            System.out.println("Failed to fetch data from the database.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void onHangHoaButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent qlsp = FXMLLoader.load(getClass().getResource("ServiceView.fxml"));
            rightPane.getChildren().setAll(qlsp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDanhSachPhongButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent qlp = FXMLLoader.load(getClass().getResource("ttp.fxml"));
            rightPane.getChildren().setAll(qlp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onLoaiPhongButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent Roomtype = FXMLLoader.load(getClass().getResource("RoomTypeView.fxml"));
            rightPane.getChildren().setAll(Roomtype);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNhanVienButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent qlnv = FXMLLoader.load(getClass().getResource("qlnv.fxml"));
            rightPane.getChildren().setAll(qlnv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBaoCaoButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent baocaoView = FXMLLoader.load(getClass().getResource("BaoCaoView.fxml"));
            rightPane.getChildren().setAll(baocaoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onBookingButtonClick() {
        rightPane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingView.fxml"));
            Parent roomBooking = loader.load();

            RoomBookingController roomBookingController = loader.getController();
            roomBookingController.setUserId(userId);
            roomBookingController.setUserIDAndInitialize(userId);

            rightPane.getChildren().setAll(roomBooking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CustomerButtonOnClick() {
        rightPane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerview.fxml"));
            Parent customer = loader.load();

//            RoomBookingController roomBookingController = loader.getController();
//            roomBookingController.setUserId(userId);
//            roomBookingController.setUserIDAndInitialize(userId);

            rightPane.getChildren().setAll(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onThongKeButtonClick() {
        rightPane.getChildren().clear();
        try {
            Parent statisticsView = FXMLLoader.load(getClass().getResource("StatisticsView.fxml"));
            rightPane.getChildren().setAll(statisticsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDoiMatKhauButtonClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Đổi mật khẩu");
        dialog.setHeaderText("Nhập mật khẩu cũ và mật khẩu mới:");

        PasswordField oldPasswordField = new PasswordField();
        PasswordField newPasswordField = new PasswordField();

        oldPasswordField.setPromptText("Mật khẩu cũ");
        newPasswordField.setPromptText("Mật khẩu mới");

        GridPane grid = new GridPane();
        grid.add(new Label("Mật khẩu cũ:"), 0, 0);
        grid.add(oldPasswordField, 1, 0);
        grid.add(new Label("Mật khẩu mới:"), 0, 1);
        grid.add(newPasswordField, 1, 1);
        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        // Kiểm tra kết quả khi người dùng ấn OK
        result.ifPresent(newPassword -> {
            String oldPassword = oldPasswordField.getText();

        });
    }

    @FXML
    private void onThoatButtonClick() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Xác nhận");
        confirmation.setHeaderText("Bạn có chắc chắn muốn đóng ứng dụng không?");

        if (confirmation.showAndWait().orElse(null) == ButtonType.OK) {
            Stage stage = (Stage) rightPane.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onPhongButtonClick();

        try {
            ResultSet resultSet = User_DAO.getNameByUserID(userId);
            while (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                fullNameLabel.setText(fullName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        updateSysTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateSysTime();
            }
        };
        timer.start();

        System.out.println("trở về main với check in ID " + checkInID );
    }
}

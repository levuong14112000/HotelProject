package com.example.hotelproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;


    private Stage loginStage;
    private int userId;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        ResultSet resultSet = User_DAO.getIDbyUsernamePassword(username, password);
        while (resultSet.next()) {
            userId = resultSet.getInt("UserID");
            };

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng không để trống !");
            alert.showAndWait();
        } else {
            if (User_DAO.checkLogin(username, password)) {
                System.out.println("Đăng nhập thành công!");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                    Parent root = loader.load();

                    MainController mainController = loader.getController();
                    mainController.setUserId(userId);
                    mainController.setUserIDAndInitialize(userId);
//                    System.out.println(userId);

                    Scene scene = new Scene(root);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.centerOnScreen();
                    primaryStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Sai thông tin đăng nhập");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void onExitButtonClick(){
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}

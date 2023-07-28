package com.example.hotelproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();

        loginController.setLoginStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
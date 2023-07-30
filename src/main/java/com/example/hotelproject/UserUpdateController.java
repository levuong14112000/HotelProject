package com.example.hotelproject;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserUpdateController {
    @FXML
    private Label lbUserID;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox cbadmin;

    private  UserController userController;

    protected void openUpdateWindows(UserController userController, int id, String name, String username, String password, boolean admin){
        this.userController = userController;
        lbUserID.setText(String.valueOf(id));
        txtFullName.setText(name);
        txtUserName.setText(username);
        txtPassword.setText(password);
        cbadmin.setSelected(admin);
    }
    @FXML
    protected void  updateUserBtn(){
        int id = Integer.parseInt(lbUserID.getText());
        txtUserName.setDisable(true);
        String fullName = txtFullName.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        boolean ad = cbadmin.isSelected();

        User nv = new User();
        nv.setFullName(fullName);
        nv.setUsername(userName);
        nv.setPassword(password);

        User_DAO.updateNV(id, fullName, userName, password,ad);

        Stage currenStage = (Stage) txtFullName.getScene().getWindow();
        userController.khoiTaoTable();
        currenStage.close();
//        UserController userController = new UserController();
//        userController.khoiTaoTable();
    }

    @FXML
    private javafx.scene.control.Button cancelBtn;
    @FXML
    protected void cancel(){
        Stage currenStage = (Stage) cancelBtn.getScene().getWindow();
        currenStage.close();
    }
}

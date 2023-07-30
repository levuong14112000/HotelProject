package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAddController {
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox checkAdmin;

    private UserController userController;
    public void setUserController(UserController userController){
        this.userController = userController;
    }

    @FXML
    protected void addNewUserBtn() {
        String fullName = txtFullName.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        boolean isadmin;
        boolean flag = false;
        if(checkAdmin.isSelected())
        {
            isadmin = true;
        }
        else{
            isadmin = false;
        }
        ObservableList<User> listnv = FXCollections.observableArrayList();
        ResultSet rs = User_DAO.readAll();
        try{
            while (rs.next()){
                String UserNameInDB = rs.getString("UserName");
                if (userName.equals(UserNameInDB))
                {
                    flag = true;
                    break;
                }
                else {
                    flag = false;
                }
            }
            if (flag == false){
                User_DAO.getInstance().addNV(fullName, userName, password, isadmin);

                txtFullName.setText("");
                txtUserName.setText("");
                txtPassword.setText("");
                Stage currenStage = (Stage) txtFullName.getScene().getWindow();
                userController.khoiTaoTable();
                currenStage.close();
            }
            else {
                Alert infoDuplicate = new Alert(Alert.AlertType.WARNING);
                infoDuplicate.setTitle("Cảnh báo");
                infoDuplicate.setHeaderText("Tên đăng nhập đã tồn tại");
                infoDuplicate.setContentText("Hãy chọn tên đăng nhập khác");
                infoDuplicate.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private javafx.scene.control.Button cancelBtn;
    @FXML
    protected void cancel(){
        Stage currenStage = (Stage) cancelBtn.getScene().getWindow();
        currenStage.close();
    }
}

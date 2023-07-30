package com.example.hotelproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> tableSTT;
    @FXML
    private TableColumn<User, Integer> tableMaNhanVien;
    @FXML
    private TableColumn<User, String> tableHoTen;
    @FXML
    private TableColumn<User, String> tableUserName;
    @FXML
    private TableColumn<User, String> tablePassword;
    @FXML
    private TableColumn<User, Boolean> tableAdmin;


    @FXML
    private ComboBox cbbFind;
    @FXML
    private TextField txtFind;
    @FXML
    private Button btnFind;


    @FXML
    private TableView<User> table2;
    @FXML
    private TableColumn<User, Integer> tableMaNhanVien2;
    @FXML
    private TableColumn<User, String> tableHoTen2;
    @FXML
    private TableColumn<User, String> tableUserName2;
    @FXML
    private TableColumn<User, String> tablePassword2;
    @FXML
    private TableColumn<User, Boolean> tableAdmin2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User_DAO.getInstance();
        ObservableList<String> list = FXCollections.observableArrayList("Mã nhân viên", "Họ tên","Username", "Password");
        cbbFind.setItems(list);
        cbbFind.setValue("Tìm kiếm theo");
        khoiTaoTable();
        tableOnClick();
    }


    @FXML
    private User tableOnClick() {
        User nv = table.getSelectionModel().getSelectedItem();
        return nv;
    }

    private String xacDinhChucVu(boolean chucvutrenDB){
        String isAdmin;
        if (chucvutrenDB==false){
            isAdmin="nhân viên";
        }
        else {
            isAdmin="Admin";
        }
        return isAdmin;
    }
    @FXML
    public void khoiTaoTable() {
        ObservableList<User> list = FXCollections.observableArrayList();
        ResultSet rs = User_DAO.readAll();
        try {
            while (rs.next()) {
                boolean del = rs.getBoolean("Deleted");
                if (del==false){
                    int UserID = rs.getInt("UserID");
                    String FullName = rs.getString("FullName");
                    String UserName = rs.getString("UserName");
                    String Passwork = rs.getString("Password");
                    boolean chucVuDB = rs.getBoolean("IsAdmin");
                    String chucVu= xacDinhChucVu(chucVuDB);
                    User qlnv = new User(UserID,FullName,UserName,Passwork,chucVu,del);
                    list.add(qlnv);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        tableMaNhanVien.setCellValueFactory(new PropertyValueFactory<>("userID"));
        tableHoTen.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        tablePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        table.setItems(list);
    }


    @FXML
    private void findNVBtn(ActionEvent event) {
        String loaiTK = null;
        String textTK = txtFind.getText();
        if (cbbFind.getValue().toString() == "Mã nhân viên") {
            loaiTK = "UserID";
        } else if (cbbFind.getValue().toString() == "Họ tên") {
            loaiTK = "FullName";
        } else if (cbbFind.getValue().toString() == "Username") {
            loaiTK = "Username";
        } else if (cbbFind.getValue().toString() == "Password") {
            loaiTK = "Password";
        }
        if (loaiTK != null && !textTK.isEmpty()){
            System.out.println(User_DAO.find2(loaiTK, textTK));

            ObservableList<User> list = FXCollections.observableArrayList();
            ResultSet rs = User_DAO.find2(loaiTK, textTK);
            try {
                while (rs.next()) {
                    boolean del = rs.getBoolean("Deleted");
                    if (del == false) {
                        int UserID = rs.getInt("UserID");
                        String FullName = rs.getString("FullName");
                        String UserName = rs.getString("UserName");
                        String Passwork = rs.getString("Password");
                        boolean chucVuDB = rs.getBoolean("IsAdmin");
                        String chucVu = xacDinhChucVu(chucVuDB);
                        User qlnv = new User(UserID, FullName, UserName, Passwork, chucVu, del);
                        list.add(qlnv);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableMaNhanVien2.setCellValueFactory(new PropertyValueFactory<>("userID"));
            tableHoTen2.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            tableUserName2.setCellValueFactory(new PropertyValueFactory<>("username"));
            tablePassword2.setCellValueFactory(new PropertyValueFactory<>("password"));
            tableAdmin2.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
            table2.setItems(list);
        }
        else {
                Alert infoFind = new Alert(Alert.AlertType.WARNING);
                infoFind.setTitle("Cảnh báo");
                infoFind.setHeaderText("Loại tìm kiếm và từ khóa tìm kiếm không hợp lệ");
                infoFind.setContentText("Kiểm tra lại loại tìm kiếm và từ khóa tìm kiếm");
                infoFind.showAndWait();
        }
    }


    @FXML
    private void addNewBtn() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("insertUser.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Thao Tác");
            stage.show();
            UserAddController addController = loader.getController();
            addController.setUserController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void updateBtn() {
        if (tableOnClick()==null){
            Alert infoDuplicate = new Alert(Alert.AlertType.WARNING);
            infoDuplicate.setTitle("Cảnh báo");
            infoDuplicate.setHeaderText("Không có đối tượng sửa thông tin");
            infoDuplicate.setContentText("Hãy chọn nhân viên bạn muốn sửa");
            infoDuplicate.showAndWait();
        }
        else {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fixUser.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Thao Tác");
                stage.show();

                User nv = tableOnClick();
                int id = nv.getUserID();
                String name = nv.getFullName();
                String username = nv.getUsername();
                String pass = nv.getPassword();
                boolean admin;
                if (nv.getIsAdmin()=="Admin"){
                    admin=true;
                }
                else {
                    admin=false;
                }

                UserUpdateController updateController = loader.getController();
                updateController.openUpdateWindows(this,id, name, username, pass, admin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//
//
//    @FXML
//    private Button btnDelete;
    @FXML
    private void setBtnDelete() {
        User nv = table.getSelectionModel().getSelectedItem();
        int id= nv.getUserID();
        User_DAO.del(id);
        khoiTaoTable();
    }
}

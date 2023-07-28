package com.example.hotelproject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerInsertController {
    private Stage currentStage;
    private CustomerController customerController;

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }
    @FXML
    private TextField i_fullname , i_number , i_phone
            ;
    @FXML
    protected void InsertCustomerBtn(){
        CustomerController cs = new CustomerController();
        String FullName = i_fullname.getText();
        String IDNumber = i_number.getText();
        String PhoneNumber = i_phone.getText();
        if ( FullName.isEmpty() || PhoneNumber.isEmpty() || IDNumber.isEmpty() ) {
            cs.showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!validatePhoneNumber(PhoneNumber)) {
            cs.showErrorMessage("Số điện thoại không hợp lệ!");
            return;
        }

        if (!validateIDNumber(IDNumber)) {
           cs.showErrorMessage("Số CMND không hợp lệ!");
            return;
        }
        cs.showSuccessMessage("Insert Thành Công");
        Customer_DAO.insertCustomer(FullName, PhoneNumber, IDNumber);
        Stage currentStage = (Stage) i_fullname.getScene().getWindow();
        customerController.updateTableView();
        currentStage.close();


    }
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            return false;
        }
        if (!phoneNumber.startsWith("0")) {
            return false;
        }
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public boolean validateIDNumber(String idNumber) {
        int length = idNumber.length();
        if (length != 9 && length != 12) {
            return false;
        }
        for (char c : idNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}

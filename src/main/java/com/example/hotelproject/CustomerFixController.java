package com.example.hotelproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFixController implements Initializable {
    private CustomerController customerController;

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }
    @FXML
    Label customerid;
    @FXML
    private TextField i_fullname , i_number , i_phone;
    public void setCustomer(Customer rb){
        i_fullname.setText(rb.getFullName());
        i_phone.setText(rb.getPhoneNumber());
        i_number.setText(rb.getIdNumber());
        String CustomerID = String.valueOf(rb.getCustomerID());
        customerid.setText(CustomerID);
    }
    public void setCustomer1(Customer rb){
        i_fullname.setText(rb.getFullName());
        i_phone.setText(rb.getPhoneNumber());
        i_number.setText(rb.getIdNumber());
        String CustomerID = String.valueOf(rb.getCustomerID());
        customerid.setText(CustomerID);
    }
    @FXML
    protected void FixBookingBtn(){
        CustomerController cs = new CustomerController();
        CustomerInsertController ci = new CustomerInsertController();
        int Customer = Integer.parseInt(customerid.getText());
        String FullName = i_fullname.getText();
        String IDNumber = i_number.getText();
        String PhoneNumber = i_phone.getText();
        if ( FullName.isEmpty() || PhoneNumber.isEmpty() || IDNumber.isEmpty() ) {
            cs.showErrorMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        if (!ci.validatePhoneNumber(PhoneNumber)) {
            cs.showErrorMessage("Số điện thoại không hợp lệ!");
            return;
        }

        if (!ci.validateIDNumber(IDNumber)) {
            cs.showErrorMessage("Số CMND không hợp lệ!");
            return;
        }
        cs.showSuccessMessage("Update Thành Công");
        Customer_DAO.updateCustomer(FullName, PhoneNumber, IDNumber,Customer);
        Stage currentStage = (Stage) i_fullname.getScene().getWindow();
        customerController.updateTableView();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

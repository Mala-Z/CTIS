package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.adminTableViewObjects.EmployeeObj;
import SourceCode.Model.userTableViewObjects.ReturnObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static SourceCode.Controller.RunView.adminController;

public class UpdateEmployeeController {
    BusinessLogic businessLogic = new BusinessLogic();
//    AdminController adminController = new AdminController();
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnCancel;
    @FXML
    public TextField tfEmployeeBarcode;
    @FXML
    public TextField tfEmployeeNo;
    @FXML
    public TextField tfEmployeeName;
    @FXML
    public TextField tfPhoneNumber;
    private RunView runView;
    ArrayList<String> obList = new ArrayList<>();
    AdminController adminController = AdminController.getInstance();
    //EmployeeObj employeeObj = (EmployeeObj) adminController.employeeTableView.getSelectionModel().getSelectedItem();

    @FXML
    private void initialize() {

//        ArrayList<String> list = adminController.getEmployeeObjBarcode();
//        System.out.println(list.size());
        //System.out.println(adminController.employeeObjArrayList.get(0).getEmployeeBarcode());

//        EmployeeObj employeeObj = (EmployeeObj) adminController.employeeTableView.getSelectionModel().getSelectedItem();

//        tfEmployeeBarcode.setText("A");
//            obList.get(0).getEmployeeBarcode();
//        String employeeBarcode = employeeObj.getEmployeeBarcode();
//        String employeeNumber = employeeObj.getEmployeeNo();
//        String employeeName = employeeObj.getEmployeeName();
//        String employeePhone = employeeObj.getPhoneNumber();
//        System.out.println(employeeBarcode);
//        populateTextFields();
//        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button
    }

    @FXML
    private  void populateTextFields(){

//        EmployeeObj employeeObj = (EmployeeObj) adminController.employeeTableView.getSelectionModel().getSelectedItem();
//        String employeeBarcode = employeeObj.getEmployeeBarcode();
//        String employeeNumber = employeeObj.getEmployeeNo();
//        String employeeName = employeeObj.getEmployeeName();
//        String employeePhone = employeeObj.getPhoneNumber();
//        System.out.println(employeeBarcode);
//        tfEmployeeBarcode.appendText(employeeBarcode);
//        tfPhoneNumber.appendText(employeeNumber);
//        tfEmployeeName.setText(employeeName);
//        tfPhoneNumber.setText(employeePhone);



    }

    @FXML
    private void checkEmployeeBarcode(){
        if (tfEmployeeBarcode.getLength()>0){
            if (!businessLogic.checkEmployeeBarcode(tfEmployeeBarcode.getText())){
                tfEmployeeNo.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Employee barcode already in database");
            }
        }
    }
    @FXML private void checkEmployeeNumber(){
        if (tfEmployeeNo.getLength()>0){
            if (!businessLogic.checkEmployeeNumber(tfEmployeeNo.getText())){
                tfEmployeeName.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Employee number already in database");
            }

        }
    }
    @FXML
    private void checkEmployeeName(){
        if (tfEmployeeName.getLength()>0){
            if (!businessLogic.checkEmployeeName(tfEmployeeName.getText())){
                tfPhoneNumber.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Employee name already in database");
            }
        }
    }
    @FXML
    private void checkEmployeePhone(){
        if (tfPhoneNumber.getLength()>0){
            btnSubmit.requestFocus();
            btnSubmit();//if we press ENTER we call btnSubmit method so we dont have to click the button
        }
    }

    @FXML
    private void btnSubmit() {
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfEmployeeNo.getLength() > 0 && tfEmployeeName.getLength() > 0 && tfPhoneNumber.getLength() > 0) {
                businessLogic.insertEmployee(Integer.parseInt(tfEmployeeBarcode.getText()), tfEmployeeNo.getText(), tfEmployeeName.getText(), Integer.parseInt(tfPhoneNumber.getText()));
                MainViewController.updateAlertMessage("Registration successful");
                tfEmployeeBarcode.requestFocus();//so we can create a new employee
                tfEmployeeBarcode.clear();
                tfEmployeeNo.clear();
                tfEmployeeName.clear();
                tfPhoneNumber.clear();
            } else {
                MainViewController.updateAlertMessage("Please insert values in all fields to be able to save an employee");
            }

        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException{
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}








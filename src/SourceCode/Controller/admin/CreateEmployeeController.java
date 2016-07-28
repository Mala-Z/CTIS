package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateEmployeeController {
    BusinessLogic businessLogic = new BusinessLogic();

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



    @FXML
    private void initialize()
    {
        tfEmployeeBarcode.setText(String.valueOf(businessLogic.getNewEmployeeBarcode()));

        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button
    }

    @FXML private void checkEmployeeBarcode(){
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
    private void checkEmployeePhone() throws Exception{
        if (tfPhoneNumber.getLength()>0){
            btnSubmit.requestFocus();
            btnSubmit();//if we press ENTER we call btnSubmit method so we dont have to click the button
        }
    }

    @FXML
    private void btnSubmit() throws Exception{
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfEmployeeNo.getLength() > 0 && tfEmployeeName.getLength() > 0 && tfPhoneNumber.getLength() > 0)
            {
                businessLogic.insertEmployee(Integer.parseInt(tfEmployeeBarcode.getText()), tfEmployeeNo.getText(), tfEmployeeName.getText(), tfPhoneNumber.getText());
                //we clear the textfields inside the method "insertEmployee"

                tfEmployeeBarcode.requestFocus();//so we can create a new employee
                //tfEmployeeBarcode.clear();
//                tfEmployeeBarcode.setText(String.valueOf(businessLogic.getNewEmployeeBarcode()));
//                tfEmployeeNo.clear();
//                tfEmployeeName.clear();
//                tfPhoneNumber.clear();
            } else {
                MainViewController.updateAlertMessage("Please insert values in all fields to be able to save an employee");
            }

        } catch(Exception e) {
            System.out.println("Exception in btnsubmit, CreateEmployee: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException{
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

//    //Method for letting the user now which barcode is free for taking
//    private void chooseBarcode(){
//        if (businessLogic.checkItemPrimaryKey()){
//            tfEmployeeBarcode.setText("500");
//
//        }else {
//            tfEmployeeBarcode.setText(String.valueOf(businessLogic.getNewEmployeeBarcode()));
//        }
//    }

}








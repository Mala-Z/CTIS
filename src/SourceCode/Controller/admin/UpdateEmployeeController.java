package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


public class UpdateEmployeeController {
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
    private RunView runView;

    private String readEmployeeBarcode(){
        String content = null;
        File file = new File("/Users/Cristian/Desktop/CTIS/src/Resources/employeeBarcode.txt");
        FileReader reader = null;
        try{
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content;

    }

    @FXML
    private void initialize() {
        //populating the textfields
        tfEmployeeBarcode.setText(businessLogic.getEmployee(readEmployeeBarcode()).getEmployeeBarcode());
        tfEmployeeNo.setText(businessLogic.getEmployee(readEmployeeBarcode()).getEmployeeNo());
        tfEmployeeName.setText(businessLogic.getEmployee(readEmployeeBarcode()).getEmployeeName());
        tfPhoneNumber.setText(businessLogic.getEmployee(readEmployeeBarcode()).getPhoneNumber());


        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button
    }


    @FXML
    private void checkEmployeeBarcode(){
        if (tfEmployeeBarcode.getLength()>0){
                tfEmployeeNo.requestFocus();

        }
    }
    @FXML private void checkEmployeeNumber(){
        if (tfEmployeeNo.getLength()>0){

                tfEmployeeName.requestFocus();
        }
    }
    @FXML
    private void checkEmployeeName(){
        if (tfEmployeeName.getLength()>0){
                tfPhoneNumber.requestFocus();
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
        String employeeBarcode = tfEmployeeBarcode.getText();
        String employeeNo = tfEmployeeNo.getText();
        String employeeName = tfEmployeeName.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String oldEmployeeBarcode = readEmployeeBarcode().toString();
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfEmployeeNo.getLength() > 0 && tfEmployeeName.getLength() > 0
                    && tfPhoneNumber.getLength() > 0) {

                businessLogic.updateEmployeeTable(employeeBarcode, employeeNo, employeeName, phoneNumber, oldEmployeeBarcode);

                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                stage.close();

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








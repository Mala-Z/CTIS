package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;


public class CreateCategoryController {
    BusinessLogic businessLogic = new BusinessLogic();

    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfCategory;

    private RunView runView;


    @FXML
    private void initialize() {
        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button
    }


    @FXML
    private void checkCategory() throws Exception{
        if (tfCategory.getLength()>0){
            btnSubmit();//if we press ENTER we call btnSubmit method so we dont have to click the button
        }
    }

    @FXML
    private void btnSubmit() throws Exception{
        try {
            if (tfCategory.getLength() > 0 ) {
                businessLogic.insertCategory(tfCategory.getText());

//                tfEmployeeBarcode.requestFocus();//so we can create a new employee

                tfCategory.clear();
                MainViewController.confirmationMessage("Category added");
            } else {
                MainViewController.updateAlertMessage("Please insert a value");
            }

        } catch(Exception e) {
            System.out.println("Exception in btnSubmit(), CreateCategoryController class: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException{
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
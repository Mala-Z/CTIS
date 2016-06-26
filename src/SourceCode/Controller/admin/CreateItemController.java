package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateItemController {
    BusinessLogic businessLogic = new BusinessLogic();

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfItemBarcode;

    @FXML
    public TextField tfItemNo;

    @FXML
    private TextField tfItemName;

    @FXML
    private ComboBox categoryCombo;

    private RunView runView;

    @FXML
    private void initialize() {
        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button

        tfItemBarcode.setText(String.valueOf(businessLogic.getNewItemBarcode()));
        categoryCombo.setItems(businessLogic.getCategory());
        }
    @FXML private void checkItemBarcode(){
        if (tfItemBarcode.getLength()>0){
            if (!businessLogic.checkItemBarcode(tfItemBarcode.getText())){
                tfItemNo.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Item barcode already in database");
            }
        }
    }
    @FXML private void checkItemNumber(){
        if (tfItemNo.getLength()>0){
            if (!businessLogic.checkItemNo(tfItemNo.getText())){
                tfItemName.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Item number already in database");
            }
        }
    }
    @FXML
    private void checkItemName(){
        if (tfItemName.getLength()>0){
            btnSubmit.requestFocus();
        }
    }


    @FXML
    private void btnSubmit() {
        try {
            if (tfItemBarcode.getLength() > 0 && tfItemNo.getLength() > 0 && tfItemName.getLength() > 0 && categoryCombo.getSelectionModel().getSelectedIndex() > -1) {
                    businessLogic.insertItem(Integer.parseInt(tfItemBarcode.getText()), tfItemNo.getText(), tfItemName.getText(),
                            categoryCombo.getValue().toString());

                    tfItemBarcode.clear();
                    tfItemNo.clear();
                    tfItemName.clear();
                    tfItemBarcode.requestFocus();

            } else {
                MainViewController.updateAlertMessage("Please insert values in all fields to be able to save an item");
            }

        } catch(Exception e) {
        System.out.println("Exception in btnSubmit() CreateItemController: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}








package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Timestamp;

public class TakeItemController {
    BusinessLogic businessLogic = new BusinessLogic();

    ObservableList<String> placeList = FXCollections.observableArrayList("Address", "Car", "One day use");

    @FXML
    private TextField tfEmployeeBarcode;
    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSubmit;
    @FXML
    private ComboBox placeCombo;

    private RunView runView;

   @FXML
    private void initialize(){
       placeCombo.setItems(placeList);
    }


    @FXML
    private void btnSubmit() {
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfItemBarcode.getLength() > 0) {
                if (!businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
                    java.util.Date today = new java.util.Date();
                    Timestamp timeTaken = new Timestamp(today.getTime());
                    businessLogic.takeItem(Integer.parseInt(tfEmployeeBarcode.getText()), Integer.parseInt(tfItemBarcode.getText()), timeTaken, placeCombo.getValue().toString());
                    updateAlertMessage("Registration successful");
                    runView.showMainView();
                } else if (businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
                    updateAlertMessage("Item has been already taken by another employee");
                    tfItemBarcode.setText(null);
                }else if (placeCombo.getSelectionModel().getSelectedIndex() > 0) {
                        updateAlertMessage("Missing place for item");
                        tfItemBarcode.setText(null);
                }
            } else {
                updateAlertMessage("Missing barcode for employee or item");
            }
        } catch (Exception e) {
            System.out.println("Exception in btnSubmit() from TakeItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack(){
        try{

        }catch (Exception e){
            System.out.println("Exception in btnBack() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void btnDelete(){
        try{

        }catch (Exception e){
            System.out.println("Exception in btnDelete() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkEmployeeBarcode() {
        try {
            if (businessLogic.checkEmployeeBarcode(Integer.parseInt(tfEmployeeBarcode.getText()))) {
                tfItemBarcode.requestFocus();
            } else {
                updateAlertMessage("Please scan the barcode again");
                tfEmployeeBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkEmployeeBarcode() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {
        try {
            if (businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) {
                tableView.requestFocus();
                //show scanned item in the list
            } else {
                updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkItemBarcode() from TakeItemController class: " + e.getMessage());
        }
    }

    /* METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER */
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

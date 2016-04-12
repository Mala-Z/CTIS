package SourceCode.Controller.user;

import SourceCode.BusinessLogic.Model;
import SourceCode.Controller.RunView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Timestamp;

public class TakeItemController {
    Model model = new Model();

    ObservableList<String> placeList = FXCollections.observableArrayList("Car", "Address");

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
    private void btnBackAction() throws IOException {
        runView.showMainView();
    }

    @FXML
    private void btnDeleteAction() throws IOException {


    }

    @FXML
    private void btnSubmitAction() {
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfItemBarcode.getLength() > 0) {
                if (!model.checkIfItemIsTaken(Integer.parseInt(tfItemBarcode.getText()))) {
                    java.util.Date today = new java.util.Date();
                    Timestamp timeTaken = new Timestamp(today.getTime());
                    model.takeItem(Integer.parseInt(tfEmployeeBarcode.getText()), Integer.parseInt(tfItemBarcode.getText()), timeTaken);
                    updateAlertMessage("Registration successful");
                    runView.showMainView();
                } else if (model.checkIfItemIsTaken(Integer.parseInt(tfItemBarcode.getText()))) {
                    updateAlertMessage("Item has been already taken by another employee");
                    tfItemBarcode.setText(null);
                }
            } else {
                updateAlertMessage("Missing barcode for employee or item");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @FXML
    private void checkEmployeeBarcode() {
        try {
            if (model.checkEmployeeBarcode(Integer.parseInt(tfEmployeeBarcode.getText()))) {
                tfItemBarcode.requestFocus();
            } else {
                updateAlertMessage("Please scan the barcode again");
                tfEmployeeBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {
        try {
            if (model.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) {
                tableView.requestFocus();
                //show scanned item in the list
            } else {
                updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateItemController {
    BusinessLogic businessLogic = new BusinessLogic();

    ObservableList<String> categoryList = FXCollections.observableArrayList("Apartment cleaning", "Clothes", "Green areas", "Key", "Snow");

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfItemBarcode;

    @FXML
    private TextField tfItemNo;

    @FXML
    private TextField tfItemName;

    @FXML
    private ComboBox categoryCombo;

    private RunView runView;

    @FXML
    private void initialize() {
        categoryCombo.setItems(categoryList);
    }


    @FXML
    private void btnSubmit() {
        try {
            if (tfItemBarcode.getLength() > 0 && tfItemNo.getLength() > 0 && tfItemName.getLength() > 0 && categoryCombo.getSelectionModel().getSelectedIndex() > 0) {
                if (!businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) {
                    businessLogic.insertItem(Integer.parseInt(tfItemBarcode.getText()), tfItemNo.getText(), tfItemName.getText(),
                            categoryCombo.getValue().toString());
                    updateAlertMessage("Registration successful");
                    Stage stage = (Stage) btnCancel.getScene().getWindow();
                    stage.close();
                    runView.showAdminView();
                }
            } else {
                updateAlertMessage("Please insert values in all fields to be able to save an item");
            }

        } catch(Exception e) {
        System.out.println("Exception: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

}








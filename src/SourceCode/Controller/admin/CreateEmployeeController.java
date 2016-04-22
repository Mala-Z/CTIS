package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.Model;
import SourceCode.Controller.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateEmployeeController {
    Model model=null;

    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfEmployeeBarcode;
    @FXML
    private TextField tfEmployeeNo;
    @FXML
    private TextField tfEmployeeName;
    @FXML
    private TextField tfPhoneNumber;

    private RunView runView;

    @FXML
    private void btnSubmit() {
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfEmployeeNo.getLength() > 0 && tfEmployeeName.getLength() > 0 && tfPhoneNumber.getLength() > 0) {
                if (!model.checkEmployeeBarcode(Integer.parseInt(tfEmployeeBarcode.getText()))) {
                    model.insertEmployee(Integer.parseInt(tfEmployeeBarcode.getText()), tfEmployeeNo.getText(), tfEmployeeName.getText(), Integer.parseInt(tfPhoneNumber.getText()));
                    updateAlertMessage("Registration successful");
                    Stage stage = (Stage) btnCancel.getScene().getWindow();
                    stage.close();
                    runView.showAdminView();
                }
            } else {
                updateAlertMessage("Please insert values in all fields to be able to save an employee");
            }

        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException{
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








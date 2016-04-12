package SourceCode.Controller.user;

import SourceCode.BusinessLogic.Model;
import SourceCode.Controller.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TakeItemController {
    Model model = new Model();

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

    private RunView runView;

    @FXML
    private void btnBackAction() throws IOException {
        runView.showMainView();
    }
    @FXML
    private void btnDeleteAction() throws IOException {


    }
    @FXML
    private void setBtnSubmitAction() throws IOException {

    }
    @FXML
    private void checkEmployeeBarcode() throws IOException{
        if (model.checkEmployeeBarcode(tfEmployeeBarcode.getText())){
            tfItemBarcode.requestFocus();
        }else {
            updateAlertMessage("Please scan the barcode again");
        }
    }

    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

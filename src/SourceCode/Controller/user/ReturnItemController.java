package SourceCode.Controller.user;

import SourceCode.View.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ReturnItemController {
    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSubmit;

    private RunView runView;


    @FXML
    private void btnBackAction() throws IOException {
        runView.showMainView();
    }
    @FXML
    private void btnDeleteAction() throws IOException {
        // YOUR CODE HERE

    }

    @FXML
    private void setBtnSubmitAction() throws IOException {


    }

}

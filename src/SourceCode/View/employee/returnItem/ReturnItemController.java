package SourceCode.View.employee.returnItem;

import SourceCode.View.employee.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ReturnItemController {
    @FXML
    private TextField tfEmployeeBarcode;
    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSubmit;

    private Main main;

    @FXML
    private void btnBackAction() throws IOException {
        main.showMainView();
    }
    @FXML
    private void btnDeleteAction() throws IOException {
        // YOUR CODE HERE

    }
    @FXML
    private void setBtnSubmitAction() throws IOException {


    }

}

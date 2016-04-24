package SourceCode.Controller.admin;

import SourceCode.Controller.RunView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class AdminController {
    private RunView runView;


    @FXML
    private void btnCreateEmployee() throws IOException {
        runView.showCreateEmployee();
    }
    @FXML
    private void btnCreateItem() throws IOException {
        runView.showCreateItem();
    }
    @FXML
    private void btnLogOut() throws IOException{
        runView.showMainView();
    }
}








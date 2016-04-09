package SourceCode.Controller.admin;

import SourceCode.View.RunView;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminController {

    private RunView runView;


    @FXML
    private void btnCreateEmployeeEvent() throws IOException {
        runView.showCreateEmployee();
    }
    @FXML
    private void btnCreateItemEvent() throws IOException {
        runView.showCreateItem();
    }
    @FXML
    private void btnLogOut() throws IOException{
        runView.showMainView();
    }
}








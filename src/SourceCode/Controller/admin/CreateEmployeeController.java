package SourceCode.Controller.admin;

import SourceCode.Controller.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateEmployeeController {
    @FXML
    Button btnCreateEmployee;
    @FXML
    Button btnCancel;

    private RunView runView;



    @FXML
    private void closeButtonAction() throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}








package SourceCode.Controller.admin;

import SourceCode.View.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateItemController {
    @FXML
    private Button btnCancel;

    private RunView runView;



    @FXML
    private void closeButtonAction() throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}








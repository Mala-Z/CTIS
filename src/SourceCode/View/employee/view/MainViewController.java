package SourceCode.View.employee.view;

import SourceCode.View.employee.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by St_Muerte on 3/24/16.
 */
public class MainViewController {
    private Main main;


    @FXML
    private void goHome() throws IOException {
        main.showMainView();
    }

    @FXML
    private void goTakeItem() throws IOException{
        main.showTakeItem();


    }
    @FXML
    private void goReturnItem() throws IOException{
        main.showReturnItemView();
    }
    @FXML
    private void goSearch() throws IOException{
        main.showSearch();
    }
    @FXML
    private void goAdminView() throws IOException{
        main.showAdminView();
    }


}

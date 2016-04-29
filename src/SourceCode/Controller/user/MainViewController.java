package SourceCode.Controller.user;

import SourceCode.Controller.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;


/**
 * Created by St_Muerte on 3/24/16.
 */

public class MainViewController {
    private RunView runView;


    @FXML
    private void goHome(){
        try {
            runView.showMainView();
        }catch (Exception e){
            System.out.println("Exception in goHome() from MainViewController class:" + e.getMessage());
        }
    }

    @FXML
    private void goTakeItem(){
        try {
            runView.showTakeItem();
        }catch (Exception e){
            System.out.println("Exception in goTakeItem() from MainViewController class:" + e.getMessage());
        }
    }

    @FXML
    private void goReturnItem(){
        try {
            runView.showReturnItemView();
        }catch (Exception e){
            System.out.println("Exception in goReturnItem() from MainViewController class:" + e.getMessage());
        }
    }

    @FXML
    private void goSearch(){
        try {
            runView.showSearch();
        }catch (Exception e){
            System.out.println("Exception in goSearch() from MainViewController class:" + e.getMessage());
        }

    }

    @FXML
    private void goAdminView(){
        try {
            runView.showAdminView();
        }catch (Exception e){
            System.out.println("Exception in goAdminView() from MainViewController class:" + e.getMessage());
        }
    }

    /* METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER */
    public static void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

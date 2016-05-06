package SourceCode.Controller.main;

import SourceCode.Controller.RunView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


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
    private void goConsumables(){
        try {
            runView.showConsumables();
        }catch (Exception e){
            System.out.println("Exception in goConsumables() from MainViewController class:" + e.getMessage());
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
    public static void confirmationMessage(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();

    }
    public static void updateWarningMessage(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

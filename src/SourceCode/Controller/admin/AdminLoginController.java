package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {
    @FXML
    TextField tfUsername;
    @FXML
    TextField tfPassword;
    @FXML
    Button btnLogin;
    RunView runView;
    BusinessLogic businessLogic = new BusinessLogic();





    @FXML
    private void btnCancelAction() throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void tfLoginAction() throws IOException{
        if (tfUsername.getText().length() >0  && tfPassword.getText().length() > 0){
            if (businessLogic.checkUsername(tfUsername.getText())){
                if (businessLogic.checkPassword(tfPassword.getText())){
                    runView.showAdminView();

                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.close();
                }else {
                    MainViewController.updateAlertMessage("Password incorrect");
                }
            }else {
                MainViewController.updateAlertMessage("Username incorrect");
            }
        }else {
            MainViewController.updateAlertMessage("Please fill username and password fields");
        }
    }
    @FXML
    private void btnLoginAction() throws IOException{
        if (tfUsername.getText().length() >0  && tfPassword.getText().length() > 0){
            if (businessLogic.checkUsername(tfUsername.getText())){
                if (businessLogic.checkPassword(tfPassword.getText())){
                    runView.showAdminView();

                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.close();
                }else {
                    MainViewController.updateAlertMessage("Password incorrect");
                }
            }else {
                MainViewController.updateAlertMessage("Username incorrect");
            }
        }else {
            MainViewController.updateAlertMessage("Please fill username and password fields");
        }
    }
}

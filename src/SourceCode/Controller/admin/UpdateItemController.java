package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.categories.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.*;


public class UpdateItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnCancel;
    @FXML
    public TextField tfItemBarcode;
    @FXML
    public TextField tfItemNo;
    @FXML
    public TextField tfItemName;
    @FXML
    public ComboBox categoryCombo;

    private String readItemBarcode(){
        String content = null;
        File file = new File("/Users/Cristian/Desktop/CTIS/src/Resources/itemBarcode.txt");
        FileReader reader = null;
        try{
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content;

    }

    @FXML
    private void initialize() {
        //populating the textfields
        tfItemBarcode.setText(businessLogic.getItem(readItemBarcode()).getItemBarcode());
        tfItemNo.setText(businessLogic.getItem(readItemBarcode()).getItemNo());
        tfItemName.setText(businessLogic.getItem(readItemBarcode()).getItemName());
        categoryCombo.getItems().setAll(Category.getCategories());
        categoryCombo.getSelectionModel().select(businessLogic.getItem(readItemBarcode()).getCategory());


        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button
    }


    @FXML
    private void checkItemBarcode(){
        if (tfItemBarcode.getLength()>0){

                tfItemNo.requestFocus();
        }
    }
    @FXML private void checkItemNumber(){
        if (tfItemNo.getLength()>0){

                tfItemName.requestFocus();
        }
    }
    @FXML
    private void checkItemName(){
        if (tfItemName.getLength()>0){
            btnSubmit.requestFocus();
        }
    }


    @FXML
    private void btnSubmit() {
        String itemBarcode = tfItemBarcode.getText();
        String itemNo = tfItemNo.getText();
        String itemName = tfItemName.getText();
        String itemCategory = categoryCombo.getSelectionModel().getSelectedItem().toString();
        String oldItemBarcode = readItemBarcode().toString();
        try {
            if (tfItemBarcode.getLength() > 0 && tfItemNo.getLength() > 0 && tfItemName.getLength() > 0
                    && categoryCombo.getSelectionModel().getSelectedIndex() > -1) {

                businessLogic.updateItem(itemBarcode, itemNo, itemName, itemCategory, oldItemBarcode);

                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                stage.close();

            } else {
                MainViewController.updateAlertMessage("Please insert values in all fields to be able to save an item");
            }

        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException{
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}









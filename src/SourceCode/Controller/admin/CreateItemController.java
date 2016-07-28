package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ListIterator;

public class CreateItemController {
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

    private RunView runView;

    @FXML
    private void initialize() {
        btnSubmit.defaultButtonProperty().bind(btnSubmit.focusedProperty());//to allow enter key to fire the button

//        chooseBarcode();
        categoryCombo.setItems(businessLogic.getCategory());
        }
    @FXML
    private void checkItemBarcode(){
        if (tfItemBarcode.getLength()>0){
            if (!businessLogic.checkItemBarcode(tfItemBarcode.getText())){
                tfItemNo.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Item barcode already in database");
            }
        }
    }
    @FXML
    private void checkItemNumber(){
        if (tfItemNo.getLength()>0){
            if (!businessLogic.checkItemNo(tfItemNo.getText())){
                tfItemName.requestFocus();
            }
            else {
                MainViewController.updateWarningMessage("Item number already in database");
            }
        }
    }

    @FXML
    public void checkCategoryCombo(){
//        System.out.println(categoryCombo.getSelectionModel().getSelectedItem().toString());
        if (categoryCombo.getSelectionModel().getSelectedItem() != null) {
            chooseBarcode();
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
        try {
            if (tfItemBarcode.getLength() > 0 && tfItemNo.getLength() > 0 && tfItemName.getLength() > 0 && categoryCombo.getSelectionModel().getSelectedIndex() > -1)
            {
                    businessLogic.insertItem(Integer.parseInt(tfItemBarcode.getText()), tfItemNo.getText(), tfItemName.getText(),
                            categoryCombo.getValue().toString());
                //we clear the textfields inside the method "insertItem"

//                tfItemNo.clear();
//                tfItemName.clear();
//                tfItemBarcode.clear();
//                tfItemNo.requestFocus();
//                chooseBarcode();
                checkCategoryCombo();
            } else {
                MainViewController.updateAlertMessage("Please insert values in all fields to be able to save an item");
            }

        } catch(Exception e) {
        System.out.println("Exception in btnSubmit() CreateItemController: " + e);
        }
    }

    @FXML
    private void btnCancel() throws IOException {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    //Method for letting the user now which barcode is free for taking
    private void chooseBarcode(){
        if (businessLogic.checkItemPrimaryKey()){
            tfItemBarcode.setText("10000");
        }else {
            tfItemBarcode.setText(String.valueOf(getNewItemBarcode()));
        }
    }
    public long getNewItemBarcode(){
        ConnectDB connectDB = Factory.connectDB;
//        CreateItemController createItemCon = new CreateItemController();
        long max = 0;
        //without key and cleaning department categories
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        filteredList.addAll(businessLogic.getCategory());
        filteredList.remove("Key");
        filteredList.remove("Cleaning department");
//        if (checkItemPrimaryKey()){
//            System.out.println(3);
//            createItemCon.tfItemBarcode.setText("10000");
//        }
        if(categoryCombo.getSelectionModel().getSelectedItem().equals("Key")){
            try {
                String sql = "SELECT MAX(itemBarcode) AS max FROM Item WHERE itemCategory = 'Key'";
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    max = resultSet.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in getNewItemBarcode2() from BusinessLogic class: "  + e.getMessage());
            }

        }

        else if(categoryCombo.getSelectionModel().getSelectedItem().toString().equals("Cleaning department")){
            try {
                String sql = "SELECT MAX(itemBarcode) AS max FROM Item WHERE itemCategory = 'Cleaning department'";
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    max = resultSet.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in getNewItemBarcode2() from BusinessLogic class: "  + e.getMessage());
            }

        }
//        ListIterator iterator = filteredList.listIterator();
//        categoryCombo.getSelectionModel().getSelectedItem().equals(filteredList)
        else {
            try {
                String sql = "SELECT MAX(itemBarcode) AS max FROM Item WHERE itemCategory != 'Cleaning department' AND itemCategory != 'Key'";
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    max = resultSet.getLong(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in getNewItemBarcode2() from BusinessLogic class: "  + e.getMessage());
            }
        }
        return max+1;
    }
//    private void chooseItemBarcode(){
//        //without key and cleaning department categories
//        ObservableList<String> filteredList = FXCollections.observableArrayList();
//        filteredList.addAll(businessLogic.getCategory());
//        filteredList.remove("Key");
//        filteredList.remove("Cleaning department");
//
//        if (businessLogic.checkItemPrimaryKey()){
//            tfItemBarcode.setText("10000");
//        }
//        else if(categoryCombo.getSelectionModel().getSelectedItem().toString().equals("Key")){
//
//        }
//        else if(categoryCombo.getSelectionModel().getSelectedItem().toString().equals("Cleaning department")){
//
//        }
//        else if(categoryCombo.getSelectionModel().getSelectedItem().equals(filteredList)){
//
//        }
//    }

}








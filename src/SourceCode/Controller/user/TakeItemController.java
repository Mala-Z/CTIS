package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.otherPurposeObjects.WriteTakeToDB;
import SourceCode.Model.userTableViewObjects.TakeObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class TakeItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;


    private ObservableList takeItemData = FXCollections.observableArrayList();
    private ObservableList<String> placeList = FXCollections.observableArrayList("On Person", "Address", "Car");
    private ArrayList<String> itemBarcodeList = new ArrayList<>();
    private ArrayList<String> comboBoxList = new ArrayList<>();
    private ArrayList<String> tfPlaceList = new ArrayList<>();

    @FXML
    private TextField tfEmployeeBarcode;
    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSubmit;
    @FXML
    private ComboBox placeCombo;
    @FXML
    private TextField tfPlaceReference;

    @FXML
    private TableColumn itemNumberColumn;
    @FXML
    private TableColumn itemCategoryColumn;
    @FXML
    private TableColumn itemNameColumn;
    @FXML
    private TableColumn placeColumn;
    @FXML
    private TableColumn placeReferenceColumn;

    private ArrayList<Integer> barcodeList = new ArrayList<>();

    private RunView runView;

    private ArrayList<WriteTakeToDB> takeItemList = new ArrayList<>();
    private List<String> firstEmployeeBarcode = new ArrayList<>();





    @FXML
    private void initialize() {

        placeCombo.getItems().addAll(placeList);

        placeCombo.getSelectionModel().selectFirst();

        //tfPlaceList.add(tfPlaceReference.getText());

    }

    @FXML
    private void comboBoxAction(){
        placeCombo.getSelectionModel().getSelectedItem();
        //comboBoxList.add(placeCombo.getSelectionModel().getSelectedItem().toString());
    }


    @FXML
    private void btnSubmit() {
        try {

                businessLogic.takeItem(takeItemList);
                MainViewController.updateAlertMessage("Registration successful");



        } catch (Exception e) {
            System.out.println("Exception in btnSubmit() from TakeItemController class:" + e.getMessage());
        }
    }

//    @FXML
//    private void btnSubmit() {
//        try {
//            if (tfEmployeeBarcode.getLength() > 0 && tfItemBarcode.getLength() > 0) {
//                java.util.Date today = new java.util.Date();
//                Timestamp timeTaken = new Timestamp(today.getTime());
//                businessLogic.takeItem(tfEmployeeBarcode.getText(), tfItemBarcode.getText(), timeTaken, placeCombo.getValue().toString(), tfPlaceReference.getText());
//                //businessLogic.takeItem(takeItemData.get(0).toString(), takeItemData.get(0).toString(), timeTaken, takeItemData.get(0).toString(), takeItemData.get(0).toString());
//                MainViewController.updateAlertMessage("Registration successful");
//                runView.showMainView();
//
//            } else {
//                MainViewController.updateAlertMessage("Missing barcode for employee or item");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Exception in btnSubmit() from TakeItemController class:" + e.getMessage());
//        }
//    }

    @FXML
    private void btnBack() {
        try {
            barcodeList.clear();
            runView.showMainView();
        } catch (Exception e) {
            System.out.println("Exception in btnBack() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void btnDelete() {
        try {

        } catch (Exception e) {
            System.out.println("Exception in btnDelete() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkEmployeeBarcode() {

        try {
            if (businessLogic.checkEmployeeBarcode(Integer.parseInt(tfEmployeeBarcode.getText()))) {
                firstEmployeeBarcode.add(tfEmployeeBarcode.getText());
                String name = businessLogic.getEmployee(tfEmployeeBarcode.getText());
                tfEmployeeBarcode.setText(name);
                tfEmployeeBarcode.setEditable(false);
                tfEmployeeBarcode.setFont(new Font("Arial Black", 13));




                tfItemBarcode.requestFocus();
            } else {
                MainViewController.updateAlertMessage("Please scan the barcode again");
                tfEmployeeBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkEmployeeBarcode() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {

        try {
            if (businessLogic.checkItemBarcode(tfItemBarcode.getText())) {
                if (!businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
                    if (!barcodeList.contains(Integer.valueOf(tfItemBarcode.getText()))){//check for duplicates
                        //itemBarcodeList.add(tfItemBarcode.getText());

                        populateTableView();

                        String employeeBarcodeString = firstEmployeeBarcode.get(0);
                        String itemBarcodeString = tfItemBarcode.getText();
                        java.util.Date date = new java.util.Date();
                        Timestamp timeStamp = new Timestamp(date.getTime());
                        String timeTaken = timeStamp.toString();
                        String place = placeCombo.getSelectionModel().getSelectedItem().toString();
                        String placeReference = tfPlaceReference.getText();

                        WriteTakeToDB writeTakeToDB = new WriteTakeToDB(employeeBarcodeString, itemBarcodeString, timeTaken, place, placeReference);

                        takeItemList.add(writeTakeToDB);
                        System.out.println(takeItemList.toString());

                        //add barcodes to list and after check for barcodes scanned twice
                        barcodeList.add(Integer.parseInt(tfItemBarcode.getText()));

                        tfItemBarcode.clear();
                        tfPlaceReference.clear();

                    }else  if (barcodeList.contains(Integer.valueOf(tfItemBarcode.getText()))){
                        MainViewController.updateAlertMessage("You have already scanned this item");
                    }
                } else if (businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
                    MainViewController.updateAlertMessage("Item has been already taken by another employee");
                    tfItemBarcode.setText(null);
                }
            } else if (!businessLogic.checkItemBarcode(tfItemBarcode.getText())) {
                MainViewController.updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkItemBarcode() from TakeItemController class: " + e.getMessage());
        }
    }

    /* METHOD FOR POPULATING THE TABLE VIEW */
    public void populateTableView() {
        //System.out.println(itemBarcodeList + " " + comboBoxList + " " + tfPlaceList);
        // check if we have barcodes scanned twice
        if (barcodeList.contains(Integer.valueOf(tfItemBarcode.getText()))){
            MainViewController.updateAlertMessage("You have already scanned this item");
        }

        else {
            try {
            /* SQL QUERY */
                String sql = " SELECT itemNo, itemName, category FROM Item \n" +
                        "INNER JOIN Category ON \n" +
                        "Item.itemBarcode = Category.itemBarcode\n" +
                        "WHERE Item.itemBarcode = ?";

            /* EXECUTION OF QUERY */
                int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
                preparedStatement.setInt(1, inputBarcode);


                ResultSet result = preparedStatement.executeQuery();

                while ((result.next())) {

                    String itemNo = result.getString("itemNo");
                    String itemCategory = result.getString("category");
                    String itemName = result.getString("itemName");
                    String place = placeCombo.getSelectionModel().getSelectedItem().toString(); // here we parse selected category into string
                    String placeReference = tfPlaceReference.getText();
                    TakeObj takeObj = new TakeObj(itemNo, itemCategory, itemName, place, placeReference);

                    takeItemData.setAll(takeObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception in populateTableView() from TakeItemController class: " + e.getMessage());
            }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
            itemNumberColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemNumber"));
            itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemCategory"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemName"));
            placeColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("place"));
            placeReferenceColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("placeReference"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
            tableView.getItems().addAll(takeItemData);
        }
    }

}

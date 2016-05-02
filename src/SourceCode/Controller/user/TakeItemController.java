package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.insertIntoDBObjects.WriteTakeToDB;
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
    private RunView runView;

    private ObservableList takeItemData = FXCollections.observableArrayList(); //[tableview]

    private ObservableList<String> placeList = FXCollections.observableArrayList("On Person", "Address", "Car");//[combobox]

    private ArrayList<Integer> barcodeList = new ArrayList<>();//[unique item] to check for duplicates so there cant be items taken twice

    private ArrayList<WriteTakeToDB> takeItemList = new ArrayList<>();//[insert DB] add writTakeToDB objects to this list

    private List<String> firstEmployeeBarcode = new ArrayList<>();//[barcode employee] for getting the barcode and not the names from the employee textfield




    @FXML
    private void initialize() {

        placeCombo.getItems().addAll(placeList);

        placeCombo.getSelectionModel().selectFirst();

    }

    @FXML
    private void comboBoxAction(){
        placeCombo.getSelectionModel().getSelectedItem();//so we don't get stuck with selectFirst() value
    }


    @FXML
    private void btnSubmit() {
        try {
            businessLogic.takeItem(takeItemList);
            MainViewController.updateAlertMessage("Registration successful!");
//            tfEmployeeBarcode.clear();
//            tfEmployeeBarcode.setEditable(true);
//            tfEmployeeBarcode.setFont(new Font("Arial", 13));
//            tfEmployeeBarcode.setStyle("-fx-background-color: white");
            runView.showTakeItem();//this reloads the view(we get errors because we keep the old items if we dont do this)

        } catch (Exception e) {
            System.out.println("Exception in btnSubmit() from TakeItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack() {
        try {
            //barcodeList.clear();//so we start fresh

            runView.showMainView();
        } catch (Exception e) {
            System.out.println("Exception in btnBack() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void btnDelete() {
        try {

            int row = tableView.getSelectionModel().getFocusedIndex();
            tableView.getItems().remove(row); //removes row from tableview
            takeItemList.remove(row);// removes row from list, that inserts into db
            barcodeList.remove(row); //removes index of stored barcodes(so we can re-add it later if we want to the tableview)
            //System.out.println(row);


        } catch (Exception e) {
            System.out.println("Exception in btnDelete() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkEmployeeBarcode() {

        try {
            if (businessLogic.checkEmployeeBarcode(Integer.parseInt(tfEmployeeBarcode.getText()))) {
                firstEmployeeBarcode.add(tfEmployeeBarcode.getText());//add textfield input to a list

                String name = businessLogic.getEmployee(tfEmployeeBarcode.getText());//insert barcode, return name
                tfEmployeeBarcode.setText(name);
                tfEmployeeBarcode.setEditable(false);
                tfEmployeeBarcode.setFont(new Font("Arial Black", 13));
                tfEmployeeBarcode.setStyle("-fx-background-color: lightgrey;");


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
                if (!businessLogic.searchItem(tfItemBarcode.getText())) {
                    if (!barcodeList.contains(Integer.valueOf(tfItemBarcode.getText()))){//check for duplicates

                        populateTableView();

                        //attributes for writeTakeToDB object, so we can insert the data in db
                        String employeeBarcodeString = firstEmployeeBarcode.get(0);
                        String itemBarcodeString = tfItemBarcode.getText();
                        java.util.Date date = new java.util.Date();
                        Timestamp timeStamp = new Timestamp(date.getTime());
                        String timeTaken = timeStamp.toString();
                        String place = placeCombo.getSelectionModel().getSelectedItem().toString();
                        String placeReference = tfPlaceReference.getText();

                        WriteTakeToDB writeTakeToDB = new WriteTakeToDB(employeeBarcodeString, itemBarcodeString, timeTaken, place, placeReference);

                        takeItemList.add(writeTakeToDB);// add data to list
                        //System.out.println(takeItemList.toString());

                        //add barcodes to list and after check for barcodes scanned twice
                        barcodeList.add(Integer.parseInt(tfItemBarcode.getText()));

                        tfItemBarcode.clear();
                        tfPlaceReference.clear();

                    }else  if (barcodeList.contains(Integer.valueOf(tfItemBarcode.getText()))){
                        MainViewController.updateAlertMessage("You have already scanned this item");
                        tfItemBarcode.clear();
                    }
                } else if (businessLogic.searchItem(tfItemBarcode.getText())) {
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
        System.out.println(barcodeList);
        //System.out.println(itemBarcodeList + " " + comboBoxList + " " + tfPlaceList);
        // check if we have barcodes scanned twice


            try {
            /* SQL QUERY */
                String sql = "SELECT itemNo, itemName, category FROM Item \n" +
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

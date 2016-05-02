package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.insertIntoDBObjects.WriteConsumablesToDB;
import SourceCode.Model.userTableViewObjects.ConsumablesObj;
import SourceCode.Model.userTableViewObjects.ReturnObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ConsumablesController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;
    private ObservableList consumablesItemData = FXCollections.observableArrayList();


    @FXML
    private TextField tfEmployeeBarcode;
    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSubmit;
    @FXML
    private TableColumn itemNameColumn;
    @FXML
    private TableColumn timeTakenColumn;
    @FXML
    private TableColumn quantityColumn;

    private ArrayList<String> barcodeList = new ArrayList<>();//check for duplicates
    private ArrayList<WriteConsumablesToDB> consumablesList = new ArrayList<>();//[insert DB] add writeConsumablesToDB objects to this list
    private List<String> firstEmployeeBarcode = new ArrayList<>();//[barcode employee] for getting the barcode and not the names from the employee textfield


    private RunView runView;


    @FXML
    private void btnSubmit() {
        try {
            businessLogic.takeConsumables(consumablesList);
            MainViewController.updateAlertMessage("Registered successfully!");
            runView.showConsumables();//this reloads the view(we get errors because we keep the old items if we dont do this)

        } catch (Exception e) {
            System.out.println("Exception in btnSubmit() from ReturnItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack(){
        try{
            barcodeList.clear();
            runView.showMainView();
        }catch (Exception e){
            System.out.println("Exception in btnBack() from ReturnItemController class:" + e.getMessage());
        }
    }
    @FXML
    private void btnDelete() {
        try {

            int row = tableView.getSelectionModel().getFocusedIndex();
            tableView.getItems().remove(row); //removes row from tableview
            consumablesList.remove(row);// removes row from list, that inserts into db
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
            System.out.println("Exception in checkEmployeeBarcode() from ConsumablesController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {
        //unrestricted take of products(as 2 employees can take the same item)
        try {
            if (businessLogic.checkItemBarcode(tfItemBarcode.getText())) {
                    if (!barcodeList.contains(tfItemBarcode.getText())){//check for duplicates

                        tfQuantity.requestFocus();
                        System.out.println(barcodeList);

                    }else  if (barcodeList.contains(tfItemBarcode.getText())){
                        MainViewController.updateAlertMessage("You have already scanned this item");
                        tfItemBarcode.clear();
                    }
            } else if (!businessLogic.checkItemBarcode(tfItemBarcode.getText())) {
                MainViewController.updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkItemBarcode() from ConsumablesController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkQuantity(){

        try{
            populateTableView();
            //attributes for writeTakeToDB object, so we can insert the data in db
            String employeeBarcodeString = firstEmployeeBarcode.get(0);
            String itemBarcodeString = tfItemBarcode.getText();
            java.util.Date date = new java.util.Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            String timeTaken = timeStamp.toString();
            String quantity = tfQuantity.getText();

            WriteConsumablesToDB writeConsumablesToDB = new WriteConsumablesToDB(employeeBarcodeString, itemBarcodeString, quantity, timeTaken);


            consumablesList.add(writeConsumablesToDB);// add data to list
            //System.out.println(takeItemList.toString());

            //add barcodes to list and after check for barcodes scanned twice
            barcodeList.add(tfItemBarcode.getText());
            tfItemBarcode.requestFocus();
            tfItemBarcode.clear();
            tfQuantity.clear();

            }catch (Exception e){
            System.out.println("Exception in checkQuantity() from ConsumablesController class: " + e.getMessage());
        }

    }

    private void populateTableView() {

        try {
            /* SQL QUERY */
            String sql = " SELECT itemName FROM Item \n" +
                    "WHERE Item.itemBarcode = ?";


            /* EXECUTION OF QUERY */
            int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {
                String itemName = result.getString("itemName");
                String quantity = tfQuantity.getText();
                java.util.Date date = new java.util.Date();
                Timestamp timeStamp = new Timestamp(date.getTime());
                String timeTaken = timeStamp.toString();
                ConsumablesObj consumablesObj = new ConsumablesObj(itemName, quantity, timeTaken);

                consumablesItemData.setAll(consumablesObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateTableView() from ConsumablesController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("quantity"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */

        tableView.getItems().addAll(consumablesItemData);
    }

}

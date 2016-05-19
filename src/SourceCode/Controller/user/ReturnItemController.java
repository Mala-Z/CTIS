package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.insertIntoDBObjects.WriteReturnToDB;
import SourceCode.Model.userTableViewObjects.ReturnObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ReturnItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;
    private ObservableList returnItemData = FXCollections.observableArrayList();

    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSubmit;
    @FXML
    private TableColumn employeeNameColumn;
    @FXML
    private TableColumn itemCategoryColumn;
    @FXML
    private TableColumn itemNameColumn;
    @FXML
    private TableColumn placeColumn;
    @FXML
    private TableColumn timeTakenColumn;

    private ArrayList<String> barcodeList = new ArrayList<>();//check for duplicates
    private ArrayList<WriteReturnToDB> returnItemList = new ArrayList<>();//[insert DB] add writReturnToDB objects to this list
    private List<String> firstEmployeeBarcode = new ArrayList<>();//[barcode employee] for getting the barcode and not the names from the employee textfield


    private RunView runView;


    @FXML
    private void btnSubmit() {
        try {
            if (barcodeList.size() != 0){
                businessLogic.returnItem(returnItemList);
                MainViewController.updateAlertMessage("Returned successfully!");
                runView.showReturnItemView();//this reloads the view(we get errors because we keep the old items if we dont do this)
            }else {
                MainViewController.updateWarningMessage("Please insert item");
            }

        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in btnSubmit() from ReturnItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack(){
        try{
            barcodeList.clear();
            runView.showMainView();
        }catch (Exception e){
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in btnBack() from ReturnItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {
        try {
            if (businessLogic.checkItemBarcode(tfItemBarcode.getText())) { //if barcode exists
                if (businessLogic.searchItem(tfItemBarcode.getText())) { //if item was taken
                    // check if we have barcodes scanned twice
                    if (!barcodeList.contains(tfItemBarcode.getText())) {

                        populateTableView();

                        //attributes for writeTakeToDB object, so we can insert the data in db
                        String itemBarcodeString = tfItemBarcode.getText();
                        java.util.Date date = new java.util.Date();
                        Timestamp timeStamp = new Timestamp(date.getTime());
                        String timeTaken = timeStamp.toString();

                        WriteReturnToDB writeReturnToDB = new WriteReturnToDB(itemBarcodeString, timeTaken);

                        returnItemList.add(writeReturnToDB);
                        //System.out.println(returnItemList.toString());


                        //add barcodes to list and after check for barcodes scanned twice
                        barcodeList.add(tfItemBarcode.getText());

                        tfItemBarcode.clear();
                    }else if (barcodeList.contains(tfItemBarcode.getText())) {
                        MainViewController.updateAlertMessage("You have already scanned this item");
                    }

                } else if (!businessLogic.searchItem(tfItemBarcode.getText())) { //item not taken
                    MainViewController.updateAlertMessage("Item was not taken by any employee");
                    tfItemBarcode.setText(null);
                }
            } else if (!businessLogic.checkItemBarcode(tfItemBarcode.getText()))  {
                MainViewController.updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in checkItemBarcode() from ReturnItemController class: " + e.getMessage());
        }
    }

    private void populateTableView() {

            try {
            /* SQL QUERY */
                String sql = " SELECT employeeName, itemName, place, timeTaken, category FROM BorrowedItem\n" +
                        "INNER JOIN Employee ON\n" +
                        "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                        "INNER JOIN Item ON\n" +
                        "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                        "INNER JOIN Category ON\n" +
                        "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                        "INNER JOIN Place ON\n" +
                        "BorrowedItem.id = Place.borrowedItemID\n" +
                        "WHERE BorrowedItem.itemBarcode = ? and timeReturned IS NULL;";



            /* EXECUTION OF QUERY */
                int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

                preparedStatement.setInt(1, inputBarcode);

                ResultSet result = preparedStatement.executeQuery();

                while ((result.next())) {
//                Employee employee = new Employee(0, null , result.getString("employeeName"), 0);
//                Item item = new Item(0, null, result.getString("itemName"), null);
//                BorrowedItem borrowedItem = new BorrowedItem(0, 0, 0, result.getString("place"), result.getString("timeTaken"), null);

                    String employeeName = result.getString("employeeName");
                    String itemCategory = result.getString("category");
                    String itemName = result.getString("itemName");
                    String place = result.getString("place");
                    String timeTaken = result.getString("timeTaken");
                    ReturnObj returnObj = new ReturnObj(employeeName, itemCategory, itemName, place, timeTaken);

                    returnItemData.setAll(returnObj);
                }

            } catch (Exception e) {
                e.printStackTrace();
                MainViewController.updateWarningMessage("Error");
                System.out.println("Exception in populateTableView() from ReturnItemController class: " + e.getMessage());
            }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
            employeeNameColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("employeeName"));
            itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("itemCategory"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("itemName"));
            placeColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("place"));
            timeTakenColumn.setCellValueFactory(new PropertyValueFactory<ReturnObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */

            tableView.getItems().addAll(returnItemData);
    }

}

package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.userTableViewObjects.ReturnObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


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


    private RunView runView;


    @FXML
    private void btnSubmit() {
        int textFieldInput = Integer.parseInt(tfItemBarcode.getText());
        try {
            if (tfItemBarcode.getLength() > 0) {
                if (businessLogic.searchItem(textFieldInput)) {

                   java.util.Date today = new java.util.Date();
                    Timestamp timeReturned = new Timestamp(today.getTime());
                    businessLogic.returnItem(textFieldInput);
                    businessLogic.updateBorrowedItemTable("BorrowedItem", timeReturned, textFieldInput);

                    updateAlertMessage("Item returned");
                    runView.showMainView();
                } else if (!businessLogic.searchItem(textFieldInput)) {
                    updateAlertMessage("Item has not been taken by employee");
                    tfItemBarcode.setText(null);
                }
            } else {
                updateAlertMessage("Missing item barcode");
            }
        } catch (Exception e) {
            System.out.println("Exception in btnSubmit() from ReturnItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack(){
        try{
            runView.showMainView();
        }catch (Exception e){
            System.out.println("Exception in btnBack() from ReturnItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {
        try {
            if (businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) { //if barcode exists
                if (businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) { //if item was taken
                    populateTableView();
                    //tableView.requestFocus();
                } else if (!businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) { //item not taken
                    updateAlertMessage("Item was not taken by any employee");
                    tfItemBarcode.setText(null);
                }
            } else if (!businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText())))  {
                updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
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

            while ((result.next()))
            {
//                Employee employee = new Employee(0, null , result.getString("employeeName"), 0);
//                Item item = new Item(0, null, result.getString("itemName"), null);
//                BorrowedItem borrowedItem = new BorrowedItem(0, 0, 0, result.getString("place"), result.getString("timeTaken"), null);

                    String employeeName = result.getString("employeeName");
                    String itemCategory = result.getString("category");
                    String itemName = result.getString("itemName");
                    String place = result.getString("place");
                    String timeTaken = result.getString("timeTaken");
                    ReturnObj returnObj = new ReturnObj(employeeName, itemCategory,  itemName, place, timeTaken);

                    returnItemData.setAll(returnObj);
                }

        } catch (Exception e) {
            e.printStackTrace();
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
    /* METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER */
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

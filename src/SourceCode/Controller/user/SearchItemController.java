package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.dbObj.BorrowedItem;
import SourceCode.Model.dbObj.Employee;
import SourceCode.Model.dbObj.Item;
import SourceCode.Model.userTableViewObjects.SearchObj;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;


public class SearchItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;

    private ObservableList searchItemData = FXCollections.observableArrayList();

    @FXML
    Button btnSearch;
    @FXML
    Button btnBack;
    // Table Columns
    @FXML
    TableColumn employeeNameColumn;
    @FXML
    TableColumn telephoneNoColumn;
    @FXML
    TableColumn itemCategoryColumn;
    @FXML
    TableColumn itemNameColumn;
    @FXML
    TableColumn placeColumn;
    @FXML
    TableColumn timeTakenColumn;

    @FXML
    Label employeeBarcodeLabel;
    @FXML
    TextField tfItemNumber;
    @FXML
    ComboBox comboBox;
    @FXML
    TableView tableView;

    private RunView runView;

    @FXML
    public void btnSearch(){
        try{
            System.out.println("Save button clicked");

            if ((tfItemNumber.getText() != null && !tfItemNumber.getText().isEmpty())) {
                employeeBarcodeLabel.setText(tfItemNumber.getText());
                employeeBarcodeLabel.setVisible(true);
            } else {
                employeeBarcodeLabel.setText("You have not left a comment.");
            }
            if(comboBox.getItems()!= null){
            }
        }catch (Exception e){
            System.out.println("Exception in btnSearch() from SearchItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack(){
        try{
            runView.showMainView();
        }catch (Exception e){
            System.out.println("Exception in btnBack() from SearchItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {

        try {

            if (businessLogic.checkItemBarcode(tfItemNumber.getText())||businessLogic.checkItemNo(tfItemNumber.getText())) {
                searchByItemBarcode();
                searchByItemNumber();

            } else {
                MainViewController.updateAlertMessage("Please check the barcode or the item number");
                tfItemNumber.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkItemBarcode()/itemNumber() from SearchItemController class:" + e.getMessage());
        }
    }


        public void searchByItemNumber() {

            try {
            /* SQL QUERY */
                String sql = "SELECT employeeName, phoneNumber, itemName, place, timeTaken, category FROM BorrowedItem\n" +
                        "INNER JOIN Employee ON\n" +
                        "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                        "INNER JOIN PhoneNumber ON\n" +
                        "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                        "INNER JOIN Category ON\n" +
                        "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                        "INNER JOIN Item ON\n" +
                        "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                        "INNER JOIN Place ON\n" +
                        "BorrowedItem.id = Place.borrowedItemID\n" +
                        "WHERE Item.itemNo = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
                //int inputItemNo = Integer.parseInt(tfItemNumber.getText());

                String inputItemNo = tfItemNumber.getText();
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
                preparedStatement.setString(1, inputItemNo);


                ResultSet result = preparedStatement.executeQuery();

                while ((result.next())) {

                    String employeeName = result.getString("employeeName");
                    String phoneNumber = result.getString("phoneNumber");
                    String itemCategory = result.getString("category");
                    String itemName = result.getString("itemName");
                    String place = result.getString("place");
                    String timeTaken = result.getString("timeTaken");
                    SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory,  itemName, place, timeTaken);

                    searchItemData.setAll(searchObj);

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception in searchByItemNo() from SearchItemController class: " + e.getMessage());
            }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
            employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
            telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
            itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
            placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
            timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
            tableView.getItems().addAll(searchItemData);
            searchItemData.clear();  //i did this because it would duplicate the last element if the item was returned

        }

    public void searchByItemBarcode() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, timeTaken, category FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "INNER JOIN Category ON\n" +
                    "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE BorrowedItem.itemBarcode = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfItemNumber.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String phoneNumber = result.getString("phoneNumber");
                String itemCategory = result.getString("category");
                String itemName = result.getString("itemName");
                String place = result.getString("place");
                String timeTaken = result.getString("timeTaken");
                SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory,  itemName, place, timeTaken);

                searchItemData.setAll(searchObj);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in searchByItemBarcode() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().addAll(searchItemData);
        searchItemData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

}

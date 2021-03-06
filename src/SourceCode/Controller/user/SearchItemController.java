package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.userTableViewObjects.SearchObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SearchItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;

    private ObservableList searchData = FXCollections.observableArrayList();

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
    TableColumn placeReferenceColumn;
    @FXML
    TableColumn timeTakenColumn;

    @FXML
    Label employeeBarcodeLabel;
    @FXML
    TextField tfSearch;
    @FXML
    ComboBox comboBox;
    @FXML
    TableView tableView;

    private RunView runView;

    @FXML
    private void initialize(){

        comboBox.getItems().addAll(businessLogic.getCategory());
    }

    @FXML
    public void btnSearch(){
        try{
            System.out.println("Save button clicked");

            if ((tfSearch.getText() != null && !tfSearch.getText().isEmpty())) {
                employeeBarcodeLabel.setText(tfSearch.getText());
                employeeBarcodeLabel.setVisible(true);
            } else {
                employeeBarcodeLabel.setText("You have not left a comment.");
            }
            if(comboBox.getItems()!= null){
            }
        }catch (Exception e){
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in btnSearch() from SearchItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack(){
        try{
            runView.showMainView();
        }catch (Exception e){
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in btnBack() from SearchItemController class:" + e.getMessage());
        }
    }


    @FXML
    private void checkTextField() {
        try {
            if (businessLogic.checkItemBarcode(tfSearch.getText()) || businessLogic.checkItemNo(tfSearch.getText())
                    || businessLogic.checkEmployeeName(tfSearch.getText())|| businessLogic.checkEmployeeBarcode(tfSearch.getText())) {
                if (businessLogic.seachItemBarcodeForSearchScreen(tfSearch.getText().toString())) {
                    searchByItemBarcode();
                    tfSearch.clear();

                }
                 else if (businessLogic.searchItemByNumber(tfSearch.getText())){
                    searchByItemNumber();
                    tfSearch.clear();

                }
                else if(businessLogic.searchEmployeeByName(tfSearch.getText())) {

                    searchByEmployeeName();
                    tfSearch.clear();

                }
                else if (businessLogic.searchEmployeeByBarcode(tfSearch.getText())){
                    searchByEmployeeBarcode();
                    tfSearch.clear();

                }
                else {
                    MainViewController.updateAlertMessage("Item(s) not taken");
                    tfSearch.clear();
                }

                //checks if it has been taken
            }else {
                MainViewController.updateAlertMessage("Please input the correct value");
                tfSearch.clear();
            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in checkTextField() from SearchItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void comboBoxCategory(){
        try {
                searchByComboBox();

        } catch (Exception ex) {
            MainViewController.updateWarningMessage("Error");
            System.out.println("combobox exception in comboBoxCategory()");
        }
    }


    public void searchByItemNumber() {

            try {
            /* SQL QUERY */
                String sql = "SELECT employeeName, phoneNumber, itemName, place, placeReference, timeTaken, itemCategory FROM BorrowedItem\n" +
                        "INNER JOIN Employee ON\n" +
                        "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                        "INNER JOIN PhoneNumber ON\n" +
                        "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                        "INNER JOIN Item ON\n" +
                        "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                        "INNER JOIN Place ON\n" +
                        "BorrowedItem.id = Place.borrowedItemID\n" +
                        "WHERE Item.itemNo = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
                //int inputItemNo = Integer.parseInt(tfSearch.getText());

                String inputItemNo = tfSearch.getText();
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
                preparedStatement.setString(1, inputItemNo);


                ResultSet result = preparedStatement.executeQuery();

                while ((result.next())) {

                    String employeeName = result.getString("employeeName");
                    String phoneNumber = result.getString("phoneNumber");
                    String itemCategory = result.getString("itemCategory");
                    String itemName = result.getString("itemName");
                    String place = result.getString("place");
                    String placeReference = result.getString("placeReference");
                    String timeTaken = result.getString("timeTaken");
                    SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory,  itemName, place, placeReference, timeTaken);

                    searchData.addAll(searchObj);

                }
            } catch (Exception e) {
                MainViewController.updateWarningMessage("Error");
                e.printStackTrace();
                System.out.println("Exception in searchByItemNo() from SearchItemController class: " + e.getMessage());
            }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
            employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
            telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
            itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
            placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
            placeReferenceColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("placeReference"));
            timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
            tableView.getItems().setAll(searchData);
            searchData.clear();  //i did this because it would duplicate the last element if the item was returned

        }

    public void searchByItemBarcode() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, placeReference, timeTaken, itemCategory FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE BorrowedItem.itemBarcode = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String phoneNumber = result.getString("phoneNumber");
                String itemCategory = result.getString("itemCategory");
                String itemName = result.getString("itemName");
                String place = result.getString("place");
                String placeReference = result.getString("placeReference");
                String timeTaken = result.getString("timeTaken");
                SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory,  itemName, place, placeReference, timeTaken);

                searchData.addAll(searchObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemBarcode() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
        placeReferenceColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("placeReference"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().setAll(searchData);
        searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }
    public void searchByEmployeeName() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, placeReference, timeTaken, itemCategory FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE Employee.employeeName = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
            //String inputBarcode = tfSearch.getText();
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String phoneNumber = result.getString("phoneNumber");
                String itemCategory = result.getString("itemCategory");
                String itemName = result.getString("itemName");
                String place = result.getString("place");
                String placeReference = result.getString("placeReference");
                String timeTaken = result.getString("timeTaken");
                SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory,  itemName, place, placeReference, timeTaken);

                searchData.addAll(searchObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeName() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
        placeReferenceColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("placeReference"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().setAll(searchData);
        searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }
    public void searchByEmployeeBarcode() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, placeReference, timeTaken, itemCategory FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE Employee.employeeBarcode = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */

            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String phoneNumber = result.getString("phoneNumber");
                String itemCategory = result.getString("itemCategory");
                String itemName = result.getString("itemName");
                String place = result.getString("place");
                String placeReference = result.getString("placeReference");
                String timeTaken = result.getString("timeTaken");
                SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory,  itemName, place, placeReference, timeTaken);

                searchData.addAll(searchObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeBarcode() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
        placeReferenceColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("placeReference"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().setAll(searchData);
        searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }
    public void searchByComboBox() {


        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, placeReference, timeTaken, itemCategory FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE Item.itemCategory = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
            String inputCategory = comboBox.getSelectionModel().getSelectedItem().toString();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputCategory);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String phoneNumber = result.getString("phoneNumber");
                String itemCategory = result.getString("itemCategory");
                String itemName = result.getString("itemName");
                String place = result.getString("place");
                String placeReference = result.getString("placeReference");
                String timeTaken = result.getString("timeTaken");
                SearchObj searchObj = new SearchObj(employeeName, phoneNumber, itemCategory, itemName, place, placeReference, timeTaken);

                searchData.addAll(searchObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByComboBox() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("phoneNo"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("place"));
        placeReferenceColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("placeReference"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().setAll(searchData);
        searchData.clear();  //i did this because it would duplicate the last element if the item was returned


    }


}

package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.adminTableViewObjects.BorrowedItemObj;
import SourceCode.Model.adminTableViewObjects.EmployeeObj;
import SourceCode.Model.adminTableViewObjects.ItemObj;
import SourceCode.Model.adminTableViewObjects.UsedProductObj;
import SourceCode.Model.dbTablesObjects.Employee;
import SourceCode.Model.dbTablesObjects.Item;
import SourceCode.Model.userTableViewObjects.SearchObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static SourceCode.Controller.RunView.adminController;

public class AdminController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;
    private ObservableList employeeTabData = FXCollections.observableArrayList();
    private ObservableList itemTabData = FXCollections.observableArrayList();
    private ObservableList borrowedItemTabData = FXCollections.observableArrayList();
    private ObservableList usedProductTabData = FXCollections.observableArrayList();

    private ObservableList searchData = FXCollections.observableArrayList();

    @FXML
    TextField tfSearch;
    //Tabs
    @FXML
    Tab employeeTab;
    @FXML
    Tab itemTab;
    @FXML
    Tab borrowedItemTab;
    @FXML
    Tab usedProductTab;
    //TableView
    @FXML
    TableView employeeTableView;
    @FXML
    TableView itemTableView;
    @FXML
    TableView borrowedItemTableView;
    @FXML
    TableView usedProductTableView;
    //Employee columns
    @FXML
    TableColumn employeeBarcodeColumn;
    @FXML
    TableColumn employeeNumberColumn;
    @FXML
    TableColumn employeeNameColumn;
    @FXML
    TableColumn employeePhoneColumn;
    //Item columns
    @FXML
    TableColumn itemBarcodeColumn;
    @FXML
    TableColumn itemNumberColumn;
    @FXML
    TableColumn itemNameColumn;
    @FXML
    TableColumn itemCategoryColumn;
    //Borrowed Item columns
    @FXML
    TableColumn borrowedItemEmployeeNameColumn;
    @FXML
    TableColumn borrowedItemNumberColumn;
    @FXML
    TableColumn borrowedItemCategoryColumn;
    @FXML
    TableColumn borrowedItemNameColumn;
    @FXML
    TableColumn borrowedItemTimeTakenColumn;
    @FXML
    TableColumn borrowedItemTimeReturnedColumn;
    //Used Item columns
    @FXML
    TableColumn usedProductEmployeeNameColumn;
    @FXML
    TableColumn usedProductItemNumberColumn;
    @FXML
    TableColumn usedProductItemNameColumn;
    @FXML
    TableColumn usedProductTimeTakenColumn;
    @FXML
    TableColumn usedProductQuantityColumn;
    String string;
    //UpdateEmployeeController updateEmployeeController = new UpdateEmployeeController();
    public ArrayList<EmployeeObj> employeeObjArrayList = new ArrayList<>();
    private RunView runView;

    public AdminController(){

    }

    public static AdminController getInstance(){
        if (adminController == null){
            adminController = new AdminController();
            return adminController;
        }
        return adminController;
    }

    public String toString()
    {
        return string;
    }


    @FXML
    private void btnCreateEmployee() throws IOException {
        runView.showCreateEmployee();
    }

    @FXML
    private void btnCreateItem() throws IOException {
        runView.showCreateItem();
    }

    @FXML
    private void btnLogout() throws IOException {
        runView.showMainView();
    }

    @FXML
    private void btnRefreshAction() throws IOException {
        runView.showAdminView();
    }
    @FXML
    private void btnDeleteAction() throws IOException{
        if (employeeTableView.getSelectionModel().getSelectedItem()!=null || itemTableView.getSelectionModel().getSelectedItem()!=null) {

            if (employeeTab.isSelected()) {
                EmployeeObj employeeObj = (EmployeeObj) employeeTableView.getSelectionModel().getSelectedItem();
                businessLogic.deleteEmployee(employeeObj.getEmployeeBarcode());

                int row = employeeTableView.getSelectionModel().getFocusedIndex();
                employeeTableView.getItems().remove(row); //removes row from tableview

                MainViewController.updateAlertMessage("Employee deleted");
            } else if (itemTab.isSelected()) {

                ItemObj itemObj = (ItemObj) itemTableView.getSelectionModel().getSelectedItem();
                businessLogic.deleteItem(itemObj.getItemBarcode());

                int row = itemTableView.getSelectionModel().getFocusedIndex();
                itemTableView.getItems().remove(row); //removes row from tableview

                MainViewController.updateAlertMessage("Item deleted");
            } else {
                MainViewController.updateWarningMessage("There has been an error while trying to delete");
                System.out.println("Error in btnDeleteAction() in AdminController");
            }
        }else {
            MainViewController.updateAlertMessage("Please select row to delete");
        }
    }

    @FXML
    private void btnUpdateAction() throws IOException {
        if (employeeTableView.getSelectionModel().getSelectedItem()!=null || itemTableView.getSelectionModel().getSelectedItem()!=null) {
            if (employeeTab.isSelected()) {
                if (employeeTableView.getSelectionModel().getSelectedItem() != null) {
                    runView.showUpdateEmployee();
                }
            } else if (itemTab.isSelected()) {
                if (itemTableView.getSelectionModel().getSelectedItem() != null) {
                    runView.showUpdateItem();
                }
            } else {
                MainViewController.updateAlertMessage("Please select a row to update");
            }
        }else {
            MainViewController.updateAlertMessage("Please select row to update");
        }

    }

    @FXML
    private void initialize(){

        populateEmployee();
        populateItem();
        populateBorrowedItem();
        populateUsedProduct();

    }
    //stores selected row to file
    @FXML
    public void employeeTableViewAction() throws UnsupportedEncodingException, FileNotFoundException, NullPointerException, InvocationTargetException{
        try {
            String employeeBarcode = null;
            ArrayList<EmployeeObj> list = new ArrayList<>();
            EmployeeObj employeeObj = (EmployeeObj) employeeTableView.getSelectionModel().getSelectedItem();
            list.add(employeeObj);
            employeeBarcode = list.get(0).getEmployeeBarcode();
            PrintWriter writer = new PrintWriter("/Users/Cristian/Desktop/CTIS/src/Resources/employeeBarcode.txt", "UTF-8");
            writer.println(employeeBarcode);
            writer.close();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    //stores selected row to file
    @FXML
    public void itemTableViewAction() throws UnsupportedEncodingException, FileNotFoundException, NullPointerException,java.lang.reflect.InvocationTargetException, RuntimeException{
        String itemBarcode = null;
        ArrayList<ItemObj> list = new ArrayList<>();
        ItemObj itemObj = (ItemObj) itemTableView.getSelectionModel().getSelectedItem();
        list.add(itemObj);
        itemBarcode = list.get(0).getItemBarcode();
        PrintWriter writer = new PrintWriter("/Users/Cristian/Desktop/CTIS/src/Resources/itemBarcode.txt", "UTF-8");
        writer.println(itemBarcode);
        writer.close();

    }


    @FXML
    //EMPLOYEE TAB
    private void populateEmployee() {


        try {
            /* SQL QUERY */
            String sql = "SELECT Employee.employeeBarcode, Employee.employeeNo, Employee.employeeName, PhoneNumber.phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "ORDER BY employeeNo;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeBarcode = result.getString("employeeBarcode");
                String employeeNo = result.getString("employeeNo");
                String employeeName = result.getString("employeeName");
                String phoneNumber = result.getString("phoneNumber");

                EmployeeObj employeeObj = new EmployeeObj(employeeBarcode, employeeNo, employeeName, phoneNumber);

                employeeTabData.addAll(employeeObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateTableView() from TakeItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeBarcodeColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeBarcode"));
        employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeNo"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeName"));
        employeePhoneColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("phoneNumber"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        employeeTableView.getItems().addAll(employeeTabData);
    }

    @FXML
    protected void populateItem() {

            try {
            /* SQL QUERY */
                String sql = "SELECT Item.itemBarcode, Item.itemNo, Item.itemName, Category.category FROM Item\n" +
                        "INNER JOIN Category ON\n" +
                        "Item.itemBarcode = Category.itemBarcode\n" +
                        "ORDER BY category;";

            /* EXECUTION OF QUERY */
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

                ResultSet result = preparedStatement.executeQuery();

                while ((result.next())) {

                    String itembarcode = result.getString("itemBarcode");
                    String itemNo = result.getString("itemNo");
                    String itemName = result.getString("itemName");
                    String category = result.getString("category");
                    ItemObj itemObj = new ItemObj(itembarcode, itemNo, itemName, category);

                    itemTabData.addAll(itemObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception in populateTableView() from TakeItemController class: " + e.getMessage());
            }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
            itemBarcodeColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemBarcode"));
            itemNumberColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemNo"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemName"));
            itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("category"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
            itemTableView.getItems().addAll(itemTabData);

    }

    @FXML
    private void populateBorrowedItem() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, category, itemName, timeTaken, timeReturned FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Category ON\n" +
                    "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "ORDER BY timeTaken;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("category");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                BorrowedItemObj borrowedItemObj =
                        new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned);

                borrowedItemTabData.addAll(borrowedItemObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateBorrowedItem() from TakeItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("employeeName"));
        borrowedItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("itemCategory"));
        borrowedItemNumberColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("itemNumber"));
        borrowedItemNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("itemName"));
        borrowedItemTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("timeTaken"));
        borrowedItemTimeReturnedColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("timeReturned"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().addAll(borrowedItemTabData);
    }

    @FXML
    private void populateUsedProduct() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
                    "INNER JOIN Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "ORDER BY timeTaken;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String quantityTaken = result.getString("quantityTaken");
                UsedProductObj usedProductObj =
                        new UsedProductObj(employeeName, itemNumber, itemName, timeTaken, quantityTaken);

                usedProductTabData.addAll(usedProductObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateUsedProduct() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        usedProductEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("employeeName"));
        usedProductItemNumberColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("itemNumber"));
        usedProductItemNameColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("itemName"));
        usedProductTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("timeTaken"));
        usedProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("quantityTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        usedProductTableView.getItems().addAll(usedProductTabData);
    }

    @FXML
    private void searchTfAction(){
        String input = tfSearch.getText();
        try {
            if (itemTab.isSelected()) {
                if (businessLogic.checkItemBarcode(input) || businessLogic.checkItemNo(input)
                        || businessLogic.checkItemCategory(input) || businessLogic.checkItemName(input)) {
                    searchData.clear();
                    itemTableView.getItems().clear();
                    searchByItemBarcodeInItem();
                    searchByItemNumberInItem();
                    searchByItemCategoryInItem();
                    searchByItemNameInItem();
                } else {
                    MainViewController.updateAlertMessage("Please check the value again");
                }
            }
            if (employeeTab.isSelected()) {
                if (businessLogic.checkEmployeeBarcode(input) || businessLogic.checkEmployeeNumber(input) ||
                         businessLogic.checkEmployeeName(input)) {
                    employeeTableView.getItems().clear();
                    searchData.clear();
                    searchByEmployeeBarcodeInEmployee();
                    searchByEmployeeNumberInEmployee();
                    searchByEmployeeNameInEmployee();

                } else {
                    MainViewController.updateAlertMessage("Please check the value again");
                }
            }
            if (borrowedItemTab.isSelected()){
                if (businessLogic.checkEmployeeName(input) || businessLogic.checkItemCategory(input)
                        || businessLogic.checkItemNo(input) || businessLogic.checkItemName(input)) {
                    searchData.clear();
                    borrowedItemTableView.getItems().clear();
                    searchByEmployeeNameInBorrowed();
                    searchByCategoryInBorrowed();
                    searchByItemNumberInBorrowed();
                    searchByItemNameInBorrowed();
                }else {
                    MainViewController.updateAlertMessage("Please check the value again");
                }

            }
            if (usedProductTab.isSelected()){
                if (businessLogic.checkEmployeeName(input) || businessLogic.checkItemNo(input) || businessLogic.checkItemName(input)){
                    searchData.clear();
                    usedProductTableView.getItems().clear();
                    searchByEmployeeNameInUsed();
                    searchByItemNumberInUsed();
                    searchByItemNameInUsed();
                }else {
                    MainViewController.updateWarningMessage("Please check the value again");
                }
            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in searchTfAction() from AdminController class:" + e.getMessage());
        }

    }

    @FXML
    private void searchByItemBarcodeInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Item.itemBarcode, itemNo, itemName, category FROM Item\n" +
                    "INNER JOIN Category ON\n" +
                    "Item.itemBarcode = Category.itemBarcode\n" +
                    "WHERE Item.itemBarcode = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemBarcode = result.getString("itemBarcode");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String itemCategory = result.getString("category");
                ItemObj itemObj = new ItemObj(itemBarcode, itemNumber, itemName, itemCategory);

                searchData.addAll(itemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemBarcodeInItem() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        itemBarcodeColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemBarcode"));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemNo"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemName"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("category"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNumberInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Item.itemBarcode, Item.itemNo, itemName, category FROM Item\n" +
                    "INNER JOIN Category ON\n" +
                    "Item.itemBarcode = Category.itemBarcode\n" +
                    "WHERE Item.itemNo = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemBarcode = result.getString("itemBarcode");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String itemCategory = result.getString("category");
                ItemObj itemObj = new ItemObj(itemBarcode, itemNumber, itemName, itemCategory);

                searchData.addAll(itemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumberInItem() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        itemBarcodeColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemBarcode"));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemNo"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemName"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("category"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemCategoryInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Item.itemBarcode, Item.itemNo, itemName, category FROM Item\n" +
                    "INNER JOIN Category ON\n" +
                    "Item.itemBarcode = Category.itemBarcode\n" +
                    "WHERE Category.category = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemBarcode = result.getString("itemBarcode");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String itemCategory = result.getString("category");
                ItemObj itemObj = new ItemObj(itemBarcode, itemNumber, itemName, itemCategory);

                searchData.addAll(itemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumberInItem() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        itemBarcodeColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemBarcode"));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemNo"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemName"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("category"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNameInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Item.itemBarcode, Item.itemNo, itemName, category FROM Item\n" +
                    "INNER JOIN Category ON\n" +
                    "Item.itemBarcode = Category.itemBarcode\n" +
                    "WHERE Item.itemName = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemBarcode = result.getString("itemBarcode");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String itemCategory = result.getString("category");
                ItemObj itemObj = new ItemObj(itemBarcode, itemNumber, itemName, itemCategory);

                searchData.addAll(itemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumberInItem() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        itemBarcodeColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemBarcode"));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemNo"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemName"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("category"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByEmployeeBarcodeInEmployee() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Employee.employeeBarcode, employeeNo, employeeName, phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "WHERE Employee.employeeBarcode = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeBarcode = result.getString("employeebarcode");
                String employeeNumber = result.getString("employeeNo");
                String employeeName = result.getString("employeeName");
                String employeePhone = result.getString("phoneNumber");
                EmployeeObj employeeObj = new EmployeeObj(employeeBarcode, employeeNumber, employeeName, employeePhone);

                searchData.addAll(employeeObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeBarcodeInEmployee() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeBarcodeColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeBarcode"));
        employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeNo"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeName"));
        employeePhoneColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("phoneNumber"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        employeeTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByEmployeeNumberInEmployee() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Employee.employeeBarcode, Employee.employeeNo, employeeName, phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "WHERE Employee.employeeNo = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeBarcode = result.getString("employeebarcode");
                String employeeNumber = result.getString("employeeNo");
                String employeeName = result.getString("employeeName");
                String employeePhone = result.getString("phoneNumber");
                EmployeeObj employeeObj = new EmployeeObj(employeeBarcode, employeeNumber, employeeName, employeePhone);

                searchData.addAll(employeeObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeNumberInEmployee() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeBarcodeColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeBarcode"));
        employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeNo"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeName"));
        employeePhoneColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("phoneNumber"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        employeeTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByEmployeeNameInEmployee() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Employee.employeeBarcode, Employee.employeeNo, Employee.employeeName, phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "WHERE Employee.employeeName = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeBarcode = result.getString("employeebarcode");
                String employeeNumber = result.getString("employeeNo");
                String employeeName = result.getString("employeeName");
                String employeePhone = result.getString("phoneNumber");
                EmployeeObj employeeObj = new EmployeeObj(employeeBarcode, employeeNumber, employeeName, employeePhone);

                searchData.addAll(employeeObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeNameInEmployee() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        employeeBarcodeColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeBarcode"));
        employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeNo"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("employeeName"));
        employeePhoneColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObj, String>("phoneNumber"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        employeeTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByEmployeeNameInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, category, itemNo, itemName, timeTaken, timeReturned FROM BorrowedItem\n" +
                    "INNER JOIN Category ON\n" +
                    "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                    "INNER JOIN  Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itembarcode = Item.itemBarcode\n" +
                    "WHERE Employee.employeeName = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("category");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned);

                searchData.addAll(borrowedItemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeNameInBorrowed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        borrowedItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        borrowedItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        borrowedItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        borrowedItemTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        borrowedItemTimeReturnedColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeReturned"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByCategoryInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, category, itemNo, itemName, timeTaken, timeReturned FROM BorrowedItem\n" +
                    "INNER JOIN Category ON\n" +
                    "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                    "INNER JOIN  Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itembarcode = Item.itemBarcode\n" +
                    "WHERE Category.category = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("category");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned);

                searchData.addAll(borrowedItemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByCategoryInBorrowed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        borrowedItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        borrowedItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        borrowedItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        borrowedItemTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        borrowedItemTimeReturnedColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeReturned"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNumberInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, category, itemNo, itemName, timeTaken, timeReturned FROM BorrowedItem\n" +
                    "INNER JOIN Category ON\n" +
                    "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                    "INNER JOIN  Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itembarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemNo = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("category");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned);

                searchData.addAll(borrowedItemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumberInBorrowed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        borrowedItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        borrowedItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        borrowedItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        borrowedItemTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        borrowedItemTimeReturnedColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeReturned"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNameInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, category, itemNo, itemName, timeTaken, timeReturned FROM BorrowedItem\n" +
                    "INNER JOIN Category ON\n" +
                    "BorrowedItem.itemBarcode = Category.itemBarcode\n" +
                    "INNER JOIN  Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itembarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemName = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("category");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned);

                searchData.addAll(borrowedItemObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNameInBorrowed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        borrowedItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemCategory"));
        borrowedItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        borrowedItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        borrowedItemTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        borrowedItemTimeReturnedColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeReturned"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByEmployeeNameInUsed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Employee.employeeName = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String quantityTaken = result.getString("quantityTaken");
                UsedProductObj usedProductObj = new UsedProductObj(employeeName, itemNumber, itemName,  timeTaken, quantityTaken);

                searchData.addAll(usedProductObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByEmployeeNameInUsed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        usedProductEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        usedProductItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        usedProductItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        usedProductTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        usedProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("quantityTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        usedProductTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNumberInUsed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemNo = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String quantityTaken = result.getString("quantityTaken");
                UsedProductObj usedProductObj = new UsedProductObj(employeeName, itemNumber, itemName,  timeTaken, quantityTaken);

                searchData.addAll(usedProductObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumberInUsed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        usedProductEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        usedProductItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        usedProductItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        usedProductTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        usedProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("quantityTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        usedProductTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNameInUsed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemName = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String quantityTaken = result.getString("quantityTaken");
                UsedProductObj usedProductObj = new UsedProductObj(employeeName, itemNumber, itemName,  timeTaken, quantityTaken);

                searchData.addAll(usedProductObj);

            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumberInUsed() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        usedProductEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("employeeName"));
        usedProductItemNumberColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemNumber"));
        usedProductItemNameColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("itemName"));
        usedProductTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("timeTaken"));
        usedProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<SearchObj, String>("quantityTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        usedProductTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }
}








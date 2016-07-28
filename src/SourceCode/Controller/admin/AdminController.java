package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.adminTableViewObjects.*;
import SourceCode.Model.userTableViewObjects.SearchObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

import static SourceCode.Controller.RunView.adminController;

public class AdminController {
    BusinessLogic businessLogic = new BusinessLogic();
    ConnectDB connectDB = Factory.connectDB;
    private ObservableList employeeTabData = FXCollections.observableArrayList();
    private ObservableList itemTabData = FXCollections.observableArrayList();
    private ObservableList borrowedItemTabData = FXCollections.observableArrayList();
    private ObservableList usedProductTabData = FXCollections.observableArrayList();
    private ObservableList categoryTabData = FXCollections.observableArrayList();

    private ObservableList searchData = FXCollections.observableArrayList();

    @FXML
    TextField tfSearch;
    //Tabs
    @FXML
    Tab employeeTab;
    @FXML
    Tab itemTab;
    @FXML
    Tab categoryTab;
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
    @FXML
    TableView categoryTableView;
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
    @FXML
    TableColumn borrowedItemFunctionalColumn;
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
    @FXML
    TableColumn categoryColumn;
    @FXML
    DatePicker fromDatePicker;
    @FXML
    DatePicker toDatePicker;
    @FXML
    Button btnCreateEmployee;
    @FXML
    Button btnCreateItem;
    @FXML
    Button btnCreateCategory;
    @FXML
    Button btnUpdate;
    @FXML
    Button btnDelete;



    //UpdateEmployeeController updateEmployeeController = new UpdateEmployeeController();
    public ArrayList<EmployeeObj> employeeObjArrayList = new ArrayList<>();
    private RunView runView;

    @FXML
    private void initialize(){

        populateEmployee();
        populateItem();
        populateCategory();
        populateBorrowedItem();
        populateUsedProduct();

        fromDatePicker.setVisible(false);
        toDatePicker.setVisible(false);

    }

    public AdminController(){

    }

    @FXML
    private void btnCreateEmployeeAction() throws IOException {
        runView.showCreateEmployee();
    }
    @FXML
    private void btnCreateItemAction() throws IOException {
        runView.showCreateItem();
    }

    @FXML
    private void btnCreateCategoryAction() throws IOException {
        runView.showCreateCategory();
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
        if (employeeTableView.getSelectionModel().getSelectedItem()!=null || itemTableView.getSelectionModel().getSelectedItem()!=null
                || categoryTableView.getSelectionModel().getSelectedItem()!=null) {

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
            } else if (categoryTab.isSelected()) {

                CategoryObj categoryObj = (CategoryObj) categoryTableView.getSelectionModel().getSelectedItem();
                businessLogic.deleteCategory(categoryObj.getCategory());

                int row = categoryTableView.getSelectionModel().getFocusedIndex();
                categoryTableView.getItems().remove(row);//removes row from tableview

                MainViewController.updateAlertMessage("Category deleted");


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
        if (employeeTableView.getSelectionModel().getSelectedItem()!=null || itemTableView.getSelectionModel().getSelectedItem()!=null
                ||categoryTableView.getSelectionModel().getSelectedItem() !=null) {
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
    //stores selected row to file
    @FXML
    public void employeeTableViewAction() throws UnsupportedEncodingException, FileNotFoundException, NullPointerException, InvocationTargetException{
        try {
            String employeeBarcode = null;
            ArrayList<EmployeeObj> list = new ArrayList<>();
            EmployeeObj employeeObj = (EmployeeObj) employeeTableView.getSelectionModel().getSelectedItem();
            list.add(employeeObj);
            employeeBarcode = list.get(0).getEmployeeBarcode();
            PrintWriter writer = new PrintWriter("src/Resources/employeeBarcode.txt", "UTF-8");///Users/Cristian/Desktop/CTIS/
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
        PrintWriter writer = new PrintWriter("src/Resources/itemBarcode.txt", "UTF-8");
        writer.println(itemBarcode);
        writer.close();

    }
    @FXML
    public void categoryTableViewAction() throws UnsupportedEncodingException, FileNotFoundException, NullPointerException, InvocationTargetException{
        try {
            String category = null;
            ArrayList<CategoryObj> list = new ArrayList<>();
            CategoryObj categoryObj = (CategoryObj) categoryTableView.getSelectionModel().getSelectedItem();
            list.add(categoryObj);
            category = list.get(0).getCategory();
            PrintWriter writer = new PrintWriter("src/Resources/category.txt", "UTF-8");///Users/Cristian/Desktop/CTIS/
            writer.println(category);
            writer.close();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    @FXML
    //EMPLOYEE TAB
    private void populateEmployee() {


        try {
            /* SQL QUERY */
            String sql = "SELECT Employee.employeeBarcode, Employee.employeeNo, Employee.employeeName, PhoneNumber.phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "ORDER BY employeeNo ASC;";

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
                String sql = "SELECT Item.itemBarcode, Item.itemNo, Item.itemName, Item.itemCategory FROM Item\n" +
                        "ORDER BY itemNo ASC\n" +
                        "LIMIT 50";

            /* EXECUTION OF QUERY */
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

                ResultSet result = preparedStatement.executeQuery();

                while ((result.next())) {

                    String itembarcode = result.getString("itemBarcode");
                    String itemNo = result.getString("itemNo");
                    String itemName = result.getString("itemName");
                    String itemCategory = result.getString("itemCategory");
                    ItemObj itemObj = new ItemObj(itembarcode, itemNo, itemName, itemCategory);

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
            itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemCategory"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
            itemTableView.getItems().setAll(itemTabData);

    }

    @FXML
    private void populateCategory() {

        try {
            /* SQL QUERY */
            String sql = "SELECT * FROM Category;";


            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String category = result.getString("category");

                CategoryObj categoryObj = new CategoryObj(category);

                categoryTabData.addAll(categoryObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateUsedProduct() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        categoryColumn.setCellValueFactory(new PropertyValueFactory<CategoryObj, String>("category"));


        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        categoryTableView.getItems().addAll(categoryTabData);
    }

    @FXML
    private void populateBorrowedItem() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, itemCategory, itemName, timeTaken, timeReturned, functional FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "ORDER BY timeTaken DESC\n" +
                    "LIMIT 30;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("itemCategory");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                String functional = result.getString("functional");

                BorrowedItemObj borrowedItemObj =
                        new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned, functional);

                borrowedItemTabData.addAll(borrowedItemObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateBorrowedItem() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("employeeName"));
        borrowedItemCategoryColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("itemCategory"));
        borrowedItemNumberColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("itemNumber"));
        borrowedItemNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("itemName"));
        borrowedItemTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("timeTaken"));
        borrowedItemTimeReturnedColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("timeReturned"));
        borrowedItemFunctionalColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("functional"));

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
                    "ORDER BY timeTaken DESC\n" +
                    "LIMIT 30;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                int quantityTaken = result.getInt("quantityTaken");
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
                if (businessLogic.checkItemBarcode(input)){
                    searchData.clear();
                    itemTableView.getItems().clear();
                    searchByItemBarcodeInItem();
                }else if (businessLogic.checkItemNo(input)){
                    searchData.clear();
                    itemTableView.getItems().clear();
                    searchByItemNumberInItem();
                }else if (businessLogic.checkItemCategory(input)){
                    searchData.clear();
                    itemTableView.getItems().clear();
                    searchByItemCategoryInItem();
                }else if (businessLogic.checkItemName(input)){
                    searchData.clear();
                    itemTableView.getItems().clear();
                    searchByItemNameInItem();
                } else {
                    MainViewController.updateAlertMessage("Please check the value again");
                }

//                if (businessLogic.checkItemBarcode(input) || businessLogic.checkItemNo(input)
//                        || businessLogic.checkItemCategory(input) || businessLogic.checkItemName(input)) {
//                    searchData.clear();
//                    itemTableView.getItems().clear();
//                    searchByItemBarcodeInItem();
//                    searchByItemNumberInItem();
//                    searchByItemCategoryInItem();
//                    searchByItemNameInItem();
//                } else {
//                    MainViewController.updateAlertMessage("Please check the value again");
//                }

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



                if (fromDatePicker.getValue() != null && toDatePicker.getValue() !=null) {
                    if (businessLogic.checkEmployeeName(input) || businessLogic.checkItemNo(input) || businessLogic.checkItemName(input)
                            || businessLogic.checkEmployeeBarcode(input) || businessLogic.checkItemBarcode(input)) {
                        searchData.clear();
                        usedProductTableView.getItems().clear();

                        searchByEmployeeNameInUsed();
                        searchByEmployeeBarcodeInUsed();
                        searchByItemBarcodeInUsed();
                        searchByItemNumberInUsed();
                        searchByItemNameInUsed();
                    } else {
                        MainViewController.updateWarningMessage("Please check the value again");
                    }
                }else {
                    MainViewController.updateWarningMessage("Please select From and To dates");
                }
            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            System.out.println("Exception in searchTfAction() from AdminController class:" + e.getMessage());
        }
    }
    @FXML
    private void usedProductTabAction(){
        if (usedProductTab.isSelected()) {
            btnCreateEmployee.setVisible(false);
            btnCreateItem.setVisible(false);
            btnCreateCategory.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            fromDatePicker.setVisible(true);
            toDatePicker.setVisible(true);
        }else {
            btnCreateEmployee.setVisible(true);
            btnCreateItem.setVisible(true);
            btnCreateCategory.setVisible(true);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
            fromDatePicker.setVisible(false);
            toDatePicker.setVisible(false);

        }

    }

    @FXML
    private void searchByItemBarcodeInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT itemBarcode, itemNo, itemName, itemCategory FROM Item\n" +
                    "WHERE itemBarcode = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemBarcode = result.getString("itemBarcode");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String itemCategory = result.getString("itemCategory");
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
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemCategory"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNumberInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Item.itemBarcode, Item.itemNo, itemName, itemCategory FROM Item\n" +
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
                String itemCategory = result.getString("itemCategory");
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
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemCategory"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemCategoryInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT itemBarcode, itemNo, itemName, itemCategory FROM Item\n" +
                    "WHERE itemCategory = ?;";

            /* EXECUTION OF QUERY */
            String inputCategory = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputCategory);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemBarcode = result.getString("itemBarcode");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String itemCategory = result.getString("itemCategory");
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
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemCategory"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        itemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNameInItem() {

        try {

            /* SQL QUERY */
            String sql = "SELECT Item.itemBarcode, Item.itemNo, itemName, itemCategory FROM Item\n" +
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
                String itemCategory = result.getString("itemCategory");
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
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemObj, String>("itemCategory"));

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
            String sql = "SELECT employeeName, itemCategory, itemNo, itemName, timeTaken, timeReturned, functional FROM BorrowedItem\n" +
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
                String itemCategory = result.getString("itemCategory");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                String functional = result.getString("functional");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned, functional);

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
        borrowedItemFunctionalColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("functional"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByCategoryInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemCategory, itemNo, itemName, timeTaken, timeReturned, functional FROM BorrowedItem\n" +
                    "INNER JOIN  Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itembarcode = Item.itemBarcode\n" +
                    "WHERE itemCategory = ?;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemCategory = result.getString("itemCategory");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                String functional = result.getString("functional");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned, functional);

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
        borrowedItemFunctionalColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("functional"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNumberInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemCategory, itemNo, itemName, timeTaken, timeReturned, functional FROM BorrowedItem\n" +
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
                String itemCategory = result.getString("itemCategory");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                String functional = result.getString("functional");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned, functional);

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
        borrowedItemFunctionalColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("functional"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private void searchByItemNameInBorrowed() {

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemCategory, itemNo, itemName, timeTaken, timeReturned, functional FROM BorrowedItem\n" +
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
                String itemCategory = result.getString("itemCategory");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                String functional = result.getString("functional");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemCategory, itemNumber, itemName, timeTaken, timeReturned, functional);

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
        borrowedItemFunctionalColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("functional"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        borrowedItemTableView.getItems().setAll(searchData);
        //searchData.clear();  //i did this because it would duplicate the last element if the item was returned

    }

    @FXML
    private Date fromDateAction(){
        Date date = Date.valueOf(fromDatePicker.getValue());
        return date;
    }
    @FXML
    private Date toDateAction(){
        Date date = Date.valueOf(toDatePicker.getValue());
        return date;
    }

    @FXML
    private void searchByEmployeeNameInUsed() {
        int total = 0;

        try {

            /* SQL QUERY */
//            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
//                    "INNER JOIN  Employee ON\n" +
//                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
//                    "INNER JOIN Item ON\n" +
//                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
//                    "WHERE Employee.employeeName = ?;";
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken, SUM(quantityTaken) as Total FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Employee.employeeName = ?\n" +
                    "AND (timeTaken BETWEEN ? AND ?)\n" +
                    "GROUP BY itemNo;";
//            "AND (timeTaken BETWEEN curdate()-INTERVAL 30 DAY AND CURDATE()+1)\n" +

            /* EXECUTION OF QUERY */
            String employeeName = tfSearch.getText();

            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, employeeName);
            preparedStatement.setDate(2, fromDateAction());
            preparedStatement.setDate(3, toDateAction());
            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String name = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                int quantityTaken = result.getInt("Total");
                UsedProductObj usedProductObj = new UsedProductObj(name, itemNumber, itemName,  timeTaken, quantityTaken);

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
        int totalQuantityTaken = 0;

        try {

            /* SQL QUERY */
//            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
//                    "INNER JOIN  Employee ON\n" +
//                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
//                    "INNER JOIN Item ON\n" +
//                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
//                    "WHERE Item.itemNo = ?;";
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken, SUM(quantityTaken) AS total FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemNo = ?\n" +
                    "GROUP BY itemNo;";

            /* EXECUTION OF QUERY */
            String inputBarcode = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

//            while (result.next()){
//                int quantityTaken = result.getInt("quantityTaken");
//                totalQuantityTaken = quantityTaken + totalQuantityTaken;
//                System.out.println(totalQuantityTaken);
//            }

            if (result.next()) {

                String employeeName = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                int quantityTaken = result.getInt("total");
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
    private void searchByEmployeeBarcodeInUsed() {
        int total = 0;

        try {

            /* SQL QUERY */
//            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
//                    "INNER JOIN  Employee ON\n" +
//                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
//                    "INNER JOIN Item ON\n" +
//                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
//                    "WHERE Employee.employeeName = ?;";
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken, SUM(quantityTaken) as Total FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Employee.employeeBarcode = ?\n" +
                    "AND (timeTaken BETWEEN ? AND ?)\n" +
                    "GROUP BY itemNo;";
//            "AND (timeTaken BETWEEN curdate()-INTERVAL 30 DAY AND CURDATE()+1)\n" +

            /* EXECUTION OF QUERY */
            String employeeBarcode = tfSearch.getText();

            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, employeeBarcode);
            preparedStatement.setDate(2, fromDateAction());
            preparedStatement.setDate(3, toDateAction());
            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String name = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                int quantityTaken = result.getInt("Total");
                UsedProductObj usedProductObj = new UsedProductObj(name, itemNumber, itemName,  timeTaken, quantityTaken);

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
    private void searchByItemBarcodeInUsed() {
        int total = 0;

        try {

            /* SQL QUERY */
//            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
//                    "INNER JOIN  Employee ON\n" +
//                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
//                    "INNER JOIN Item ON\n" +
//                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
//                    "WHERE Employee.employeeName = ?;";
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken, SUM(quantityTaken) as Total FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemBarcode = ?\n" +
                    "AND (timeTaken BETWEEN ? AND ?)\n" +
                    "GROUP BY itemNo;";
//            "AND (timeTaken BETWEEN curdate()-INTERVAL 30 DAY AND CURDATE()+1)\n" +

            /* EXECUTION OF QUERY */
            String employeeBarcode = tfSearch.getText();

            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, employeeBarcode);
            preparedStatement.setDate(2, fromDateAction());
            preparedStatement.setDate(3, toDateAction());
            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String name = result.getString("employeeName");
                String itemNumber = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                int quantityTaken = result.getInt("Total");
                UsedProductObj usedProductObj = new UsedProductObj(name, itemNumber, itemName,  timeTaken, quantityTaken);

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

    private int countQuantity(){


        int totalQuantityTaken = 0;

        try {

            /* SQL QUERY */
            String sql = "SELECT employeeName, itemNo, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
                    "INNER JOIN  Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode\n" +
                    "WHERE Item.itemNo = ?;";

            /* EXECUTION OF QUERY */
            String employeeName = tfSearch.getText();
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, employeeName);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()){
                int quantityTaken = result.getInt("quantityTaken");
                totalQuantityTaken = quantityTaken + totalQuantityTaken;
                System.out.println(totalQuantityTaken);
            }
        } catch (Exception e) {
            MainViewController.updateWarningMessage("Error");
            e.printStackTrace();
            System.out.println("Exception in countQuantity() from AdminController class: " + e.getMessage());
        }
        return totalQuantityTaken;
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
                int quantityTaken = result.getInt("quantityTaken");
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
/*
use sql7124928;
SELECT itemBarcode, SUM(quantityTaken) AS Total
FROM UsedProduct WHERE employeeBarcode = '1111'
GROUP BY itemBarcode;
 */







package SourceCode.Controller.admin;

import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.adminTableViewObjects.BorrowedItemObj;
import SourceCode.Model.adminTableViewObjects.EmployeeObj;
import SourceCode.Model.adminTableViewObjects.ItemObj;
import SourceCode.Model.adminTableViewObjects.UsedProductObj;
import SourceCode.Model.userTableViewObjects.TakeObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminController {
    private RunView runView;
    ConnectDB connectDB = Factory.connectDB;
    private ObservableList employeeTabData = FXCollections.observableArrayList();
    private ObservableList itemTabData = FXCollections.observableArrayList();
    private ObservableList borrowedItemTabData = FXCollections.observableArrayList();
    private ObservableList usedProductTabData = FXCollections.observableArrayList();
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
    TableColumn borrowedItemNameColumn;
    @FXML
    TableColumn borrowedItemTimeTakenColumn;
    @FXML
    TableColumn borrowedItemTimeReturnedColumn;
    //Used Item columns
    @FXML
    TableColumn usedProductEmployeeNameColumn;
    @FXML
    TableColumn usedProductItemNameColumn;
    @FXML
    TableColumn usedProductTimeTakenColumn;
    @FXML
    TableColumn usedProductQuantityColumn;

    @FXML
    private void btnCreateEmployee() throws IOException {
        runView.showCreateEmployee();
    }
    @FXML
    private void btnCreateItem() throws IOException {
        runView.showCreateItem();
    }
    @FXML
    private void btnLogOut() throws IOException{
        runView.showMainView();
    }

    //we say initialize so the fxml can recognize the method,
    //we don't assign the method in fxml, it takes it automatically
    @FXML
    private void initialize(){
        populateEmployee();
        populateItem();
        populateBorrowedItem();
        populateUsedProduct();
        //FOR ME
        //TODO add barcodes to each method and store them into the object
        //TODO so we can manage the rows[delete/update(dont show them in the tableview)]

        //TODO instead of itemName, change to itemNumber(search purposes, also delete if taken/returned item was mistaken)

    }
    @FXML
    //EMPLOYEE TAB
    private void populateEmployee() {

        try {
            /* SQL QUERY */
            String sql = "SELECT Employee.employeeBarcode, Employee.employeeNo, Employee.employeeName, PhoneNumber.phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode;";

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
    private void populateItem() {

        try {
            /* SQL QUERY */
            String sql = " SELECT Item.itemBarcode, Item.itemNo, Item.itemName, Category.category FROM Item\n" +
                    "INNER JOIN Category ON\n" +
                    "Item.itemBarcode = Category.itemBarcode;";

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
            String sql = "SELECT employeeName, itemName, timeTaken, timeReturned FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String timeReturned = result.getString("timeReturned");
                BorrowedItemObj borrowedItemObj = new BorrowedItemObj(employeeName, itemName, timeTaken, timeReturned);

                borrowedItemTabData.addAll(borrowedItemObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateBorrowedItem() from TakeItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        borrowedItemEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItemObj, String>("employeeName"));
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
            String sql = "SELECT employeeName, itemName, timeTaken, quantityTaken FROM UsedProduct\n" +
                    "INNER JOIN Employee ON\n" +
                    "UsedProduct.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "UsedProduct.itemBarcode = Item.itemBarcode;";

            /* EXECUTION OF QUERY */
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String employeeName = result.getString("employeeName");
                String itemName = result.getString("itemName");
                String timeTaken = result.getString("timeTaken");
                String quantityTaken = result.getString("quantityTaken");
                UsedProductObj usedProductObj = new UsedProductObj(employeeName, itemName, timeTaken, quantityTaken);

                usedProductTabData.addAll(usedProductObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateUsedProduct() from AdminController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        usedProductEmployeeNameColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("employeeName"));
        usedProductItemNameColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("itemName"));
        usedProductTimeTakenColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("timeTaken"));
        usedProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<UsedProductObj, String>("quantityTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        usedProductTableView.getItems().addAll(usedProductTabData);
    }


}








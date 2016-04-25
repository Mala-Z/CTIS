package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.BorrowedItem;
import SourceCode.Model.Employee;
import SourceCode.Model.Item;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
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
//    Factory factory = Factory.getInstance();
    ConnectDB connectDB = Factory.connectDB;
    private ObservableList searchItemData = FXCollections.observableArrayList();

    @FXML
    Button btnSearch;
    @FXML
    Button btnBack;
    @FXML
    TableColumn employeeNameColumn;
    @FXML
    TableColumn telephoneNoColumn;
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
            if (businessLogic.checkItemBarcode(Integer.parseInt(tfItemNumber.getText()))) {

                searchByItemBarcode();

            } else {
                updateAlertMessage("Please scan the barcode again");
                tfItemNumber.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkItemBarcode() from SearchItemController class:" + e.getMessage());
        }
    }

    public void searchByItemNumber() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, timeTaken FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "BorrowedItem.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE itemNo = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
            int inputBarcode = Integer.parseInt(tfItemNumber.getText());
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                Employee employee = new Employee();
                Item item = new Item();
                BorrowedItem borrowedItem = new BorrowedItem();

                employee.nameProperty().set(result.getString("employeeName"));
                employee.telephoneProperty().set(result.getInt("phoneNumber"));
                item.itemNameProperty().set(result.getString("itemName"));
                borrowedItem.placeProperty().set(result.getString("place"));
                borrowedItem.timeTakenProperty().set(result.getString("timeTaken"));

                //searchItemData.addAll(employee, item, borrowedItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in searchByItemNumber() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING UP COLUMNS FOR TABLE VIEW */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("phoneNumber"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItem, String>("place"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItem, ObjectProperty<DateTimeFormatter>>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        //tableView.getItems().addAll(searchItemData);
    }

    public void searchByItemBarcode() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, phoneNumber, itemName, place, timeTaken FROM BorrowedItem\n" +
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
            int inputBarcode = Integer.parseInt(tfItemNumber.getText());
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                Employee employee = new Employee();
                Item item = new Item();
                BorrowedItem borrowedItem = new BorrowedItem();

                employee.nameProperty().set(result.getString("employeeName"));
                employee.telephoneProperty().set(result.getInt("phoneNumber"));
                item.itemNameProperty().set(result.getString("itemName"));
                borrowedItem.placeProperty().set(result.getString("place"));
                borrowedItem.timeTakenProperty().set(result.getString("timeTaken"));

                searchItemData.addAll(item,borrowedItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in searchByItemBarcode() from SearchItemController class: " + e.getMessage());
        }

        /* SETTING UP COLUMNS FOR TABLE VIEW */
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        telephoneNoColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("phoneNumber"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItem, String>("place"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<BorrowedItem, String>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().addAll(searchItemData);

    }

    /* METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER */
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

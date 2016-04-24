package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.BorrowedItem;
import SourceCode.Model.Employee;
import SourceCode.Model.Item;
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
    Factory factory = Factory.getInstance();

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
    private TableColumn <Employee, String> employeeNameColumn;
    @FXML
    private TableColumn <Item, String>itemNameColumn;
    @FXML
    private TableColumn <BorrowedItem, String> placeColumn;
    @FXML
    private TableColumn <BorrowedItem, String> timeTakenColumn;


    private RunView runView;


    @FXML
    private void btnSubmit() {
        try {
            if (tfItemBarcode.getLength() > 0) {
                if (businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {

                   java.util.Date today = new java.util.Date();
                    Timestamp timeReturned = new Timestamp(today.getTime());
                    businessLogic.returnItem(Integer.parseInt(tfItemBarcode.getText()));
                    businessLogic.updateBorrowedItemTable("BorrowedItem", timeReturned, Integer.parseInt(tfItemBarcode.getText()));

                    updateAlertMessage("Item returned");
                    runView.showMainView();
                } else if (!businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
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
                    tableView.requestFocus();
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

    public void populateTableView() {

        try {
            /* SQL QUERY */
            String sql = "SELECT employeeName, itemName, place, timeTaken FROM BorrowedItem\n" +
                    "INNER JOIN Employee ON\n" +
                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Item ON\n" +
                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Place ON\n" +
                    "BorrowedItem.id = Place.borrowedItemID\n" +
                    "WHERE BorrowedItem.itemBarcode = ? and timeReturned IS NULL;";

            /* EXECUTION OF QUERY */
            int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
            PreparedStatement preparedStatement = factory.preparedStatement(sql);
            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {
                Employee employee = new Employee();
                Item item = new Item();
                BorrowedItem borrowedItem = new BorrowedItem();

                employee.nameProperty().set(result.getString("employeeName"));
                item.itemNameProperty().set(result.getString("itemName"));
                borrowedItem.placeProperty().set(result.getString("place"));
                borrowedItem.timeTakenProperty().set(result.getString("timeTaken"));


                returnItemData.addAll(employee,item,borrowedItem);
                /*System.out.println("emp:" + employee);
                System.out.println("item:" + item);
                System.out.println("borro:" + borrowedItem);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateTableView() from ReturnItemController class: " + e.getMessage());
        }

        /* SETTING UP COLUMNS FOR TABLE VIEW */
        //employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<>("timeTaken"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().addAll(returnItemData);
    }

//    public void initComponents() {
//
//        //CALLING BUILD DATA METHOD
//        buildData();
//
//        //SETTING COLUMNS FOR BORROWED ITEM TABLE VIEW
//        TableColumn columnEmployeeName = new TableColumn<Employee, String>("Employee name: ");
//        columnEmployeeName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
//        columnEmployeeName.setMinWidth(100);
//        TableColumn columnItemName = new TableColumn<BorrowedItem, String>("Item name: ");
//        columnItemName.setCellValueFactory(new PropertyValueFactory<BorrowedItem, String>("itemName"));
//        columnItemName.setMinWidth(100);
//        TableColumn columnItemPlace = new TableColumn<BorrowedItem, String>("Place: ");
//        columnItemPlace.setCellValueFactory(new PropertyValueFactory<BorrowedItem, String>("place"));
//        columnItemPlace.setMinWidth(100);
//        TableColumn columnTimeTaken = new TableColumn<BorrowedItem, DateTimeFormatter>("Time taken: ");
//        columnTimeTaken.setCellValueFactory(new PropertyValueFactory<BorrowedItem, DateTimeFormatter>("timeTaken"));
//        columnTimeTaken.setMinWidth(170);
//
//        //ASSIGNING THE COLUMNS TO THE TABLE VIEW
//
//        tableView.getColumns().setAll(columnEmployeeName, columnItemName, columnItemPlace, columnTimeTaken);
//    }


//    public void buildData(){
//        //OBSERVABLE LIST
//        returnItemData = FXCollections.observableArrayList();
//
//        try {
//            //SQL QUERIES
//            String sql = "SELECT employeeName, itemName, place, timeTaken FROM BorrowedItem\n" +
//                    "INNER JOIN Employee ON\n" +
//                    "BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
//                    "INNER JOIN Item ON\n" +
//                    "BorrowedItem.itemBarcode = Item.itemBarcode\n" +
//                    "INNER JOIN Place ON\n" +
//                    "BorrowedItem.id = Place.borrowedItemID\n" +
//                    "WHERE BorrowedItem.itemBarcode = ? AND timeReturned IS NULL;";
//
//            //EXECUTE QUERIES
//            int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
//            PreparedStatement preparedStatement = factory.preparedStatement(sql);
//            preparedStatement.setInt(1, inputBarcode);
//
//            ResultSet result = preparedStatement.executeQuery();
//
//            while ((result.next())){
//                if (!businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
//                    Employee employee = new Employee();
//                    Item item = new Item();
//                    BorrowedItem borrowedItem = new BorrowedItem();
//                    employee.nameProperty().set(result.getString("employeeName"));
//                    item.itemNameProperty().set(result.getString("itemName"));
//                    borrowedItem.placeProperty().set(result.getString("place"));
//                    borrowedItem.timeTakenProperty().set(result.getString("timeTaken"));
//
//                    returnItemData.add(borrowedItem);
//                }
//            }
//            //OBSERVABLE LIST ADDED TO TABLE VIEW
//            //tableView.setItems(returnItemData);
//            tableView.getItems().addAll(returnItemData);
//        } catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Exception in buildData() from ReturnItemController class: " + e.getMessage());
//        }
//    }

    /* METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER */
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

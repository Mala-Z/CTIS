package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.BorrowedItem;
import SourceCode.Model.Employee;
import SourceCode.Model.Item;
import SourceCode.Model.tableViewObjects.ReturnObj;
import SourceCode.Model.tableViewObjects.TakeObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class TakeItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    //Factory factory = Factory.getInstance();
    ConnectDB connectDB = Factory.connectDB;

    ObservableList<String> placeList = FXCollections.observableArrayList("On Person", "Address", "Car");
    private ObservableList takeItemData = FXCollections.observableArrayList();

    @FXML
    private TextField tfEmployeeBarcode;
    @FXML
    private TextField tfItemBarcode;
    @FXML
    private TableView tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSubmit;
    @FXML
    private ComboBox placeCombo;
    @FXML
    private TableColumn itemNumberColumn;
    @FXML
    private TableColumn itemNameColumn;
    @FXML
    private TableColumn placeColumn;

    private RunView runView;

    @FXML
    private void initialize() {
        placeCombo.setItems(placeList);
        placeCombo.getSelectionModel().selectFirst();
    }


    @FXML
    private void btnSubmit() {
        try {
            if (tfEmployeeBarcode.getLength() > 0 && tfItemBarcode.getLength() > 0) {
                java.util.Date today = new java.util.Date();
                Timestamp timeTaken = new Timestamp(today.getTime());
                businessLogic.takeItem(Integer.parseInt(tfEmployeeBarcode.getText()), Integer.parseInt(tfItemBarcode.getText()), timeTaken, placeCombo.getValue().toString());
                updateAlertMessage("Registration successful");
                runView.showMainView();

            } else {
                updateAlertMessage("Missing barcode for employee or item");
            }

        } catch (Exception e) {
            System.out.println("Exception in btnSubmit() from TakeItemController class:" + e.getMessage());
        }
    }

    @FXML
    private void btnBack() {
        try {
            runView.showMainView();
        } catch (Exception e) {
            System.out.println("Exception in btnBack() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void btnDelete() {
        try {

        } catch (Exception e) {
            System.out.println("Exception in btnDelete() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkEmployeeBarcode() {
        try {
            if (businessLogic.checkEmployeeBarcode(Integer.parseInt(tfEmployeeBarcode.getText()))) {
                tfItemBarcode.requestFocus();
            } else {
                updateAlertMessage("Please scan the barcode again");
                tfEmployeeBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkEmployeeBarcode() from TakeItemController class: " + e.getMessage());
        }
    }

    @FXML
    private void checkItemBarcode() {
        try {
            if (businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) {
                if (!businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
                    populateTableView();
                    //tableView.requestFocus();
                } else if (businessLogic.searchItem(Integer.parseInt(tfItemBarcode.getText()))) {
                    updateAlertMessage("Item has been already taken by another employee");
                    tfItemBarcode.setText(null);
                }
            } else if (!businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) {
                updateAlertMessage("Please scan the barcode again");
                tfItemBarcode.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception in checkItemBarcode() from TakeItemController class: " + e.getMessage());
        }
    }

    /* METHOD FOR POPULATING THE TABLE VIEW */
    public void populateTableView() {

        try {
            /* SQL QUERY */
            String sql = "SELECT itemNo, itemName FROM Item WHERE itemBarcode = ?;";

            /* EXECUTION OF QUERY */
            int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemNo = result.getString("itemNo");
                String itemName = result.getString("itemName");
                String place = placeCombo.getSelectionModel().getSelectedItem().toString(); // here we parse selected category into string
                TakeObj takeObj = new TakeObj(itemNo, itemName, place);

//                Item item = new Item();
//                item.itemNoProperty().set(result.getString("itemNo"));
//                item.itemNameProperty().set(result.getString("itemName"));
//                takeItemData.add(item);
                takeItemData.setAll(takeObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in populateTableView() from TakeItemController class: " + e.getMessage());
        }

        /* SETTING VALUES FROM OBJECT INTO COLUMNS */
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemNumber"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("place"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().addAll(takeItemData);
    }

    /* METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER */
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

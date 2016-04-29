package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.ConnectDB;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.userTableViewObjects.TakeObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class TakeItemController {
    BusinessLogic businessLogic = new BusinessLogic();
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
    private TableColumn itemCategoryColumn;
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
                MainViewController.updateAlertMessage("Registration successful");
                runView.showMainView();

            } else {
                MainViewController.updateAlertMessage("Missing barcode for employee or item");
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
                MainViewController.updateAlertMessage("Please scan the barcode again");
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
                    MainViewController.updateAlertMessage("Item has been already taken by another employee");
                    tfItemBarcode.setText(null);
                }
            } else if (!businessLogic.checkItemBarcode(Integer.parseInt(tfItemBarcode.getText()))) {
                MainViewController.updateAlertMessage("Please scan the barcode again");
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
            String sql = " SELECT itemNo, itemName, category FROM Item \n" +
                    "INNER JOIN Category ON \n" +
                    "Item.itemBarcode = Category.itemBarcode\n" +
                    "WHERE Item.itemBarcode = ?";

            /* EXECUTION OF QUERY */
            int inputBarcode = Integer.parseInt(tfItemBarcode.getText());
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();

            while ((result.next())) {

                String itemNo = result.getString("itemNo");
                String itemCategory = result.getString("category");
                String itemName = result.getString("itemName");
                String place = placeCombo.getSelectionModel().getSelectedItem().toString(); // here we parse selected category into string
                TakeObj takeObj = new TakeObj(itemNo, itemCategory, itemName, place);

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
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemCategory"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("itemName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<TakeObj, String>("place"));

        /* ADDING THE OBSERVABLE LIST TO THE TABLE VIEW */
        tableView.getItems().addAll(takeItemData);
    }

}

package SourceCode.Controller.user;

import SourceCode.BusinessLogic.BusinessLogic;
import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.RunView;
import SourceCode.Model.BorrowedItem;
import SourceCode.Model.SearchedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;


public class SearchItemController {
    BusinessLogic businessLogic = new BusinessLogic();
    Factory factory = Factory.getInstance();

    private ObservableList searchItemData;

    @FXML
    TableColumn columnX;
    @FXML
    Label employeeBarcodeLabel;
    @FXML
    TextField tfItemNumber;
    private RunView runView;
    @FXML
    ComboBox comboBox;
    @FXML
    TableView tableView;
    @FXML
    TableColumn itemColumn;
    @FXML
    TableColumn numberColumn;
    @FXML
    TableColumn nameColumn;

    @FXML
    private void btnBackAction() throws IOException {
        runView.showMainView();
    }

    public void searchButtonClicked() {
        System.out.println("Save button clicked");

        if ((tfItemNumber.getText() != null && !tfItemNumber.getText().isEmpty())) {
            employeeBarcodeLabel.setText(tfItemNumber.getText());
            employeeBarcodeLabel.setVisible(true);
        } else {
            employeeBarcodeLabel.setText("You have not left a comment.");
        }
        if(comboBox.getItems()!= null){
        }

    }
    @FXML
    private void checkItemBarcode() {
        int barcode = Integer.parseInt(tfItemNumber.getText());
        try {
            if (businessLogic.checkItemBarcode(barcode)) {

                initComponents();

            } else {
                updateAlertMessage("Please scan the barcode again");
                tfItemNumber.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    public void initComponents() {

        //CALLING BUILD DATA METHOD
        buildData();


        //SETTING COLUMNS FOR RETURN ITEM TABLE VIEW
        TableColumn columnEmployeeName = new TableColumn<SearchedItem, String>("Employee: ");
        columnEmployeeName.setCellValueFactory(new PropertyValueFactory<SearchedItem, String>("employeeName"));
        columnEmployeeName.setMinWidth(50);
        TableColumn columnItemName = new TableColumn<SearchedItem, String>("Item: ");
        columnItemName.setCellValueFactory(new PropertyValueFactory<SearchedItem, String>("itemName"));
        columnItemName.setMinWidth(100);
        TableColumn columnItemPlace = new TableColumn<SearchedItem, String>("Place: ");
        columnItemPlace.setCellValueFactory(new PropertyValueFactory<SearchedItem, String>("place"));
        columnItemPlace.setMinWidth(100);
        TableColumn columnTimeTaken = new TableColumn<BorrowedItem, DateTimeFormatter>("Time taken: ");
        columnTimeTaken.setCellValueFactory(new PropertyValueFactory<BorrowedItem, DateTimeFormatter>("timeTaken"));
        columnTimeTaken.setMinWidth(150);


        //ASSIGNING THE COLUMNS TO THE TABLE VIEW

        tableView.getColumns().setAll(columnEmployeeName, columnItemName, columnItemPlace, columnTimeTaken);
    }
    public void buildData(){
        //THE CONNECTION
        //Connection conn;

        //THE OBSERVABLE LIST
        searchItemData = FXCollections.observableArrayList();
        try {

            //conn = factory.conn;

            //SQL QUERIES
            String sql = "SELECT employeeName, itemName, place, timeTaken FROM BorrowedItem\n" +
                    "INNER JOIN Item ON BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Employee ON BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Place ON BorrowedItem.itemBarcode = Place.itemBarcode\n" +
                    "WHERE BorrowedItem.itemBarcode = ? AND BorrowedItem.timeReturned IS NULL;";

            //EXECUTE QUERIES
            int inputBarcode = Integer.parseInt(tfItemNumber.getText());
            PreparedStatement preparedStatement = factory.preparedStatement(sql);
            preparedStatement.setInt(1, inputBarcode);

            ResultSet result = preparedStatement.executeQuery();



            while ((result.next())){
                int rowBarcode = result.getInt("itemBarcode");
                //int itemBarcodeInt = Integer.parseInt(tfItemNumber.getText());

                if (rowBarcode == inputBarcode) {

                    SearchedItem searchedItem = new SearchedItem();
                    searchedItem.employeeNameProperty().set(result.getString("employeeName"));
                    searchedItem.itemNameProperty().set(result.getString("itemName"));
                    searchedItem.itemPlaceProperty().set(result.getString("place"));
                    searchedItem.timeTakenProperty().set(result.getString("timeTaken"));

                    searchItemData.add(searchedItem);


                }
            }
            //OBSERVABLE LIST ADDED TO TABLE VIEW
            //tableView.setItems(searchItemData);
            tableView.getItems().addAll(searchItemData);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building SearchedItem Data");
        }

    }


    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }




}

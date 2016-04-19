package SourceCode.Controller.user;

import SourceCode.BusinessLogic.Model;
import SourceCode.Controller.RunView;
import SourceCode.Model.BorrowedItem;
import SourceCode.Model.Item;
import SourceCode.Model.SearchedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;


public class SearchController  {

    Model model = new Model();
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
            if (model.checkItemBarcode(barcode)) {


                initComponents();
                //tableView.getItems(buildData());
               /* TableColumn firstNameCol = new TableColumn("First Name");
                firstNameCol.setMinWidth(100);
                firstNameCol.setCellValueFactory(
                        new PropertyValueFactory<Item, String>("itemName"));

                TableColumn lastNameCol = new TableColumn("Last Name");
                TableColumn emailCol = new TableColumn("Email");

                tableView.getColumns().addAll(firstNameCol, lastNameCol, emailCol);*/
                //show scanned item in the list
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


        //SETTING COLUMNS FOR ITEM TABLE VIEW
        TableColumn columnItem = new TableColumn<Item, Integer>("Barcode: ");
        columnItem.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemBarcode"));
        columnItem.setMinWidth(200);
        TableColumn columnItemNo = new TableColumn<Item, Integer>("Item number: ");
        columnItemNo.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemNo"));
        columnItemNo.setMinWidth(200);
        TableColumn colProperty = new TableColumn<Item, String>("Description: ");
        colProperty.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        colProperty.setMinWidth(300);

        //SETTING COLUMNS FOR USED ITEM TABLE VIEW
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
        columnTimeTaken.setMinWidth(170);


        //ASSIGNING THE COLUMNS TO THE TABLE VIEW

        tableView.getColumns().setAll(columnEmployeeName, columnItemName, columnItemPlace, columnTimeTaken);
    }
    public void buildData(){
        //THE CONNECTION
        Connection conn;

        //THE OBSERVABLE LIST
        searchItemData = FXCollections.observableArrayList();
        try {
            model.connectToDatabase();
            conn = model.conn;

            String itemBarcodeString1 = tfItemNumber.getText();
            int employeeBarcodeInt1 = Integer.parseInt(itemBarcodeString1);

            //SQL QUERIES
            String sql = "use ctis_racoon;\n" +
                    "SELECT employeeName, itemName, place, timeTaken FROM BorrowedItem\n" +
                    "INNER JOIN Item ON BorrowedItem.itemBarcode = Item.itemBarcode\n" +
                    "INNER JOIN Employee ON BorrowedItem.employeeBarcode = Employee.employeeBarcode\n" +
                    "INNER JOIN Place ON BorrowedItem.itemBarcode = Place.itemBarcode\n" +
                    "WHERE BorrowedItem.itemBarcode = 'employeeBarcodeInt1' AND BorrowedItem.timeReturned IS NULL;";

            //EXECUTE QUERIES
            ResultSet result = conn.createStatement().executeQuery(sql);



            while ((result.next())){
                int rowBarcode = result.getInt("itemBarcode");
                String itemBarcodeString = tfItemNumber.getText();
                int employeeBarcodeInt = Integer.parseInt(itemBarcodeString);

                if (rowBarcode == employeeBarcodeInt) {

                    SearchedItem searchedItem = new SearchedItem();
                    searchedItem.employeeNameProperty().set(result.getString("employeeName"));
                    searchedItem.itemNameProperty().set(result.getString("itemName"));
                    searchedItem.itemPlaceProperty().set(result.getString("place"));
                    searchedItem.timeTakenProperty().set(result.getString("timeTaken"));

                    searchItemData.add(searchItemData);

                }


            }
            //OBSERVABLE LIST ADDED TO TABLE VIEW
            //tableView.setItems(searchItemData);
            tableView.getItems().addAll(searchItemData);
            //tableView.edit();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building BorrowedItem Data");
        }

    }


    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }




}

package SourceCode.View;

import SourceCode.Controller.AdminController;
import SourceCode.Model.Employee;
import SourceCode.BusinessLogic.Model;
import SourceCode.Model.Item;
import SourceCode.Model.UsedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class AdminView {
    private Model model;
    private AdminController adminController;

    //STAGE, SCENE AND BORDER PANES FOR ADMIN VIEW
    private Stage stage;
    private Scene scene;
    private BorderPane mainBorderPane;
    private BorderPane borderPane;
    private GridPane gridPane;

    //OBSERVABLE LISTS
    private ObservableList employeeData;
    private ObservableList itemData;
    private ObservableList usedItemData;

    //TABLE VIEWS
    private TableView employeeTable = new TableView();
    private TableView itemTable = new TableView();
    private TableView usedItemTable = new TableView();

    //TABS FOR THE TABLE VIEWS
    private Tab employeeTab;
    private Tab itemTab;
    private Tab usedItemTab;

    //TEXT FIELDS
    TextField employeeBarcodeTxt, itemBarcodeTxt, nameTxt, identificationNoTxt, telephoneNoTxt, itemNoTxt, descriptionTxt;

    //LABELS
    Label title, barcodeLbl, nameLbl, identificationNoLbl, telephoneNoLbl, itemNoLbl, descriptionLbl;

    //BUTTONS
    Button createEmployee, createItem, saveButton, cancelButton, deleteButton, updateButton, logOutButton;

    //CONSTRUCTOR
    public AdminView() {
        model = new Model();
        adminController = new AdminController(this, model);
        initComponents();
    }

    //STARTING MAIN SCREEN FOR ADMIN VIEW
    private void initComponents() {

        //CALLING BUILD DATA METHOD
        buildData();

        //SETTING COLUMNS FOR EMPLOYEE TABLE VIEW
        TableColumn columnBarcode = new TableColumn<Employee, Integer>("Barcode: ");
        columnBarcode.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeBarcode"));
        columnBarcode.setMinWidth(170);
        TableColumn columnIdentificationNo = new TableColumn<Employee, Integer>("Identification number: ");
        columnIdentificationNo.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("identificationNo"));
        columnIdentificationNo.setMinWidth(170);
        TableColumn columnName = new TableColumn<Employee, String>("Name: ");
        columnName.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        columnName.setMinWidth(170);
        TableColumn columnTelephoneNo = new TableColumn<Employee, Integer>("Telephone number: ");
        columnTelephoneNo.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("telephoneNo"));
        columnTelephoneNo.setMinWidth(170);


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
        TableColumn columnId = new TableColumn<UsedItem, Integer>("Id: ");
        columnId.setCellValueFactory(new PropertyValueFactory<UsedItem, Integer>("id"));
        columnId.setMinWidth(50);
        TableColumn columnBarcodeEmployee = new TableColumn<UsedItem, Integer>("Employee barcode: ");
        columnBarcodeEmployee.setCellValueFactory(new PropertyValueFactory<UsedItem, Integer>("employeeBarcode"));
        columnBarcodeEmployee.setMinWidth(170);
        TableColumn columnBarcodeItem = new TableColumn<UsedItem, Integer>("Item barcode: ");
        columnBarcodeItem.setCellValueFactory(new PropertyValueFactory<UsedItem, Integer>("itemBarcode"));
        columnBarcodeItem.setMinWidth(170);
        TableColumn columnTimeTaken = new TableColumn<UsedItem, DateTimeFormatter>("Time taken: ");
        columnTimeTaken.setCellValueFactory(new PropertyValueFactory<UsedItem, DateTimeFormatter>("timeTaken"));
        columnTimeTaken.setMinWidth(170);
        TableColumn columnTimeReturned = new TableColumn<UsedItem, DateTimeFormatter>("Time returned: ");
        columnTimeReturned.setCellValueFactory(new PropertyValueFactory<UsedItem, DateTimeFormatter>("timeReturned"));
        columnTimeReturned.setMinWidth(170);

        //ASSIGNING THE COLUMNS TO THE TABLE VIEW
        employeeTable.getColumns().addAll(columnBarcode, columnIdentificationNo, columnName, columnTelephoneNo);
        itemTable.getColumns().addAll(columnItem, columnItemNo, colProperty);
        usedItemTable.getColumns().addAll(columnId, columnBarcodeEmployee, columnBarcodeItem, columnTimeTaken, columnTimeReturned);

        //BUTTONS
        createEmployee = new Button("Create employee");
        createItem = new Button("Create item");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        logOutButton = new Button("Log out");

        //BUTTONS ACTION
        createEmployee.setOnAction(event -> {
            mainBorderPane.setCenter(createEmployee());
        });

        createItem.setOnAction(event1 -> {
            mainBorderPane.setCenter(createItem());

        });

        updateButton.setOnAction(event -> {
            mainBorderPane.setCenter(updateRow());
        });

        deleteButton.setOnAction(event -> {
            //DELETING ROW FROM TABLE VIEW AND FROM DATABASE
            if (employeeTab.isSelected()) {
                Employee employee = (Employee) employeeTable.getSelectionModel().getSelectedItem();
                adminController.deleteEmployee(employee.getBarcode());
                employeeData.remove(employeeTable.getSelectionModel().getSelectedIndex());
                updateAlertMessage(" Employee deleted ");
            } else if (itemTab.isSelected()) {
                Item item = (Item) itemTable.getSelectionModel().getSelectedItem();
                adminController.deleteItem(item.getBarcode());
                itemData.remove(itemTable.getSelectionModel().getSelectedIndex());
                updateAlertMessage(" Item deleted ");
            } else if (usedItemTab.isSelected()) {
                UsedItem usedItem = (UsedItem) usedItemTable.getSelectionModel().getSelectedItem();
                adminController.deleteUsedItem(usedItem.getId());
                usedItemData.remove(usedItemTable.getSelectionModel().getSelectedIndex());
                updateAlertMessage(" UsedItem row deleted ");
            }
        });

        logOutButton.setOnAction(event -> {
            adminController.logOutButton();
        });

        //INITIALIZING THE TABS
        employeeTab = new Tab();
        employeeTab.setText("Employee");
        employeeTab.setContent(new Rectangle(450, 250, Color.WHITE));
        employeeTab.setClosable(false);//SETTING TAB TO NOT CLOSABLE

        itemTab = new Tab();
        itemTab.setText("Item");
        itemTab.setContent(new Rectangle(450, 250, Color.WHITE));
        itemTab.setClosable(false); //SETTING TAB TO NOT CLOSABLE

        usedItemTab = new Tab();
        usedItemTab.setText("UsedItem");
        usedItemTab.setContent(new Rectangle(450, 250, Color.WHITE));
        usedItemTab.setClosable(false); //SETTING TAB TO NOT CLOSABLE

        //ADDING THE TABLE VIEWS TO THE TABS
        employeeTab.setContent(employeeTable);
        itemTab.setContent(itemTable);
        usedItemTab.setContent(usedItemTable);

        //CREATING A TAB PANE FOR HOLDING THE TABS
        TabPane tabPane = new TabPane();
        tabPane.setMinSize(800, 400);
        tabPane.getTabs().addAll(employeeTab, itemTab, usedItemTab);

        //ADDING THE BUTTONS TO AN H-BOX
        HBox hBox = new HBox();
        hBox.getChildren().addAll(createEmployee, createItem, updateButton, deleteButton);

        //INITIALIZING THE BORDER PANES
        mainBorderPane = new BorderPane();
        mainBorderPane.setId("AdminViewBorderPane");
        gridPane = new GridPane();

        //SETTING UP THE BORDER PANES
        mainBorderPane.setCenter(gridPane);
        gridPane.getChildren().addAll(tabPane, hBox, logOutButton);
        gridPane.setConstraints(tabPane, 1, 1);
        gridPane.setConstraints(hBox, 1, 2);
        gridPane.setConstraints(logOutButton, 1, 3);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gridPane.setMinSize(700, 400);

        //CREATING A SCENE AND ADDING THE MAIN BORDER PANE TO IT
        scene = new Scene(mainBorderPane, 800, 500);
        scene.getStylesheets().add
                (UserView.class.getResource("/CSS/UserView.css").toExternalForm());

        //CREATING A STAGE AND SETTING IT UP
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Racoon");
        stage.setResizable(true);
        stage.show();
    }

    //THE VIEW FOR CREATE EMPLOYEE BUTTON
    private Pane createEmployee() {

        //THE LAYOUT FOR THE PANE
        borderPane = new BorderPane();

        //LABELS
        title = new Label(" Insert employee ");
        title.setId("titleInsertEmployee");
        barcodeLbl = new Label(" Barcode ");
        barcodeLbl.setId("employeeBarcode");
        identificationNoLbl = new Label("Identification number ");
        identificationNoLbl.setId("identificationNo");
        nameLbl = new Label(" Name ");
        nameLbl.setId("nameEmployee");
        telephoneNoLbl = new Label(" Telephone number ");
        telephoneNoLbl.setId("telephoneNo");

        //TEXT FIELDS
        employeeBarcodeTxt = new TextField();
        employeeBarcodeTxt.setPromptText("-employee barcode-");
        nameTxt = new TextField();
        nameTxt.setPromptText("-employee name-");
        identificationNoTxt = new TextField();
        identificationNoTxt.setPromptText("-identification number-");
        telephoneNoTxt = new TextField();
        telephoneNoTxt.setPromptText("-telephone number-");

        //BUTTONS
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        saveButton.setOnAction(event -> {
            adminController.createEmployee(employeeBarcodeTxt.getText(), identificationNoTxt.getText(), nameTxt.getText(), telephoneNoTxt.getText());
            employeeData.add(new Employee(
                    Integer.valueOf(employeeBarcodeTxt.getText()),
                    Integer.valueOf(identificationNoTxt.getText()),
                    nameTxt.getText(),
                    Integer.valueOf(telephoneNoTxt.getText())));
            employeeBarcodeTxt.clear();
            nameTxt.clear();
        });

        cancelButton.setOnAction(event -> {
            adminController.cancelButton();
            mainBorderPane.setCenter(gridPane);
        });

        //BOXES
        VBox vBox = new VBox();
        vBox.getChildren().addAll(barcodeLbl, employeeBarcodeTxt, identificationNoLbl, identificationNoTxt, nameLbl, nameTxt,
                telephoneNoLbl, telephoneNoTxt);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, saveButton);

        //SETTING THE CHILDREN'S POSITION INTO THE BORDER PANE
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        //CREATING A NEW SCENE AND ADDING THE BORDER PANE TO IT
        scene = new Scene(borderPane, 400, 300);

        return borderPane; //RETURNING THE BORDER PANE
    }

    //THE VIEW FOR CREATE ITEM BUTTON
    private Pane createItem() {

        //THE LAYOUT FOR THE PANE
        borderPane = new BorderPane();

        //LABELS
        title = new Label(" Insert Item ");
        title.setId("createItem");
        barcodeLbl = new Label(" Barcode ");
        barcodeLbl.setId("barcode");
        itemNoLbl = new Label(" Item number ");
        itemNoLbl.setId("itemNo");
        descriptionLbl = new Label(" Description ");
        descriptionLbl.setId("description");

        //TEXT FIELDS
        itemBarcodeTxt = new TextField();
        itemBarcodeTxt.setPromptText("-item barcode-");
        itemNoTxt = new TextField();
        itemNoTxt.setPromptText("-item number-");
        descriptionTxt = new TextField();
        descriptionTxt.setPromptText("-item description-");

        //BUTTONS
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        saveButton.setOnAction(event -> {
            adminController.createItem(itemBarcodeTxt.getText(),itemNoTxt.getText(), descriptionTxt.getText());
            itemData.add(new Item(
                    Integer.valueOf(itemBarcodeTxt.getText()),
                    Integer.valueOf(itemNoTxt.getText()),
                    descriptionTxt.getText()));
            itemBarcodeTxt.clear();
            descriptionTxt.clear();
        });

        cancelButton.setOnAction(event -> {
            adminController.cancelButton();
            mainBorderPane.setCenter(gridPane);
        });

        //ADDING CHILDREN TO THE BOXES
        VBox vBox = new VBox();
        vBox.getChildren().addAll(barcodeLbl, itemBarcodeTxt, itemNoLbl, itemNoTxt, descriptionLbl, descriptionTxt);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, saveButton);

        //SETTING THE CHILDREN'S POSITION INTO THE BORDER PANE
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        //CREATING A NEW SCENE AND ADDING THE BORDER PANE TO IT
        scene = new Scene(borderPane, 400, 300);

        return borderPane; //RETURNING THE BORDER PANE
    }

    //THE VIEW FOR UPDATE BUTTON
    private Pane updateRow() {

        //THE LAYOUT FOR THE PANE
        borderPane = new BorderPane();

        //LABELS
        title = new Label(" Update information ");
        title.setId("titleUpdateRow");
        barcodeLbl = new Label(" Employee barcode/Item barcode ");
        barcodeLbl.setId("barcodeItem");
        nameLbl = new Label(" Employee name/Item description ");
        nameLbl.setId("nameProperty");

        //TEXT FIELDS
        employeeBarcodeTxt = new TextField();
        nameTxt = new TextField();

        //GETTING THE INFORMATION FROM THE SELECTED ROW AND ADDING IT TO THE TEXT FIELDS
        try{
                Employee employee = (Employee) employeeTable.getSelectionModel().getSelectedItem();
                Item item = (Item) itemTable.getSelectionModel().getSelectedItem();
            if (employeeTab.isSelected()) {
                String getBarcode = String.valueOf(employee.getBarcode());
                String getName = employee.getName();
                employeeBarcodeTxt.appendText(getBarcode);
                nameTxt.appendText(getName);
            } else if (itemTab.isSelected()) {
                String getBarcode = String.valueOf(item.getBarcode());
                String getName = item.getDescription();
                itemBarcodeTxt.appendText(getBarcode);
                nameTxt.appendText(getName);
            }
        }catch (Exception e){
            updateAlertMessage(" Please select a row to update! ");
            System.out.println("Exception in updateRow(): " + e.getMessage());
        }

        //BUTTONS
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        saveButton.setOnAction(event -> {
            if (employeeTab.isSelected()) {
                Employee employee = (Employee) employeeTable.getSelectionModel().getSelectedItem();
                String getBarcodeEmployee = String.valueOf(employee.getBarcode());
                //String newBarcodeEmployee = equals().getText();
                String newName = nameTxt.getText();
                /*adminViewController.updateEmployeeTable(newBarcodeEmployee, newName, getBarcodeEmployee);
                employeeData.remove(employee);
                employeeData.add(new Employee(
                        Integer.valueOf(employeeBarcode.getText()),
                        Integer.valueOf(identificationNoTxt.getText()),
                        nameTxt.getText()),
                        Integer.valueOf(telephoneNoTxt.getText()));*/

                updateAlertMessage(" Employee saved ");
            } else if (itemTab.isSelected()) {
                Item item = (Item) itemTable.getSelectionModel().getSelectedItem();
                String getBarcodeKey = String.valueOf(item.getBarcode());
                String newBarcodeKey = itemBarcodeTxt.getText();
                String newProperty = nameTxt.getText();
                adminController.updateItemTable(newBarcodeKey, newProperty, getBarcodeKey);
                itemData.remove(item);
                itemData.add(new Item(
                        Integer.valueOf(itemBarcodeTxt.getText()),
                        Integer.valueOf(itemNoTxt.getText()),
                        nameTxt.getText()));
                updateAlertMessage(" Item saved ");
            }
            setBorderPaneNotVisible();
            mainBorderPane.setCenter(gridPane);
        });

        cancelButton.setOnAction(event -> {
            adminController.cancelButton();
            mainBorderPane.setCenter(gridPane);
        });

        //BOXES
        VBox vBox = new VBox();
        vBox.getChildren().addAll(barcodeLbl, employeeBarcodeTxt, nameLbl, nameTxt);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, saveButton);

        //SETTING THE CHILDREN'S POSITION INTO THE BORDER PANE
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        //CREATING NEW SCENE AND ADDING THE BORDER PANE TO IT
        scene = new Scene(borderPane, 400, 300);

        return borderPane; //RETURNING THE BORDER PANE
    }

    //METHOD FOR RETRIEVING DATA FROM THE DATABASE AND ADDING IT TO THE TABLE VIEWS
    public void buildData() {
       //THE CONNECTION
        Model model = new Model();
        Connection conn;


        //THE OBSERVABLE LISTS
        employeeData = FXCollections.observableArrayList();
        itemData = FXCollections.observableArrayList();
        usedItemData = FXCollections.observableArrayList();

        try {
            model.connectToDatabase();
            conn = model.conn;

            //SQL QUERIES
            String sql = "SELECT * FROM Employee;";
            String sql2 = "SELECT * FROM Item;";
            String sql3 = "SELECT * FROM UsedItem;";
            //String sql3 = "SELECT id, name, itemNo, timeTaken, timeReturned FROM UsedItem JOIN Employee ON Employee.employeeBarcode = UsedItem.employeeBarcode JOIN Item ON Item.itemBarcode = UsedItem.itemBarcode;";

            //EXECUTE QUERIES
            ResultSet result = conn.createStatement().executeQuery(sql);
            ResultSet result2 = conn.createStatement().executeQuery(sql2);
            ResultSet result3 = conn.createStatement().executeQuery(sql3);

            //DATA ADDED TO OBSERVABLE LIST
            while (result.next()) {
                Employee employee = new Employee();
                employee.employeeBarcodeProperty().set(result.getInt("employeeBarcode"));
                employee.identificationNoProperty().set(result.getInt("identificationNo"));
                employee.nameProperty().set(result.getString("name"));
                employee.telephoneProperty().set(result.getInt("telephoneNo"));

                employeeData.add(employee);
            }

            while (result2.next()) {
                Item item = new Item();
                item.itemBarcodeProperty().set(result2.getInt("itemBarcode"));
                item.itemNoProperty().set(result2.getInt("itemNo"));
                item.descriptionProperty().set(result2.getString("description"));

                itemData.add(item);
            }

            while (result3.next()) {
                UsedItem usedItem = new UsedItem();
                //Employee employee = new Employee();
                //Item item = new Item();
                usedItem.idProperty().set(result3.getInt("id"));
                usedItem.employeeBarcodeProperty().set(result3.getInt("employeeBarcode"));
                usedItem.itemBarcodeProperty().set(result3.getInt("itemBarcode"));
                //employee.nameProperty().set(result3.getString("name"));
                //item.itemNoProperty().set(result3.getInt("itemNo"));
                usedItem.timeTakenProperty().set(result3.getString("timeTaken"));
                usedItem.timeReturnedProperty().set(result3.getString("timeReturned"));

                usedItemData.add(usedItem);
            }

            //OBSERVABLE LIST ADDED TO TABLE VIEW
            employeeTable.setItems(employeeData);
            itemTable.setItems(itemData);
            usedItemTable.setItems(usedItemData);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE ADMIN
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //METHOD FOR CLOSING THE STAGE
    public void closeStage() {
        stage.close();
    }

    //METHOD FOR SETTING THE BORDER PANE NOT VISIBLE
    public void setBorderPaneNotVisible() {
        borderPane.setVisible(false);
    }

    //METHOD FOR SETTING THE GRID PANE IN THE CENTER OF THE MAIN BORDER PANE
    public void setMainBorderPaneCenter() {
        mainBorderPane.setCenter(gridPane);
    }
}
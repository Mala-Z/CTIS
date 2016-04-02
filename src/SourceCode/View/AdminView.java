package SourceCode.View;

import SourceCode.Controller.AdminController;
import SourceCode.Model.BorrowedItem;
import SourceCode.Model.Employee;
import SourceCode.BusinessLogic.Model;
import SourceCode.Model.Item;
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

    //COMBO BOX
    ComboBox categoryCombo;

    //TEXT FIELDS
    TextField employeeBarcodeTxt, itemBarcodeTxt, nameTxt, employeeNoTxt, telephoneNoTxt, itemNoTxt, descriptionTxt;

    //LABELS
    Label title, barcodeLbl, nameLbl, employeeNoLbl, telephoneNoLbl, itemNoLbl, descriptionLbl;

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
        TableColumn columnBarcode = new TableColumn<Employee, Integer>("Employee barcode: ");
        columnBarcode.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeBarcode"));
        columnBarcode.setMinWidth(170);
        TableColumn columnIdentificationNo = new TableColumn<Employee, Integer>("Employee number: ");
        columnIdentificationNo.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeNo"));
        columnIdentificationNo.setMinWidth(170);
        TableColumn columnName = new TableColumn<Employee, String>("Employee name: ");
        columnName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        columnName.setMinWidth(170);
        TableColumn columnTelephoneNo = new TableColumn<Employee, Integer>("Telephone number: ");
        columnTelephoneNo.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("phoneNumber"));
        columnTelephoneNo.setMinWidth(170);


        //SETTING COLUMNS FOR ITEM TABLE VIEW
        TableColumn columnItem = new TableColumn<Item, Integer>("Item barcode: ");
        columnItem.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemBarcode"));
        columnItem.setMinWidth(150);
        TableColumn columnItemNo = new TableColumn<Item, Integer>("Item number: ");
        columnItemNo.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemNo"));
        columnItemNo.setMinWidth(150);
        TableColumn colProperty = new TableColumn<Item, String>("Item name: ");
        colProperty.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        colProperty.setMinWidth(270);
        TableColumn colCategory = new TableColumn<Item, String>("Category: ");
        colCategory.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
        colCategory.setMinWidth(200);

        //SETTING COLUMNS FOR USED ITEM TABLE VIEW
        TableColumn columnId = new TableColumn<BorrowedItem, Integer>("Id: ");
        columnId.setCellValueFactory(new PropertyValueFactory<BorrowedItem, Integer>("id"));
        columnId.setMinWidth(50);
        TableColumn columnBarcodeEmployee = new TableColumn<BorrowedItem, Integer>("Employee barcode: ");
        columnBarcodeEmployee.setCellValueFactory(new PropertyValueFactory<BorrowedItem, Integer>("employeeBarcode"));
        columnBarcodeEmployee.setMinWidth(170);
        TableColumn columnBarcodeItem = new TableColumn<BorrowedItem, Integer>("Item barcode: ");
        columnBarcodeItem.setCellValueFactory(new PropertyValueFactory<BorrowedItem, Integer>("itemBarcode"));
        columnBarcodeItem.setMinWidth(170);
        TableColumn columnTimeTaken = new TableColumn<BorrowedItem, DateTimeFormatter>("Time taken: ");
        columnTimeTaken.setCellValueFactory(new PropertyValueFactory<BorrowedItem, DateTimeFormatter>("timeTaken"));
        columnTimeTaken.setMinWidth(170);
        TableColumn columnTimeReturned = new TableColumn<BorrowedItem, DateTimeFormatter>("Time returned: ");
        columnTimeReturned.setCellValueFactory(new PropertyValueFactory<BorrowedItem, DateTimeFormatter>("timeReturned"));
        columnTimeReturned.setMinWidth(170);

        //ASSIGNING THE COLUMNS TO THE TABLE VIEW
        employeeTable.getColumns().addAll(columnBarcode, columnIdentificationNo, columnName, columnTelephoneNo);
        itemTable.getColumns().addAll(columnItem, columnItemNo, colProperty, colCategory);
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
                BorrowedItem usedItem = (BorrowedItem) usedItemTable.getSelectionModel().getSelectedItem();
                adminController.deleteUsedItem(usedItem.getId());
                usedItemData.remove(usedItemTable.getSelectionModel().getSelectedIndex());
                updateAlertMessage(" BorrowedItem row deleted ");
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
        usedItemTab.setText("BorrowedItem");
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
        employeeNoLbl = new Label("Employee number ");
        employeeNoLbl.setId("employeeNumber");
        nameLbl = new Label(" Name ");
        nameLbl.setId("nameEmployee");
        telephoneNoLbl = new Label(" Telephone number ");
        telephoneNoLbl.setId("telephoneNo");

        //TEXT FIELDS
        employeeBarcodeTxt = new TextField();
        employeeBarcodeTxt.setPromptText("-employee barcode-");
        nameTxt = new TextField();
        nameTxt.setPromptText("-employee name-");
        employeeNoTxt = new TextField();
        employeeNoTxt.setPromptText("-employee number-");
        telephoneNoTxt = new TextField();
        telephoneNoTxt.setPromptText("-telephone number-");

        //BUTTONS
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        saveButton.setOnAction(event -> {
            adminController.createEmployee(employeeBarcodeTxt.getText(), employeeNoTxt.getText(), nameTxt.getText(), telephoneNoTxt.getText());
            employeeData.add(new Employee(
                    Integer.valueOf(employeeBarcodeTxt.getText()),
                    employeeNoTxt.getText(),
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
        vBox.getChildren().addAll(barcodeLbl, employeeBarcodeTxt, employeeNoLbl, employeeNoTxt, nameLbl, nameTxt,
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
        descriptionLbl = new Label(" Item Name ");
        descriptionLbl.setId("itemName");

        //TEXT FIELDS
        itemBarcodeTxt = new TextField();
        itemBarcodeTxt.setPromptText("-item barcode-");
        itemNoTxt = new TextField();
        itemNoTxt.setPromptText("-item number-");
        descriptionTxt = new TextField();
        descriptionTxt.setPromptText("-item name-");

        //COMBO BOX
        ObservableList<String> options = model.getCategory();
        categoryCombo = new ComboBox(options);
        categoryCombo.setPromptText("Choose a categoryCombo");
        categoryCombo.setMinWidth(200);

        //BUTTONS
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        saveButton.setOnAction(event -> {
            adminController.createItem(itemBarcodeTxt.getText(), itemNoTxt.getText(), descriptionTxt.getText(), String.valueOf(categoryCombo.getValue()));
            itemData.add(new Item(
                    Integer.valueOf(itemBarcodeTxt.getText()),
                    itemNoTxt.getText(),
                    descriptionTxt.getText(),
                    String.valueOf(categoryCombo.getValue())));
            itemBarcodeTxt.clear();
            descriptionTxt.clear();
        });

        cancelButton.setOnAction(event -> {
            adminController.cancelButton();
            mainBorderPane.setCenter(gridPane);
        });

        //ADDING CHILDREN TO THE BOXES
        VBox vBox = new VBox();
        vBox.getChildren().addAll(barcodeLbl, itemBarcodeTxt, itemNoLbl, itemNoTxt, descriptionLbl, descriptionTxt, categoryCombo);
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
        nameLbl = new Label(" Employee name/Item name ");
        nameLbl.setId("nameProperty");

        //TEXT FIELDS
        employeeBarcodeTxt = new TextField();
        nameTxt = new TextField();

        //GETTING THE INFORMATION FROM THE SELECTED ROW AND ADDING IT TO THE TEXT FIELDS
        try {
            Employee employee = (Employee) employeeTable.getSelectionModel().getSelectedItem();
            Item item = (Item) itemTable.getSelectionModel().getSelectedItem();
            if (employeeTab.isSelected()) {
                String getBarcode = String.valueOf(employee.getBarcode());
                String getName = employee.getName();
                employeeBarcodeTxt.appendText(getBarcode);
                nameTxt.appendText(getName);
            } else if (itemTab.isSelected()) {
                String getBarcode = String.valueOf(item.getBarcode());
                String getName = item.getItemName();
                itemBarcodeTxt.appendText(getBarcode);
                nameTxt.appendText(getName);
            }
        } catch (Exception e) {
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
                int oldBarcode = employee.getBarcode();
                adminController.updateEmployeeTable(Integer.valueOf(employeeBarcodeTxt.getText()), employeeNoTxt.getText(), nameTxt.getText(), Integer.valueOf(telephoneNoTxt.getText()), oldBarcode);
                employeeData.remove(employee);
                employeeData.add(new Employee(
                        Integer.valueOf(employeeBarcodeTxt.getText()),
                        employeeNoTxt.getText(),
                        nameTxt.getText(),
                        Integer.valueOf(telephoneNoTxt.getText())));

                updateAlertMessage(" Employee saved ");
            } else if (itemTab.isSelected()) {
                Item item = (Item) itemTable.getSelectionModel().getSelectedItem();
                int oldBarcode = item.getBarcode();
                adminController.updateItemTable(Integer.valueOf(itemBarcodeTxt.getText()), itemNoTxt.getText(), descriptionTxt.getText(), String.valueOf(categoryCombo.getValue()), oldBarcode);
                itemData.remove(item);
                itemData.add(new Item(
                        Integer.valueOf(itemBarcodeTxt.getText()),
                        itemNoTxt.getText(),
                        descriptionTxt.getText(),
                        String.valueOf(categoryCombo.getValue())));
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
            String sql1 = "SELECT * FROM PhoneNumber;";
            String sql2 = "SELECT * FROM Item;";
            String sql3 = "SELECT * FROM BorrowedItem;";
            String sql4 = "SELECT * FROM Category;";
            //String sql3 = "SELECT id, Employee.name, itemNo, timeTaken, timeReturned FROM BorrowedItem JOIN Employee ON Employee.employeeBarcode = BorrowedItem.employeeBarcode JOIN Item ON Item.itemBarcode = BorrowedItem.itemBarcode;";

            //EXECUTE QUERIES
            ResultSet result = conn.createStatement().executeQuery(sql);
            ResultSet result1 = conn.createStatement().executeQuery(sql1);
            ResultSet result2 = conn.createStatement().executeQuery(sql2);
            ResultSet result3 = conn.createStatement().executeQuery(sql3);
            ResultSet result4 = conn.createStatement().executeQuery(sql4);

            //DATA ADDED TO OBSERVABLE LIST
            while (result.next() && result1.next()) {
                Employee employee = new Employee();
                employee.employeeBarcodeProperty().set(result.getInt("employeeBarcode"));
                employee.employeeNoProperty().set(result.getString("employeeNo"));
                employee.nameProperty().set(result.getString("employeeName"));
                employee.telephoneProperty().set(result1.getInt("phoneNumber"));

                employeeData.add(employee);
            }

            while (result2.next() && result4.next()) {
                Item item = new Item();
                item.itemBarcodeProperty().set(result2.getInt("itemBarcode"));
                item.itemNoProperty().set(result2.getString("itemNo"));
                item.itemNameProperty().set(result2.getString("itemName"));
                item.categoryProperty().set(result4.getString("category"));

                itemData.add(item);
            }

            while (result3.next()) {
                BorrowedItem borrowedItem = new BorrowedItem();

                borrowedItem.idProperty().set(result3.getInt("id"));
                borrowedItem.employeeBarcodeProperty().set(result3.getInt("employeeBarcode"));
                borrowedItem.itemBarcodeProperty().set(result3.getInt("itemBarcode"));
                borrowedItem.timeTakenProperty().set(result3.getString("timeTaken"));
                borrowedItem.timeReturnedProperty().set(result3.getString("timeReturned"));

                usedItemData.addAll(borrowedItem);
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
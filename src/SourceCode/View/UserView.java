package SourceCode.View;

import SourceCode.Controller.UserController;
import SourceCode.BusinessLogic.Model;
import SourceCode.Model.Admin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.Date;

public class UserView extends Application {
    private Admin admin;
    private Model model;
    private UserController userController;
    private Scene scene;
    private BorderPane mainBorderPane;
    private BorderPane borderPane;
    private MenuItem loginSubmenu = new Menu(" Log in ");

    //BUTTONS
    Button takeButton, returnButton, searchButton, logInButton, cancelButton, submitButton;

    //TEXT FIELDS
    TextField userTxt, passwordTxt, employeeTxt, itemTxt, timeTxt, searchTxt;

    //TEXT AREA
    TextArea resultTxt;

    //LABELS
    Label title, userLbl, passwordLbl;

    //MAIN METHOD TO START THE APPLICATION
    public static void main(String[] args) {
        launch(args);
    }

    //CONTROLLER
    public UserView(){
        model = new Model();
        userController = new UserController(this, model);
    }

    //START METHOD FOR USER VIEW
    @Override
    public void start(Stage primaryStage) throws Exception {
        model.connectToDatabase();

        //LAYOUT FOR THE SCENE
        mainBorderPane = new BorderPane();

        //BUTTONS
        takeButton = new Button("  Take key  ");
        returnButton = new Button(" Return key ");
        searchButton = new Button(" Search key ");

        //BUTTONS ACTIONS
        takeButton.setOnAction(event -> {
            mainBorderPane.getChildren().addAll(takeItemPane(scene));
        });

        returnButton.setOnAction(event -> {
            mainBorderPane.getChildren().addAll(returnItemPane(scene));


        });
        searchButton.setOnAction(event -> {
            mainBorderPane.getChildren().addAll(searchItemPane(scene));
        });


        //ADMINISTRATOR MENU
        Menu menu = new Menu("Administrator");
        menu.getItems().addAll(loginSubmenu);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);
        loginSubmenu.setOnAction(event -> {
            loginSubmenu.setVisible(false);
            mainBorderPane.getChildren().addAll(adminLogIn(scene));
        });

        VBox vBox = new VBox(3);

        //SETTING CHILDREN POSITIONS IN THE BORDER PANE
        mainBorderPane.setTop(menuBar);
        mainBorderPane.setCenter(vBox);

        //ADDING CHILDREN TO THE V-BOX
        vBox.getChildren().addAll(takeButton, returnButton, searchButton);
        vBox.setPadding(new Insets(40, 70, 240, 135));
        vBox.setSpacing(22);

        //CREATING THE SCENE AND SETTING IT UP
        scene = new Scene(mainBorderPane, 400, 300);
        scene.getStylesheets().add
                (UserView.class.getResource("/CSS/UserView.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Racoon");
        primaryStage.show();
    }

    //VIEW FOR ADMIN LOG IN FROM THE MENU
    private Pane adminLogIn(Scene scene) {

        borderPane = new BorderPane();

        title = new Label(" Log in ");
        title.setId("loginAdmin");
        userLbl = new Label("Username");
        passwordLbl = new Label("Password");
        userLbl.setStyle("-fx-font-size: 15");
        passwordLbl.setStyle("-fx-font-size: 15");
        userLbl.setStyle("-fx-font-family: Courier");
        passwordLbl.setStyle("-fx-font-family: Courier");
        userLbl.setStyle("-fx-text-fill: white");
        passwordLbl.setStyle("-fx-text-fill: white");

        userTxt = new TextField();
        userTxt.setPromptText("username");
        passwordTxt = new PasswordField();
        passwordTxt.setPromptText("password");

        logInButton = new Button("Log in");
        cancelButton = new Button("Cancel");
        logInButton.setStyle("-fx-font-family: Courier");
        logInButton.setStyle("-fx-font-size: 10px");
        cancelButton.setStyle("-fx-font-family: Courier");
        cancelButton.setStyle("-fx-font-size: 10px");

        //BUTTONS ACTIONS
        cancelButton.setOnAction(event -> {
            userController.cancelButton();
            loginSubmenu.setVisible(true);
        });

        logInButton.setOnAction(event -> {
            userController.logInAction(userTxt.getText(), passwordTxt.getText());
            userTxt.clear();
            passwordTxt.clear();
        });

        //CREATING THE BOXES AND ADDING THE CHILDREN
        HBox titleBox = new HBox();
        titleBox.getChildren().add(title);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(userLbl, userTxt, passwordLbl, passwordTxt);
        vBox.setSpacing(10);
        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(cancelButton, logInButton);
        hBox.setPadding(new Insets(10, 10, 10, 20));
        titleBox.setPadding(new Insets(-40, 0, 0, 40));

        //SETTING THE BORDER PANE
        borderPane.setPadding(new Insets(60, 110, 130, 110));
        borderPane.setAlignment(titleBox, Pos.TOP_CENTER);
        borderPane.setTop(titleBox);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        scene = new Scene(borderPane, 400, 300);

        return borderPane;  //RETURNING THE BORDER PANE
    }

    //PANE FOR TAKE ITEM BUTTON
    private Pane takeItemPane(Scene scene) {

        borderPane = new BorderPane();

        //LABELS
        title = new Label(" Take item  ");
        title.setStyle("-fx-font-style: oblique");
        title.setStyle("-fx-font-size: 22px");
        title.setPadding(new Insets(-30, 0, 40, 25));

        //TEXT-FIELDS
        employeeTxt = new TextField();
        employeeTxt.setPromptText("Employee barcode");
        itemTxt = new TextField();
        itemTxt.setPromptText("Item barcode");

        int id = 0; //id column from UsedItem in MySQL
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String timestampString = timestamp.toString();
        timeTxt = new TextField(timestampString);
        timeTxt.setEditable(false);  //timeTaken non editable

        //BUTTONS
        submitButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        submitButton.setOnAction(event -> {
            userController.takeItem(employeeTxt.getText(), itemTxt.getText(), timeTxt.getText());
        });

        cancelButton.setOnAction(event -> {
            userController.cancelButton();
        });

        //CREATING AND SETTING UP THE BOXES
        VBox vBox = new VBox();
        vBox.getChildren().addAll(employeeTxt, itemTxt, timeTxt);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, submitButton);
        hBox.setPadding(new Insets(10, 10, 10, 20));

        //SETTING UP THE BORDER PANE
        borderPane.setPadding(new Insets(60, 110, 110, 110));
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        scene = new Scene(borderPane, 400, 300);

        return borderPane; //RETURNING THE BORDER PANE
    }

    //PANE FOR RETURN ITEM BUTTON
    private Pane returnItemPane(Scene scene) {

        //LAYOUT OF THE SCENE
        borderPane = new BorderPane();

        //TEXTFIELD
        itemTxt = new TextField();
        itemTxt.setPromptText("Item barcode");

        //LABELS
        title = new Label("Return Item");
        title.setStyle("-fx-font-style: oblique");
        title.setStyle("-fx-font-size: 22px");
        title.setPadding(new Insets(-30, 0, 40, 25));

        //BUTTONS
        submitButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String timeStampString = timestamp.toString();

        timeTxt = new TextField(timeStampString);
        timeTxt.setPromptText("Time returned");
        timeTxt.setEditable(false);  //timeTaken non editable

        //BUTTONS ACTIONS
        submitButton.setOnAction(event -> {
            userController.returnItem(timeTxt.getText(), itemTxt.getText());
            itemTxt.clear();
        });

        cancelButton.setOnAction(event -> {
            userController.cancelButton();
        });

        //CREATING BOXES AND SETTING THEM UP
        VBox vBox = new VBox();
        vBox.getChildren().addAll(itemTxt, timeTxt);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, submitButton);
        hBox.setPadding(new Insets(10, 10, 10, 20));

        borderPane.setPadding(new Insets(60, 110, 110, 110));
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        scene = new Scene(borderPane, 400, 300);

        return borderPane;  //RETURNING THE BORDER PANE
    }

    //PANE FOR SEARCH ITEM BUTTON
    private Pane searchItemPane(Scene scene) {

        //LAYOUT OF THE SCENE
        borderPane = new BorderPane();

        //LABELS
        title = new Label(" Search key ");
        title.setStyle("-fx-font-style: oblique");
        title.setStyle("-fx-font-size: 22px");
        title.setPadding(new Insets(-30, 0, 40, 25));

        //TEXT FIELDS
        searchTxt = new TextField();
        searchTxt.setPromptText(" Search for Item ");
        resultTxt = new TextArea();
        resultTxt.setMinSize(200, 100);
        resultTxt.setPromptText(" The result for your search will appear here: ");
        resultTxt.setEditable(false);

        //BUTTONS
        submitButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        //BUTTONS ACTIONS
        submitButton.setOnAction(event -> {
            resultTxt.clear();
            userController.searchItem(searchTxt.getText());
            resultTxt.appendText(model.returnItemInfoToUser(searchTxt.getText()));
        });

        cancelButton.setOnAction(event -> {
            userController.cancelButton();
        });

        //CREATING AND SETTING THE BOXES
        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchTxt, resultTxt);
        vBox.setSpacing(10);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancelButton, submitButton);
        hBox.setPadding(new Insets(10, 10, 10, 20));

        //SETTING UP THE BORDER PANE
        borderPane.setPadding(new Insets(60, 110, 110, 110));
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);

        scene = new Scene(borderPane, 400, 300);

        return borderPane; //RETURNING THE BORDER PANE
    }

    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //METHOD FOR SETTING THE BORDER PANE NOT VISIBLE
    public void setBorderPaneNotVisible(){
        borderPane.setVisible(false);
    }

    //METHOD FOR STARTING THE ADMIN VIEW
    public void setAdminViewTrue(){
        new AdminView();
    }
}



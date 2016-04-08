package SourceCode.View.employee;
/**
 * Created by St_Muerte on 3/24/16.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;



    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Employee");
        showMainView();

    }
    public static void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        mainLayout = loader.load();
        Scene scene= new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);
    }
    public static void showTakeItem() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/TakeItem.fxml"));
        BorderPane takeItem = loader.load();
        mainLayout.setCenter(takeItem);
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);
    }
    public static void showReturnItemView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((Main.class.getResource("view/ReturnView.fxml")));
        BorderPane returnItem = loader.load();
        mainLayout.setCenter(returnItem);
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);

    }
    public static void showSearch()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((Main.class.getResource("view/SearchView.fxml")));
        BorderPane search = loader.load();
        mainLayout.setCenter(search);
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);



    }
    public static void showAdminView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((Main.class.getResource("view/AdminView.fxml")));
        BorderPane admin = loader.load();
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);
        mainLayout.setCenter(admin);

    }


    public static void main(String[] args) {
        launch(args);
    }
}

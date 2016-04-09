package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RunView extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Employee");
        showMainView();

    }
    //MainView
    public static void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RunView.class.getResource("fxml/MainView.fxml"));
        mainLayout = loader.load();
        Scene scene= new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);
    }
    //TakeItem
    public static void showTakeItem() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RunView.class.getResource("fxml/TakeItem.fxml"));
        BorderPane takeItem = loader.load();
        mainLayout.setCenter(takeItem);
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);
    }
    //ReturnItem
    public static void showReturnItemView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((RunView.class.getResource("fxml/ReturnView.fxml")));
        BorderPane returnItem = loader.load();
        mainLayout.setCenter(returnItem);
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);

    }
    //Search
    public static void showSearch()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((RunView.class.getResource("fxml/SearchView.fxml")));
        BorderPane search = loader.load();
        mainLayout.setCenter(search);
        primaryStage.setHeight(480);
        primaryStage.setWidth(610);



    }
    //AdminView
    public static void showAdminView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((RunView.class.getResource("fxml/AdminView.fxml")));
        BorderPane admin = loader.load();
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);
        mainLayout.setCenter(admin);

    }
    //CreateEmployee
    public static void showCreateEmployee() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("fxml/CreateEmployee.fxml")));
            BorderPane borderPane = (BorderPane) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(borderPane));
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    //CreateItem
    public static void showCreateItem() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("fxml/CreateItem.fxml")));
            BorderPane borderPane = (BorderPane) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(borderPane));
            stage.show();

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}

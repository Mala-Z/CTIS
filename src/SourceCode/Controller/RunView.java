package SourceCode.Controller;

import SourceCode.BusinessLogic.Factory;
import SourceCode.Controller.admin.AdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class RunView extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;
    Factory factory;
    public static AdminController adminController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Racoon");
        showMainView();

        factory.getInstance();

    }

    /** MainView **/
    public static void showMainView(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RunView.class.getResource("/SourceCode/View/fxml/MainView.fxml"));
            mainLayout = loader.load();
            Scene scene= new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setHeight(480);
            primaryStage.setWidth(610);
        }catch (Exception e){
            System.out.println("Exception in showMainView() from RunView class:" + e.getMessage());
        }
    }

    /** TakeItem **/
    public static void showTakeItem(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RunView.class.getResource("/SourceCode/View/fxml/TakeItem.fxml"));
            BorderPane takeItem = loader.load();
            mainLayout.setCenter(takeItem);
            primaryStage.setHeight(480);
            primaryStage.setWidth(610);
        }catch (Exception e){
            System.out.println("Exception in showTakeItem() from RunView class:" + e.getMessage());
        }
    }

    /** ReturnItem **/
    public static void showReturnItemView(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("/SourceCode/View/fxml/ReturnView.fxml")));
            BorderPane returnItem = loader.load();
            mainLayout.setCenter(returnItem);
            primaryStage.setHeight(480);
            primaryStage.setWidth(610);
        }catch(Exception e){
            System.out.println("Exception in showReturnItemView() from RunView class:" + e.getMessage());
        }
    }

    /** SearchItem **/
    public static void showSearch(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("/SourceCode/View/fxml/SearchView.fxml")));
            BorderPane search = loader.load();
            mainLayout.setCenter(search);
            primaryStage.setHeight(480);
            primaryStage.setWidth(610);
        }catch (Exception e) {
            System.out.println("Exception in showSearch() from RunView class:" + e.getMessage());
        }
    }

    /** AdminView **/
    public static void showAdminView(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("/SourceCode/View/fxml/AdminView.fxml")));
            BorderPane admin = loader.load();
            primaryStage.setWidth(800);
            primaryStage.setHeight(500);
            mainLayout.setCenter(admin);
            //adminController.populateTableView();
        }catch (Exception e){
            System.out.println("Exception in showAdminView() from RunView class:" + e.getMessage());
        }
    }

    /** CreateEmployee **/
    public static void showCreateEmployee(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("/SourceCode/View/fxml/CreateEmployee.fxml")));
            BorderPane borderPane = (BorderPane) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(borderPane));
            stage.show();
        } catch (Exception e){
            System.out.println("Exception in showCreateEmployee() from RunView class:" + e.getMessage());
        }
    }

    /** CreateItem **/
    public static void showCreateItem(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((RunView.class.getResource("/SourceCode/View/fxml/CreateItem.fxml")));
            BorderPane borderPane = (BorderPane) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(borderPane));
            stage.show();
        } catch (Exception e){
            System.out.println("Exception in showCreateItem() from RunView class:" + e.getMessage());
        }
    }


}

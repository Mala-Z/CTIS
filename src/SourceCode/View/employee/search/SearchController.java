package SourceCode.View.employee.search;

import SourceCode.View.employee.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class SearchController implements Initializable {
    @FXML
    TextField EmployeeBarcodeTextField;
    @FXML
    Label employeeBarcodeLabel;
    @FXML
    TextField tfItemNumber;
    private Main main;
    @FXML
    ComboBox comboBox;
    @FXML
    TableView tableview;
    @FXML
    TableColumn itemColumn;
    @FXML
    TableColumn numberColumn;
    @FXML
    TableColumn nameColumn;
    @FXML
    RadioButton radio;



    public void radioButton(ActionEvent e){
        radio.requestFocus();

    }


    @FXML
    private void btnBackAction() throws IOException {
        main.showMainView();
        System.out.println("goes to main");
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
            //tableview.
        }
    }


    public void setData(){
        comboBox.getItems().clear();

        ArrayList list = new ArrayList();
        list.add("S");
        list.add("M");
        list.add("P");
        list.add("C");
        comboBox.getItems().addAll(list);
        ObservableList<String> obList = FXCollections.observableArrayList(list);
        /*TableColumn<String, Map> col = new TableColumn<>("Objects:", obList);
        col.setCellFactory(new PropertyValueFactory<String>("shit");
        itemColumn.setCellFactory(new PropertyValueFactory<String, Collections>());*/

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        numberColumn.setCellFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellFactory(new PropertyValueFactory<>("name"));
        //tableview.getItems().setAll(obList);*/
    }

}

package SourceCode.Controller.user;

import SourceCode.Controller.RunView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class SearchController implements Initializable {
    @FXML
    Label employeeBarcodeLabel;
    @FXML
    TextField tfItemNumber;
    private RunView runView;
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

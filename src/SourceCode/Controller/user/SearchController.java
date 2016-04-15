package SourceCode.Controller.user;

import SourceCode.BusinessLogic.Model;
import SourceCode.Controller.RunView;
import SourceCode.Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class SearchController  {

    Model model = new Model();
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
        }

    }
    @FXML
    private void checkItemBarcode() {
        try {

            int barcode = Integer.parseInt(tfItemNumber.getText());
            if (model.checkItemBarcode(barcode)) {

//                final ObservableList<Employee> data = FXCollections.observableArrayList(
//                        new Employee(3123, "1", "Alex", 62993),
//                        new Employee(4345, "2", "Ana", 42234),
//                        new Employee(3423, "3", "Ani", 23322)
//                );
//                TableColumn columnX = new TableColumn("Primary");
//                columnX.setMinWidth(100);
//                columnX.setCellValueFactory(
//                        new PropertyValueFactory<Employee, String>("columnX")
//                );
//                tableview.setItems(data);
//                tableview.getColumns().addAll(columnX);

                tableview.requestFocus();
                //show scanned item in the list


            } else {
                updateAlertMessage("Please scan the barcode again");
                tfItemNumber.setText(null);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


    //METHOD FOR THE ALERT MESSAGES SHOWN TO THE USER
    public void updateAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }




}

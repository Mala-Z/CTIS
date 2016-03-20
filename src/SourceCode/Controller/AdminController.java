package SourceCode.Controller;

import SourceCode.BusinessLogic.Model;
import SourceCode.View.AdminView;

public class AdminController {
    private Model model;
    private AdminView adminView;

    //CONSTRUCTOR
    public AdminController(AdminView adminView, Model model) {
        this.adminView = adminView;
        this.model = model;
    }

    //METHOD FOR CREATE EMPLOYEE BUTTON
    public void createEmployee(String inputBarcode, String inputIdNo, String inputName, String inputTelephoneNo ){
        try {
            if (inputBarcode.length() > 0 && inputName.length() > 0 && inputIdNo.length() > 0 && inputTelephoneNo.length() > 0) {
                model.insertEmployee(inputBarcode, inputIdNo, inputName, inputTelephoneNo);
                adminView.updateAlertMessage(" New employee was created ");
                adminView.setBorderPaneNotVisible();
                adminView.setMainBorderPaneCenter();
            } else if(inputBarcode.length() == 0 || inputName.length() == 0 || inputIdNo.length() == 0 || inputTelephoneNo.length() == 0){
                adminView.updateAlertMessage(" Please insert values in all text fields ");
            }
        }catch(Exception e){
            System.out.println("Exception in createEmployee(): " + e.getMessage());
        }
    }

    //METHOD FOR CREATE ITEM BUTTON
    public void createItem(String inputBarcode,String inputItemNo, String inputDescription, String inputCategory){
        try {
            if (inputBarcode.length() > 0 && inputItemNo.length() > 0 && inputDescription.length() > 0 && inputCategory.length() > 0) {
                model.insertItem(inputBarcode, inputItemNo,inputDescription, inputCategory);
                adminView.updateAlertMessage(" New item was created ");
                adminView.setBorderPaneNotVisible();
                adminView.setMainBorderPaneCenter();
            } else if(inputBarcode.length() == 0 || inputItemNo.length() == 0 || inputDescription.length() == 0 || inputCategory.length() == 0){
                adminView.updateAlertMessage(" Please insert values in all text fields ");
            }
        }catch(Exception e){
            System.out.println("Exception in createItem(): " + e.getMessage());
        }
    }

    //METHOD FOR UPDATING EMPLOYEE TABLE
    public void updateEmployeeTable(int barcode, String identificationNo, String name, int telephoneNo, int oldBarcode) {
        model.updateEmployeeTable("Employee", barcode, identificationNo, name, telephoneNo, oldBarcode);
    }

    //METHOD FOR UPDATING KEY TABLE
    public void updateItemTable(int barcode, String itemNo, String description, String category, int oldBarcode) {
        model.updateItemTable("Item", barcode, itemNo, description, category, oldBarcode);
    }

    //METHOD FOR DELETING AN EMPLOYEE FROM EMPLOYEE TABLE
    public void deleteEmployee(int id){
        model.deleteEmployee(id);
    }

    //METHOD FOR DELETING A KEY FROM KEY TABLE
    public void deleteItem(int id){
        model.deleteItem(id);
    }

    //METHOD FOR DELETING A ROW IN USED KEY TABLE
    public void deleteUsedItem(int id){
        model.deleteUsedItem(id);
    }

    //METHOD FOR LOG OUT BUTTON
    public void logOutButton(){
        adminView.closeStage();
    }

    //METHOD FOR CANCEL BUTTON
    public void cancelButton(){
        adminView.setBorderPaneNotVisible();
    }

}

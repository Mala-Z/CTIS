package SourceCode.Controller;

import SourceCode.BusinessLogic.Model;
import SourceCode.View.UserView;

public class UserController {
    private Model model;
    private UserView userView;

    //CONSTRUCTOR
    public UserController(UserView userView, Model model) {
        this.userView = userView;
        this.model = model;
    }

    //METHOD FOR TAKE ITEM BUTTON
    public void takeItem(String employeeInput, String itemInput, String timeDateInput) {
        try {
            String employee = model.checkEmployeeBarcode(employeeInput);
            String item = model.checkItemBarcode(itemInput);
            String checkKey = model.checkIfItemIsTaken(itemInput);
            if ((employeeInput.length() > 0) && (itemInput.length() > 0)) {
                if (employee.equals(employeeInput) && item.equals(itemInput) && !checkKey.equals(itemInput)) {
                    model.takeItem(employeeInput, itemInput, timeDateInput);
                    userView.updateAlertMessage("Your item registration was successful");  //alert message
                    userView.setBorderPaneNotVisible();  //closing the window after taking key
                } else if (!employee.equals(employeeInput) || !item.equals(itemInput)) {
                    userView.updateAlertMessage("Wrong input for item or employee");  //alert message
                    userView.setBorderPaneNotVisible();
                } else if (checkKey.equals(itemInput)) {
                    userView.updateAlertMessage(" Item is already taken by another employee");
                    userView.setBorderPaneNotVisible();
                }
            } else if ((employeeInput.length() == 0) || (itemInput.length() == 0)) {
                userView.updateAlertMessage("No input for employee or item"); //alert message
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    //METHOD FOR RETURN ITEM BUTTON
    public void returnItem(String timeDateInput, String itemInput) {
        try {
            String item = model.checkIfItemIsTaken(itemInput);
            if (item.length() > 0 && timeDateInput.length() > 0) {
                if (item.equals(itemInput)) {
                    model.updateUsedItemTable("BorrowedItem", timeDateInput, itemInput);
                    userView.updateAlertMessage(" Item return successful");
                    userView.setBorderPaneNotVisible();  //closing the window after taking key
                } else if (!item.equals(itemInput)) {
                    userView.updateAlertMessage("The item you are trying to return was not taken, please try again");
                    userView.setBorderPaneNotVisible();
                    //if key is not taken
                }
            } else if (itemInput.length() == 0) {
                userView.updateAlertMessage(" No input, please try again ");
                //if input does not exist
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    //METHOD FOR SEARCH ITEM BUTTON
    public String searchItem(String itemInput) {
        try {
            String item = model.checkItemBarcode(itemInput);
            String checkedItem = model.checkIfItemIsTaken(itemInput);
            if (itemInput.length() > 0) {
                if (checkedItem.equals(itemInput)) {
                    model.returnItemInfoToUser(itemInput);
                    userView.updateAlertMessage(" Search successful ");
                } else if (!item.equals(itemInput)) {
                    userView.updateAlertMessage(" Barcode does not exist ");
                    userView.setBorderPaneNotVisible();
                } else if (!checkedItem.equals(itemInput)) {
                    userView.updateAlertMessage(" This item is not taken ");
                    userView.setBorderPaneNotVisible();
                }
            } else if (itemInput.length() == 0) {
                userView.updateAlertMessage(" No input in the barcode item field ");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return itemInput;
    }

    //METHOD FOR ADMINISTRATOR LOG IN
    public void logInAction(String nameInput, String passInput) {
        try {
            String user = model.checkUser(nameInput);
            String pass = model.checkPassword(passInput);
            if (nameInput.length() > 0 && passInput.length() > 0) {
                if (user.equals(nameInput) && pass.equals(passInput)) {
                    model.getLogin(nameInput, passInput);
                    userView.updateAlertMessage("Welcome: " + nameInput);
                    userView.setAdminViewTrue();
                } else if (!user.equals(nameInput) || !pass.equals(passInput)) {
                    userView.updateAlertMessage(" Wrong username or password! ");
                }
            } else if (nameInput.length() == 0 && passInput.length() == 0) {
                userView.updateAlertMessage(" No input for username or password ");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    //METHOD FOR CANCEL BUTTON
    public void cancelButton() {
        userView.setBorderPaneNotVisible();
    }
}


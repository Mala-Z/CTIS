package SourceCode.BusinessLogic;

import SourceCode.Controller.main.MainViewController;
import SourceCode.Model.adminTableViewObjects.EmployeeObj;
import SourceCode.Model.adminTableViewObjects.ItemObj;
import SourceCode.Model.dbTablesObjects.Employee;
import SourceCode.Model.dbTablesObjects.Item;
import SourceCode.Model.insertIntoDBObjects.WriteConsumablesToDB;
import SourceCode.Model.insertIntoDBObjects.WriteReturnToDB;
import SourceCode.Model.insertIntoDBObjects.WriteTakeToDB;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BusinessLogic {

    private Item item;
    private EmployeeObj employeeObj;
    ConnectDB connectDB = Factory.connectDB;


    /* METHOD FOR INSERTING EMPLOYEE INTO THE DATABASE */
    public void insertEmployee(int employeeBarcode, String employeeNo, String employeeName, int telephoneNo) throws MySQLIntegrityConstraintViolationException{
        String sql = "INSERT INTO Employee VALUES (?, ?, ?)";
        String sql2 = "INSERT INTO PhoneNumber (id, phoneNumber, employeeBarcode) VALUES (null, ?, (SELECT employeeBarcode FROM Employee WHERE employeeBarcode =?));";
        try {
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            PreparedStatement preparedStatement2 = connectDB.preparedStatement(sql2);
            preparedStatement.setInt(1, employeeBarcode);
            preparedStatement.setString(2, employeeNo);
            preparedStatement.setString(3, employeeName);
            preparedStatement2.setInt(1, telephoneNo);
            preparedStatement2.setInt(2, employeeBarcode);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            MainViewController.updateAlertMessage("Registration successful");
        } catch (SQLException e) {
            e.printStackTrace();
            MainViewController.updateWarningMessage("Possible duplicates");
            System.out.println("Error in insertEmployee() from BusinessLogic class: " + e.getMessage());
        }
    }

    /* METHOD FOR INSERTING ITEM INTO THE DATABASE */
    public void insertItem(int itemBarcode, String itemNo, String itemName, String category) {
        String sql = "INSERT INTO Item (itemBarcode, itemNo, itemName) VALUES (?, ?, ?); ";
        String sql2 = "INSERT INTO Category (id, category, itemBarcode) VALUES (null, ?, (SELECT itemBarcode FROM Item WHERE itemBarcode = ?));";
        try {
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            PreparedStatement preparedStatement2 = connectDB.preparedStatement(sql2);
            preparedStatement.setInt(1, itemBarcode);
            preparedStatement.setString(2, itemNo);
            preparedStatement.setString(3, itemName);
            preparedStatement2.setString(1, category);
            preparedStatement2.setInt(2, itemBarcode);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            MainViewController.updateAlertMessage("Registration successful");
        } catch (SQLException e) {
            MainViewController.updateWarningMessage("Possible duplicates");
            e.printStackTrace();
            System.out.println("Error in insertItem() from BusinessLogic class: " + e.getMessage());
        }
    }

    /* METHOD FOR UPDATING THE EMPLOYEE TABLE */
    public void updateEmployeeTable(String employeeBarcode, String employeeNo, String employeeName, String phoneNumber, String oldBarcode) {
        String sql = "UPDATE Employee, PhoneNumber\n" +
                "SET Employee.employeeBarcode = ?, Employee.employeeNo = ?, Employee.employeeName = ?, PhoneNumber.phoneNumber = ?\n" +
                "WHERE Employee.employeeBarcode = PhoneNumber.employeeBarcode\n" +
                "AND Employee.employeeBarcode = ?;";

        try {

            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, employeeBarcode);
            preparedStatement.setString(2, employeeNo);
            preparedStatement.setString(3, employeeName);

            //preparedStatement.setString(4, oldBarcode);
            preparedStatement.setString(4, phoneNumber);

            preparedStatement.setString(5, oldBarcode);
            preparedStatement.executeUpdate();


            MainViewController.updateAlertMessage("Registration successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateEmployeeTable() from BusinessLogic class: " + e.getMessage());
            MainViewController.updateWarningMessage("Possible duplicates!");
        }
    }

    /* METHOD FOR UPDATING THE ITEM TABLE */
    public void updateItemTable(String itemBarcode, String itemNo, String itemName, String itemCategory, String oldBarcode) {
        String sql = "UPDATE Item, Category\n" +
                "SET Item.itemBarcode = ?, Item.itemNo = ?, Item.itemName = ?, Category.category = ?\n" +
                "WHERE Item.itemBarcode = Category.itemBarcode\n" +
                "AND Item.itemBarcode = ?;";

        try {

            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, itemBarcode);
            preparedStatement.setString(2, itemNo);
            preparedStatement.setString(3, itemName);

            preparedStatement.setString(4, itemCategory);

            preparedStatement.setString(5, oldBarcode);
            preparedStatement.executeUpdate();


            MainViewController.updateAlertMessage("Registration successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateItemTable() from BusinessLogic class: " + e.getMessage());
            MainViewController.updateWarningMessage("Possible duplicates!");
        }
    }

    /* METHOD FOR UPDATING THE BORROWED ITEM TABLE */
    public Item updateBorrowedItemTable(String table, Timestamp timeReturned, int itemBarcode) {
        String sql = "UPDATE " + table + " SET timeReturned =? WHERE itemBarcode =? AND timeReturned IS null;";
        try {
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            preparedStatement.setTimestamp(1, timeReturned);
            preparedStatement.setInt(2, itemBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateBorrowedItemTable() from BusinessLogic class: " + e.getMessage());
        }
        return item;
    }

    /* METHOD FOR DELETING AN EMPLOYEE FROM THE DATABASE */
    public void deleteEmployee(String employeeBarcode) {
        String sql = "DELETE FROM Employee WHERE employeeBarcode=?";
        try {
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, employeeBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            MainViewController.updateWarningMessage("Error while trying to delete employee");
            System.out.println("Error in deleteEmployee() from BusinessLogic class: " + e.getMessage());
        }
    }

    /* METHOD FOR DELETING AN ITEM FROM THE DATABASE */
    public void deleteItem(String itemBarcode) {
        String sql = "DELETE FROM Item WHERE itemBarcode=?";
        try {
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setString(1, itemBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            MainViewController.updateWarningMessage("Error while trying to delete employee");
            System.out.println("Error in deleteItem() from BusinessLogic class: " + e.getMessage());
        }
    }

    /* METHOD FOR INSERTING INTO BORROWED ITEM TABLE */
    public void takeItem(ArrayList<WriteTakeToDB> arrayList) {
        String sql = "INSERT INTO BorrowedItem VALUES (null, ?, ?, ?, null);";
        String sql2 = "INSERT INTO Place VALUES (null, ?, ?, (SELECT id FROM BorrowedItem WHERE itemBarcode = ? AND timeReturned IS NULL));";
        Iterator<WriteTakeToDB> it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                WriteTakeToDB writeTakeToDB = it.next();
                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
                PreparedStatement preparedStatement2 = connectDB.preparedStatement(sql2);
                preparedStatement.setInt(1, Integer.valueOf(writeTakeToDB.getEmployeeBarcode()));
                preparedStatement.setString(2, writeTakeToDB.getItemBarcode());
                preparedStatement.setString(3, writeTakeToDB.getTimeTaken());
                preparedStatement2.setString(1, writeTakeToDB.getPlace());
                preparedStatement2.setString(2, writeTakeToDB.getPlaceReference());
                preparedStatement2.setString(3, writeTakeToDB.getItemBarcode());
                preparedStatement.executeUpdate();
                preparedStatement2.executeUpdate();
                //preparedStatement.close();
                //preparedStatement2.close();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in takeItem() from BusinessLogic class: " + e.getMessage());
            }
        }
    }

    public void takeConsumables(ArrayList<WriteConsumablesToDB> arrayList) {
        String sql = "INSERT INTO UsedProduct VALUES (null, ?, ?, ?, ?)";

        Iterator<WriteConsumablesToDB> it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                WriteConsumablesToDB writeConsumablesToDB = it.next();

                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
                preparedStatement.setString(1, writeConsumablesToDB.getEmployeeBarcode());
                preparedStatement.setString(2, writeConsumablesToDB.getItemBarcode());
                preparedStatement.setString(3, writeConsumablesToDB.getQuantity());
                preparedStatement.setString(4, writeConsumablesToDB.getTimeTaken());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in takeConsumables() from BusinessLogic class: " + e.getMessage());
            }
        }
    }
    public void returnItem(ArrayList<WriteReturnToDB> arrayList) {
        String sql = "UPDATE BorrowedItem SET timeReturned = ? WHERE itemBarcode = ? AND timeReturned IS null;";

        Iterator<WriteReturnToDB> it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                WriteReturnToDB writeReturnToDB = it.next();

                PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
                preparedStatement.setString(1, writeReturnToDB.getTimeReturned());
                preparedStatement.setString(2, writeReturnToDB.getItembarcode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in returnItem() from BusinessLogic class: " + e.getMessage());
            }
        }
    }

    /* METHOD FOR CHECKING IF THE ITEM IS ALREADY REGISTERED TAKEN */
    public boolean searchItem(String itemBarcode) {
        try {
            String query = "SELECT itemBarcode FROM BorrowedItem WHERE itemBarcode =? AND timeReturned is null;";

            PreparedStatement preparedStatement = connectDB.preparedStatement(query);
            preparedStatement.setString(1, itemBarcode);

            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in searchItem() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }
    /* METHOD FOR CHECKING IF THE ITEM IS ALREADY REGISTERED TAKEN */
    public boolean searchItemByNumber(String itemNumber) {
        try {
            String query = "SELECT itemNo FROM Item" +
                    " INNER JOIN BorrowedItem ON" +
                    " Item.itemBarcode = BorrowedItem.itemBarcode" +
                    " WHERE itemNo =? AND timeReturned is null;";

            PreparedStatement preparedStatement = connectDB.preparedStatement(query);
            preparedStatement.setString(1, itemNumber);

            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in searchItemByNumber() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }

    /* METHOD FOR CHECKING THE EMPLOYEE BARCODE STORED INTO THE DATABASE */
    public boolean checkEmployeeBarcode(String employeeBarcode) {

        try {
            String query = "SELECT employeeBarcode FROM Employee WHERE employeeBarcode=?";

            PreparedStatement preparedStatement = connectDB.preparedStatement(query);

            preparedStatement.setString(1, employeeBarcode);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkEmployeeBarcode() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }
    public boolean checkEmployeeNumber(String employeeNumber) {

        try {
            String query = "SELECT employeeNo FROM Employee WHERE employeeNo=?";

            PreparedStatement preparedStatement = connectDB.preparedStatement(query);

            preparedStatement.setString(1, employeeNumber);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkEmployeeName() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }
    public boolean checkEmployeeName(String employeeName) {

        try {
            String query = "SELECT employeeName FROM Employee WHERE employeeName=?";

            PreparedStatement preparedStatement = connectDB.preparedStatement(query);

            preparedStatement.setString(1, employeeName);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkEmployeeName() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }


    /* METHOD FOR CHECKING THE ITEM BARCODE STORED INTO THE DATABASE */
    public boolean checkItemBarcode(String itemBarcode) {

        try {

            String query = "SELECT itemBarcode FROM Item WHERE itemBarcode = ?";
            PreparedStatement preparedStatement = connectDB.preparedStatement(query);
            preparedStatement.setString(1, itemBarcode);

            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkItemBarcode() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }
    public boolean checkItemNo(String itemNo) {

        try {
            String query = "SELECT itemNo FROM Item WHERE itemNo = ?";
            PreparedStatement preparedStatement = connectDB.preparedStatement(query);
            preparedStatement.setString(1, itemNo);

            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkItemNo() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }
    public boolean checkItemCategory(String itemCategory) {

        try {
            String query = "SELECT category FROM Category WHERE category = ?";
            PreparedStatement preparedStatement = connectDB.preparedStatement(query);
            preparedStatement.setString(1, itemCategory);

            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkItemCategory() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }

    /* METHOD FOR CHECKING THE LOGIN CREDENTIALS */
    public boolean checkLogInCredentials(String username, String password) {
        try {
            String query = "SELECT * FROM Admin WHERE username=? AND password=?";

            PreparedStatement preparedStatement = connectDB.preparedStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(1, password);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in checkLoginCredentials() from BusinessLogic class: " + e.getMessage());
        }
        return false;
    }

    /* RETURN THE LOGIN CREDENTIALS */
    public String getLogin(String name, String pass) {
        String out = "";
        try {
            String nameAndPass = "SELECT * FROM Admin where username = ? AND password= ?";

            PreparedStatement preparedStatement = connectDB.preparedStatement(nameAndPass);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                out = results.getString(1) + "\n" + results.getString(2);
            } else {
                out = " no data ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getLogin() from BusinessLogic class: " + e.getMessage());
        }
        return out;
    }

    /* METHOD FOR RETURNING THE CATEGORY */
    public ObservableList<String> getCategory() {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT category FROM Category";
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                String category = (resultSet.getString(1));


                observableList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getCategory() from BusinessLogic class: "  + e.getMessage());
        }
        return observableList;
    }

    public ObservableList<String> getPlace() {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT place FROM Place";
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                String place = (resultSet.getString(1));


                observableList.add(place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getPlace() from BusinessLogic class: "  + e.getMessage());
        }
        return observableList;
    }
    public String getEmployeeName(String employeeBarcode) {
        String name = "";
        //ArrayList<String> arrayList = new ArrayList<>();

        try {
            String sql = "SELECT employeeName FROM Employee WHERE employeeBarcode = ?";
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            preparedStatement.setString(1, employeeBarcode);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {


                name = resultSet.getString(1);
                //arrayList.add(employeeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getEmployeeName() from BusinessLogic class: "  + e.getMessage());
        }
        return name;
    }

    public EmployeeObj getEmployee(String employeeBarcode) {
        //ObservableList<EmployeeObj> observableList = FXCollections.observableArrayList();
        EmployeeObj employeeObj = new EmployeeObj();

        try {
            String sql = "SELECT Employee.employeeBarcode, employeeNo, employeeName, phoneNumber FROM Employee\n" +
                    "INNER JOIN PhoneNumber ON\n" +
                    "Employee.employeeBarcode = PhoneNumber.employeeBarcode\n"+
                    "WHERE Employee.employeeBarcode = ?";
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            preparedStatement.setString(1, employeeBarcode);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String barcode = resultSet.getString("employeeBarcode");
                String number = resultSet.getString("employeeNo");
                String name = resultSet.getString("employeeName");
                String phone = resultSet.getString("phoneNumber");

                employeeObj.setEmployeeBarcode(barcode);
                employeeObj.setEmployeeNo(number);
                employeeObj.setEmployeeName(name);
                employeeObj.setPhoneNumber(phone);

                //observableList.add(employeeObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getEmployee() from BusinessLogic class: "  + e.getMessage());
        }
        return employeeObj;
    }
    public ItemObj getItem(String itemBarcode) {
        //ObservableList<EmployeeObj> observableList = FXCollections.observableArrayList();
        ItemObj itemObj = new ItemObj();

        try {
            String sql = "SELECT Item.itemBarcode, itemNo, itemName, category FROM Item\n" +
                    "INNER JOIN Category ON\n" +
                    "Item.itemBarcode = Category.itemBarcode\n"+
                    "WHERE Item.itemBarcode = ?";
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);

            preparedStatement.setString(1, itemBarcode);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String barcode = resultSet.getString("itemBarcode");
                String number = resultSet.getString("itemNo");
                String name = resultSet.getString("itemName");
                String category = resultSet.getString("category");

                itemObj.setItemBarcode(barcode);
                itemObj.setItemNo(number);
                itemObj.setItemName(name);
                itemObj.setCategory(category);

                //observableList.add(employeeObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getItem() from BusinessLogic class: "  + e.getMessage());
        }
        return itemObj;
    }

    /* METHOD FOR DELETING A ROW FROM BORROWED ITEM TABLE IN THE DATABASE */
    public void deleteBorrowedItem(int id) {
        String sql = "DELETE FROM UsedItem WHERE id=?";
        try {
            PreparedStatement preparedStatement = connectDB.preparedStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in deleteUsedItem() from BusinessLogic class: " + e.getMessage());
        }
    }

}

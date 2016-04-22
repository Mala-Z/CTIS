
package SourceCode.BusinessLogic;

import SourceCode.Model.Employee;
import SourceCode.Model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Model {
    private Item item;
    private Employee employee;
    public static Connection conn = null;
    private static Model model = null;


    //CONSTRUCTOR
    private Model() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        System.out.println("***********Welcome to Racoon**************");
        try {
            String DB_URL = "jdbc:mysql://db4free.net:3306/ctis_racoon";
            String USER = "ctis_admin";
            String PASS = "Ra%c00n%CTIS";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("conn obj created " + conn + " message: ");
        } catch (SQLException e) {
            System.out.println("db error" + e.getMessage());
        }
    }

    public static Model getInstance(){
        if(model == null){
            return new Model();
        }
        return model;
    }
    public ResultSet resultSet(String sql){
        try{
            return conn.createStatement().executeQuery(sql);
        }
        catch (SQLException ex){
            return null;
        }
    }
    public PreparedStatement preparedStatement(String sql){
        try{
            return conn.prepareStatement(sql);
        }
        catch (SQLException ex){
            return null;
        }
    }

    //METHOD FOR INSERTING EMPLOYEE INTO THE DATABASE
    public void insertEmployee(int employeeBarcode, String employeeNo, String employeeName, int telephoneNo) {
        String sql = "INSERT INTO Employee VALUES (?, ?, ?)";
        String sql2 = "INSERT INTO PhoneNumber (id, phoneNumber, employeeBarcode) VALUES (null, ?, (SELECT employeeBarcode FROM Employee WHERE employeeBarcode =?));";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement.setInt(1, employeeBarcode);
            preparedStatement.setString(2, employeeNo);
            preparedStatement.setString(3, employeeName);
            preparedStatement2.setInt(1, telephoneNo);
            preparedStatement2.setInt(2, employeeBarcode);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR INSERTING ITEM INTO THE DATABASE
    public void insertItem(int itemBarcode, String itemNo, String itemName, String category) {
        String sql = "INSERT INTO Item (itemBarcode, itemNo, itemName) VALUES (?, ?, ?); " ;
        String sql2 = "INSERT INTO Category (id, category, itemBarcode) VALUES (null, ?, (SELECT itemBarcode FROM Item WHERE itemBarcode = ?));";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement.setInt(1, itemBarcode);
            preparedStatement.setString(2, itemNo);
            preparedStatement.setString(3, itemName);
            preparedStatement2.setString(1, category);
            preparedStatement2.setInt(2, itemBarcode);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR UPDATING THE BORROWED ITEM TABLE
    public Item updateBorrowedItemTable(String table, Timestamp timeReturned, int itemBarcode) {
        String sql = "UPDATE " + table + " SET timeReturned =? WHERE itemBarcode =? AND timeReturned IS null;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setTimestamp(1, timeReturned);
            preparedStatement.setInt(2, itemBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    //METHOD FOR UPDATING THE EMPLOYEE TABLE
    public Employee updateEmployeeTable(String table, int employeeBarcode, String employeeNo, String employeeName, int telephoneNo, int oldBarcode) {
        String sql = "UPDATE " + table + " SET employeeBarcode=?, employeeNo=?, employeeName=?, telephoneNo=? WHERE employeeBarcode=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, employeeBarcode);
            preparedStatement.setString(2, employeeNo);
            preparedStatement.setString(3, employeeName);
            preparedStatement.setInt(4, telephoneNo);
            preparedStatement.setInt(5, oldBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    //METHOD FOR UPDATING THE ITEM TABLE
    public Item updateItemTable(String table, int itemBarcode, String itemNo, String itemName, String category, int oldBarcode) {
        String sql = "UPDATE " + table + " SET itemBarcode=?, itemNo=?, description=?, category=? WHERE itemBarcode=?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, itemBarcode);
            preparedStatement.setString(2, itemNo);
            preparedStatement.setString(3, itemName);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, oldBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    //METHOD FOR CHECKING THE EMPLOYEE BARCODE STORED INTO THE DATABASE
    public boolean checkEmployeeBarcode(int employeeBarcode) {
        try {
            String query = "SELECT employeeBarcode FROM Employee WHERE employeeBarcode=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, employeeBarcode);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    //METHOD FOR CHECKING THE ITEM BARCODE STORED INTO THE DATABASE
    public boolean checkItemBarcode(int itemBarcode) {

        try {
            String query = "SELECT itemBarcode FROM Item WHERE itemBarcode = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, itemBarcode);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //METHOD FOR INSERTING INTO BORROWED ITEM TABLE
    public void takeItem(int employeeBarcode, int itemBarcode, Timestamp timeTaken, String place) {
        String sql = "INSERT INTO BorrowedItem VALUES (null , ? , ? , ? , null)";
        String sql2 = "INSERT INTO Place (id, place, itemBarcode) VALUES (null, ?, (SELECT itemBarcode FROM BorrowedItem WHERE itemBarcode =?));";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement.setInt(1, employeeBarcode);
            preparedStatement.setInt(2, itemBarcode);
            preparedStatement.setTimestamp(3, timeTaken);
            preparedStatement2.setString(1, place);
            preparedStatement2.setInt(2, itemBarcode);
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR CHECKING IF THE ITEM IS ALREADY REGISTERED TAKEN
    public boolean checkIfItemIsTaken(int itemBarcode) {
        try {
            String query = "SELECT itemBarcode FROM BorrowedItem WHERE itemBarcode =? AND timeReturned is null;";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, itemBarcode);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //METHOD FOR RETURNING THE TAKEN ITEM TO THE DATABASE
    public void returnItem(int itemBarcode) {
        try {
            String query = "SELECT itemName, timeTaken, employeeName FROM BorrowedItem INNER JOIN Item ON BorrowedItem.itemBarcode = Item.itemBarcode INNER JOIN Employee ON BorrowedItem.employeeBarcode = Employee.employeeBarcode WHERE BorrowedItem.itemBarcode = ? AND BorrowedItem.timeReturned IS NULL;";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, itemBarcode);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*//METHOD FOR CHECKING THE USERNAME OF THE ADMINISTRATOR
    public String checkUser(String user) {
        String out = "";
        try {
            String query = "SELECT username FROM Admin WHERE username =?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, user);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                out = results.getString(1);
            } else {
                out = "";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    //METHOD FOR CHECKING THE PASSWORD OF THE ADMINISTRATOR
    public String checkPassword(String pass) {
        String out = "";
        try {
            String query = "SELECT password FROM Admin WHERE password=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, pass);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                out = results.getString(1);
            } else {
                out = "";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }*/

    //METHOD FOR CHECKING THE LOGIN CREDENTIALS
    public boolean checkLoginCredentials(String username, String password) {
        try {
            String query = "SELECT * FROM Admin WHERE username=? AND password=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

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
        }
        return false;
    }

    //RETURN THE LOGIN CREDENTIALS
    public String getLogin(String name, String pass) {
        String out = "";
        try {
            String nameAndPass = "SELECT * FROM Admin where username = ? AND password= ?";

            PreparedStatement preparedStatement = conn.prepareStatement(nameAndPass);

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
        }
        return out;
    }

    //METHOD FOR RETURNING THE CATEGORY
    public ObservableList<String> getCategory() {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT category FROM Category";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                String category = (resultSet.getString(1));


                observableList.add(category);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return observableList;
    }
    //method to populate tableview
    /*public ObservableList<String> getCategory() {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT category FROM Item";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                String category = (resultSet.getString(1));


                observableList.add(category);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return observableList;
    }*/

    //METHOD FOR DELETING AN EMPLOYEE FROM THE DATABASE
    public void deleteEmployee(int employeeBarcode) {
        String sql = "DELETE FROM Employee WHERE employeeBarcode=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, employeeBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR DELETING AN ITEM FROM THE DATABASE
    public void deleteItem(int itemBarcode) {
        String sql = "DELETE FROM Item WHERE itemBarcode=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, itemBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR DELETING A ROW FROM USED ITEM TABLE IN THE DATABASE
    public void deleteUsedItem(int id) {
        String sql = "DELETE FROM UsedItem WHERE id=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
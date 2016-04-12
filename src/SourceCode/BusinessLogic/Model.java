
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

    //CONSTRUCTOR
    public Model() {
        connectToDatabase();
    }

    public void connectToDatabase() {
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

    //METHOD FOR INSERTING EMPLOYEE INTO THE DATABASE
    public void insertEmployee(String employeeBarcode, String identificationNo, String employeeName, String telephoneNo) {
        String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, employeeBarcode);
            preparedStatement.setString(2, identificationNo);
            preparedStatement.setString(3, employeeName);
            preparedStatement.setString(4, telephoneNo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR INSERTING ITEM INTO THE DATABASE  ---insert category into category table---?
    public void insertItem(String itemBarcode, String itemNo, String itemName, String category) {
        String sql = "INSERT INTO Item VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, itemBarcode);
            preparedStatement.setString(2, itemNo);
            preparedStatement.setString(3, itemName);
            preparedStatement.setString(4, category);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR UPDATING THE USED ITEM TABLE
    public Item updateUsedItemTable(String table, String timeReturned, String itemBarcode) {
        String sql = "UPDATE " + table + " SET timeReturned =? WHERE itemBarcode =? AND timeReturned IS null;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, timeReturned);
            preparedStatement.setString(2, itemBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    //METHOD FOR UPDATING THE EMPLOYEE TABLE
    public Employee updateEmployeeTable(String table, int employeeBarcode, String identificationNo, String name, int telephoneNo, int oldBarcode) {
        String sql = "UPDATE " + table + " SET employeeBarcode=?, identificationNo=?, name=?, telephoneNo=? WHERE employeeBarcode=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, employeeBarcode);
            preparedStatement.setString(2, identificationNo);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, telephoneNo);
            preparedStatement.setInt(5, oldBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    //METHOD FOR UPDATING THE ITEM TABLE
    public Item updateItemTable(String table, int itemBarcode, String itemNo, String description, String category, int oldBarcode) {
        String sql = "UPDATE " + table + " SET itemBarcode=?, itemNo=?, description=?, category=? WHERE itemBarcode=?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, itemBarcode);
            preparedStatement.setString(2, itemNo);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, oldBarcode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    //METHOD FOR CHECKING THE EMPLOYEE BARCODE STORED INTO THE DATABASE
    public boolean checkEmployeeBarcode(String employeeBarcode) {
        try {
            String query = "SELECT employeeBarcode FROM Employee WHERE employeeBarcode=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, employeeBarcode);
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
    public String checkItemBarcode(String itemBarcode) {
        String out = "";
        try {
            String query = "SELECT itemBarcode FROM Item WHERE itemBarcode = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, itemBarcode);
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

    //METHOD FOR INSERTING ITEM INTO THE DATABASE
    public void takeItem(String employeeBarcode, String itemBarcode, String timeTaken) {
        String sql = "INSERT INTO UsedItem VALUES (null , ? , ? , ? , null)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, employeeBarcode);
            preparedStatement.setString(2, itemBarcode);
            preparedStatement.setString(3, timeTaken);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //METHOD FOR CHECKING IF THE ITEM IS ALREADY REGISTERED TAKEN
    public String checkIfItemIsTaken(String itemBarcode) {
        String out = "";
        try {
            String query = "SELECT itemBarcode FROM UsedItem \n" +
                    "WHERE itemBarcode =?" +
                    "AND timeReturned is null;";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, itemBarcode);
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

    //METHOD FOR RETURNING THE TAKEN ITEM TO THE DATABASE
    public String returnItemInfoToUser(String itemBarcode) {
        String out = "";
        try {
            String query = "SELECT description, name, timeTaken FROM UsedItem INNER JOIN Item ON UsedItem.itemBarcode = Item.itemBarcode INNER JOIN Employee ON UsedItem.employeeBarcode = Employee.employeeBarcode WHERE UsedItem.itemBarcode = ? AND UsedItem.timeReturned IS NULL;";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, itemBarcode);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next()) {
                out = results.getString(1) + "\n" + results.getString(2) + "\n" + results.getString(3);
            } else {
                out = " no data ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    //METHOD FOR CHECKING THE USERNAME OF THE ADMINISTRATOR
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
    }

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
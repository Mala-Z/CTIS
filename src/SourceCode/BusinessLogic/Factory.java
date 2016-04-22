package SourceCode.BusinessLogic;

import SourceCode.Model.Employee;
import SourceCode.Model.Item;

import java.sql.*;

public class Factory {
    private Item item;
    private Employee employee;
    public static Connection conn;
    private static Factory factory = null;


    //CONSTRUCTOR
    private Factory() {
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

    public static Factory getInstance() {
        if (factory == null) {
            return new Factory();
        }
        return factory;
    }

    public ResultSet resultSet(String sql) {
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("resultSet method error");
            return null;
        }
    }

    public PreparedStatement preparedStatement(String sql) {
        try {
            return conn.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println("preparedStatment method error");
            return null;
        }
    }


}

package SourceCode.BusinessLogic;

import java.sql.*;

/**
 * Created by Paula on 25/04/16.
 */
public class ConnectDB {
    public static Connection conn;

    /* CONSTRUCTOR */
    public ConnectDB() {
        connectToDatabase();
    }

    /* DATABASE CONNECTION */
    private static void connectToDatabase() {
        System.out.println("***********Welcome to Racoon**************");
        try {
            String DB_URL = "jdbc:mysql://db4free.net:3306/ctis_racoon";
            String USER = "ctis_admin";
            String PASS = "Ra%c00n%CTIS";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("conn obj created " + conn + " message: ");
        } catch (SQLException e) {
            System.out.println("DB error" + e.getMessage());
        }
    }

    /* RESULT SET METHOD */
    public ResultSet resultSet(String sql) {
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error in resultSet() from ConnectDB class");
            return null;
        }
    }

    /* PREPARED STATEMENT METHOD */
    public PreparedStatement preparedStatement(String sql) {
        try {
            return conn.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println("Error in preparedStatement() from Factory class");
            return null;
        }
    }


}

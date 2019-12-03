package com.example.work0905.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    // Database credentials
    private static final String dbName = "work0905db";
    private static final String user = "work0905db";
    private static final String url = "jdbc:mysql://den1.mysql6.gear.host:3306/" + dbName;
    private static final String password = "Oa4fXkQ?6S9!";

    // Given Order
//    public static final int LOG_IN =

    // Database tables and columns
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String COLUMN_EMPLOYEES_ID = "`id_employee`";
    public static final String COLUMN_EMPLOYEES_EMAIL = "email";
    public static final String COLUMN_EMPLOYEES_PASSWORD = "`password`";
    public static final String COLUMN_EMPLOYEES_NAME = "name";
    public static final String COLUMN_EMPLOYEES_LAST_NAME = "last_name";
    public static final String COLUMN_EMPLOYEES_SALARY = "salary";
    public static final String COLUMN_EMPLOYEES_SALARY_TYPE = "salary_type";
    public static final String COLUMN_EMPLOYEES_STANDARD_SHIFT = "standard_shift";
    public static final String COLUMN_EMPLOYEES_HOLIDAYS_LEFT = "holidays_left";
    public static final String COLUMN_EMPLOYEES_HOLIDAYS_BONUS = "holidays_bonus";

    // Prepared Statement Queries
    private static final String QUERY_USER_AUTHENTICATION = "SELECT " + COLUMN_EMPLOYEES_PASSWORD + " FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_EMPLOYEES_ID + " =?";
    public static final String QUERY_USER_CHECK_IN_OUT = "CREATE TABLE IF NOT EXISTS " + dbName + ".";

    private Connection conn;
    private PreparedStatement preparedStatement;



    public boolean openDbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            preparedStatement = conn.prepareStatement(QUERY_USER_AUTHENTICATION);
            System.out.println("\nConnection SUCCESSFUL\n");
            return true;
        } catch (SQLException e) {
            System.out.println("\nCONNECTION FAILED TO DB\n");
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("NO DRIVER CLASS");
            e.printStackTrace();
            return false;
        }
    }


    public void closeDbConnection() {
        try {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
//                System.out.println("\nConnection CLOSED OK!\n");
            }
        } catch (SQLException e) {
//            System.out.println("Couldn't close connection");
            e.printStackTrace();
        }
    }


    // Used to log in a user
    public boolean userAuthentication(String employeeID, String password){
        try {
            // Replacing the first occurrence of the prepared statement's wildcard with the employeeID
            preparedStatement.setString(1, employeeID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                if(password.equals(result.getString(1))){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public boolean checkInOut(String username){
        return true;
    }

}

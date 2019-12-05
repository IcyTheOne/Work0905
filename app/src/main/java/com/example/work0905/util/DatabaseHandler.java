package com.example.work0905.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.work0905.model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

    // Database credentials
    private static final String dbName = "work0905db";
    private static final String user = "work0905db";
    private static final String url = "jdbc:mysql://den1.mysql6.gear.host:3306/" + dbName;
    private static final String password = "Oa4fXkQ?6S9!";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEBER = "remeber";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // CONSTANTS used to insert the correct QUERY to the PREPARED simpleStatement, when we open
    // connection with database (openDbConnection method).
    public static final int FOR_LOG_IN = 1;
    public static final int FOR_CHECK_IN_OUT = 2;

    /**
     * Database tables and columns
     **/
    // Table holding details specific for each employee
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EMPLOYEES_ID = "id_employee";
    public static final String COLUMN_EMPLOYEES_EMAIL = "email";
    public static final String COLUMN_EMPLOYEES_PASSWORD = "password";
    public static final String COLUMN_EMPLOYEES_NAME = "name";
    public static final String COLUMN_EMPLOYEES_LAST_NAME = "last_name";
    public static final String COLUMN_EMPLOYEES_SALARY = "salary";
    public static final String COLUMN_EMPLOYEES_SALARY_TYPE = "salary_type";
    public static final String COLUMN_EMPLOYEES_STANDARD_SHIFT = "standard_shift";
    public static final String COLUMN_EMPLOYEES_HOLIDAYS_LEFT = "holidays_left";
    public static final String COLUMN_EMPLOYEES_HOLIDAYS_BONUS = "holidays_bonus";

    // Each employee has it's own workdays table
    public static final String TABLE_WORKDAYS = "workdays_";
    public static final String COLUMN_WORKDAYS_WORKDAY_ID = "id_workday";
    public static final String COLUMN_WORKDAYS_DATE = "date";
    public static final String COLUMN_WORKDAYS_CHECK_IN = "check_in";
    public static final String COLUMN_WORKDAYS_CHECK_OUT = "check_out";
    public static final String COLUMN_WORKDAYS_MINUTES_WORKED = "minutes_worked";
    public static final String COLUMN_WORKDAYS_OVERTIME = "overtime";

    /**
     * Queries
     **/
//    private static final String QUERY_USER_AUTHENTICATION = "SELECT " + COLUMN_EMPLOYEES_PASSWORD + " FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_EMPLOYEES_ID + " =?";
    private static final String QUERY_GET_EMPLOYEE_DETAILS = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_EMPLOYEES_ID + " =?";
    // Is not allowed to use the wild card (?) of prepared statements as a placeholder of table names. Table names must be hardcoded.
    // So we have to use a variable for the table name and create the string of the simpleStatement inside the method we want it.
    private static final String QUERY_CREATE_WORKDAYS_TABLE_1 = "CREATE TABLE IF NOT EXISTS " + dbName + "." + TABLE_WORKDAYS;
    private static final String QUERY_CREATE_WORKDAYS_TABLE_2 = " (" +
            COLUMN_WORKDAYS_WORKDAY_ID + " INT NOT NULL AUTO_INCREMENT, " +
            COLUMN_WORKDAYS_DATE + " DATE NULL, " +
            COLUMN_WORKDAYS_CHECK_IN + " TIMESTAMP NULL, " +
            COLUMN_WORKDAYS_CHECK_OUT + " TIMESTAMP NULL, " +
            COLUMN_WORKDAYS_MINUTES_WORKED + " INT NULL, " +
            COLUMN_WORKDAYS_OVERTIME + " INT NULL, " +
            "PRIMARY KEY (" + COLUMN_WORKDAYS_WORKDAY_ID + ")" +
            ")";
    public static final String QUERY_INSERT_INTO_WORKDAYS_TABLE_1 = "INSERT INTO " + TABLE_WORKDAYS;
    public static final String QUERY_INSERT_INTO_WORKDAYS_TABLE_2 = " (" ;
            ;


    private Connection conn;
    //Prepared simpleStatement to INSERT, UPDATE and DELETE
    private PreparedStatement preparedStatement;
    // Simple simpleStatement to CREATE tables as the preparedStatement's wildcard can not be
    // used as a placeholder for table names
    private Statement simpleStatement;


    public boolean openDbConnection(int querySelection) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            switch (querySelection) {
                case FOR_LOG_IN:
                    preparedStatement = conn.prepareStatement(QUERY_GET_EMPLOYEE_DETAILS);
                    break;
                case FOR_CHECK_IN_OUT:
                    simpleStatement = conn.createStatement();
                    break;
            }
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
            if(simpleStatement != null) {
                simpleStatement.close();
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


    // Used to log in a user and get its information
    public boolean userAuthentication(String employeeID, String password, Employee employee){
        try {
            preparedStatement.setString(1, employeeID);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                if(password.equals(result.getString(3))){
                    // Setting up the employee object with all his info
                    employee.setId(result.getString(1));
                    employee.setEmail(result.getString(2));
                    employee.setPassword(result.getString(3));
                    employee.setName(result.getString(4));
                    employee.setLast_name(result.getString(5));
                    employee.setSalary(result.getInt(6));
                    employee.setSalary_type(result.getString(7));
                    employee.setStandard_shift(result.getInt(8));
                    employee.setHolidays_left(result.getInt(9));
                    employee.setHolidays_bonus(result.getInt(10));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    // As this method is used with the check in-out button, first we create a table for the user if does not exist,
    // and we insert date and timestamp data.
    public boolean checkInOut(String username){
        try {
            // executeUpdate() method, executes the given SQL simpleStatement which may be an INSERT, UPDATE, or DELETE simpleStatement or an SQL simpleStatement that returns nothing, such as an SQL DDL simpleStatement.
            // https://stackoverflow.com/questions/1905607/cannot-issue-data-manipulation-statements-with-executequery
            simpleStatement.executeUpdate(QUERY_CREATE_WORKDAYS_TABLE_1 + username + QUERY_CREATE_WORKDAYS_TABLE_2);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

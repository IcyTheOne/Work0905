package com.example.work0905.util;

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

    // CONSTANTS used to insert the correct QUERY to the PREPARED simpleStatement, when we open
    // connection with database (openDbConnection method).
    public static final int FOR_LOG_IN = 1;
    public static final int FOR_CHECK_IN_OUT = 2;
    public static final int FOR_UPDATE_EMAIL = 3;
    public static final int FOR_UPDATE_PASSWORD = 4;

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
    public static final String COLUMN_WORKDAYS_IS_CHECKED_OUT = "is_checked_out";
    public static final String COLUMN_WORKDAYS_MINUTES_WORKED = "minutes_worked";
    public static final String COLUMN_WORKDAYS_OVERTIME = "overtime";

    /**
     * Queries
     **/
    public static final String SET_TIMEZONE = "SET time_zone = \"+1:00\"";
//    private static final String QUERY_USER_AUTHENTICATION = "SELECT " + COLUMN_EMPLOYEES_PASSWORD + " FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_EMPLOYEES_ID + " =?";
    private static final String QUERY_GET_EMPLOYEE_DETAILS = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_EMPLOYEES_ID + " =?";
    // Is not allowed to use the wild card (?) of prepared statements as a placeholder of table names. Table names must be hardcoded.
    // So we have to use a variable for the table name and create the string of the simpleStatement inside the method we want it.
    private static final String QUERY_CREATE_WORKDAYS_TABLE_1 = "CREATE TABLE IF NOT EXISTS " + dbName + "." + TABLE_WORKDAYS;
    private static final String QUERY_CREATE_WORKDAYS_TABLE_2 = " (" +
            COLUMN_WORKDAYS_WORKDAY_ID + " INT NOT NULL AUTO_INCREMENT, " +
            COLUMN_WORKDAYS_DATE + " DATE NULL, " +
            COLUMN_WORKDAYS_CHECK_IN + " TIME NULL, " +
            COLUMN_WORKDAYS_CHECK_OUT + " TIME NULL, " +
            COLUMN_WORKDAYS_IS_CHECKED_OUT + " TINYINT NULL, " +
            COLUMN_WORKDAYS_MINUTES_WORKED + " INT NULL, " +
            COLUMN_WORKDAYS_OVERTIME + " INT NULL, " +
            "PRIMARY KEY (" + COLUMN_WORKDAYS_WORKDAY_ID + ")" +
            ")";
    public static final String QUERY_INSERT_CHECK_IN_INTO_WORKDAYS_TABLE_1 = "INSERT INTO " + TABLE_WORKDAYS;
    public static final String QUERY_INSERT_CHECK_IN_INTO_WORKDAYS_TABLE_2 = " (" +
            COLUMN_WORKDAYS_DATE + ", " +
            COLUMN_WORKDAYS_CHECK_IN + ", " +
            COLUMN_WORKDAYS_CHECK_OUT + ", " +
            COLUMN_WORKDAYS_IS_CHECKED_OUT + ", " +
            COLUMN_WORKDAYS_MINUTES_WORKED + ", " +
            COLUMN_WORKDAYS_OVERTIME +
            ") VALUES (" +
            "CURDATE(), " +
            "NOW(), " +
            "NULL, " +
            "false, " + // false is equal to 0, and true is equal to 1
            "NULL, NULL )";
    public static final String QUERY_UPDATE_CHECK_OUT_INTO_WORKDAYS_TABLE_1 = "UPDATE " + TABLE_WORKDAYS;
    public static final String QUERY_UPDATE_CHECK_OUT_INTO_WORKDAYS_TABLE_2 = " SET " + COLUMN_WORKDAYS_CHECK_OUT + " = NOW() WHERE " + COLUMN_WORKDAYS_WORKDAY_ID + " = ";

    public static final String QUERY_UPDATE_CHECK_OUT_STATUS_INTO_WORKDAYS_TABLE_1 = "UPDATE " + TABLE_WORKDAYS;
    public static final String QUERY_UPDATE_CHECK_OUT_STATUS_INTO_WORKDAYS_TABLE_2 = " SET " + COLUMN_WORKDAYS_IS_CHECKED_OUT + " = TRUE WHERE " + COLUMN_WORKDAYS_WORKDAY_ID + " = ";

    public static final String QUERY_GET_LAST_ROW_PRIMARY_KEY_1 = "SELECT MAX(" + COLUMN_WORKDAYS_WORKDAY_ID + ")" + " FROM " + TABLE_WORKDAYS;

    // Get CHECK_IN value to check if the employee has hit or not the check in button, and act correspondingly.
    // This query will always select the last row in the database
    public static final String QUERY_GET_CHECK_OUT_STATUS_VALUE_1 = "SELECT " + COLUMN_WORKDAYS_IS_CHECKED_OUT + " FROM " + TABLE_WORKDAYS;
    public static final String QUERY_GET_CHECK_OUT_STATUS_VALUE_2 = " WHERE " + COLUMN_WORKDAYS_WORKDAY_ID + " = " + "(SELECT MAX(" + COLUMN_WORKDAYS_WORKDAY_ID + ")" + " FROM " + TABLE_WORKDAYS;
    public static final String QUERY_GET_CHECK_OUT_STATUS_VALUE_3 = ")";

    public static final String QUERY_INSERT_INTO_WORKDAYS_TABLE_1 = "INSERT INTO " + TABLE_WORKDAYS;
    public static final String QUERY_INSERT_INTO_WORKDAYS_TABLE_2 = " (" ;
            ;
    public static final String QUERY_UPDATE_EMAIL = "UPDATE email FROM " + TABLE_EMPLOYEES + " WHERE " + " id_employee=?";
    public static final String QUERY_UPDATE_PASSWORD = "UPDATE password FROM " + TABLE_EMPLOYEES + " WHERE " + " id_employee=?";

    private Connection conn;
    //Prepared simpleStatement to INSERT, UPDATE and DELETE
    private PreparedStatement preparedStatement;
    // Simple simpleStatement to CREATE tables as the preparedStatement's wildcard can not be
    // used as a placeholder for table names
    private Statement simpleStatement;





    /*/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\*/





    public boolean openDbConnection(int querySelection) {
        try {
            // Pre-loading class Driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            switch (querySelection) {
                case FOR_LOG_IN:
                    preparedStatement = conn.prepareStatement(QUERY_GET_EMPLOYEE_DETAILS);
                    simpleStatement = conn.createStatement();
                    break;
                case FOR_CHECK_IN_OUT:
                    simpleStatement = conn.createStatement();
                    break;
                case FOR_UPDATE_EMAIL:
                    preparedStatement = conn.prepareStatement(QUERY_UPDATE_EMAIL);
                case FOR_UPDATE_PASSWORD:
                    preparedStatement = conn.prepareStatement(QUERY_UPDATE_PASSWORD);
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
                    // If provided credentials exist in the database, set up the employee object with all his info
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
                    employee.setCheckedOut(isCheckedOut(employeeID));
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
    // and then we insert date and timestamp data.
    public boolean checkIn(String username){
        try {
            // executeUpdate() method, executes the given SQL simpleStatement which may be an INSERT, UPDATE, or DELETE simpleStatement or an SQL simpleStatement that returns nothing, such as an SQL DDL simpleStatement.
            // https://stackoverflow.com/questions/1905607/cannot-issue-data-manipulation-statements-with-executequery
            simpleStatement.executeUpdate(SET_TIMEZONE);
            simpleStatement.executeUpdate(QUERY_CREATE_WORKDAYS_TABLE_1 + username + QUERY_CREATE_WORKDAYS_TABLE_2);
            simpleStatement.executeUpdate(QUERY_INSERT_CHECK_IN_INTO_WORKDAYS_TABLE_1 + username + QUERY_INSERT_CHECK_IN_INTO_WORKDAYS_TABLE_2);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkOut(String username){
        int primaryKeyId = -1;
        try {
            simpleStatement.executeUpdate(SET_TIMEZONE);
            ResultSet result = simpleStatement.executeQuery(QUERY_GET_LAST_ROW_PRIMARY_KEY_1 + username);
            while(result.next()){
                primaryKeyId = result.getInt(1);
            }
            if(primaryKeyId >= 0) {
                simpleStatement.executeUpdate(QUERY_UPDATE_CHECK_OUT_INTO_WORKDAYS_TABLE_1 + username + QUERY_UPDATE_CHECK_OUT_INTO_WORKDAYS_TABLE_2 + primaryKeyId);
                simpleStatement.executeUpdate(QUERY_UPDATE_CHECK_OUT_STATUS_INTO_WORKDAYS_TABLE_1 + username + QUERY_UPDATE_CHECK_OUT_STATUS_INTO_WORKDAYS_TABLE_2 + primaryKeyId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // Checking
    public boolean isCheckedOut(String username){
        try {
            simpleStatement.executeUpdate(QUERY_CREATE_WORKDAYS_TABLE_1 + username + QUERY_CREATE_WORKDAYS_TABLE_2);

            // Checking if we have a new empty table
            ResultSet resultSet = simpleStatement.executeQuery("SELECT COUNT(*) FROM workdays_" + username);
            if(resultSet.next()) {
                if(resultSet.getInt(1) == 0)
                return true;
            }
            ResultSet result = simpleStatement.executeQuery(QUERY_GET_CHECK_OUT_STATUS_VALUE_1 + username + QUERY_GET_CHECK_OUT_STATUS_VALUE_2 + username + QUERY_GET_CHECK_OUT_STATUS_VALUE_3);
            while (result.next()) {
                if(result.getInt(1) == 1){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}

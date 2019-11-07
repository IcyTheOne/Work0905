import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    static String url = "den1.mysql6.gear.host";
    static String user = "work0905db";
    static String password = "Oa4fXkQ?6S9!";

    public static void getConnection(){
        try {
            Connection myConn = DriverManager.getConnection(url,user,password);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }







    public static void login(String employeeID,String password){



    }

    public static void punchIn(){


    }



}

package cue.edu.co.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static String url = "jdbc:mysql://localhost:3306/toy_store";
    private static String user = "root";
    private static String password = "root123";
    private static Connection connection;
    public static Connection getInstance() throws SQLException {
        if(connection==null){
            connection = DriverManager.getConnection(url,user,password);
        }
        return connection;
    }

}

package cue.edu.co.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection connection;
    public static Connection getInstance() throws SQLException {
        if(connection==null) {
            String password = "root123";
            String user = "root";
            String url = "jdbc:mysql://localhost:3306/toy_store";
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

}

package org.sebastian.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static String url = "jdbc:mysql://localhost:3306/usersproyectjava";
    private static String user = "root";
    private static String password = "warcrychile123";

    private static Connection connection;

    public static Connection getIntanceConnection() throws SQLException {
        if( connection == null ){
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }



}

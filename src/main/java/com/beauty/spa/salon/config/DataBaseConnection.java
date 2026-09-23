package main.java.com.beauty.spa.salon.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Carlitos
 */
public class DataBaseConnection {
    private static Connection conn;
    
    public DataBaseConnection() {
        
    }
    
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(Credentials.URL_DB, Credentials.USER_DB, Credentials.PASS_DB);
        }
        return conn;
    }
    
    // Alias por si alguna otra parte llama a getDataBaseConnection()
    public static Connection getDataBaseConnection() throws SQLException {
        return getConnection();
    }
}
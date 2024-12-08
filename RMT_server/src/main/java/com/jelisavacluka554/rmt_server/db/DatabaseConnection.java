package com.jelisavacluka554.rmt_server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton for obtaining a connection to the PostgreSQL database,
 * using JDBC driver.
 * @author luka
 */
public class DatabaseConnection {
    
    private static Connection conn = null;
    
    // TODO: Place database connection parameters into a file.
    
    private DatabaseConnection() {
        
    }
    
    public static Connection getConnection()throws SQLException {
        if(conn == null || conn.isClosed())
        {
            String url = "jdbc:postgresql://127.0.0.1:5432/RMT1";
            conn = DriverManager.getConnection(url, "postgres", "postgres");
        }
        
        return conn;
    }
    
}

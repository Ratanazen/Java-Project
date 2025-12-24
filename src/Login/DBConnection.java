package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/login_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create connection
            conn = DriverManager.getConnection(URL, USER, PASS);
            
            // Test connection
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            System.err.println("Add mysql-connector-java-8.0.xx.jar to your libraries");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Check if MySQL is running and database exists");
            e.printStackTrace();
        }
        return conn;
    }
    
    // Test method
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connection test successful!");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection test failed!");
        }
    }
}
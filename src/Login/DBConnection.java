package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Change "login_db" to match your database name if different
    private static final String URL = "jdbc:mysql://localhost:3306/login_db";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASS = "";     // Your MySQL password

    public static Connection getConnection() {
        try {
            // Ensure you have the MySQL Connector JAR in your project libraries
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
            return null;
        }
    }
}
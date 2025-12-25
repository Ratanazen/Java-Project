package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/login_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "1234";
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            LOGGER.info("New database connection created.");
            return connection;
        } catch (ClassNotFoundException e) {
            LOGGER.severe("MySQL Driver not found: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            LOGGER.severe("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // CORRECTED - Only one parameter (Connection)
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Database connection closed.");
            }
        } catch (SQLException e) {
            LOGGER.severe("Error closing connection: " + e.getMessage());
        }
    }
}
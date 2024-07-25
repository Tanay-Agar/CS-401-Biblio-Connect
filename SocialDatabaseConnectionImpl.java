package social_Network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SocialDatabaseConnectionImpl {
    private static final String URL = "jdbc:mysql://localhost:3306/social_network_db";
    private static final String USER = "project_user";
    private static final String PASSWORD = "Luna123!";
    
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
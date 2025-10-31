package example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                connection =
                        DriverManager.getConnection(
                                // адрес БД , имя пользователя, пароль
                                "jdbc:postgresql://localhost:5432/demo","postgres","qwerty007");
                return connection;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void releaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

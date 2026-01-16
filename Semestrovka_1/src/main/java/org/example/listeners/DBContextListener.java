package org.example.listeners;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.repository.DBConnection;
import java.sql.SQLException;

@WebListener
public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Class.forName("org.postgresql.Driver");

            DBConnection.init();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver не найден");
        } catch (SQLException e) {
            throw new RuntimeException("Не получилось подключиться к базе данных");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (DBConnection.dataSource != null) {
            ((HikariDataSource) DBConnection.dataSource).close();
        }
    }
}
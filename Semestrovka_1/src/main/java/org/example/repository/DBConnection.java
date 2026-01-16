package org.example.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    public static DataSource dataSource;

    public static void init() throws SQLException {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/semestrovka_1");
        config.setUsername("postgres");
        config.setPassword("qwerty007");
        config.setConnectionTimeout(50000);
        config.setMaximumPoolSize(10);

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new SQLException("Не подключилось к базе данных");
        }


    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Пул соединений не инициализирован. Проверьте DBContextListener");
        }
        return dataSource.getConnection();
    }


}

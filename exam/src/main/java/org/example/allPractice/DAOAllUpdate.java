package org.example.allPractice;

import org.example.Admins.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//13. Написать DAO с методами: получить все сущности таблицы, обновить сущность
public class DAOAllUpdate {
    String url = "jdnc";
    String username = "username";
    String password = "password";

    public List<Admin> findAll() {
        String sql = "select * from admins";
        List<Admin> admins = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setName(resultSet.getString("name"));
                admin.setRole(resultSet.getString("role"));
                admin.setImg(resultSet.getString("img"));
                admins.add(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admins;
    }

    public void update(Admin admin) {
        String sql = "update admins set name = ?, role = ?, img = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getRole());
            statement.setString(3, admin.getImg());
            statement.setInt(4, 123);
            int changedRow = statement.executeUpdate();
            if (changedRow == 0) {
                throw new SQLException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

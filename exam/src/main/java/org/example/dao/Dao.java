package org.example.dao;

import org.example.Admins.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//3) Написать ДАО с 2 методами чтобы
// 1 метод "выводил" все сущности в таблице,
// а 2 их обновлял
// (Почему PreparedStatement можно не использовать?)

public class Dao {

    String url = "jdbc:mysql://localhost:3306/";
    String username = "";
    String password = "";

    public List<Admin> findAll() {
        String sql = "select * from admins";
        List<Admin> admins = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            String line;
            while(resultSet.next()) {
                Admin admin = new Admin();
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String img = resultSet.getString("img");
                admin.setName(name);
                admin.setRole(role);
                admin.setImg(img);
                admins.add(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return admins;
    }

    public void update(Admin admin) {
        String sql = "update admins set name = ?, role = ? where id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getRole());
            statement.setString(3, "123123");
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

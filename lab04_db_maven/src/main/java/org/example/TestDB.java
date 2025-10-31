package org.example;

import java.sql.*;

public class TestDB {
    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");

            Connection connection =
                    DriverManager.getConnection(
                            // адрес БД , имя пользователя, пароль
                            "jdbc:postgresql://localhost:5432/demo","postgres","qwerty007");

            Statement statement = connection.createStatement();
            //Boolean result = statement.execute("create table users(id bigint primary key, name varchar(50))");

            String sql = "select * from bookings.airplanes_data";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.print(resultSet.getString("airplane_code") + "; ");
                System.out.print(resultSet.getString("model") + "; ");
                System.out.print(resultSet.getString("range") + "; ");
                System.out.print(resultSet.getString("speed") + "; ");
                System.out.println();
            }

            resultSet.close();

            String sqlInsert = "insert into bookings.airplanes_data "
                    + "(airplane_code, model, range, speed) values"
                    + "('U22', '{\"en\": \"Sukhoy S100\", \"ru\": \"Сухой S100\"}'::jsonb, 5000, 850)";

            String sqlUpdate = "update bookings.airplanes_data set speed = 900 where airplane_code = 'U22'";

            String sqlDelete = "delete from bookings.airplanes_data where airplane_code = 'U22'";

            statement.executeUpdate(sqlDelete);


            statement.close();
            connection.close();


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
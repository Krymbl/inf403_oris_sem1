package org.example.repository;

import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users (username, phone_number, first_name, " +
                "last_name, gender, birthday, email, hash_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, user);
            statement.setString(8, user.getHashPassword());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("пользователя с username " + user.getUsername());
            }

        }
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, user.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("пользователя с ID " + user.getId() + ", username " + user.getUsername());
            }

        }
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, " +
                "gender = ?, birthday = ?, role = ?, updated_at = current_timestamp WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getGender());
            statement.setDate(4, user.getBirthday());
            statement.setString(5,user.getRole());
            statement.setLong(6, user.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("пользователя с ID " + user.getId() + ", username " + user.getUsername());
            }

        }
    }

    public void updateHashPassword(String username, String newHashPassword) throws SQLException {
        String sql = "UPDATE users SET hash_password = ?, updated_at = current_timestamp WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newHashPassword);
            statement.setString(2, username);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("пароль пользователя с username " + username + ", password " + newHashPassword);
            }

        }

    }

    public void updateUsername(String username, String newUsername) throws SQLException {
        String sql = "UPDATE users SET username = ?, updated_at = current_timestamp WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newUsername);
            statement.setString(2, username);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("username пользователя с username " + username);
            }
        }
    }

    public void updatePhoneNumber(String username, String phoneNumber) throws SQLException {
        String sql = "UPDATE users SET phone_number = ?, updated_at = current_timestamp WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, phoneNumber);
            statement.setString(2, username);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("телефон пользователя с username " + username);
            }
        }
    }

    public void updateEmail(String username, String email) throws SQLException {
        String sql = "UPDATE users SET email = ?, updated_at = current_timestamp WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, username);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("почту пользователя с username " + username);
            }
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }

        }
        return users;
    }

    public User findById(Long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                } else {
                    throw new NoDataFoundException("пользователе", "ID: " + id);
                }
            }
        }
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                } else {
                    throw new NoDataFoundException("пользователе", "username: " + username);
                }
            }
        }
    }

    public User findByPhoneNumber(String phoneNumber) throws SQLException {
        String sql = "SELECT * FROM users WHERE phone_number  = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, phoneNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                } else {
                    throw new NoDataFoundException("пользователе", "телефон: " + phoneNumber);
                }
            }

        }
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email  = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                } else {
                    throw new NoDataFoundException("пользователе", "почта: " + email);
                }
            }

        }
    }

    public String getHashPassword(String username) throws SQLException {
        String sql = "SELECT hash_password FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("hash_password");
                } else {
                    throw new NoDataFoundException("пароле пользователя", "username: " + username);
                }
            }

        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setHashPassword(resultSet.getString("hash_password"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setGender(resultSet.getString("gender"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setEmail(resultSet.getString("email"));
        user.setRole(resultSet.getString("role"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return user;
    }

    private void setStatement(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPhoneNumber());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getGender());
        statement.setDate(6, user.getBirthday());
        statement.setString(7, user.getEmail());
    }


}

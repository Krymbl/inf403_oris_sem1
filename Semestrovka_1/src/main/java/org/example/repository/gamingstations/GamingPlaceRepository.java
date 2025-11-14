package org.example.repository.gamingstations;

import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.gamingstations.GamingPlace;
import org.example.repository.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GamingPlaceRepository {

    public void save(GamingPlace gamingPlace) throws SQLException {
        String sql = "INSERT INTO gaming_places (name, type, category, is_available, price_per_hour) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, gamingPlace);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("игровое место");
            }
        }
    }

    public void delete(GamingPlace gamingPlace) throws SQLException {
        String sql = "DELETE FROM gaming_places WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gamingPlace.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("игровое место с ID " + gamingPlace.getId());
            }
        }

    }

    public void update(GamingPlace gamingPlace) throws SQLException {
        String sql = "UPDATE gaming_places SET name = ?, type = ?, category = ?, is_available = ?, " +
                "price_per_hour = ?, updated_at = current_timestamp WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, gamingPlace);
            statement.setLong(6, gamingPlace.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("игровое место с ID " + gamingPlace.getId());
            }
        }
    }

    public List<GamingPlace> findAll() throws SQLException {
        List<GamingPlace> gamingPlaces = new ArrayList<>();
        String sql = "SELECT * FROM gaming_places order by id ASC";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                gamingPlaces.add(getGamingPlace(resultSet));

            }


        }

        return gamingPlaces;
    }

    public GamingPlace findById(long id) throws SQLException {
        String sql = "SELECT * FROM gaming_places WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getGamingPlace(resultSet);
                } else {
                    throw new NoDataFoundException("игровом месте", "с ID " + id);
                }
            }
        }
    }

    public List<GamingPlace> findByCategory(String category) throws SQLException {
        List<GamingPlace> gamingPlaces = new ArrayList<>();
        String sql = "SELECT * FROM gaming_places WHERE category = ? order by id ASC";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    gamingPlaces.add(getGamingPlace(resultSet));
                }

                if (gamingPlaces.isEmpty()) {
                    throw new NoDataFoundException("игровых местах", "с категорией " + category);
                }
            }
        }

        return gamingPlaces;
    }

    public List<GamingPlace> findByType(String type) throws SQLException {
        List<GamingPlace> gamingPlaces = new ArrayList<>();
        String sql = "SELECT * FROM gaming_places WHERE type = ? order by id ASC";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, type);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    gamingPlaces.add(getGamingPlace(resultSet));
                }

                if (gamingPlaces.isEmpty()) {
                    throw new NoDataFoundException("игровых местах", "с типом " + type);
                }
            }
        }

        return gamingPlaces;
    }

    public List<GamingPlace> findAvailable(Boolean isAvailable) throws SQLException {
        List<GamingPlace> gamingPlaces = new ArrayList<>();
        String sql = "SELECT * FROM gaming_places WHERE is_available = ? order by id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, isAvailable);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    gamingPlaces.add(getGamingPlace(resultSet));
                }

                if (gamingPlaces.isEmpty()) {
                    throw new NoDataFoundException("игровых местах", "с доступностью " + isAvailable);
                }
            }
        }
        return gamingPlaces;
    }



    private void setStatement(PreparedStatement statement, GamingPlace gamingPlace) throws SQLException {
        statement.setString(1, gamingPlace.getName());
        statement.setString(2, gamingPlace.getType());
        statement.setString(3, gamingPlace.getCategory());
        statement.setBoolean(4, gamingPlace.isAvailable());
        statement.setDouble(5, gamingPlace.getPricePerHour());
    }

    private GamingPlace getGamingPlace(ResultSet resultSet) throws SQLException {
        GamingPlace gamingPlace = new GamingPlace();
        gamingPlace.setId(resultSet.getLong("id"));
        gamingPlace.setName(resultSet.getString("name"));
        gamingPlace.setType(resultSet.getString("type"));
        gamingPlace.setCategory(resultSet.getString("category"));
        gamingPlace.setAvailable(resultSet.getBoolean("is_available"));
        gamingPlace.setPricePerHour(resultSet.getDouble("price_per_hour"));
        gamingPlace.setCreatedAt(resultSet.getTimestamp("created_at"));
        gamingPlace.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return gamingPlace;
    }



}

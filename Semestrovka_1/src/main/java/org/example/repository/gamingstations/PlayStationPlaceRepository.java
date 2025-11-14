package org.example.repository.gamingstations;

import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.gamingstations.PlayStationPlace;
import org.example.repository.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayStationPlaceRepository {

    public void save(PlayStationPlace playStationPlace) throws SQLException {
        String sql = "INSERT INTO playstation_places (gaming_place_id, tv, max_players) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatement(statement, playStationPlace);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("плейстейшн место");
            }

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    playStationPlace.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public void delete(PlayStationPlace playStationPlace) throws SQLException {
        String sql = "DELETE FROM playstation_places WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playStationPlace.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("плейстейшн место с ID " + playStationPlace.getId());
            }
        }
    }

    public void update(PlayStationPlace playStationPlace) throws SQLException {
        String sql = "UPDATE playstation_places SET gaming_place_id = ?, tv = ?, max_players = ?, updated_at = current_timestamp WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, playStationPlace);
            statement.setLong(4, playStationPlace.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("плейстейшн место с ID " + playStationPlace.getId());
            }
        }
    }

    public List<PlayStationPlace> findAll() throws SQLException {
        List<PlayStationPlace> playStationPlaces = new ArrayList<>();
        // Упрощенный запрос без JOIN
        String sql = "SELECT * FROM playstation_places ORDER BY id ASC ";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                playStationPlaces.add(getPlayStationPlace(resultSet));
            }
        }
        return playStationPlaces;
    }

    public PlayStationPlace findById(Long id) throws SQLException {
        String sql = "SELECT * FROM playstation_places WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getPlayStationPlace(resultSet);
                } else {
                    throw new NoDataFoundException("плейстейшн месте", "с ID " + id);
                }
            }
        }
    }

    public PlayStationPlace findByGamingPlaceId(Long gamingPlaceId) throws SQLException {
        String sql = "SELECT * FROM playstation_places WHERE gaming_place_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gamingPlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getPlayStationPlace(resultSet);
                } else {
                    throw new NoDataFoundException("плейстейшн месте", "с gaming_place_id " + gamingPlaceId);
                }
            }
        }
    }

    public List<PlayStationPlace> findByAvailability(Boolean isAvailable) throws SQLException {
        List<PlayStationPlace> playStationPlaces = new ArrayList<>();
        String sql = "SELECT playstation_places.* FROM playstation_places  " +
                "JOIN gaming_places ON playstation_places.gaming_place_id = gaming_places.id " +
                "WHERE gaming_places.is_available = ? order by id asc ";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, isAvailable);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    playStationPlaces.add(getPlayStationPlace(resultSet));
                }
            }
        }
        return playStationPlaces;
    }

    public List<PlayStationPlace> findByCategory(String category) throws SQLException {
        List<PlayStationPlace> playStationPlaces = new ArrayList<>();
        String sql = "SELECT playstation_places.* FROM playstation_places " +
                "JOIN gaming_places ON playstation_places.gaming_place_id = gaming_places.id " +
                "WHERE gaming_places.category = ? order by id asc ";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    playStationPlaces.add(getPlayStationPlace(resultSet));
                }
            }
        }
        return playStationPlaces;
    }

    public List<PlayStationPlace> findByMaxPlayers(int maxPlayers) throws SQLException {
        List<PlayStationPlace> playStationPlaces = new ArrayList<>();
        String sql = "SELECT * FROM playstation_places WHERE max_players = ? order by id asc ";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, maxPlayers);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    playStationPlaces.add(getPlayStationPlace(resultSet));
                }
            }
        }
        return playStationPlaces;
    }

    private PlayStationPlace getPlayStationPlace(ResultSet resultSet) throws SQLException {
        PlayStationPlace playStationPlace = new PlayStationPlace();

        playStationPlace.setId(resultSet.getLong("id"));
        playStationPlace.setGamingPlaceId(resultSet.getLong("gaming_place_id"));
        playStationPlace.setTv(resultSet.getString("tv"));
        playStationPlace.setMaxPlayers(resultSet.getInt("max_players"));
        playStationPlace.setCreatedAt(resultSet.getTimestamp("created_at"));
        playStationPlace.setUpdatedAt(resultSet.getTimestamp("updated_at"));

        return playStationPlace;
    }

    private void setStatement(PreparedStatement statement, PlayStationPlace playStationPlace) throws SQLException {
        statement.setLong(1, playStationPlace.getGamingPlaceId());
        statement.setString(2, playStationPlace.getTv());
        statement.setInt(3, playStationPlace.getMaxPlayers());
    }
}
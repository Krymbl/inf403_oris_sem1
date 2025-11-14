package org.example.repository.gamingstations;

import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.devices.Computer;
import org.example.model.gamingstations.ComputerPlace;
import org.example.model.gamingstations.GamingPlace;
import org.example.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputerPlaceRepository {


    public void save(ComputerPlace computerPlace) throws SQLException {
        String sql = "INSERT INTO computer_places (gaming_place_id, keyboard, mouse, headset, monitor, chair) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, computerPlace);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("компьютерное место");
            }
        }

    }

    public void delete(ComputerPlace computerPlace) throws SQLException {
        String sql = "DELETE FROM computer_places WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, computerPlace.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("компьютерное место с ID " + computerPlace.getId());
            }
        }
    }

    public void update(ComputerPlace computerPlace) throws SQLException {
        String sql = "UPDATE computer_places SET gaming_place_id = ?, keyboard = ?, mouse = ?, " +
                "headset = ?, monitor = ?, chair = ?, updated_at = current_timestamp " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, computerPlace);
            statement.setLong(7, computerPlace.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("компьютерное место с ID " + computerPlace.getId());
            }
        }
    }



    public List<ComputerPlace> findAll() throws SQLException {
        List<ComputerPlace> computerPlaces = new ArrayList<>();
        String sql = "SELECT * FROM computer_places ORDER BY id ASC";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                computerPlaces.add(getComputerPlace(resultSet));
            }

        }
        return computerPlaces;
    }

    public ComputerPlace findById(Long id) throws SQLException {
        String sql = "SELECT * FROM computer_places WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getComputerPlace(resultSet);
                } else {
                    throw new NoDataFoundException("компьютерном месте", "с ID " + id);
                }
            }
        }
    }

    public ComputerPlace findByGamingPlaceId(Long gamingPlaceId) throws SQLException {
        String sql = "SELECT * FROM computer_places WHERE gaming_place_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gamingPlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getComputerPlace(resultSet);
                } else {
                    throw new NoDataFoundException("компьютерном месте", "с gaming_place_id " + gamingPlaceId);
                }
            }
        }
    }

    public List<ComputerPlace> findByAvailability(Boolean isAvailable) throws SQLException {
        List<ComputerPlace> computerPlaces = new ArrayList<>();
        String sql = "SELECT computer_places.* FROM computer_places " +
                "JOIN gaming_places ON computer_places.gaming_place_id = gaming_places.id " +
                "WHERE gaming_places.is_available = ? order by id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, isAvailable);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    computerPlaces.add(getComputerPlace(resultSet));
                }

                if (computerPlaces.isEmpty()) {
                    throw new NoDataFoundException("компьютерных местах", "с доступностью " + isAvailable);
                }
            }
        }
        return computerPlaces;
    }

    public List<ComputerPlace> findByCategory(String category) throws SQLException {
        List<ComputerPlace> computerPlaces = new ArrayList<>();
        String sql = "SELECT computer_places.* FROM computer_places " +
                "JOIN gaming_places ON computer_places.gaming_place_id = gaming_places.id " +
                "WHERE gaming_places.category = ? order by id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    computerPlaces.add(getComputerPlace(resultSet));
                }

                if (computerPlaces.isEmpty()) {
                    throw new NoDataFoundException("компьютерных местах", "с категорией " + category);
                }
            }
        }
        return computerPlaces;
    }

    private ComputerPlace getComputerPlace(ResultSet resultSet) throws SQLException {
        ComputerPlace computerPlace = new ComputerPlace();

        computerPlace.setId(resultSet.getLong("id"));
        computerPlace.setGamingPlaceId(resultSet.getLong("gaming_place_id")); // Исправлен метод
        computerPlace.setKeyboard(resultSet.getString("keyboard"));
        computerPlace.setMouse(resultSet.getString("mouse"));
        computerPlace.setHeadset(resultSet.getString("headset"));
        computerPlace.setMonitor(resultSet.getString("monitor"));
        computerPlace.setChair(resultSet.getString("chair"));
        computerPlace.setCreatedAt(resultSet.getTimestamp("created_at"));
        computerPlace.setUpdatedAt(resultSet.getTimestamp("updated_at"));

        return computerPlace;
    }

    private void setStatement(PreparedStatement statement, ComputerPlace computerPlace) throws SQLException {
        statement.setLong(1, computerPlace.getGamingPlaceId());
        statement.setString(2, computerPlace.getKeyboard());
        statement.setString(3, computerPlace.getMouse());
        statement.setString(4, computerPlace.getHeadset());
        statement.setString(5, computerPlace.getMonitor());
        statement.setString(6, computerPlace.getChair());
    }
}

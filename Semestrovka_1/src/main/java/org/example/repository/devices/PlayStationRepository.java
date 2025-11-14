package org.example.repository.devices;

import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.devices.PlayStation;
import org.example.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayStationRepository {

    public void save(PlayStation playStation) throws SQLException {
        String sql = "insert into playstations (model, version) values (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, playStation);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("плейстейшн");
            }
        }
    }

    public void delete(PlayStation playStation) throws SQLException {
        String sql = "delete from playstations where id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playStation.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("плейстейшн с ID " + playStation.getId());
            }
        }

    }

    public void update(PlayStation playStation) throws SQLException {
        String sql = "update playstations set model = ?, version = ?, updated_at = current_timestamp where id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, playStation);

            statement.setLong(3, playStation.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("плейстейшн с ID " + playStation.getId());
            }

        }
    }

    public List<PlayStation> findAll() throws SQLException {
        List<PlayStation> playStations = new ArrayList<>();
        String sql = "select * from playstations ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                playStations.add(getPlayStation(resultSet));
            }

        }

        return playStations;
    }

    public PlayStation findById(Long id) throws SQLException {
        String sql = "select * from playstations  where id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getPlayStation(resultSet);
                } else {
                    throw new NoDataFoundException("плейстейшене", "с ID " + id);
                }
            }
        }
    }

    private PlayStation getPlayStation(ResultSet resultSet) throws SQLException {
        PlayStation playStation = new PlayStation();
        playStation.setId(resultSet.getLong("id"));
        playStation.setModel(resultSet.getString("model"));
        playStation.setVersion(resultSet.getString("version"));
        playStation.setCreatedAt(resultSet.getTimestamp("created_at"));
        playStation.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return playStation;
    }

    private void setStatement(PreparedStatement statement, PlayStation playStation) throws SQLException {
        statement.setString(1, playStation.getModel());
        statement.setString(2, playStation.getVersion());
    }
}

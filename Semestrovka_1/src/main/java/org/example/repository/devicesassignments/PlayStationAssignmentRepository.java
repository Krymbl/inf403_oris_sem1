package org.example.repository.devicesassignments;


import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.devicesassignments.PlayStationAssignment;
import org.example.repository.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayStationAssignmentRepository {

    public void save(PlayStationAssignment assignment) throws SQLException {
        String sql = "INSERT INTO playstation_assignments (playstation_id, playstation_place_id, assigned_at, unassigned_at, is_active) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, assignment);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("назначение PlayStation");
            }
        }
    }

    public void delete(PlayStationAssignment assignment) throws SQLException {
        String sql = "DELETE FROM playstation_assignments WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, assignment.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("назначение PlayStation с ID " + assignment.getId());
            }
        }
    }

    public void update(PlayStationAssignment assignment) throws SQLException {
        String sql = "UPDATE playstation_assignments SET playstation_id = ?, playstation_place_id = ?, assigned_at = ?, unassigned_at = ?, is_active = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, assignment);
            statement.setLong(6, assignment.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("назначение PlayStation с ID " + assignment.getId());
            }
        }
    }

    public List<PlayStationAssignment> findAll() throws SQLException {
        List<PlayStationAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM playstation_assignments ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                assignments.add(getPlayStationAssignment(resultSet));
            }

        }
        return assignments;
    }

    public PlayStationAssignment findById(Long id) throws SQLException {
        String sql = "SELECT * FROM playstation_assignments where id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getPlayStationAssignment(resultSet);
                } else {
                    throw new NoDataFoundException("назначении PlayStation", "с ID " + id);
                }
            }
        }
    }

    public List<PlayStationAssignment> findByPlayStationId(Long playStationId) throws SQLException {
        List<PlayStationAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM playstation_assignments where playstation_id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playStationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(getPlayStationAssignment(resultSet));
                }

                if (assignments.isEmpty()) {
                    throw new NoDataFoundException("назначениях PlayStation", "с playstation_id " + playStationId);
                }
            }
        }
        return assignments;
    }

    public List<PlayStationAssignment> findByPlayStationPlaceId(Long playStationPlaceId) throws SQLException {
        List<PlayStationAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM playstation_assignments where playstation_place_id  = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playStationPlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(getPlayStationAssignment(resultSet));
                }

                if (assignments.isEmpty()) {
                    throw new NoDataFoundException("назначениях PlayStation места", "с playstation_place_id " + playStationPlaceId);
                }
            }
        }
        return assignments;
    }

    public List<PlayStationAssignment> findByIsActive(Boolean isActive) throws SQLException {
        List<PlayStationAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM playstation_assignments where is_active = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, isActive);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(getPlayStationAssignment(resultSet));
                }

                if (assignments.isEmpty()) {
                    throw new NoDataFoundException("назначениях PlayStation", "с is_active " + isActive);
                }
            }
        }
        return assignments;
    }

    public PlayStationAssignment findActiveByPlayStationId(Long playStationId) throws SQLException {
        String sql = "SELECT * FROM playstation_assignments where playstation_id = ? and is_active = true ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playStationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getPlayStationAssignment(resultSet);
                } else {
                    throw new NoDataFoundException("активном назначении PlayStation", "с playstation_id " + playStationId);
                }
            }
        }
    }

    public PlayStationAssignment findActiveByPlayStationPlaceId(Long playStationPlaceId) throws SQLException {
        String sql = "SELECT * FROM playstation_assignments where playstation_place_id  = ? and is_active = true ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, playStationPlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getPlayStationAssignment(resultSet);
                } else {
                    throw new NoDataFoundException("активном назначении PlayStation места", "с playstation_place_id " + playStationPlaceId);
                }
            }
        }
    }

    private PlayStationAssignment getPlayStationAssignment(ResultSet resultSet) throws SQLException {
        PlayStationAssignment assignment = new PlayStationAssignment();
        assignment.setId(resultSet.getLong("id"));
        assignment.setPlayStationId(resultSet.getLong("playstation_id"));
        assignment.setPlayStationPlaceId(resultSet.getLong("playstation_place_id"));
        assignment.setAssignedAt(resultSet.getTimestamp("assigned_at"));
        assignment.setUnassignedAt(resultSet.getTimestamp("unassigned_at"));
        assignment.setIsActive(resultSet.getBoolean("is_active"));
        assignment.setCreatedAt(resultSet.getTimestamp("created_at"));
        assignment.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return assignment;
    }

    private void setStatement(PreparedStatement statement, PlayStationAssignment assignment) throws SQLException {
        statement.setLong(1, assignment.getPlayStationId());
        statement.setLong(2, assignment.getPlayStationPlaceId());

        if (assignment.getAssignedAt() != null) {
            statement.setTimestamp(3, assignment.getAssignedAt());
        } else {
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        }

        if (assignment.getUnassignedAt() != null) {
            statement.setTimestamp(4, assignment.getUnassignedAt());
        } else {
            statement.setNull(4, Types.TIMESTAMP);
        }

        statement.setBoolean(5, assignment.getIsActive() != null ? assignment.getIsActive() : true);
    }
}
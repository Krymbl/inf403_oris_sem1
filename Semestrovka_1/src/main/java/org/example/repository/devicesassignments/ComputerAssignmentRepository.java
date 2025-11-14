package org.example.repository.devicesassignments;


import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.devicesassignments.ComputerAssignment;
import org.example.repository.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputerAssignmentRepository {

    public void save(ComputerAssignment assignment) throws SQLException {
        String sql = "INSERT INTO computer_assignments (computer_id, computer_place_id, assigned_at, unassigned_at, is_active) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, assignment);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("назначение компьютера");
            }
        }
    }

    public void delete(ComputerAssignment assignment) throws SQLException {
        String sql = "DELETE FROM computer_assignments WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, assignment.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("назначение компьютера с ID " + assignment.getId());
            }
        }
    }

    public void update(ComputerAssignment assignment) throws SQLException {
        String sql = "UPDATE computer_assignments SET computer_id = ?, computer_place_id = ?, assigned_at = ?, unassigned_at = ?, is_active = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, assignment);
            statement.setLong(6, assignment.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("назначение компьютера с ID " + assignment.getId());
            }
        }
    }



    public List<ComputerAssignment> findAll() throws SQLException {
        List<ComputerAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM computer_assignments ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                assignments.add(getComputerAssignment(resultSet));
            }

        }
        return assignments;
    }

    public ComputerAssignment findById(Long id) throws SQLException {
        String sql = "SELECT * FROM computer_assignments where id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getComputerAssignment(resultSet);
                } else {
                    throw new NoDataFoundException("назначении компьютера", "с ID " + id);
                }
            }
        }
    }

    public List<ComputerAssignment> findByComputerId(Long computerId) throws SQLException {
        List<ComputerAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM computer_assignments where computer_id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, computerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(getComputerAssignment(resultSet));
                }

                if (assignments.isEmpty()) {
                    throw new NoDataFoundException("назначениях компьютера", "с computer_id " + computerId);
                }
            }
        }
        return assignments;
    }

    public List<ComputerAssignment> findByComputerPlaceId(Long computerPlaceId) throws SQLException {
        List<ComputerAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM computer_assignments where computer_place_id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, computerPlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(getComputerAssignment(resultSet));
                }

                if (assignments.isEmpty()) {
                    throw new NoDataFoundException("назначениях компьютерного места", "с computer_place_id " + computerPlaceId);
                }
            }
        }
        return assignments;
    }

    public List<ComputerAssignment> findByIsActive(Boolean isActive) throws SQLException {
        List<ComputerAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM computer_assignments where is_active = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, isActive);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(getComputerAssignment(resultSet));
                }

                if (assignments.isEmpty()) {
                    throw new NoDataFoundException("назначениях компьютеров", "с is_active " + isActive);
                }
            }
        }
        return assignments;
    }

    public ComputerAssignment findActiveByComputerId(Long computerId) throws SQLException {
        String sql = "SELECT * FROM computer_assignments where computer_id = ? and is_active = true ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, computerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getComputerAssignment(resultSet);
                } else {
                    throw new NoDataFoundException("активном назначении компьютера", "с computer_id " + computerId);
                }
            }
        }
    }

    public ComputerAssignment findActiveByComputerPlaceId(Long computerPlaceId) throws SQLException {
        String sql = "SELECT * FROM computer_assignments where computer_place_id = ? and is_active = true ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, computerPlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getComputerAssignment(resultSet);
                } else {
                    throw new NoDataFoundException("активном назначении компьютерного места", "с computer_place_id " + computerPlaceId);
                }
            }
        }
    }

    private ComputerAssignment getComputerAssignment(ResultSet resultSet) throws SQLException {
        ComputerAssignment assignment = new ComputerAssignment();
        assignment.setId(resultSet.getLong("id"));
        assignment.setComputerId(resultSet.getLong("computer_id"));
        assignment.setComputerPlaceId(resultSet.getLong("computer_place_id"));
        assignment.setAssignedAt(resultSet.getTimestamp("assigned_at"));
        assignment.setUnassignedAt(resultSet.getTimestamp("unassigned_at"));
        assignment.setIsActive(resultSet.getBoolean("is_active"));
        assignment.setCreatedAt(resultSet.getTimestamp("created_at"));
        assignment.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return assignment;
    }

    private void setStatement(PreparedStatement statement, ComputerAssignment assignment) throws SQLException {
        statement.setLong(1, assignment.getComputerId());
        statement.setLong(2, assignment.getComputerPlaceId());

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
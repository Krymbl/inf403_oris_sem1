package org.example.repository.devices;

import org.example.exceptions.EntityDeleteException;
import org.example.exceptions.EntitySaveException;
import org.example.exceptions.EntityUpdateException;
import org.example.exceptions.NoDataFoundException;
import org.example.model.devices.Computer;
import org.example.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputerRepository {

    public void save(Computer computer) throws SQLException {
        String sql = "insert into computers (name, cpu, ram, video_card) values (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, computer);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("компьютер");
            }
        }
    }

    public void delete(Computer computer) throws SQLException {
        String sql = "delete from computers where id = ?";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, computer.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityDeleteException("компьютер с ID " + computer.getId() + ", названием " + computer.getName());
            }
        }

    }

    public void update(Computer computer) throws SQLException {
        String sql = "update computers set name = ?, cpu = ?, ram = ?, video_card = ?, updated_at = current_timestamp where id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, computer);

            statement.setLong(5, computer.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("компьютер с ID " + computer.getId() + ", названием " + computer.getName());
            }

        }
    }

    public List<Computer> findAll() throws SQLException {
        List<Computer> computers = new ArrayList<>();
        String sql = "select * from computers ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                computers.add(getComputer(resultSet));
            }

        }

        return computers;
    }

    public Computer findById(Long id) throws SQLException {
        String sql = "select * from computers where id = ?";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getComputer(resultSet);
                } else {
                    throw new NoDataFoundException("компьютере", "с ID " + id);
                }
            }
        }
    }

    private Computer getComputer(ResultSet resultSet) throws SQLException {
        Computer computer = new Computer();
        computer.setId(resultSet.getLong("id"));
        computer.setName(resultSet.getString("name"));
        computer.setCpu(resultSet.getString("cpu"));
        computer.setRam(resultSet.getInt("ram"));
        computer.setVideoCard(resultSet.getString("video_card"));
        computer.setCreatedAt(resultSet.getTimestamp("created_at"));
        computer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return computer;
    }

    private void setStatement(PreparedStatement statement, Computer computer) throws SQLException {
        statement.setString(1, computer.getName());
        statement.setString(2, computer.getCpu());
        statement.setInt(3, computer.getRam());
        statement.setString(4, computer.getVideoCard());
    }


}

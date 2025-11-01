package comp.club.repository;

import comp.club.model.Computer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputerRepository {
    private Connection connection;

    public ComputerRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Computer> findAll() {
        List<Computer> computers = new ArrayList<>();
        String sql = "SELECT * FROM computers ORDER BY id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                computers.add(mapResultSetToComputer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении данных", e);
        }
        return computers;
    }

    public Computer findById(int id) {
        String sql = "SELECT * FROM computers WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToComputer(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении по айди: " + id, e);
        }
        return null;
    }

    public boolean save(Computer computer) {
        if (computer.getId() == 0) {
            return insert(computer);
        } else {
            return update(computer);
        }
    }

    private boolean insert(Computer computer) {
        String sql = "INSERT INTO computers (name, processor, ram, video_card, is_available, price_per_hour) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setComputerParameters(pstmt, computer);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        computer.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Не получилось записать данные", e);
        }
    }

    private boolean update(Computer computer) {
        String sql = "UPDATE computers SET name = ?, processor = ?, ram = ?, video_card = ?, is_available = ?, price_per_hour = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setComputerParameters(pstmt, computer);
            pstmt.setInt(7, computer.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating computer", e);
        }
    }

    private Computer mapResultSetToComputer(ResultSet rs) throws SQLException {
        return new Computer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("processor"),
                rs.getInt("ram"),
                rs.getString("video_card"),
                rs.getBoolean("is_available"),
                rs.getDouble("price_per_hour")
        );
    }

    private void setComputerParameters(PreparedStatement pstmt, Computer computer) throws SQLException {
        pstmt.setString(1, computer.getName());
        pstmt.setString(2, computer.getProcessor());
        pstmt.setInt(3, computer.getRam());
        pstmt.setString(4, computer.getVideoCard());
        pstmt.setBoolean(5, computer.getIsAvailable());
        pstmt.setDouble(6, computer.getPricePerHour());
    }
}
package org.example.repository;

import org.example.exceptions.*;
import org.example.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    public void save(Reservation reservation) throws SQLException {
        String sql = "insert into reservations (user_id, person_number, " +
                "game_place_id, start_time, end_time, status, total_price) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, reservation);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntitySaveException("бронирование");
            }
        }

    }

    public void delete(Reservation reservation) throws SQLException {
        String sql = "delete from reservations where id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, reservation.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new EntityDeleteException("бронирование с ID " + reservation.getId());
            }
        }
    }

    public void update(Reservation reservation) throws SQLException {

        String sql = "update reservations set user_id = ?, person_number = ?, " +
                "game_place_id = ?, start_time = ?, end_time = ?, status = ?, total_price = ?," +
                " updated_at = current_timestamp where id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(statement, reservation);

            statement.setLong(8, reservation.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityUpdateException("бронирование с ID " + reservation.getId());
            }
        }

    }

    public List<Reservation> findAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "select * from reservations ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                reservations.add(getReservation(resultSet));
            }


        }

        return reservations;

    }

    public Reservation findByID(Long id) throws SQLException {
        String sql = "select * from reservations where id = ?";

        try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getReservation(resultSet);
                } else {
                    throw new NoDataFoundException("бронировании", "ID: " + id);
                }
            }
        }

    }

    public List<Reservation> findAllByUserId(Long userId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "select * from reservations where user_id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(getReservation(resultSet));
                }

                if (reservations.isEmpty()) {
                    throw new NoDataFoundException("бронированиях пользователя с ID " + userId);
                }
            }

        }

        return reservations;
    }

    public List<Reservation> findAllByPersonPhoneNumber(String personNumber) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "select * from reservations where person_number = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, personNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(getReservation(resultSet));
                }

                if (reservations.isEmpty()) {
                    throw new NoDataFoundException("бронированиях пользователя с телефоном " + personNumber);
                }
            }

        }
        return reservations;
    }

    public List<Reservation> findAllByGamePlaceId(Long gamePlaceId) throws  SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "select * from reservations where game_place_id = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gamePlaceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(getReservation(resultSet));
                }

                if (reservations.isEmpty()) {
                    throw new NoDataFoundException("бронированиях игрового места с ID " + gamePlaceId);
                }
            }

        }

        return reservations;
    }

    public List<Reservation> findAllByStatus(String status) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "select * from reservations where status = ? ORDER BY id ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(getReservation(resultSet));
                }

                if (reservations.isEmpty()) {
                    throw new NoDataFoundException("бронированиях со статусом " + status);
                }
            }

        }
        return reservations;
    }

    private Reservation getReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getLong("id"));
        reservation.setUserId(resultSet.getLong("user_id"));
        reservation.setPersonPhoneNumber(resultSet.getString("person_number"));
        reservation.setGamePlaceId(resultSet.getLong("game_place_id"));
        reservation.setStartTime(resultSet.getTimestamp("start_time"));
        reservation.setEndTime(resultSet.getTimestamp("end_time"));
        reservation.setStatus(resultSet.getString("status"));
        reservation.setTotalPrice(resultSet.getLong("total_price"));
        reservation.setCreatedAt(resultSet.getTimestamp("created_at"));
        reservation.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return reservation;
    }

    private void setStatement(PreparedStatement statement, Reservation reservation) throws SQLException {
        statement.setLong(1, reservation.getUserId());
        statement.setString(2, reservation.getPersonPhoneNumber());
        statement.setLong(3, reservation.getGamePlaceId());
        statement.setTimestamp(4, reservation.getStartTime());
        statement.setTimestamp(5, reservation.getEndTime());
        statement.setString(6, reservation.getStatus());
        statement.setDouble(7, reservation.getTotalPrice());
    }
}

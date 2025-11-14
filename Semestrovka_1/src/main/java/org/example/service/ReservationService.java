package org.example.service;

import org.example.model.Reservation;
import org.example.repository.ReservationRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository = new ReservationRepository();

    public void save(Reservation reservation) throws SQLException {
        validate(reservation);
        reservationRepository.save(reservation);
    }

    public void cancel(Long id) throws SQLException {
        Reservation reservation = reservationRepository.findByID(id);
        reservation.setStatus("CANCELLED");
        reservationRepository.update(reservation);
    }

    public void delete(Reservation reservation) throws SQLException {
        reservationRepository.delete(reservation);
    }

    public void update(Reservation reservation) throws SQLException {
        validate(reservation);
        reservationRepository.update(reservation);
    }

    public List<Reservation> getAll() throws SQLException {
        return reservationRepository.findAll();
    }

    public Reservation getById(Long id) throws SQLException {
        return reservationRepository.findByID(id);
    }

    public List<Reservation> getAllByUserId(Long id) throws SQLException {
        return reservationRepository.findAllByUserId(id);
    }

    public List<Reservation> getAllByPersonPhoneNumber(String phoneNumber) throws SQLException {
        return reservationRepository.findAllByPersonPhoneNumber(phoneNumber);
    }

    public List<Reservation> getAllByGamePlaceId(Long gamePlaceId) throws  SQLException {
        return reservationRepository.findAllByGamePlaceId(gamePlaceId);
    }

    public List<Reservation> getActive() throws SQLException {
        return reservationRepository.findAllByStatus("ACTIVE");
    }

    public List<Reservation> getCompleted() throws SQLException {
        return reservationRepository.findAllByStatus("COMPLETED");
    }

    public List<Reservation> getCancelled() throws SQLException {
        return reservationRepository.findAllByStatus("CANCELLED");
    }

    public List<Reservation> getUpcomingReservations() throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return getAll().stream()
                .filter(r -> r.getStartTime().after(now) && "ACTIVE".equals(r.getStatus()))
                .toList();
    }

    private void validate(Reservation reservation) throws SQLException {
        if (reservation.getStartTime() == null || reservation.getEndTime() == null) {
            throw new IllegalArgumentException("Время начала и окончания должны быть указаны");
        }

        if (!reservation.getEndTime().after(reservation.getStartTime())) {
            throw new IllegalArgumentException("Время окончания должно быть после времени начала");
        }

        if (reservation.getGamePlaceId() == null) {
            throw new IllegalArgumentException("ID игрового места должен быть указан");
        }

        if (!isTimeSlotAvailable(reservation.getGamePlaceId(), reservation.getStartTime(), reservation.getEndTime())) {
            throw new IllegalArgumentException("Данное время уже занято");
        }

        if (reservation.getUserId() == null) {
            throw new IllegalArgumentException("ID пользователя обязательно");
        }

        if (reservation.getPersonPhoneNumber() == null) {
            throw new IllegalArgumentException("Номер пользователя обязателен");
        }
        if (reservation.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Цена должна быть больше 0");
        }
    }

    public boolean isTimeSlotAvailable(Long gamePlaceId, Timestamp startTime, Timestamp endTime) throws SQLException {
        List<Reservation> existingReservations = getAllByGamePlaceId(gamePlaceId);

        return existingReservations.stream()
                .filter(r -> "ACTIVE".equals(r.getStatus()))
                .noneMatch(r -> isTimeOverlap(r.getStartTime(), r.getEndTime(), startTime, endTime));
    }

    private boolean isTimeOverlap(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2) {
        return start1.before(end2) && start2.before(end1);
    }
}

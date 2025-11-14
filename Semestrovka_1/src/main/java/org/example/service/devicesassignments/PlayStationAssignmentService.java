package org.example.service.devicesassignments;

import org.example.model.devicesassignments.PlayStationAssignment;
import org.example.repository.devices.PlayStationRepository;
import org.example.repository.devicesassignments.PlayStationAssignmentRepository;
import org.example.repository.gamingstations.PlayStationPlaceRepository;

import java.sql.SQLException;
import java.util.List;

public class PlayStationAssignmentService {
    private final PlayStationAssignmentRepository assignmentRepository = new PlayStationAssignmentRepository();
    private final PlayStationRepository playStationRepository = new PlayStationRepository();
    private final PlayStationPlaceRepository playStationPlaceRepository = new PlayStationPlaceRepository();

    public void save(PlayStationAssignment assignment) throws SQLException{
        validate(assignment);
        assignmentRepository.save(assignment);
    }

    public void update(PlayStationAssignment assignment) throws SQLException {
        validate(assignment);
        assignmentRepository.update(assignment);
    }

    public void delete(PlayStationAssignment assignment) throws SQLException {
        assignmentRepository.delete(assignment);
    }

    public List<PlayStationAssignment> getAll() throws SQLException {
        return assignmentRepository.findAll();
    }

    public PlayStationAssignment getById(Long id) throws SQLException {
        return assignmentRepository.findById(id);
    }


    private void validate(PlayStationAssignment assignment) throws SQLException {

        if (assignment == null) {
            throw new IllegalArgumentException("Назначение не может быть null");
        }

        if (assignment.getPlayStationId() == null) {
            throw new IllegalArgumentException("ID PlayStation обязательно");
        }

        if (assignment.getPlayStationPlaceId() == null) {
            throw new IllegalArgumentException("ID PlayStation места обязательно");
        }

        if (assignment.getAssignedAt() == null) {
            throw new IllegalArgumentException("Время назначения обязательно");
        }

        if (assignment.getUnassignedAt() != null &&
                !assignment.getUnassignedAt().after(assignment.getAssignedAt())) {
            throw new IllegalArgumentException("Время снятия назначения должно быть после времени назначения");
        }

        if (!isPlayStationExist(assignment.getPlayStationId())) {
            throw new IllegalArgumentException("Нет компьютера с таким ID: " + assignment.getPlayStationId());
        }
        if (!isPlayStationPlaceExist(assignment.getPlayStationPlaceId())) {
            throw new IllegalArgumentException("Нет компьютерного места с таким ID: " + assignment.getPlayStationPlaceId());
        }
    }

    private boolean isPlayStationExist(Long playStationId) throws SQLException {
        try {
            playStationRepository.findById(playStationId);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }

    private boolean isPlayStationPlaceExist(Long playStationPlaceId) throws SQLException {
        try {
            playStationPlaceRepository.findById(playStationPlaceId);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }
}
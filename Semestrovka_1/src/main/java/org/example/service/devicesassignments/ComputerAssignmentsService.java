package org.example.service.devicesassignments;

import org.example.model.devicesassignments.ComputerAssignment;
import org.example.repository.devices.ComputerRepository;
import org.example.repository.devicesassignments.ComputerAssignmentRepository;
import org.example.repository.gamingstations.ComputerPlaceRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ComputerAssignmentsService {


    private final ComputerAssignmentRepository assignmentRepository = new ComputerAssignmentRepository();
    private final ComputerRepository computerRepository = new ComputerRepository();
    private final ComputerPlaceRepository computerPlaceRepository = new ComputerPlaceRepository();


    public void save(ComputerAssignment assignment) throws SQLException{
        validate(assignment);
        assignmentRepository.save(assignment);
    }
    public void update(ComputerAssignment assignment) throws SQLException {
        validate(assignment);
        assignmentRepository.update(assignment);
    }

    public void delete(ComputerAssignment assignment) throws SQLException {
        assignmentRepository.delete(assignment);
    }

    public List<ComputerAssignment> getAll() throws SQLException {
        return assignmentRepository.findAll();
    }
    public ComputerAssignment getById(Long id) throws SQLException {
        return assignmentRepository.findById(id);
    }

    private void validate(ComputerAssignment assignment) throws SQLException {
        if (assignment == null) {
            throw new IllegalArgumentException("Назначение не может быть null");
        }

        if (assignment.getComputerId() == null) {
            throw new IllegalArgumentException("ID компьютера обязательно");
        }

        if (assignment.getComputerPlaceId() == null) {
            throw new IllegalArgumentException("ID компьютерного места обязательно");
        }

        if (assignment.getAssignedAt() == null) {
            throw new IllegalArgumentException("Время назначения обязательно");
        }

        if (assignment.getUnassignedAt() != null &&
                !assignment.getUnassignedAt().after(assignment.getAssignedAt())) {
            // снятие не может быть раньше назначения
            throw new IllegalArgumentException("Время снятия назначения должно быть после времени назначения");
        }

        if (!isComputerExist(assignment.getComputerId())) {
            throw new IllegalArgumentException("Нет компьютера с таким ID: " + assignment.getComputerId());
        }
        if (!isComputerPlaceExist(assignment.getComputerPlaceId())) {
            throw new IllegalArgumentException("Нет компьютерного места с таким ID: " + assignment.getComputerPlaceId());
        }
    }

    private boolean isComputerExist(Long computerId) throws SQLException {
        try {
            computerRepository.findById(computerId);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }

    private boolean isComputerPlaceExist(Long computerPlaceId) throws SQLException {
        try {
            computerPlaceRepository.findById(computerPlaceId);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }
}

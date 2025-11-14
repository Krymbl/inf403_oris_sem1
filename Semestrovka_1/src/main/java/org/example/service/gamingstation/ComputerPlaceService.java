package org.example.service.gamingstation;

import org.example.exceptions.NoDataFoundException;
import org.example.model.devicesassignments.ComputerAssignment;
import org.example.model.gamingstations.ComputerPlace;
import org.example.repository.devicesassignments.ComputerAssignmentRepository;
import org.example.repository.gamingstations.ComputerPlaceRepository;
import org.example.repository.gamingstations.GamingPlaceRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ComputerPlaceService {

    private final ComputerPlaceRepository computerPlaceRepository = new ComputerPlaceRepository();
    private final ComputerAssignmentRepository assignmentRepository = new ComputerAssignmentRepository();
    private final GamingPlaceRepository gamingPlaceRepository = new GamingPlaceRepository();

    public void save(ComputerPlace computerPlace) throws SQLException {
        validate(computerPlace);
        computerPlaceRepository.save(computerPlace);
    }

    public void delete(ComputerPlace computerPlace) throws SQLException {
        computerPlaceRepository.delete(computerPlace);
    }

    public void update(ComputerPlace computerPlace) throws SQLException {
        validate(computerPlace);
        computerPlaceRepository.update(computerPlace);
    }

    public List<ComputerPlace> getAll() throws SQLException {
        return computerPlaceRepository.findAll();
    }

    public ComputerPlace getById(Long id) throws SQLException {
        return computerPlaceRepository.findById(id);
    }

    public ComputerPlace getByGamingPlaceId(Long gamingPlaceId) throws SQLException {
        return computerPlaceRepository.findByGamingPlaceId(gamingPlaceId);
    }

    public List<ComputerPlace> getByCategory(String category) throws SQLException {
        return computerPlaceRepository.findByCategory(category);
    }

    public List<ComputerPlace> getAvailable() throws SQLException {
        return computerPlaceRepository.findByAvailability(true);
    }

    public List<ComputerPlace> getNotAvailable() throws SQLException {
        return computerPlaceRepository.findByAvailability(false);
    }


    public void validate(ComputerPlace computerPlace) throws SQLException {
        if (computerPlace == null) {
            throw new IllegalArgumentException("Компьютерное место не может быть null");
        }

        if (computerPlace.getGamingPlaceId() == null) {
            throw new IllegalArgumentException("ID игрового места обязательно");
        }

        if (computerPlace.getKeyboard() == null || computerPlace.getKeyboard().trim().isEmpty()) {
            throw new IllegalArgumentException("Название клавиатуры обязательно");
        }

        if (computerPlace.getMouse() == null || computerPlace.getMouse().trim().isEmpty()) {
            throw new IllegalArgumentException("Название мышки обязательно");
        }

        if (computerPlace.getHeadset() == null || computerPlace.getHeadset().trim().isEmpty()) {
            throw new IllegalArgumentException("Название наушников обязательно");
        }

        if (computerPlace.getMonitor() == null || computerPlace.getMonitor().trim().isEmpty()) {
            throw new IllegalArgumentException("Название монитора обязательно");
        }

        if (computerPlace.getChair() == null || computerPlace.getChair().trim().isEmpty()) {
            throw new IllegalArgumentException("Название стула обязательно");
        }

        if (!isGamingPlaceExists(computerPlace.getGamingPlaceId())) {
            throw new IllegalArgumentException("Игровое место с ID: " + computerPlace.getGamingPlaceId() + "не найдено");
        }

        if (!isGamingPlaceForComputer(computerPlace.getGamingPlaceId())) {
            throw new IllegalArgumentException("Игровое место с ID: " + computerPlace.getGamingPlaceId() + " не для компьютера");
        }
    }

    public boolean isGamingPlaceForComputer(Long gamingPlaceId) throws SQLException {
        return gamingPlaceRepository.findById(gamingPlaceId).getType().equals("COMPUTER");
    }

    public boolean isGamingPlaceExists(Long gamingPlaceId) throws SQLException {
        try {
            gamingPlaceRepository.findById(gamingPlaceId);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }
}
package org.example.service.gamingstation;

import org.example.exceptions.NoDataFoundException;
import org.example.model.devicesassignments.PlayStationAssignment;
import org.example.model.gamingstations.PlayStationPlace;
import org.example.repository.devicesassignments.PlayStationAssignmentRepository;
import org.example.repository.gamingstations.GamingPlaceRepository;
import org.example.repository.gamingstations.PlayStationPlaceRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class PlayStationPlaceService {

    private final PlayStationPlaceRepository playStationPlaceRepository = new PlayStationPlaceRepository();
    private final PlayStationAssignmentRepository assignmentRepository = new PlayStationAssignmentRepository();
    private final GamingPlaceRepository gamingPlaceRepository = new GamingPlaceRepository();

    public void save(PlayStationPlace playStationPlace) throws SQLException {
        validate(playStationPlace);
        playStationPlaceRepository.save(playStationPlace);
    }

    public void delete(PlayStationPlace playStationPlace) throws SQLException {
        playStationPlaceRepository.delete(playStationPlace);
    }

    public void update(PlayStationPlace playStationPlace) throws SQLException {
        validate(playStationPlace);
        playStationPlaceRepository.update(playStationPlace);
    }

    public List<PlayStationPlace> getAll() throws SQLException {
        return playStationPlaceRepository.findAll();
    }

    public PlayStationPlace getById(Long id) throws SQLException {
        return playStationPlaceRepository.findById(id);
    }

    public PlayStationPlace getByGamingPlaceId(Long gamingPlaceId) throws SQLException {
        return playStationPlaceRepository.findByGamingPlaceId(gamingPlaceId);
    }

    public List<PlayStationPlace> getByCategory(String category) throws SQLException {
        return playStationPlaceRepository.findByCategory(category);
    }

    public List<PlayStationPlace> getAvailable() throws SQLException {
        return playStationPlaceRepository.findByAvailability(true);
    }

    public List<PlayStationPlace> getNotAvailable() throws SQLException {
        return playStationPlaceRepository.findByAvailability(false);
    }

    public List<PlayStationPlace> getByMaxPlayers(int maxPlayers) throws SQLException {
        return playStationPlaceRepository.findByMaxPlayers(maxPlayers);
    }

    public void validate(PlayStationPlace playStationPlace) throws SQLException {
        if (playStationPlace == null) {
            throw new IllegalArgumentException("Плейстейшн место не может быть null");
        }

        if (playStationPlace.getGamingPlaceId() == null) {
            throw new IllegalArgumentException("ID игрового места обязательно");
        }

        if (playStationPlace.getTv() == null || playStationPlace.getTv().trim().isEmpty()) {
            throw new IllegalArgumentException("Название телевизора обязательно");
        }

        if (playStationPlace.getMaxPlayers() <= 0) {
            throw new IllegalArgumentException("Максимальное количество игроков должно быть больше 0");
        }
        if (!isPlayStationPlaceExists(playStationPlace.getGamingPlaceId())) {
            throw new IllegalArgumentException("Игровое место с ID: " + playStationPlace.getGamingPlaceId() + " не найдено");
        }
        if (!isGamingPlaceForPlayStation(playStationPlace.getGamingPlaceId())) {
            throw new IllegalArgumentException("Игровое место с ID: " + playStationPlace.getGamingPlaceId() + " не для плейстейшн");
        }
    }

    public boolean isGamingPlaceForPlayStation(Long gamingPlaceId) throws SQLException {
        return gamingPlaceRepository.findById(gamingPlaceId).getType().equals("PLAYSTATION");
    }

    public boolean isPlayStationPlaceExists(Long gamingPlaceId) throws SQLException {
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
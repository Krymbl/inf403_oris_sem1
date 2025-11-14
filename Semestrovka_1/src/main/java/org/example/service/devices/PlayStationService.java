package org.example.service.devices;

import org.example.model.devices.PlayStation;
import org.example.repository.devices.PlayStationRepository;

import java.sql.SQLException;
import java.util.List;

public class PlayStationService {

    private final PlayStationRepository playStationRepository = new PlayStationRepository();

    public void save(PlayStation playStation) throws SQLException {
        validate(playStation);
        playStationRepository.save(playStation);
    }

    public void delete(PlayStation playStation) throws SQLException {
        playStationRepository.delete(playStation);
    }

    public void update(PlayStation playStation) throws SQLException {
        validate(playStation);
        playStationRepository.update(playStation);
    }

    public List<PlayStation> getAll() throws SQLException {
        return playStationRepository.findAll();
    }

    public PlayStation getById(Long id) throws SQLException {
        return playStationRepository.findById(id);
    }

    public void validate(PlayStation playStation) {
        if (playStation == null) {
            throw new IllegalArgumentException("Плейстейшн не может быть null");
        }

        if (playStation.getModel() == null || playStation.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Модель плейстейшена обязательна");
        }

        if (playStation.getVersion() == null || playStation.getVersion().trim().isEmpty()) {
            throw new IllegalArgumentException("Версия плейстейшена обязательна");
        }
    }
}

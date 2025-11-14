package org.example.service.gamingstation;

import org.example.model.gamingstations.GamingPlace;
import org.example.repository.gamingstations.GamingPlaceRepository;

import java.sql.SQLException;
import java.util.List;

public class GamingPlaceService {

    private final GamingPlaceRepository gamingPlaceRepository = new GamingPlaceRepository();

    public void save(GamingPlace gamingPlace) throws SQLException {
        validate(gamingPlace);
        gamingPlaceRepository.save(gamingPlace);
    }

    public void delete(GamingPlace gamingPlace) throws SQLException {
        gamingPlaceRepository.delete(gamingPlace);
    }

    public void update(GamingPlace gamingPlace) throws SQLException {
        validate(gamingPlace);
        gamingPlaceRepository.update(gamingPlace);
    }

    public List<GamingPlace> getAll() throws SQLException {
        return gamingPlaceRepository.findAll();
    }

    public GamingPlace getById(long id) throws SQLException {
        return gamingPlaceRepository.findById(id);
    }

    public List<GamingPlace> getByCategory(String category) throws SQLException {
        return gamingPlaceRepository.findByCategory(category);
    }

    public List<GamingPlace> getByType(String type) throws SQLException {
        return gamingPlaceRepository.findByType(type);
    }

    public List<GamingPlace> getAvailable() throws SQLException {
        return gamingPlaceRepository.findAvailable(true);
    }

    public List<GamingPlace> getNotAvailable() throws SQLException {
        return gamingPlaceRepository.findAvailable(false);
    }

    public void validate(GamingPlace gamingPlace) {

        if (gamingPlace == null) {
            throw new IllegalArgumentException("Игровое место не может быть null");
        }

        if (gamingPlace.getName() == null || gamingPlace.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя игрового места обязательно");
        }

        if (gamingPlace.getType() == null || gamingPlace.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Тип игрового места обязателен");
        }

        if (gamingPlace.getCategory() == null || gamingPlace.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Категория игрового места обязательно");
        }

        if (gamingPlace.getPricePerHour() <= 0) {
            throw new IllegalArgumentException("Цена игрового места должна быть больше 0");
        }
    }
}

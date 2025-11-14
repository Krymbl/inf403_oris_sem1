package org.example.service.devices;

import org.example.model.devices.Computer;
import org.example.repository.devices.ComputerRepository;

import java.sql.SQLException;
import java.util.List;

public class ComputerService {

    private final ComputerRepository computerRepository = new ComputerRepository();


    public void save(Computer computer) throws SQLException {
        validate(computer);
        computerRepository.save(computer);
    }

    public void delete(Computer computer) throws SQLException {
        computerRepository.delete(computer);
    }

    public void update(Computer computer) throws SQLException {
        validate(computer);
        computerRepository.update(computer);
    }

    public List<Computer> getAll() throws SQLException {
        return computerRepository.findAll();
    }

    public Computer getById(Long id) throws SQLException {
        return computerRepository.findById(id);
    }


    public void validate(Computer computer) {
        if (computer == null) {
            throw new IllegalArgumentException("Компьютер не может быть null");
        }

        if (computer.getName() == null || computer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя компьютера обязательно");
        }

        if (computer.getCpu() == null || computer.getCpu().trim().isEmpty()) {
            throw new IllegalArgumentException("Процессор компьютера обязателен");
        }

        if (computer.getRam() <= 0) {
            throw new IllegalArgumentException("Оперативная память должна быть больше 0");
        }

        if (computer.getVideoCard() == null || computer.getVideoCard().trim().isEmpty()) {
            throw new IllegalArgumentException("Видеокарта компьютера обязательна");
        }

    }

}

package comp.club.service;

import comp.club.model.Computer;
import comp.club.repository.ComputerRepository;

import java.sql.Connection;
import java.util.List;

public class ComputerService {
    private ComputerRepository computerRepository;

    public ComputerService(Connection connection) {
        this.computerRepository = new ComputerRepository(connection);
    }

    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    public Computer getComputerById(int id) {
        return computerRepository.findById(id);
    }

    public boolean addComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    public boolean updateComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    public boolean validateComputer(Computer computer) {
        return computer.getName() != null && !computer.getName().trim().isEmpty() &&
                computer.getProcessor() != null && !computer.getProcessor().trim().isEmpty() &&
                computer.getRam() > 0 &&
                computer.getPricePerHour() > 0;
    }
}

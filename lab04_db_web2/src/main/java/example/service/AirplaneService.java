package example.service;

import jakarta.servlet.http.HttpServletRequest;
import example.model.Airplane;
import example.repository.AirplaneRepository;

import java.util.List;

public class AirplaneService {

    private AirplaneRepository repository = new AirplaneRepository();

    public void fillAttributes(HttpServletRequest request) {
        List<Airplane> airplanes = repository.findAll();
        request.setAttribute("airplanes", airplanes);
    }
}
package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.Airport;
import org.example.repository.AirportRepository;

import java.util.List;

public class AirportService {

    private AirportRepository repository = new AirportRepository();

    public void fillAttributes(HttpServletRequest request) {
        List<Airport> airports = repository.findAll();
        request.setAttribute("airports", airports);
    }
}

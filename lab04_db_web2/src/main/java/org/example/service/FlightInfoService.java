package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.FlightInfo;
import org.example.repository.FlightInfoRepository;

import java.util.List;

public class FlightInfoService {

    private FlightInfoRepository flightInfoRepository = new FlightInfoRepository();

    public void fillAttributes(HttpServletRequest request, String airportCode, String date, String typeTable) {
        List<FlightInfo> flightsInfo = flightInfoRepository.findAll(airportCode, date, typeTable);
        request.setAttribute("flightsInfo", flightsInfo);

    }

}

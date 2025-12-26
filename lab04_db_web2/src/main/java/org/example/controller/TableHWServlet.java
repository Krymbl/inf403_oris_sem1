package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AirportService;
import org.example.service.FlightInfoService;

import java.io.IOException;
@WebServlet("/tables")
public class TableHWServlet extends HttpServlet {

    private AirportService airportService = new AirportService();
    private FlightInfoService flightInfoService =  new FlightInfoService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        airportService.fillAttributes(request);
        request.getRequestDispatcher("/tables.ftlh").forward(request, response);


    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String airportCode = request.getParameter("airportCode");
        String date = request.getParameter("date");
        String type = request.getParameter("type");

        request.setAttribute("selectedAirport", airportCode);
        request.setAttribute("selectedDate", date);
        request.setAttribute("selectedType", type);

        airportService.fillAttributes(request);
        flightInfoService.fillAttributes(request, airportCode, date, type);

        request.getRequestDispatcher("/tables.ftlh").forward(request, response);

    }
}

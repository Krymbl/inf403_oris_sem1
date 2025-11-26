package org.example.controller.admin.devices.playstation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devices.PlayStation;
import org.example.service.devices.PlayStationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/playstations")
public class PlayStationsAdminServlet extends HttpServlet {
    private PlayStationService playStationService;

    public void init() throws ServletException {
        playStationService = new PlayStationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<PlayStation> playStations = playStationService.getAll();
            request.setAttribute("playStations", playStations);
            request.getRequestDispatcher("/admin/devices/playstations/showAllPlayStations.ftlh").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ошибка при загрузке списка playstation");
        }
    }
}
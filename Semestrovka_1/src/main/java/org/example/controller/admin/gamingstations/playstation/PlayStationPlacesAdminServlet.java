package org.example.controller.admin.gamingstations.playstation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.gamingstations.PlayStationPlace;
import org.example.service.gamingstation.PlayStationPlaceService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/playStationPlaces")
public class PlayStationPlacesAdminServlet extends HttpServlet {
    private PlayStationPlaceService playStationPlaceService;

    public void init() throws ServletException {
        playStationPlaceService = new PlayStationPlaceService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<PlayStationPlace> playStationPlaces = playStationPlaceService.getAll();
            request.setAttribute("playStationPlaces", playStationPlaces);
            request.getRequestDispatcher("/admin/gamingstations/playstation/showAllPlayStationPlaces.ftlh").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ошибка при загрузке списка playstation мест");
        }
    }
}
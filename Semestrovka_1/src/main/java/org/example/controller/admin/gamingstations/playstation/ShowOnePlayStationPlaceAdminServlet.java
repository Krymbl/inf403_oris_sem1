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

@WebServlet("/admin/playStationPlaceShowOne")
public class ShowOnePlayStationPlaceAdminServlet extends HttpServlet {
    private PlayStationPlaceService playStationPlaceService;

    @Override
    public void init() throws ServletException {
        playStationPlaceService = new PlayStationPlaceService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        PlayStationPlace playStationPlace = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                playStationPlace = playStationPlaceService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (playStationPlace == null) {
            response.sendRedirect("playStationPlaces?error=not_found");
            return;
        }

        request.setAttribute("playStationPlace", playStationPlace);
        request.getRequestDispatcher("/admin/gamingstations/playstation/showOnePlayStationPlace.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Long gamingPlaceId =  Long.parseLong(request.getParameter("gamingPlaceId"));
            String tv = request.getParameter("tv");
            int maxPlayers = Integer.parseInt(request.getParameter("maxPlayers"));

            PlayStationPlace playStationPlace = new PlayStationPlace();
            playStationPlace.setGamingPlaceId(gamingPlaceId);
            playStationPlace.setId(id);
            playStationPlace.setTv(tv);
            playStationPlace.setMaxPlayers(maxPlayers);

            playStationPlaceService.update(playStationPlace);
            response.sendRedirect("playStationPlaces?success=updated");
        } catch (Exception e) {
            response.sendRedirect("playStationPlaceShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
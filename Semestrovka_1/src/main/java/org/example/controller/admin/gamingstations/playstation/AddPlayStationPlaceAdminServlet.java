package org.example.controller.admin.gamingstations.playstation;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.gamingstations.PlayStationPlace;
import org.example.service.gamingstation.PlayStationPlaceService;

import java.io.IOException;

@WebServlet("/admin/addPlayStationPlace")
public class AddPlayStationPlaceAdminServlet extends HttpServlet {
    private PlayStationPlaceService playStationPlaceService;

    @Override
    public void init() throws ServletException {
        playStationPlaceService = new PlayStationPlaceService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/gamingstations/playstation/addPlayStationPlace.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long gamingPlaceId = Long.parseLong(request.getParameter("gamingPlaceId"));
            String tv = request.getParameter("tv");
            int maxPlayers = Integer.parseInt(request.getParameter("maxPlayers"));

            PlayStationPlace playStationPlace = new PlayStationPlace();
            playStationPlace.setGamingPlaceId(gamingPlaceId);
            playStationPlace.setTv(tv);
            playStationPlace.setMaxPlayers(maxPlayers);

            playStationPlaceService.save(playStationPlace);
            response.sendRedirect("playStationPlaces?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addPlayStationPlace?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
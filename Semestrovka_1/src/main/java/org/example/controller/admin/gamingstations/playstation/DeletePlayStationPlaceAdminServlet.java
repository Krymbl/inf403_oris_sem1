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

@WebServlet("/admin/deletePlayStationPlace")
public class DeletePlayStationPlaceAdminServlet extends HttpServlet {
    private PlayStationPlaceService playStationPlaceService;

    @Override
    public void init() throws ServletException {
        playStationPlaceService = new PlayStationPlaceService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("playStationPlaces?error=" +
                    java.net.URLEncoder.encode("ID PlayStation места не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            PlayStationPlace playStationPlace = new PlayStationPlace();
            playStationPlace.setId(id);

            playStationPlaceService.delete(playStationPlace);

            response.sendRedirect("playStationPlaces?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("playStationPlaces?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("playStationPlaces?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении PlayStation места: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("playStationPlaces?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
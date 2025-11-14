package org.example.controller.admin.gamingstations.gaming;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.gamingstations.GamingPlace;
import org.example.service.gamingstation.GamingPlaceService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteGamingPlace")
public class DeleteGamingPlaceAdminServlet extends HttpServlet {
    private GamingPlaceService gamingPlaceService;

    @Override
    public void init() throws ServletException {
        gamingPlaceService = new GamingPlaceService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("gamingPlaces?error=" +
                    java.net.URLEncoder.encode("ID игрового места не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            GamingPlace gamingPlace = new GamingPlace();
            gamingPlace.setId(id);

            gamingPlaceService.delete(gamingPlace);

            response.sendRedirect("gamingPlaces?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("gamingPlaces?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("gamingPlaces?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении игрового места: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gamingPlaces?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
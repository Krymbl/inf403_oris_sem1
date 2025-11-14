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

@WebServlet("/admin/deletePlayStation")
public class DeletePlayStationAdminServlet extends HttpServlet {
    private PlayStationService playStationService;

    @Override
    public void init() throws ServletException {
        playStationService = new PlayStationService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("playstations?error=" +
                    java.net.URLEncoder.encode("ID PlayStation не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            PlayStation playStation = new PlayStation();
            playStation.setId(id);

            playStationService.delete(playStation);

            response.sendRedirect("playstations?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("playstations?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("playstations?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении PlayStation: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("playstations?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
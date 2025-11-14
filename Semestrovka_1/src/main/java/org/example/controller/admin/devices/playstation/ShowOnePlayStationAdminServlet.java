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

@WebServlet("/admin/playStationShowOne")
public class ShowOnePlayStationAdminServlet extends HttpServlet {
    private PlayStationService playStationService;

    @Override
    public void init() throws ServletException {
        playStationService = new PlayStationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        PlayStation playStation = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                playStation = playStationService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (playStation == null) {
            response.sendRedirect("playstations?error=not_found");
            return;
        }

        request.setAttribute("playStation", playStation);
        request.getRequestDispatcher("/admin/devices/playstations/showOnePlayStation.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String model = request.getParameter("model");
            String version = request.getParameter("version");

            PlayStation playStation = new PlayStation();
            playStation.setId(id);
            playStation.setModel(model);
            playStation.setVersion(version);

            playStationService.update(playStation);
            response.sendRedirect("playstations?success=updated");
        } catch (Exception e) {
            response.sendRedirect("playStationShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
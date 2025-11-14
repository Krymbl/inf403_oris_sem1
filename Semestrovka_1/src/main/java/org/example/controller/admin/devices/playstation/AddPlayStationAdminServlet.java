package org.example.controller.admin.devices.playstation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devices.PlayStation;
import org.example.service.devices.PlayStationService;

import java.io.IOException;

@WebServlet("/admin/addPlayStation")
public class AddPlayStationAdminServlet extends HttpServlet {
    private PlayStationService playStationService;

    @Override
    public void init() throws ServletException {
        playStationService = new PlayStationService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/devices/playstations/addPlayStation.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String model = request.getParameter("model");
            String version = request.getParameter("version");

            PlayStation playStation = new PlayStation();
            playStation.setModel(model);
            playStation.setVersion(version);

            playStationService.save(playStation);
            response.sendRedirect("playstations?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addPlayStation?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
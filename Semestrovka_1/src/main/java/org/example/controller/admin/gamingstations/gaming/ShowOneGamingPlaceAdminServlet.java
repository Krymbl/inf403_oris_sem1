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

@WebServlet("/admin/gamingPlaceShowOne")
public class ShowOneGamingPlaceAdminServlet extends HttpServlet {
    private GamingPlaceService gamingPlaceService;

    @Override
    public void init() throws ServletException {
        gamingPlaceService = new GamingPlaceService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        GamingPlace gamingPlace = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                gamingPlace = gamingPlaceService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (gamingPlace == null) {
            response.sendRedirect("gamingPlaces?error=not_found");
            return;
        }

        request.setAttribute("gamingPlace", gamingPlace);
        request.getRequestDispatcher("/admin/gamingstations/gaming/showOneGamingPlace.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String category = request.getParameter("category");
            double pricePerHour = Double.parseDouble(request.getParameter("pricePerHour"));
            boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));

            GamingPlace gamingPlace = new GamingPlace();
            gamingPlace.setId(id);
            gamingPlace.setName(name);
            gamingPlace.setType(type);
            gamingPlace.setCategory(category);
            gamingPlace.setPricePerHour(pricePerHour);
            gamingPlace.setAvailable(isAvailable);

            gamingPlaceService.update(gamingPlace);
            response.sendRedirect("gamingPlaces?success=updated");
        } catch (Exception e) {
            response.sendRedirect("gamingPlaceShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
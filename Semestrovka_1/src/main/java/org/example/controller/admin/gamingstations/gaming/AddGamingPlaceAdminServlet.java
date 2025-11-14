package org.example.controller.admin.gamingstations.gaming;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.gamingstations.GamingPlace;
import org.example.service.gamingstation.GamingPlaceService;

import java.io.IOException;

@WebServlet("/admin/addGamingPlace")
public class AddGamingPlaceAdminServlet extends HttpServlet {
    private GamingPlaceService gamingPlaceService;

    @Override
    public void init() throws ServletException {
        gamingPlaceService = new GamingPlaceService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/gamingstations/gaming/addGamingPlace.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            String category = request.getParameter("category");
            boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));
            double pricePerHour = Double.parseDouble(request.getParameter("pricePerHour"));

            GamingPlace gamingPlace = new GamingPlace();
            gamingPlace.setName(name);
            gamingPlace.setType(type);
            gamingPlace.setCategory(category);
            gamingPlace.setAvailable(isAvailable);
            gamingPlace.setPricePerHour(pricePerHour);

            gamingPlaceService.save(gamingPlace);
            response.sendRedirect("gamingPlaces?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addGamingPlace?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
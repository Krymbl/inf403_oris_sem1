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
import java.util.List;

@WebServlet("/admin/gamingPlaces")
public class GamingPlacesAdminServlet extends HttpServlet {
    private GamingPlaceService gamingPlaceService;

    public void init() throws ServletException {
        gamingPlaceService = new GamingPlaceService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<GamingPlace> gamingPlaces = gamingPlaceService.getAll();
            request.setAttribute("gamingPlaces", gamingPlaces);
            request.getRequestDispatcher("/admin/gamingstations/gaming/showAllGamingPlaces.ftlh").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
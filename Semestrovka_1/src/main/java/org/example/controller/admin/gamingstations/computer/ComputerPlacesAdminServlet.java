package org.example.controller.admin.gamingstations.computer;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.gamingstations.ComputerPlace;
import org.example.service.gamingstation.ComputerPlaceService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/computerPlaces")
public class ComputerPlacesAdminServlet extends HttpServlet {
    private ComputerPlaceService computerPlaceService;

    public void init() throws ServletException {
        computerPlaceService = new ComputerPlaceService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ComputerPlace> computerPlaces = computerPlaceService.getAll();
            request.setAttribute("computerPlaces", computerPlaces);
            request.getRequestDispatcher("/admin/gamingstations/computer/showAllComputerPlaces.ftlh").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
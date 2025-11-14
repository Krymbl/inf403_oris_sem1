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

@WebServlet("/admin/computerPlaceShowOne")
public class ShowOneComputerPlaceAdminServlet extends HttpServlet {
    private ComputerPlaceService computerPlaceService;

    @Override
    public void init() throws ServletException {
        computerPlaceService = new ComputerPlaceService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        ComputerPlace computerPlace = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                computerPlace = computerPlaceService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (computerPlace == null) {
            response.sendRedirect("computerPlaces?error=not_found");
            return;
        }

        request.setAttribute("computerPlace", computerPlace);
        request.getRequestDispatcher("/admin/gamingstations/computer/showOneComputerPlace.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Long gamingPlaceId = Long.parseLong(request.getParameter("gamingPlaceId"));
            String keyboard = request.getParameter("keyboard");
            String mouse = request.getParameter("mouse");
            String headset = request.getParameter("headset");
            String monitor = request.getParameter("monitor");
            String chair = request.getParameter("chair");

            ComputerPlace computerPlace = new ComputerPlace();
            computerPlace.setId(id);
            computerPlace.setGamingPlaceId(gamingPlaceId);
            computerPlace.setKeyboard(keyboard);
            computerPlace.setMouse(mouse);
            computerPlace.setHeadset(headset);
            computerPlace.setMonitor(monitor);
            computerPlace.setChair(chair);

            computerPlaceService.update(computerPlace);
            response.sendRedirect("computerPlaces?success=updated");
        } catch (Exception e) {
            response.sendRedirect("computerPlaceShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
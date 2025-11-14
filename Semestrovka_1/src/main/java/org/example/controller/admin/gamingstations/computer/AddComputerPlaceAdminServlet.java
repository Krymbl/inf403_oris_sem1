package org.example.controller.admin.gamingstations.computer;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.gamingstations.ComputerPlace;
import org.example.service.gamingstation.ComputerPlaceService;

import java.io.IOException;

@WebServlet("/admin/addComputerPlace")
public class AddComputerPlaceAdminServlet extends HttpServlet {
    private ComputerPlaceService computerPlaceService;

    @Override
    public void init() throws ServletException {
        computerPlaceService = new ComputerPlaceService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/gamingstations/computer/addComputerPlace.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long gamingPlaceId = Long.parseLong(request.getParameter("gamingPlaceId"));
            String keyboard = request.getParameter("keyboard");
            String mouse = request.getParameter("mouse");
            String headset = request.getParameter("headset");
            String monitor = request.getParameter("monitor");
            String chair = request.getParameter("chair");

            ComputerPlace computerPlace = new ComputerPlace();
            computerPlace.setGamingPlaceId(gamingPlaceId);
            computerPlace.setKeyboard(keyboard);
            computerPlace.setMouse(mouse);
            computerPlace.setHeadset(headset);
            computerPlace.setMonitor(monitor);
            computerPlace.setChair(chair);

            computerPlaceService.save(computerPlace);
            response.sendRedirect("computerPlaces?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addComputerPlace?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
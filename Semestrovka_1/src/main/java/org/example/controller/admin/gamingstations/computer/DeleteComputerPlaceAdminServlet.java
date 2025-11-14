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

@WebServlet("/admin/deleteComputerPlace")
public class DeleteComputerPlaceAdminServlet extends HttpServlet {
    private ComputerPlaceService computerPlaceService;

    @Override
    public void init() throws ServletException {
        computerPlaceService = new ComputerPlaceService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("computerPlaces?error=" +
                    java.net.URLEncoder.encode("ID компьютерного места не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            ComputerPlace computerPlace = new ComputerPlace();
            computerPlace.setId(id);

            computerPlaceService.delete(computerPlace);

            response.sendRedirect("computerPlaces?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("computerPlaces?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("computerPlaces?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении компьютерного места: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("computerPlaces?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
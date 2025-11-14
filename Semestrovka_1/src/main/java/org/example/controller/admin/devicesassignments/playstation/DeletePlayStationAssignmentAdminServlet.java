package org.example.controller.admin.devicesassignments.playstation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devicesassignments.PlayStationAssignment;
import org.example.service.devicesassignments.PlayStationAssignmentService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deletePlayStationAssignment")
public class DeletePlayStationAssignmentAdminServlet extends HttpServlet {
    private PlayStationAssignmentService playStationAssignmentService;

    @Override
    public void init() throws ServletException {
        playStationAssignmentService = new PlayStationAssignmentService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("playStationAssignments?error=" +
                    java.net.URLEncoder.encode("ID назначения не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            PlayStationAssignment assignment = playStationAssignmentService.getById(id);

            playStationAssignmentService.delete(assignment);

            response.sendRedirect("playStationAssignments?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("playStationAssignments?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("playStationAssignments?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении назначения: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("playStationAssignments?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
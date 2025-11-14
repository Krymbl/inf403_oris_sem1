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
import java.sql.Timestamp;

@WebServlet("/admin/playStationAssignmentShowOne")
public class ShowOnePlayStationAssignmentAdminServlet extends HttpServlet {
    private PlayStationAssignmentService playStationAssignmentService;

    @Override
    public void init() throws ServletException {
        playStationAssignmentService = new PlayStationAssignmentService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        PlayStationAssignment playStationAssignment = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                playStationAssignment = playStationAssignmentService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (playStationAssignment == null) {
            response.sendRedirect("playStationAssignments?error=not_found");
            return;
        }

        request.setAttribute("playStationAssignment", playStationAssignment);
        request.getRequestDispatcher("/admin/devicesassignments/playstation/showOnePlayStationAssignment.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Long playStationId = Long.parseLong(request.getParameter("playStationId"));
            Long playStationPlaceId = Long.parseLong(request.getParameter("playStationPlaceId"));
            Timestamp assignedAt = Timestamp.valueOf(request.getParameter("assignedAt").replace("T", " ") + ":00");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            Timestamp unassignedAt = null;
            String unassignedAtParam = request.getParameter("unassignedAt");
            if (unassignedAtParam != null && !unassignedAtParam.trim().isEmpty()) {
                unassignedAt = Timestamp.valueOf(unassignedAtParam.replace("T", " ") + ":00");
            }

            PlayStationAssignment playStationAssignment = new PlayStationAssignment();
            playStationAssignment.setId(id);
            playStationAssignment.setPlayStationId(playStationId);
            playStationAssignment.setPlayStationPlaceId(playStationPlaceId);
            playStationAssignment.setAssignedAt(assignedAt);
            playStationAssignment.setUnassignedAt(unassignedAt);
            playStationAssignment.setIsActive(isActive);

            playStationAssignmentService.update(playStationAssignment);

            response.sendRedirect("playStationAssignments?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("playStationAssignmentShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
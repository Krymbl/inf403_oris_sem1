package org.example.controller.admin.devicesassignments.playstation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devicesassignments.PlayStationAssignment;
import org.example.service.devicesassignments.PlayStationAssignmentService;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/admin/addPlayStationAssignment")
public class AddPlayStationAssignmentAdminServlet extends HttpServlet {
    private PlayStationAssignmentService playStationAssignmentService;

    @Override
    public void init() throws ServletException {
        playStationAssignmentService = new PlayStationAssignmentService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/devicesassignments/playstation/addPlayStationAssignment.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long playStationId = Long.parseLong(request.getParameter("playStationId"));
            Long playStationPlaceId = Long.parseLong(request.getParameter("playStationPlaceId"));
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
            Timestamp assignedAt = Timestamp.valueOf(request.getParameter("assignedAt").replace("T", " ") + ":00");
            Timestamp unassignedAt = null;
            String unassignedAtParam = request.getParameter("unassignedAt");
            if (unassignedAtParam != null && !unassignedAtParam.trim().isEmpty()) {
                unassignedAt = Timestamp.valueOf(unassignedAtParam.replace("T", " ") + ":00");
            }

            PlayStationAssignment playStationAssignment = new PlayStationAssignment();
            playStationAssignment.setPlayStationId(playStationId);
            playStationAssignment.setPlayStationPlaceId(playStationPlaceId);
            playStationAssignment.setIsActive(isActive);
            playStationAssignment.setAssignedAt(assignedAt);
            playStationAssignment.setUnassignedAt(unassignedAt);


            playStationAssignmentService.save(playStationAssignment);
            response.sendRedirect("playStationAssignments?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addPlayStationAssignment?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
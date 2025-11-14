package org.example.controller.admin.devicesassignments.computer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devicesassignments.ComputerAssignment;
import org.example.service.devicesassignments.ComputerAssignmentsService;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/admin/addComputerAssignment")
public class AddComputerAssignmentAdminServlet extends HttpServlet {
    private ComputerAssignmentsService computerAssignmentsService;

    @Override
    public void init() throws ServletException {
        computerAssignmentsService = new ComputerAssignmentsService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/devicesassignments/computer/addComputerAssignment.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long computerId = Long.parseLong(request.getParameter("computerId"));
            Long computerPlaceId = Long.parseLong(request.getParameter("computerPlaceId"));
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
            Timestamp assignedAt = Timestamp.valueOf(request.getParameter("assignedAt").replace("T", " ") + ":00");
            Timestamp unassignedAt = null;
            String unassignedAtParam = request.getParameter("unassignedAt");
            if (unassignedAtParam != null && !unassignedAtParam.trim().isEmpty()) {
                unassignedAt = Timestamp.valueOf(unassignedAtParam.replace("T", " ") + ":00");
            }

            ComputerAssignment computerAssignment = new ComputerAssignment();
            computerAssignment.setComputerId(computerId);
            computerAssignment.setComputerPlaceId(computerPlaceId);
            computerAssignment.setIsActive(isActive);
            computerAssignment.setAssignedAt(assignedAt);
            computerAssignment.setUnassignedAt(unassignedAt);

            computerAssignmentsService.save(computerAssignment);
            response.sendRedirect("computerAssignments?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addComputerAssignment?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
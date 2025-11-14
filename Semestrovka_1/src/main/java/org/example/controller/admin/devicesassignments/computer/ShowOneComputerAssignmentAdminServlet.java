package org.example.controller.admin.devicesassignments.computer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devicesassignments.ComputerAssignment;
import org.example.service.devicesassignments.ComputerAssignmentsService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/admin/computerAssignmentShowOne")
public class ShowOneComputerAssignmentAdminServlet extends HttpServlet {
    private ComputerAssignmentsService computerAssignmentsService;

    @Override
    public void init() throws ServletException {
        computerAssignmentsService = new ComputerAssignmentsService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        ComputerAssignment computerAssignment = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                computerAssignment = computerAssignmentsService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (computerAssignment == null) {
            response.sendRedirect("computerAssignments?error=not_found");
            return;
        }

        request.setAttribute("computerAssignment", computerAssignment);
        request.getRequestDispatcher("/admin/devicesassignments/computer/showOneComputerAssignment.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Long computerId = Long.parseLong(request.getParameter("computerId"));
            Long computerPlaceId = Long.parseLong(request.getParameter("computerPlaceId"));
            Timestamp assignedAt = Timestamp.valueOf(request.getParameter("assignedAt").replace("T", " ") + ":00");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            Timestamp unassignedAt = null;
            String unassignedAtParam = request.getParameter("unassignedAt");
            if (unassignedAtParam != null && !unassignedAtParam.trim().isEmpty()) {
                unassignedAt = Timestamp.valueOf(unassignedAtParam.replace("T", " ") + ":00");
            }

            ComputerAssignment computerAssignment = new ComputerAssignment();
            computerAssignment.setId(id);
            computerAssignment.setComputerId(computerId);
            computerAssignment.setComputerPlaceId(computerPlaceId);
            computerAssignment.setAssignedAt(assignedAt);
            computerAssignment.setUnassignedAt(unassignedAt);
            computerAssignment.setIsActive(isActive);

            computerAssignmentsService.update(computerAssignment);

            response.sendRedirect("computerAssignments?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("computerAssignmentShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
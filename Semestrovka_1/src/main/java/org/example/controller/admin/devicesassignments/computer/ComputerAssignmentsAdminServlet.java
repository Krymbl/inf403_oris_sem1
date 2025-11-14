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
import java.util.List;

@WebServlet("/admin/computerAssignments")
public class ComputerAssignmentsAdminServlet extends HttpServlet {
    private ComputerAssignmentsService computerAssignmentsService;

    @Override
    public void init() throws ServletException {
        computerAssignmentsService = new ComputerAssignmentsService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ComputerAssignment> computerAssignments = computerAssignmentsService.getAll();
            request.setAttribute("computerAssignments", computerAssignments);
            request.getRequestDispatcher("/admin/devicesassignments/computer/showAllComputerAssignments.ftlh").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
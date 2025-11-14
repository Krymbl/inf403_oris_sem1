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

@WebServlet("/admin/deleteComputerAssignment")
public class DeleteComputerAssignmentAdminServlet extends HttpServlet {
    private ComputerAssignmentsService computerAssignmentsService;

    @Override
    public void init() throws ServletException {
        computerAssignmentsService = new ComputerAssignmentsService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("computerAssignments?error=" +
                    java.net.URLEncoder.encode("ID назначения не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            ComputerAssignment assignment = computerAssignmentsService.getById(id);

            computerAssignmentsService.delete(assignment);

            response.sendRedirect("computerAssignments?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("computerAssignments?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("computerAssignments?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении назначения: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("computerAssignments?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
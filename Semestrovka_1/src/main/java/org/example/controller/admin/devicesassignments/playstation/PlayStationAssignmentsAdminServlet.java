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
import java.util.List;

@WebServlet("/admin/playStationAssignments")
public class PlayStationAssignmentsAdminServlet extends HttpServlet {
    private PlayStationAssignmentService playStationAssignmentService;

    @Override
    public void init() throws ServletException {
        playStationAssignmentService = new PlayStationAssignmentService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<PlayStationAssignment> playStationAssignments = playStationAssignmentService.getAll();
            request.setAttribute("playStationAssignments", playStationAssignments);
            request.getRequestDispatcher("/admin/devicesassignments/playstation/showAllPlayStationAssignments.ftlh").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
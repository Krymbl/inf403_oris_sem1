package org.example.controller.admin.devices.computer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devices.Computer;
import org.example.service.devices.ComputerService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/computers")
public class ComputersAdminServlet extends HttpServlet {
    private ComputerService computerService;

    public void init() throws ServletException {
        computerService = new ComputerService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Computer> computers = computerService.getAll();
            request.setAttribute("computers", computers);
            request.getRequestDispatcher("/admin/devices/computers/showAllComputers.ftlh").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}

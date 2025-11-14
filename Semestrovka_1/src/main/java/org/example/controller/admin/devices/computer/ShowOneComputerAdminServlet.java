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

@WebServlet("/admin/computerShowOne")
public class ShowOneComputerAdminServlet extends HttpServlet {
    private ComputerService computerService;

    @Override
    public void init() throws ServletException {
        computerService = new ComputerService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        Computer computer = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                computer = computerService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (computer == null) {
            response.sendRedirect("show?error=not_found");
        }
        request.setAttribute("computer", computer);
        request.getRequestDispatcher("/admin/devices/computers/showOneComputer.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            String cpu = request.getParameter("cpu");
            int ram = Integer.parseInt(request.getParameter("ram"));
            String videoCard = request.getParameter("videoCard");

            Computer computer = new Computer();
            computer.setId(id);
            computer.setName(name);
            computer.setCpu(cpu);
            computer.setRam(ram);
            computer.setVideoCard(videoCard);

            computerService.update(computer);
            response.sendRedirect("computers?success=updated");
        } catch (Exception e) {
            response.sendRedirect("computerShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}

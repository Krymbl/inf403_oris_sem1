package org.example.controller.admin.devices.computer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.devices.Computer;
import org.example.service.devices.ComputerService;

import java.io.IOException;

@WebServlet("/admin/addComputer")
public class AddComputerAdminServlet extends HttpServlet {
    private ComputerService computerService;

    @Override
    public void init() throws ServletException {
        computerService = new ComputerService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/devices/computers/addComputer.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String cpu = request.getParameter("cpu");
            int ram = Integer.parseInt(request.getParameter("ram"));
            String videoCard = request.getParameter("videoCard");

            Computer computer = new Computer();
            computer.setName(name);
            computer.setCpu(cpu);
            computer.setRam(ram);
            computer.setVideoCard(videoCard);

            computerService.save(computer);
            response.sendRedirect("computers?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addComputer?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
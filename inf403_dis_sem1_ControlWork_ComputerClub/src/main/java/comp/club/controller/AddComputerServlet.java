package comp.club.controller;

import comp.club.config.DatabaseConfig;
import comp.club.model.Computer;
import comp.club.service.ComputerService;
import comp.club.util.TemplateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

@WebServlet("/add")
public class AddComputerServlet extends HttpServlet {
    private ComputerService computerService;

    @Override
    public void init() throws ServletException {
        computerService = new ComputerService(DatabaseConfig.getConnection());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Writer writer = response.getWriter();
        String html = TemplateUtil.renderAddComputer();
        writer.write(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String processor = request.getParameter("processor");
            int ram = Integer.parseInt(request.getParameter("ram"));
            String videoCard = request.getParameter("videoCard");
            boolean isAvailable = "on".equals(request.getParameter("isAvailable"));
            double pricePerHour = Double.parseDouble(request.getParameter("pricePerHour"));

            Computer computer = new Computer(0, name, processor, ram, videoCard, isAvailable, pricePerHour);

            if (computerService.validateComputer(computer)) {
                computerService.addComputer(computer);
                response.sendRedirect("show");
            } else {
                response.sendRedirect("add?error=1");
            }
        } catch (Exception e) {
            response.sendRedirect("add?error=1");
        }
    }
}
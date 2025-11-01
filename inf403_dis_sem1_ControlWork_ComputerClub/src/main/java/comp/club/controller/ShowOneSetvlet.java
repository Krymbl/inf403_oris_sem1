package comp.club.controller;

import comp.club.model.Computer;
import comp.club.service.ComputerService;
import comp.club.util.TemplateUtil;
import comp.club.config.DatabaseConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

@WebServlet("/showone")
public class ShowOneSetvlet extends HttpServlet {
    private ComputerService computerService;

    @Override
    public void init() throws ServletException {
        computerService = new ComputerService(DatabaseConfig.getConnection());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Writer writer = response.getWriter();

        String idParam = request.getParameter("id");
        Computer computer = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                computer = computerService.getComputerById(id);
            } catch (NumberFormatException e) {
                // Invalid ID format
            }
        }

        if (computer == null) {
            response.sendRedirect("show?error=not_found");
            return;
        }

        String html = TemplateUtil.renderShowOneTemplate(computer);
        writer.write(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String processor = request.getParameter("processor");
            int ram = Integer.parseInt(request.getParameter("ram"));
            String videoCard = request.getParameter("videoCard");
            boolean isAvailable = "on".equals(request.getParameter("isAvailable"));
            double pricePerHour = Double.parseDouble(request.getParameter("pricePerHour"));

            Computer computer = new Computer(id, name, processor, ram, videoCard, isAvailable, pricePerHour);

            if (computerService.validateComputer(computer)) {
                computerService.updateComputer(computer);
                response.sendRedirect("show");
            } else {
                response.sendRedirect("showone?id=" + id + "&error=validation");
            }
        } catch (Exception e) {
            response.sendRedirect("showone?id=" + request.getParameter("id") + "&error=update");
        }
    }
}
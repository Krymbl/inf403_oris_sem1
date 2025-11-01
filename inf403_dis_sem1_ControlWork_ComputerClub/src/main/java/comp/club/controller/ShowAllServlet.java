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
import java.util.List;

@WebServlet("/show")
public class ShowAllServlet extends HttpServlet {
    private ComputerService computerService;

    public void init() throws ServletException {
        computerService = new ComputerService(DatabaseConfig.getConnection());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Writer writer = response.getWriter();
        List<Computer> computers = computerService.getAllComputers();
        String html = TemplateUtil.renderShowAllTemplate(computers);

        writer.write(html);
    }
}
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

@WebServlet("/admin/deleteComputer")
public class DeleteComputerAdminServlet extends HttpServlet {
    private ComputerService computerService;

    @Override
    public void init() throws ServletException {
        computerService = new ComputerService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("computers?error=" +
                    java.net.URLEncoder.encode("ID компьютера не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            Computer computer = new Computer();
            computer.setId(id);

            computerService.delete(computer);

            response.sendRedirect("computers?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("computers?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("computers?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении компьютера: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("computers?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
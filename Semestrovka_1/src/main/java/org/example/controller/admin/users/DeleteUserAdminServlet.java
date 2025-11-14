package org.example.controller.admin.users;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteUser")
public class DeleteUserAdminServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("users?error=" +
                    java.net.URLEncoder.encode("ID пользователя не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            User user = new User();
            user.setId(id);

            userService.delete(user);

            response.sendRedirect("users?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("users?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("users?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении пользователя: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("users?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
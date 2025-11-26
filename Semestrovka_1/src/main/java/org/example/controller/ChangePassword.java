package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;

@WebServlet("/user/changePassword")
public class ChangePassword extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/changePassword.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("error");
        session.removeAttribute("success");
        User user = (User) session.getAttribute("user");

        try {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            userService.authenticate(user.getUsername(), currentPassword);

            if (newPassword.equals(confirmPassword)) {
                userService.updateHashPassword(user.getUsername(), newPassword);
                session.setAttribute("success", true);
                response.sendRedirect("changePassword?success=true");
            } else {
                throw new Exception("Пароли не совпадают");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("changePassword?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }



}

package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String mood = request.getParameter("mood");

        request.setAttribute("surname", surname != null ? surname : "Не указано");
        request.setAttribute("name", name != null ? name : "Не указано");
        request.setAttribute("role", role != null ? role : "Не указано");
        request.setAttribute("mood", mood != null ? mood : "Не указано");

        request.getRequestDispatcher("/user.ftlh")
                .forward(request, response);
    }
}

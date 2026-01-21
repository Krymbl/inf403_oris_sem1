package org.example.allPractice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

//Написать сервлет и jsp/freemarker для получения всех админов сайта.
// У каждого админа есть имя, роль, может быть или не быть аватарка
@WebServlet("/admins")
public class AdminsServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService = new AdminService();

        List<Admin> admins = adminService.findAll();

        req.setAttribute("admins", admins);
        req.getRequestDispatcher("admins.ftlh").forward(req, resp);
    }
}

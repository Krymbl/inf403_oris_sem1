package org.example.servletStreetOrder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ServletOrder extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String flat = request.getParameter("flat");
        String phone = request.getParameter("phone");
        HttpSession session = request.getSession();

        try {
            Pizzeria pizzeria = new Pizzeria();
            pizzeria.makeOder(street, house, flat, phone);
            response.sendRedirect("successPage");
        } catch (Exception e) {
            String error = "Не удалось сделать заказ";
            session.setAttribute("error", error);
            response.sendRedirect("error");
        }
    }
}

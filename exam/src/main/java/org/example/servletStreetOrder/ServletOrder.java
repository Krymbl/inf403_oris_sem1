package org.example.servletStreetOrder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
написать сервлет который получает параметры улица, дом, квартира, номер телефона и делает заказ.
 в случае успешного заказа уведомляет об этом пользователя, в случае ошибки - подробности ошибки
 */
import java.io.IOException;
import java.io.PrintWriter;

public class ServletOrder extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String flat = request.getParameter("flat");
        String phone = request.getParameter("phone");
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try  {
            Pizzeria pizzeria = new Pizzeria();
            if (street.isEmpty() || house.isEmpty() || flat.isEmpty() || phone.isEmpty()) {
                throw new IllegalArgumentException("Заполните все поля");
            }
            pizzeria.makeOrder(street, house, flat, phone);
            response.setStatus(200);
            out.println("<html><head></head><body><div>Успешно оформили заказ</div></body></html>" );
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            out.println("<html><head></head><body><div>" + e.getMessage() + "</div></body></html>");
        } catch (Exception e) {
            response.setStatus(400);
            String error = "Не доставляем заказы по этому адресу";
            out.println("<html><head></head><body><div></div><div>" + error + "</div></body></html>");
        }
    }
}

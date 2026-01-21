package org.example.allPractice;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.servletStreetOrder.Pizzeria;

import java.io.IOException;
import java.io.PrintWriter;

//10. Написать сервлет который получает
// параметры улица, дом, квартира,
// номер телефона и делает заказ.
// в случае успешного заказа уведомляет
// об этом пользователя, в случае ошибки - подробности ошибки
public class OrderServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String flat = request.getParameter("flat");
        String phone = request.getParameter("phone");

        Pizzeria pizzeria = new Pizzeria();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        try {
            if (street == null || house == null || flat == null || phone == null) {
                throw new IllegalArgumentException("Заполните все данные");
            }
            pizzeria.makeOrder(street, house, flat, phone);

            response.setStatus(200);
            out.println("<html><head><meta charset='utf-8'></head><body><div>Заказ успешно сделан</div></body></html>");
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            out.println("<html><head><meta charset='utf-8'></head><body><div>" + e.getMessage() + "</div></body></html>");
        } catch (Exception e) {
            response.setStatus(400);
            out.println("<html><head><meta charset='utf-8'></head><body><div>" + e.getMessage() + "</div></body></html>");
        }
    }
}

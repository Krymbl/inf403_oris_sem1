package org.example.htmlMAX200About3xx;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class About3xx extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Стандартный редирект (302)
        response.sendRedirect("/new-page");

        // 2. Постоянный редирект (301) - ручная настройка
        response.setStatus(301);
        response.setHeader("Location", "/new-permanent-url");
        //return;


        // 3. Использование 307/308 (сохранение метода)
        response.setStatus(307); // Temporary Redirect с сохранением метода
        response.setHeader("Location", "/new-url");
        //return;

        //302 это временное перенос ресурса 301
        // это навсегда браузер может кешировать запрос
        // и временный перенос удачнее если в следущий раз
        // такой же запрос не будет редирекнутся и ты что-то поменяешь


        Cookie cookie = new Cookie("name", "123");
        // Если кука была создана с путем:
        cookie.setPath("/admin");

        // То для удаления нужно указать ТОТ ЖЕ путь:
        cookie.setPath("/admin"); // Обязательно!
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

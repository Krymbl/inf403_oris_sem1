package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/test")
public class FreemarkerTest extends HttpServlet {

    final static Logger logger = LogManager.getLogger(FreemarkerTest.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug(request.getServletPath());


//        request.setAttribute("surname", "Романов");
//        request.setAttribute("name", "Данил");
//
//        // Отрабатываем логику приложения
//        // Отрисовка страницы - передаем дальше request
//        request.getRequestDispatcher("/index.ftlh")
//                .forward(request, response);
    }
}
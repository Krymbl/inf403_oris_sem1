package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/truesession")
public class SessionServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(SessionServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ищется кука
        // ищется ассоциированная с ней сессия (объект HttpSession)
        // если не находится - создается новый (если не указан флаг false)


        HttpSession session = request.getSession(false);

        if (session == null) {
            session = request.getSession(true);
            String user = request.getParameter("user");
            if (user != null && !user.trim().isEmpty()) {
                request.setAttribute("username",  user);
                session.setAttribute("username", user);
            } else {
                request.setAttribute("username",  "Инкогнито");
                session.setAttribute("username", "Инкогнито");
            }
        } else {
            String username = (String) session.getAttribute("username");
            request.setAttribute("username", username != null ? username : "инкогнито");
        }


        request.setAttribute("sessionId", session.getId());

        request.getRequestDispatcher("/session.ftlh")
                .forward(request, response);
    }

}
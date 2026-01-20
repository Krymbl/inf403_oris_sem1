package org.example.chooseLanguage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/chooseLanguage")
public class ChooseLanguage extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String savedLanguage = "RU";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("saved_language")) {
                    savedLanguage = cookie.getValue();
                }
            }
        }
        req.setAttribute("savedLanguage", savedLanguage);
        req.getRequestDispatcher("page.ftlh").forward(req, resp);

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String language = req.getParameter("language");
        Cookie cookie = new Cookie("saved_language", language);
        cookie.setPath("/");
        cookie.setMaxAge(1000000);
        resp.addCookie(cookie);
        resp.sendRedirect("chooseLanguage");

    }
}

package org.example.allPractice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//14. Написать метод сервлета и шаблон,
// в котором пользователь выбирает
// язык (ru, en, da), сохранение выбора в cookie
@WebServlet("/language")
public class LanguageServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String language = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("language")) {
                    language = cookie.getValue();
                }
            }
        }
        req.setAttribute("language", language);
        req.getRequestDispatcher("language.ftlh").forward(req,resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("language", req.getParameter("language"));
        cookie.setPath("/");
        cookie.setMaxAge(1000000);
        resp.addCookie(cookie);
        req.getRequestDispatcher("language.ftlh").forward(req,resp);
    }

}

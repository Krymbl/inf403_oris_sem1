package org.example.allPractice;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.filterServlet.User;

import java.io.IOException;

//7. Написать фильтр для сервлетов,
// который проверяет, есть ли у
// пользователя роль администратора.

//11. Написать фильтр для проверки
// авторизации пользователя и наличия
// у него роли администратора,
// переадресация на JSP при статусах 401/403.
public class FilterAdmin implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(401);
            req.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equals(user.getRole())) {
            resp.setStatus(403);
            req.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}

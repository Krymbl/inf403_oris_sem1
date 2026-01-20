package org.example.filterAuthAdmin401;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.filterServlet.User;

import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,  FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(401);
            req.getRequestDispatcher("error.jsp").forward(req,resp);
            return;
        }

        User user = (User) session.getAttribute("user");

        if (!"ADMIN".equals(user.getRole())) {
            resp.setStatus(403);
            req.getRequestDispatcher("error.jsp").forward(req,resp);
            return;
        }

        chain.doFilter(request, response);
    }
}
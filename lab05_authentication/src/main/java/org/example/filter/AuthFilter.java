package org.example.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controller.LoginServlet;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    final static Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession(false);

        logger.debug(((HttpServletRequest)request).getServletPath());

        if (!checkExcluded(((HttpServletRequest)request).getServletPath()) &&
                (session == null || session.getAttribute("user") == null)) {
            //request.getRequestDispatcher("/login").forward(request, response);
            logger.debug("редирект");
            ((HttpServletResponse) response).sendRedirect("/lab05/login");
        } else {
            logger.debug("пропускаем");
            filterChain.doFilter(request, response);
        }
    }

    private boolean checkExcluded(String resource) {
        return resource.contains("/login") ||
                resource.contains("/usercheck");
    }
}
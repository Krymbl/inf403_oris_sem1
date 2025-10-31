package org.example;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/template/test")
public class TemplateTest extends HttpServlet {

    final static Logger logger = LogManager.getLogger(TemplateTest.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug(request.getServletPath());

        try (Writer writer = response.getWriter()) {
            new TemplateHandler().handle("test.html", request.getParameterMap(), writer);
        }
    }

    public void doPost(HttpServletRequest servletRequest,
                       HttpServletResponse servletResponse) throws ServletException, IOException {
        doGet(servletRequest, servletResponse);
    }
}
package org.example;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet("*.html")
public class TemplateEngine extends HttpServlet {

    final static Logger logger = LogManager.getLogger(TemplateEngine.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug(request.getServletPath());

        Map<String, String> params = new HashMap<>();
        Iterator<String> enumeration = request.getAttributeNames().asIterator();
        while (enumeration.hasNext()) {
            String attributeName = enumeration.next();
            String attributeValue = (String) request.getAttribute(attributeName);
            logger.debug(attributeName + " : " + attributeValue);
            params.put(attributeName, attributeValue);

        }

        String fileName = request.getServletPath().substring(1);

        //Получаем ClassLoader для загрузки ресурсов
        //ClassLoader знает где находится classpath
        //getResourceAsStream ищет файл в classpath в папке templates/
        try (InputStream is = TemplateEngine.class.getClassLoader()
                .getResourceAsStream("templates/" + fileName)) {

            if (is == null) {
                response.sendError(404, "Template not found: " + fileName);
                return;
            }

            String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            response.getWriter().write(template);
        }
    }
}
package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateHandler {
    final static Logger logger = LogManager.getLogger(TemplateHandler.class);
    public void handle(String templateName, Map<String, String[]> map, Writer writer) {
        // 1. Найти файл по имени templateName
        // 2. Прочитать файл в строку
        // 3. Найти в файле ${param_name} и заменить на значения параметра
        // 4. Передать строку во writer

//        getClass() - получаем объект Class для TemplateHandler
//        getResourceAsStream() - ищет resources в classpath и открывает как InputStream
        try (InputStream is = getClass().getResourceAsStream("/templates/" + templateName)) {
            if (is == null) {
                throw new RuntimeException("Шаблон не найден " + templateName);
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String placeholder = "${" + entry.getKey() + "}";
                content = content.replace(placeholder, entry.getValue()[0]);
            }

            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("ошибка при обработке шаблона " + templateName, e);
        }
    }
}
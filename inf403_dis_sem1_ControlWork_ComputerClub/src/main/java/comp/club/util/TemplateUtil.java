package comp.club.util;

import comp.club.model.Computer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TemplateUtil {

    public static String loadTemplate(String templateName) throws IOException {
        try (InputStream inputStream = TemplateUtil.class.getClassLoader()
                .getResourceAsStream("templates/" + templateName)) {

            if (inputStream == null) {
                throw new IOException("Шаблон не найден: " + templateName);
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static String renderIndex() throws IOException {
        return loadTemplate("index.html");
    }

    public static String renderAddComputer() throws IOException {
        return loadTemplate("addComputer.html");
    }

    public static String renderShowOneTemplate(Computer computer) throws IOException {
        String template = loadTemplate("showOne.html");
        String formContent = generateFormContent(computer);
        return template.replace("<!-- ФОРМА -->", formContent);
    }

    public static String renderShowAllTemplate(List<Computer> computers) throws IOException {
        String template = loadTemplate("showAll.html");
        String tableContent = generateTableContent(computers);
        return template.replace("<!-- ФОРМА -->", tableContent);
    }

    private static String generateTableContent(List<Computer> computers) {
        if (computers.isEmpty()) {
            return "<p>Компьютеры не найдены</p>";
        }

        StringBuilder table = new StringBuilder();
        table.append("<table border='1' cellpadding='8'>");
        table.append("<tr><th>ID</th><th>Название</th><th>Процессор</th><th>Оперативная память (GB)</th><th>Видеокарта</th><th>Доступно</th><th>Цена/час</th><th>Действие</th></tr>");

        for (Computer computer : computers) {
            table.append("<tr>");
            table.append("<td>").append(computer.getId()).append("</td>");
            table.append("<td>").append(computer.getName()).append("</td>");
            table.append("<td>").append(computer.getProcessor()).append("</td>");
            table.append("<td>").append(computer.getRam()).append("</td>");
            table.append("<td>").append(computer.getVideoCard()).append("</td>");
            table.append("<td>").append(computer.getIsAvailable() ? "Да" : "Нет").append("</td>");
            table.append("<td>").append(computer.getPricePerHour()).append("</td>");
            table.append("<td><a href='showone?id=").append(computer.getId()).append("'>Изменить</a></td>");
            table.append("</tr>");
        }

        table.append("</table>");
        return table.toString();
    }

    private static String generateFormContent(Computer computer) {
        StringBuilder form = new StringBuilder();
        form.append("<form method='post' action='showone'>");
        form.append("<input type='hidden' name='id' value='").append(computer.getId()).append("'>");
        form.append("Название: <input type='text' name='name' value='").append(computer.getName()).append("' required><br><br>");
        form.append("Процессор: <input type='text' name='processor' value='").append(computer.getProcessor()).append("' required><br><br>");
        form.append("Оперативная память (GB): <input type='number' name='ram' value='").append(computer.getRam()).append("' required><br><br>");
        form.append("Видеокарта: <input type='text' name='videoCard' value='").append(computer.getVideoCard()).append("' required><br><br>");
        form.append("Цена за час: <input type='number' step='0.01' name='pricePerHour' value='").append(computer.getPricePerHour()).append("' required><br><br>");
        form.append("Доступно: <input type='checkbox' name='isAvailable' ").append(computer.getIsAvailable() ? "checked" : "").append("><br><br>");
        form.append("<input type='submit' value='Сохранить изменения'>");
        form.append("</form>");
        return form.toString();
    }
}
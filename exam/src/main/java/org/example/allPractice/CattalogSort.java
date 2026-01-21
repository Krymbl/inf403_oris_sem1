package org.example.allPractice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Написать метод сервлета для обработки запроса
// на получение страницы каталога товаров с параметрами:
// номер страницы, тип сортировки (по новизне/по цене),
// максимальная цена товара
public class CattalogSort extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String numberPage = req.getParameter("numberPage");
        String typeOfSort = req.getParameter("typeOfSort");
        String maxPrice = req.getParameter("maxPrice");

        PsevdoCatalogService catalog = new PsevdoCatalogService();
        List<PsevdoProduct> newCatalog = catalog.findAllUpdateProducts(numberPage, typeOfSort, maxPrice);

        try(PrintWriter writer = resp.getWriter()) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.setStatus(200);
            writer.println("<html><head><meta charset='UTF-8'></head><body><div><table><tr><td>Название</td><td>Цена</td><td>Дата</td></tr>");
            for (PsevdoProduct product : newCatalog) {
                writer.println("<tr><td>product.getName()</td><td>product.getPrice()</td><td>product.getDate()</td></tr>");
            }
            writer.println("</table></div></body></html>");
        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }



    }
}

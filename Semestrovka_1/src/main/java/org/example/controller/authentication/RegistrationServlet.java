package org.example.controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public static UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/registration.ftlh").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPhoneNumber(request.getParameter("phone_number"));
        user.setHashPassword(request.getParameter("password"));
        user.setLastName(request.getParameter("lastname"));
        user.setFirstName(request.getParameter("firstname"));
        user.setGender(request.getParameter("gender"));
        //user.setBirthday(request.getParameter("birthday"));
        user.setEmail(request.getParameter("email"));
        
        try {
            userService.save(user);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/index.ftlh").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("/registration.ftlh")
                    .forward(request, response);
        }

    }
}

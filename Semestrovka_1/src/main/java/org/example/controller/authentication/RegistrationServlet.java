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
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    public static UserService userService;
    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/authentication/registration.ftlh").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            Date birthDate = null;
            String birthdayParam = request.getParameter("birthday");
            if (birthdayParam != null && !birthdayParam.trim().isEmpty()) {
                birthDate = Date.valueOf(birthdayParam);
            }
            String gender = request.getParameter("gender");

            User user = new User();
            user.setUsername(username);
            user.setHashPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setBirthday(birthDate);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setGender(gender);


            userService.save(user);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            request.setAttribute("user", user);

            response.sendRedirect("index?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("registration?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}

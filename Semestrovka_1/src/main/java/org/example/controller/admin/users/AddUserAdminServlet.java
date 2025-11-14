package org.example.controller.admin.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/admin/addUser")
public class AddUserAdminServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/users/addUser.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String role = request.getParameter("role");
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
            user.setRole(role);
            user.setBirthday(birthDate);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setGender(gender);


            userService.save(user);

            response.sendRedirect("users?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addUser?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }


}

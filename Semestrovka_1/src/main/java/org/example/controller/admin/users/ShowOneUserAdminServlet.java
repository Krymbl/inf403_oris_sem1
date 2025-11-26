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
import java.sql.SQLException;

@WebServlet("/admin/userShowOne")
public class ShowOneUserAdminServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        User user = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                user = userService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (user == null) {
            response.sendRedirect("users?error=not_found");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/admin/users/showOneUser.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser;
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            currentUser = userService.getById(id);

            String username = request.getParameter("username");
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

            if (!currentUser.getUsername().equals(username)) {
                userService.updateUsername(currentUser.getUsername(), username);
            }
            if (!currentUser.getPhoneNumber().equals(phoneNumber)) {
                userService.updatePhoneNumber(username, phoneNumber);
            }
            if (!currentUser.getEmail().equals(email)) {
                userService.updateEmail(username, email);
            }

            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRole(role);
            user.setBirthday(birthDate);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setGender(gender);


            userService.update(user);

            response.sendRedirect("users?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("userShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
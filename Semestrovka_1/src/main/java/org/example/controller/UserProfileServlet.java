package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("../login");
            return;
        }

        try {
            User currentUser = userService.getById(user.getId());
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/profile.ftlh").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("../login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        System.out.println(currentUser.getUsername());

        if (currentUser == null) {
            response.sendRedirect("../login");
            return;
        }

        try {
            String username = request.getParameter("username");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String gender = request.getParameter("gender");
            String role = request.getParameter("role");
            Date birthday = null;

            String birthdayParam = request.getParameter("birthday");
            if (birthdayParam != null && !birthdayParam.trim().isEmpty()) {
                birthday = Date.valueOf(birthdayParam);
            }

            User user = new User();
            user.setId(currentUser.getId());
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setGender(gender);
            user.setRole(role);
            user.setBirthday(birthday);

            if (!currentUser.getUsername().equals(username)) {
                userService.updateUsername(currentUser.getUsername(), username);
            }
            if (!currentUser.getPhoneNumber().equals(phoneNumber)) {
                userService.updatePhoneNumber(username, phoneNumber);
            }
            if (!currentUser.getEmail().equals(email)) {
                userService.updateEmail(username, email);
            }

            userService.update(user);

            User updatedUser = userService.getById(currentUser.getId());
            session.setAttribute("user", updatedUser);

            session.removeAttribute("error");
            session.setAttribute("success", true);
            response.sendRedirect("profile?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            session.removeAttribute("success");
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("profile?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
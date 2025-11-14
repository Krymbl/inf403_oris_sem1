package org.example.controller.admin.reservations;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Reservation;
import org.example.service.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/admin/reservationShowOne")
public class ShowOneReservationAdminServlet extends HttpServlet {
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        reservationService = new ReservationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        Reservation reservation = null;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                reservation = reservationService.getById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (reservation == null) {
            response.sendRedirect("reservations?error=not_found");
            return;
        }

        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("/admin/reservations/showOneReservation.ftlh").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Long userId =  Long.parseLong(request.getParameter("userId"));
            Long gamePlaceId =  Long.parseLong(request.getParameter("gamePlaceId"));
            String personPhoneNumber =  request.getParameter("personPhoneNumber");
            Long price = Long.parseLong(request.getParameter("totalPrice"));
            Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime").replace("T", " ") + ":00");
            Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime").replace("T", " ") + ":00");
            String status = request.getParameter("status");

            Reservation reservation = new Reservation();
            reservation.setId(id);
            reservation.setUserId(userId);
            reservation.setGamePlaceId(gamePlaceId);
            reservation.setPersonPhoneNumber(personPhoneNumber);
            reservation.setTotalPrice(price);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setStatus(status);

            reservationService.update(reservation);
            response.sendRedirect("reservations?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reservationShowOne?id=" + request.getParameter("id") + "&error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
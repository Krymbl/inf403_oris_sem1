package org.example.controller.admin.reservations;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Reservation;
import org.example.service.ReservationService;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/admin/addReservation")
public class AddReservationAdminServlet extends HttpServlet {
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        reservationService = new ReservationService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/reservations/addReservation.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(request.getParameter("userId"));
            String personPhoneNumber = request.getParameter("personPhoneNumber");
            Long gamePlaceId = Long.parseLong(request.getParameter("gamePlaceId"));
            Long totalPrice = Long.parseLong(request.getParameter("totalPrice"));

            String startTimeStr = request.getParameter("startTime").replace("T", " ") + ":00";
            String endTimeStr = request.getParameter("endTime").replace("T", " ") + ":00";
            Timestamp startTime = Timestamp.valueOf(startTimeStr);
            Timestamp endTime = Timestamp.valueOf(endTimeStr);

            String status = request.getParameter("status");

            Reservation reservation = new Reservation();
            reservation.setUserId(userId);
            reservation.setPersonPhoneNumber(personPhoneNumber);
            reservation.setGamePlaceId(gamePlaceId);
            reservation.setTotalPrice(totalPrice);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setStatus(status);

            reservationService.save(reservation);
            response.sendRedirect("reservations?success=created");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addReservation?error=" +
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
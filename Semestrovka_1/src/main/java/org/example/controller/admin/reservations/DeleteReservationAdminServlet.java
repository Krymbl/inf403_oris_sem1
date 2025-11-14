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

@WebServlet("/admin/deleteReservation")
public class DeleteReservationAdminServlet extends HttpServlet {
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        reservationService = new ReservationService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("reservations?error=" +
                    java.net.URLEncoder.encode("ID бронирования не указан", "UTF-8"));
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            Reservation reservation = new Reservation();
            reservation.setId(id);

            reservationService.delete(reservation);

            response.sendRedirect("reservations?success=deleted");

        } catch (NumberFormatException e) {
            response.sendRedirect("reservations?error=" +
                    java.net.URLEncoder.encode("Неверный формат ID", "UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("reservations?error=" +
                    java.net.URLEncoder.encode("Ошибка при удалении бронирования: " + e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("reservations?error=" +
                    java.net.URLEncoder.encode("Ошибка: " + e.getMessage(), "UTF-8"));
        }
    }
}
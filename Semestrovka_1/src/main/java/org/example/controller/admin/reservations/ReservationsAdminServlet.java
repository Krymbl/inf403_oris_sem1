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
import java.util.List;

@WebServlet("/admin/reservations")
public class ReservationsAdminServlet extends HttpServlet {
    private ReservationService reservationService;

    public void init() throws ServletException {
        reservationService = new ReservationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Reservation> reservations = reservationService.getAll();
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("/admin/reservations/showAllReservations.ftlh").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ошибка при загрузке списка бронирований");
        }
    }
}
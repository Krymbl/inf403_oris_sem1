package org.example.repository;

import org.example.model.Route;
import org.example.service.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteRepository {

    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();

        String sql = "SELECT * from bookings.routes";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.getResultSet()) {
            while (resultSet.next()) {
                Route route = new Route();
                route.setRouteNo(resultSet.getString("route_no"));
                route.setValidity(resultSet.getString("validity"));
                route.setDepartureAirport(resultSet.getString("departure_airport"));
                route.setArrivalAirport(resultSet.getString("arrival_airport"));
                route.setAirlineCode(resultSet.getString("airline_code"));
                Integer[] daysArray = (Integer[]) resultSet.getArray("days_of_week").getArray();
                route.setDaysOfWeek(Arrays.asList(daysArray));
                route.setScheduledTime(resultSet.getTime(resultSet.getString("scheduled_time")));
                route.setDuration(resultSet.getString("duration"));
                routes.add(route);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routes;
    }
}

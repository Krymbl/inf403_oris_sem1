package org.example.repository;

import org.example.model.Flight;
import org.example.service.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightRepository {

    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        String sql = "select * from bookings.flights";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.getResultSet()) {

            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setId(resultSet.getInt("id"));
                flight.setRouteNo(resultSet.getString("route_no"));
                flight.setStatus(resultSet.getString("status"));
                flight.setScheduledDeparture(resultSet.getTimestamp("scheduled_departure"));
                flight.setScheduledArrival(resultSet.getTimestamp("scheduled_arrival"));
                flight.setActualDeparture(resultSet.getTimestamp("actual_departure"));
                flight.setActualArrival(resultSet.getTimestamp("actual_arrival"));
                flights.add(flight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

}

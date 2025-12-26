package org.example.repository;

import org.example.model.Airplane;
import org.example.model.FlightInfo;
import org.example.service.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FlightInfoRepository {

    public List<FlightInfo> findAll(String airportCode, String date, String typeTable) {
        List<FlightInfo> flightsInfo = new ArrayList<>();

        LocalTime currentTime = LocalTime.now();

        String sql = typeTable.equals("arrival") ?
                "SELECT f.flight_id, r.route_no, f.scheduled_departure, " +
                        "f.scheduled_arrival, f.status, a.airport_name->>'ru' as airport_name, " +
                        "a.city->> 'ru' as city " +
                        "FROM bookings.flights f " +
                        "JOIN bookings.routes r ON f.route_no = r.route_no " +
                        "JOIN bookings.airports_data a ON r.departure_airport = a.airport_code " +
                        "WHERE r.arrival_airport = ? AND DATE(f.scheduled_arrival) = ? " +
                        "AND f.scheduled_arrival::time >= ? " +
                        "ORDER BY f.scheduled_arrival" :

                "SELECT f.flight_id, r.route_no, f.scheduled_departure, " +
                        "f.scheduled_arrival, f.status, a.airport_name->>'ru' as airport_name, " +
                        "a.city->> 'ru' as city " +
                        "FROM bookings.flights f " +
                        "JOIN bookings.routes r ON f.route_no = r.route_no " +
                        "JOIN bookings.airports_data a ON r.arrival_airport = a.airport_code " +
                        "WHERE r.departure_airport = ? AND DATE(f.scheduled_departure) = ? " +
                        "AND f.scheduled_departure::time >= ? " +
                        "ORDER BY f.scheduled_departure";



        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, airportCode);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            preparedStatement.setTime(3, java.sql.Time.valueOf(currentTime));


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FlightInfo flightInfo = new FlightInfo();
                flightInfo.setFlightId(resultSet.getInt("flight_id"));
                flightInfo.setRouteNo(resultSet.getString("route_no"));
                flightInfo.setScheduledDeparture(resultSet.getTimestamp("scheduled_departure").toLocalDateTime());
                flightInfo.setScheduledArrival(resultSet.getTimestamp("scheduled_arrival").toLocalDateTime());
                flightInfo.setStatus(resultSet.getString("status"));
                flightInfo.setCity(resultSet.getString("city"));
                flightInfo.setAirportName(resultSet.getString("airport_name"));
                flightsInfo.add(flightInfo);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flightsInfo;
    }
}

package org.example.repository;

import org.example.model.Airport;
import org.example.service.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportRepository {

    public List<Airport> findAll () {
        List<Airport> airports = new ArrayList<>();
        String sql = "select airport_code, " +
                " airport_name -> 'ru' as airport_name_ru, " +
                " city, country, coordinates, timezone " +
                " from bookings.airports_data order by airport_name_ru";

        try (Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Airport airport = new Airport();
                airport.setCode(resultSet.getString("airport_code"));
                airport.setName(resultSet.getString("airport_name_ru"));
                airport.setCity(resultSet.getString("city"));
                airport.setCountry(resultSet.getString("country"));
                airport.setCoordinates(resultSet.getString("coordinates"));
                airport.setTimezone(resultSet.getString("timezone"));
                airports.add(airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }
}

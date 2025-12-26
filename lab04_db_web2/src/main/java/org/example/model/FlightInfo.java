package org.example.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FlightInfo {
    private Integer flightId;
    private String routeNo;
    private LocalDateTime scheduledDeparture;
    private LocalDateTime scheduledArrival;
    private String status;
    private String city;
    private String airportName;

    public FlightInfo() {}

    public FlightInfo(Integer flightId, String routeNo,  LocalDateTime scheduledDeparture,  LocalDateTime scheduledArrival, String status, String city, String airportName) {
        this.flightId = flightId;
        this.routeNo = routeNo;
        this.scheduledDeparture = scheduledDeparture;
        this.scheduledArrival = scheduledArrival;
        this.status = status;
        this.city = city;
        this.airportName = airportName;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public LocalDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture( LocalDateTime scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival( LocalDateTime scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}

package org.example.model;

import java.sql.Time;
import java.util.List;

public class Route {
    private String routeNo;
    private String validity;
    private String departureAirport;
    private String arrivalAirport;
    private String airlineCode;
    private List<Integer> daysOfWeek;
    private Time scheduledTime;
    private String duration;

    public Route() {}

    public Route(String routeNo, String validity, String departureAirport, String arrivalAirport, String airlineCode, List<Integer> daysOfWeek, Time scheduledTime, String duration) {
        this.routeNo = routeNo;
        this.validity = validity;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.airlineCode = airlineCode;
        this.daysOfWeek = daysOfWeek;
        this.scheduledTime = scheduledTime;
        this.duration = duration;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Time getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Time scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

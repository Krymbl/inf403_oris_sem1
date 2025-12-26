package org.example.model;

import java.sql.Timestamp;

public class Flight {
    private Integer id;
    private String routeNo;
    private String status;
    private Timestamp scheduledDeparture;
    private Timestamp scheduledArrival;
    private Timestamp actualDeparture;
    private Timestamp actualArrival;

    public Flight() {
    }

    public Flight(Integer id, String routeNo, String status, Timestamp scheduledDeparture, Timestamp scheduledArrival, Timestamp actualDeparture, Timestamp actualArrival) {
        this.id = id;
        this.routeNo = routeNo;
        this.status = status;
        this.scheduledDeparture = scheduledDeparture;
        this.scheduledArrival = scheduledArrival;
        this.actualDeparture = actualDeparture;
        this.actualArrival = actualArrival;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(Timestamp scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public Timestamp getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(Timestamp scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public Timestamp getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(Timestamp actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public Timestamp getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(Timestamp actualArrival) {
        this.actualArrival = actualArrival;
    }
}

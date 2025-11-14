package org.example.model.gamingstations;

import org.example.model.devices.Computer;

import java.sql.Timestamp;

public class ComputerPlace {
    private Long id;
    private Long gamingPlaceId;
    private String keyboard;
    private String mouse;
    private String headset;
    private String monitor;
    private String chair;
    private Computer computer;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ComputerPlace() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamingPlaceId() {
        return gamingPlaceId;
    }
    public void setGamingPlaceId(Long gamingPlaceId) {
        this.gamingPlaceId = gamingPlaceId;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getHeadset() {
        return headset;
    }

    public void setHeadset(String headset) {
        this.headset = headset;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
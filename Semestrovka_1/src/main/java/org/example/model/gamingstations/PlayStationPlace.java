package org.example.model.gamingstations;

import org.example.model.devices.PlayStation;

import java.sql.Timestamp;

public class PlayStationPlace {
    private Long id;
    private Long gamingPlaceId;
    private String tv;
    private int maxPlayers;
    private PlayStation playStation;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public PlayStationPlace() {}

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

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public PlayStation getPlayStation() {
        return playStation;
    }

    public void setPlayStation(PlayStation playStation) {
        this.playStation = playStation;
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
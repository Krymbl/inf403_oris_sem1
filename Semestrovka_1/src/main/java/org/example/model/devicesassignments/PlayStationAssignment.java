package org.example.model.devicesassignments;

import java.sql.Timestamp;

public class PlayStationAssignment {
    private Long id;
    private Long playStationId;
    private Long playStationPlaceId;
    private Timestamp assignedAt;
    private Timestamp unassignedAt;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public PlayStationAssignment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayStationId() { return playStationId; }
    public void setPlayStationId(Long playStationId) { this.playStationId = playStationId; }

    public Long getPlayStationPlaceId() { return playStationPlaceId; }
    public void setPlayStationPlaceId(Long playStationPlaceId) { this.playStationPlaceId = playStationPlaceId; }

    public Timestamp getAssignedAt() { return assignedAt; }
    public void setAssignedAt(Timestamp assignedAt) { this.assignedAt = assignedAt; }

    public Timestamp getUnassignedAt() { return unassignedAt; }
    public void setUnassignedAt(Timestamp unassignedAt) { this.unassignedAt = unassignedAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
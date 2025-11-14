package org.example.model.devicesassignments;

import java.sql.Timestamp;

public class ComputerAssignment {
    private Long id;
    private Long computerId;
    private Long computerPlaceId;
    private Timestamp assignedAt;
    private Timestamp unassignedAt;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ComputerAssignment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getComputerId() { return computerId; }
    public void setComputerId(Long computerId) { this.computerId = computerId; }

    public Long getComputerPlaceId() { return computerPlaceId; }
    public void setComputerPlaceId(Long computerPlaceId) { this.computerPlaceId = computerPlaceId; }

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
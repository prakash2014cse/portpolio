package com.monitoring.api.model;

import java.time.Instant;

public class Incident {
    private Long id;
    private Long monitorId;
    private String monitorName;
    private Severity severity;
    private String reason;
    private String detector;
    private String details;
    private Instant openedAt;
    private Instant resolvedAt;

    public Incident() {
    }

    public Incident(Long id, Long monitorId, String monitorName, Severity severity, String reason, String detector, String details) {
        this.id = id;
        this.monitorId = monitorId;
        this.monitorName = monitorName;
        this.severity = severity;
        this.reason = reason;
        this.detector = detector;
        this.details = details;
        this.openedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMonitorId() { return monitorId; }
    public void setMonitorId(Long monitorId) { this.monitorId = monitorId; }
    public String getMonitorName() { return monitorName; }
    public void setMonitorName(String monitorName) { this.monitorName = monitorName; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getDetector() { return detector; }
    public void setDetector(String detector) { this.detector = detector; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public Instant getOpenedAt() { return openedAt; }
    public void setOpenedAt(Instant openedAt) { this.openedAt = openedAt; }
    public Instant getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Instant resolvedAt) { this.resolvedAt = resolvedAt; }
}

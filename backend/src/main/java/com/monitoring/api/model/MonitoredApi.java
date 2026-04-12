package com.monitoring.api.model;

import java.time.Instant;

public class MonitoredApi {
    private Long id;
    private String name;
    private String environment;
    private String url;
    private int expectedTimeoutMs;
    private MonitorStatus status;
    private int responseTimeMs;
    private String lastError;
    private Instant lastCheckedAt;

    public MonitoredApi() {
    }

    public MonitoredApi(Long id, String name, String environment, String url, int expectedTimeoutMs, MonitorStatus status) {
        this.id = id;
        this.name = name;
        this.environment = environment;
        this.url = url;
        this.expectedTimeoutMs = expectedTimeoutMs;
        this.status = status;
        this.lastCheckedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public int getExpectedTimeoutMs() { return expectedTimeoutMs; }
    public void setExpectedTimeoutMs(int expectedTimeoutMs) { this.expectedTimeoutMs = expectedTimeoutMs; }
    public MonitorStatus getStatus() { return status; }
    public void setStatus(MonitorStatus status) { this.status = status; }
    public int getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(int responseTimeMs) { this.responseTimeMs = responseTimeMs; }
    public String getLastError() { return lastError; }
    public void setLastError(String lastError) { this.lastError = lastError; }
    public Instant getLastCheckedAt() { return lastCheckedAt; }
    public void setLastCheckedAt(Instant lastCheckedAt) { this.lastCheckedAt = lastCheckedAt; }
}

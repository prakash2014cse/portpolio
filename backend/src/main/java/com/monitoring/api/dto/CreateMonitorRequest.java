package com.monitoring.api.dto;

import com.monitoring.api.model.AppServerType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMonitorRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String environment;

    @NotBlank
    private String url;

    private String contextPath;

    @NotNull
    private AppServerType serverType = AppServerType.TOMCAT;

    @Min(100)
    private int expectedTimeoutMs = 2000;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getContextPath() { return contextPath; }
    public void setContextPath(String contextPath) { this.contextPath = contextPath; }
    public AppServerType getServerType() { return serverType; }
    public void setServerType(AppServerType serverType) { this.serverType = serverType; }
    public int getExpectedTimeoutMs() { return expectedTimeoutMs; }
    public void setExpectedTimeoutMs(int expectedTimeoutMs) { this.expectedTimeoutMs = expectedTimeoutMs; }
}

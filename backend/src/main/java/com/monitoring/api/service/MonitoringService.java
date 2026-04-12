package com.monitoring.api.service;

import com.monitoring.api.model.Incident;
import com.monitoring.api.model.MonitoredApi;
import com.monitoring.api.model.MonitorStatus;
import com.monitoring.api.model.Severity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MonitoringService {

    private final Map<Long, MonitoredApi> monitors = new ConcurrentHashMap<>();
    private final Map<Long, Incident> incidents = new ConcurrentHashMap<>();
    private final AtomicLong incidentId = new AtomicLong(1000);
    private final Random random = new Random();

    public MonitoringService() {
        MonitoredApi smsNode = new MonitoredApi(1L, "Direct Prod Single SMS Node2", "Production", "https://prod-sms-node2.internal/api/health", 2000, MonitorStatus.DOWN);
        smsNode.setLastError("Connection refused");
        smsNode.setResponseTimeMs(0);
        monitors.put(smsNode.getId(), smsNode);

        monitors.put(2L, new MonitoredApi(2L, "User Profile API", "Production", "https://prod-users.internal/api/health", 1500, MonitorStatus.UP));
        monitors.put(3L, new MonitoredApi(3L, "Payment Gateway API", "Production", "https://prod-pay.internal/api/health", 1200, MonitorStatus.SLOW));

        Incident initial = new Incident(
                incidentId.getAndIncrement(),
                1L,
                "Direct Prod Single SMS Node2",
                Severity.CRITICAL,
                "Availability issue",
                "Applications Manager",
                "Resource unreachable with 'Connection refused'. Service is not available."
        );
        incidents.put(initial.getId(), initial);
    }

    public List<MonitoredApi> getAllMonitors() {
        return monitors.values().stream()
                .sorted(Comparator.comparing(MonitoredApi::getId))
                .toList();
    }

    public List<Incident> getOpenIncidents() {
        return incidents.values().stream()
                .filter(i -> i.getResolvedAt() == null)
                .sorted(Comparator.comparing(Incident::getOpenedAt).reversed())
                .toList();
    }

    public String generateAlertEmail(Long monitorId) {
        MonitoredApi monitoredApi = monitors.get(monitorId);
        if (monitoredApi == null) {
            return "No monitor found for id=" + monitorId;
        }
        return "Subject: ALERT - " + monitoredApi.getName() + " is " + monitoredApi.getStatus() + "\n\n"
                + "Automated monitoring has detected an outage.\n"
                + "- Resource: " + monitoredApi.getName() + "\n"
                + "- Environment: " + monitoredApi.getEnvironment() + "\n"
                + "- Status: " + monitoredApi.getStatus() + "\n"
                + "- Reason: " + monitoredApi.getLastError() + "\n"
                + "- Detector: Applications Manager\n"
                + "- Last Checked: " + monitoredApi.getLastCheckedAt() + "\n\n"
                + "Action required: Verify service process, network access, and restart if needed.";
    }

    @Scheduled(fixedDelay = 60000)
    public void simulateHealthChecks() {
        for (MonitoredApi monitor : monitors.values()) {
            int chance = random.nextInt(100);
            monitor.setLastCheckedAt(Instant.now());

            if (chance < 70) {
                int response = 100 + random.nextInt(900);
                monitor.setResponseTimeMs(response);
                monitor.setStatus(response > monitor.getExpectedTimeoutMs() ? MonitorStatus.SLOW : MonitorStatus.UP);
                monitor.setLastError(null);
            } else if (chance < 85) {
                monitor.setStatus(MonitorStatus.HANGING);
                monitor.setResponseTimeMs(monitor.getExpectedTimeoutMs() * 2);
                monitor.setLastError("Request timed out");
                createIncident(monitor, Severity.WARNING, "Performance degradation", "Endpoint is hanging / timing out");
            } else {
                monitor.setStatus(MonitorStatus.DOWN);
                monitor.setResponseTimeMs(0);
                monitor.setLastError("Connection refused");
                createIncident(monitor, Severity.CRITICAL, "Availability issue", "Resource unreachable with 'Connection refused'.");
            }
        }
    }

    private void createIncident(MonitoredApi monitor, Severity severity, String reason, String detail) {
        boolean existingOpen = incidents.values().stream()
                .anyMatch(i -> i.getMonitorId().equals(monitor.getId()) && i.getResolvedAt() == null && i.getReason().equals(reason));
        if (!existingOpen) {
            Incident incident = new Incident(
                    incidentId.getAndIncrement(),
                    monitor.getId(),
                    monitor.getName(),
                    severity,
                    reason,
                    "Applications Manager",
                    detail
            );
            incidents.put(incident.getId(), incident);
        }
    }
}

package com.monitoring.api.controller;

import com.monitoring.api.dto.CreateMonitorRequest;
import com.monitoring.api.model.Incident;
import com.monitoring.api.model.MonitoredApi;
import com.monitoring.api.service.MonitoringService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/monitors")
    public ResponseEntity<List<MonitoredApi>> monitors() {
        return ResponseEntity.ok(monitoringService.getAllMonitors());
    }

    @PostMapping("/monitors")
    public ResponseEntity<MonitoredApi> createMonitor(@Valid @RequestBody CreateMonitorRequest request) {
        return ResponseEntity.ok(monitoringService.addMonitor(request));
    }

    @GetMapping("/incidents/open")
    public ResponseEntity<List<Incident>> incidents() {
        return ResponseEntity.ok(monitoringService.getOpenIncidents());
    }

    @GetMapping("/alerts/email-preview/{monitorId}")
    public ResponseEntity<Map<String, String>> emailPreview(@PathVariable Long monitorId) {
        return ResponseEntity.ok(Map.of("content", monitoringService.generateAlertEmail(monitorId)));
    }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MonitoringApiService } from './services/monitoring-api.service';
import { CreateMonitorRequest, Incident, Monitor, ServerType } from './models/monitor.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  monitors: Monitor[] = [];
  incidents: Incident[] = [];
  alertEmail = 'Loading alert preview...';

  newMonitor: CreateMonitorRequest = {
    name: '',
    environment: 'Production',
    url: '',
    contextPath: '',
    serverType: 'TOMCAT',
    expectedTimeoutMs: 2000
  };

  serverTypes: ServerType[] = ['TOMCAT', 'SPRING_BOOT', 'OTHER'];

  constructor(private readonly monitoringApi: MonitoringApiService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.monitoringApi.getMonitors().subscribe((data) => {
      this.monitors = data;
      const critical = this.monitors.find((m) => m.name === 'Direct Prod Single SMS Node2');
      if (critical) {
        this.loadEmail(critical.id);
      }
    });

    this.monitoringApi.getOpenIncidents().subscribe((data) => {
      this.incidents = data;
    });
  }

  addMonitor(): void {
    if (!this.newMonitor.name || !this.newMonitor.url) {
      return;
    }

    this.monitoringApi.addMonitor(this.newMonitor).subscribe(() => {
      this.newMonitor = {
        name: '',
        environment: 'Production',
        url: '',
        contextPath: '',
        serverType: 'TOMCAT',
        expectedTimeoutMs: 2000
      };
      this.refresh();
    });
  }

  statusClass(status: Monitor['status']): string {
    return `badge-${status.toLowerCase()}`;
  }

  private loadEmail(monitorId: number): void {
    this.monitoringApi.getAlertEmailPreview(monitorId).subscribe((data) => {
      this.alertEmail = data.content;
    });
  }
}

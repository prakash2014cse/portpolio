import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MonitoringApiService } from './services/monitoring-api.service';
import { Incident, Monitor } from './models/monitor.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  monitors: Monitor[] = [];
  incidents: Incident[] = [];
  alertEmail = 'Loading alert preview...';

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

  statusClass(status: Monitor['status']): string {
    return `badge-${status.toLowerCase()}`;
  }

  private loadEmail(monitorId: number): void {
    this.monitoringApi.getAlertEmailPreview(monitorId).subscribe((data) => {
      this.alertEmail = data.content;
    });
  }
}

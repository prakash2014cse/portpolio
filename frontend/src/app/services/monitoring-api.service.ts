import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Incident, Monitor } from '../models/monitor.model';

@Injectable({ providedIn: 'root' })
export class MonitoringApiService {
  constructor(private readonly http: HttpClient) {}

  getMonitors(): Observable<Monitor[]> {
    return this.http.get<Monitor[]>(`${environment.apiBaseUrl}/monitors`);
  }

  getOpenIncidents(): Observable<Incident[]> {
    return this.http.get<Incident[]>(`${environment.apiBaseUrl}/incidents/open`);
  }

  getAlertEmailPreview(monitorId: number): Observable<{ content: string }> {
    return this.http.get<{ content: string }>(`${environment.apiBaseUrl}/alerts/email-preview/${monitorId}`);
  }
}

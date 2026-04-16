export type ServerType = 'TOMCAT' | 'SPRING_BOOT' | 'OTHER';

export interface Monitor {
  id: number;
  name: string;
  environment: string;
  url: string;
  contextPath?: string;
  serverType: ServerType;
  expectedTimeoutMs: number;
  status: 'UP' | 'SLOW' | 'DOWN' | 'HANGING' | 'STOPPED';
  responseTimeMs: number;
  lastError?: string;
  lastCheckedAt: string;
}

export interface CreateMonitorRequest {
  name: string;
  environment: string;
  url: string;
  contextPath?: string;
  serverType: ServerType;
  expectedTimeoutMs: number;
}

export interface Incident {
  id: number;
  monitorId: number;
  monitorName: string;
  severity: 'INFO' | 'WARNING' | 'CRITICAL';
  reason: string;
  detector: string;
  details: string;
  openedAt: string;
  resolvedAt?: string;
}

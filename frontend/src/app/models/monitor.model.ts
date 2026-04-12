export interface Monitor {
  id: number;
  name: string;
  environment: string;
  url: string;
  expectedTimeoutMs: number;
  status: 'UP' | 'SLOW' | 'DOWN' | 'HANGING' | 'STOPPED';
  responseTimeMs: number;
  lastError?: string;
  lastCheckedAt: string;
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

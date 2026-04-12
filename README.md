# API Monitoring Application (Spring Boot + Angular)

This project provides a complete monitoring setup for Java Spring Boot APIs.
It is built to detect and display API issues such as:

- API down / unavailable
- connection refused
- slow responses
- hanging endpoints
- stopped services
- repeated runtime errors

It includes your exact alert scenario:

- **Direct Prod Single SMS Node2 is down**
- **Reason: Connection refused**
- **Detector: Applications Manager**

---

## Proper Folder Structure

```text
portpolio/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/monitoring/api/
│       │   │   ├── ApiMonitoringApplication.java
│       │   │   ├── config/
│       │   │   │   └── CorsConfig.java
│       │   │   ├── controller/
│       │   │   │   └── MonitoringController.java
│       │   │   ├── model/
│       │   │   │   ├── Incident.java
│       │   │   │   ├── MonitoredApi.java
│       │   │   │   ├── MonitorStatus.java
│       │   │   │   └── Severity.java
│       │   │   └── service/
│       │   │       └── MonitoringService.java
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/                       (add tests here)
├── frontend/
│   ├── angular.json
│   ├── package.json
│   ├── proxy.conf.json
│   ├── tsconfig.json
│   ├── tsconfig.app.json
│   └── src/
│       ├── index.html
│       ├── main.ts
│       ├── styles.css
│       ├── environments/
│       │   └── environment.ts
│       └── app/
│           ├── app.component.ts
│           ├── app.component.html
│           ├── models/
│           │   └── monitor.model.ts
│           └── services/
│               └── monitoring-api.service.ts
└── README.md
```

---

## Backend APIs

- `GET /api/monitors` → list all monitored APIs
- `GET /api/incidents/open` → list active incidents
- `GET /api/alerts/email-preview/{monitorId}` → generate alert email content

---

## Prerequisites

- **Java 17+**
- **Maven 3.9+**
- **Node.js 20+** (or latest LTS)
- **npm 10+**

---

## How to Run (Step-by-step)

### 1) Run Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

Backend starts at: `http://localhost:8080`

Health check:

```bash
curl http://localhost:8080/actuator/health
```

### 2) Run Frontend (Angular)

Open a **new terminal**:

```bash
cd frontend
npm install
npm start
```

Frontend starts at: `http://localhost:4200`

> `proxy.conf.json` is configured, so frontend `/api` calls are routed to backend `http://localhost:8080` during local development.

---

## How to Test the Application

### A) API-level testing (quick)

After backend is running:

```bash
curl http://localhost:8080/api/monitors
curl http://localhost:8080/api/incidents/open
curl http://localhost:8080/api/alerts/email-preview/1
```

Expected in results:

- monitor name `Direct Prod Single SMS Node2`
- status `DOWN` (seeded initial state)
- reason `Connection refused`
- detector `Applications Manager`

### B) UI testing (manual)

After frontend + backend are both running:

1. Open `http://localhost:4200`
2. Confirm **Monitored APIs** table loads
3. Confirm **Open Incidents** contains the seeded SMS node incident
4. Confirm **Auto-generated Alert Email** is displayed

### C) Build verification commands

Backend build:

```bash
cd backend
mvn clean package
```

Frontend build:

```bash
cd frontend
npm run build
```

---

## Notes

- The service includes a scheduler that simulates health checks every 60 seconds.
- New incidents are generated when monitor status degrades (e.g., hanging/down).
- CORS is enabled for Angular local development.

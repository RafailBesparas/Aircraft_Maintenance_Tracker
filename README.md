# ✈️ Aircraft Maintenance Tracker — Version 2

## Overview

The **Aircraft Maintenance Tracker** is a Java Swing + PostgreSQL application designed to record, manage, and monitor aircraft information and scheduled maintenance activities.  
The project architecture and documentation are structured according to **DO-178C** aviation software standards, emphasizing **modularity**, **traceability**, and **maintainability**.

✅ Built using **Model-View-Presenter (MVP)** design pattern for clear separation of concerns.  
✅ Fully documented for software certification and real-world maintainability.

---

## ✈️ Features

### Aircraft Management
- Add new Aircraft (Model, Tail Number)
- View Aircraft List
- Update Aircraft Details
- Delete Aircraft Records

### Maintenance Task Scheduling
- Assign Maintenance Tasks to specific Aircraft
- Track Due Dates and Task Descriptions
- Tasks sorted automatically by Due Date
- Add Maintenance Tasks with strict date validation (YYYY-MM-DD)

### Maintenance Task Viewer
- GUI window displaying all scheduled maintenance tasks
- Aircraft ID, Task Description, Due Date, and Status visible
- Future-Proof: Designed to support Overdue Alerts and Task Completion updates

---

## 📋 System Architecture

| Layer | Description |
|:------|:------------|
| Model | `Aircraft.java`, `MaintenanceTask.java` — Data entities |
| View | `AircraftViewImplementation.java`, `MaintenanceTaskViewer.java` — GUI components |
| Presenter | `AircraftPresenter.java` — Business logic connecting Model and View |
| Database | PostgreSQL backend with `aircraft` and `maintenance_task` tables |

Designed following **MVP Architecture** principles to guarantee modularity and testability.

---

## 🛫 Technologies Used

| Technology | Purpose |
|:-----------|:--------|
| Java 17 | Core application code |
| Swing | GUI interface |
| PostgreSQL | Relational database |
| JDBC | Database connectivity |
| Maven | Project management and dependencies |
| Git | Version control (Version 2 Branch) |

---

## 🔒 Compliance Focus

- Designed following **DO-178C** Level D guidelines (basic modularity, traceability, error handling).
- Software Requirements Specification (SRS), Software Design Description (SDD), Verification Plan (VP), and Traceability Matrix (TM) are being prepared to ensure full certification traceability.
- Emphasis on **separation of concerns**, **error handling**, and **testability**.

---

## 🛠️ Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/RafailBesparas/Aircraft_Maintenance_Tracker.git
cd Aircraft_Maintenance_Tracker
git checkout version-2

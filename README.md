# ✈️ Aircraft Maintenance Tracker — Version 3

## Overview

The **Aircraft Maintenance Tracker** is a Java Swing + PostgreSQL application for managing aircraft records and scheduled maintenance activities.  
Version 3 introduces **authentication**, **decoy protection**, **data import tools**, and a **KPI dashboard**, continuing to align with **DO-178C** DAL C guidelines for aviation-grade software.

✅ Developed using the **Model-View-Presenter (MVP)** architecture  
✅ Structured for **traceability**, **testability**, and **certification readiness**  
✅ Includes complete documentation: **SRS**, **SDD**, **Verification Plan**, and **Traceability Matrix**

---

## ✈️ Key Features (v3)

### 🔐 Authentication & Security
- Password login system
- **Honeytrap mode** for decoy deployment and access logging
- Access attempts logged in `security-audit.log`

### 🛫 Aircraft Management
- Add, update, and delete aircraft (Model + Tail Number)
- Aircraft view with readable labels
- Aircraft detail popups with task status summaries

### 🧰 Maintenance Task Scheduling
- Add tasks to specific aircraft with due dates and status
- Task viewer with tabular layout and sort by due date
- Task status: **Pending** or **Completed**

### 📊 Maintenance Dashboard (KPIs)
- Total aircraft in fleet
- Pending maintenance task count
- Completed tasks for the current month

### 📁 Bulk Data Import (NEW)
- Import aircraft and tasks from **CSV**, **XML**, and **Excel**
- Duplicate filtering (tail number)
- File chooser UI for secure ingestion

---

## 📋 System Architecture

| Layer     | Components                                                                 |
|-----------|----------------------------------------------------------------------------|
| Model     | `Aircraft.java`, `MaintenanceTask.java`                                    |
| View      | `AircraftViewImplementation`, `MaintenanceTaskViewer`, `DashboardPanel`, `AircraftDetailView`, `LoginWindow` |
| Presenter | `AircraftPresenter.java` (connects model and view)                         |
| Utility   | `AircraftImportHandler`, `MaintenanceTaskImportHandler`, `Database.java`  |
| Database  | PostgreSQL with `aircraft` and `maintenance_task` tables                  |

Follows **MVP Design Pattern** for decoupled, modular, and certifiable software structure.

---

## 🧪 Verification & Compliance

- **DO-178C DAL C**-oriented software assurance
- ✅ Full traceability across SRS → SDD → Tests
- ✅ Audit log of decoy mode
- ✅ Validated through:
    - Manual GUI testing
    - JUnit 5 for DB and logic
    - Static code inspection
- 📄 Docs included:
    - `Software_Requirements_Specification.md`
    - `Software_Design_Description.md`
    - `Verification_Plan.md`
    - `Traceability_Matrix.md`

---

## 🛠️ Technologies

| Technology | Usage                         |
|------------|-------------------------------|
| Java 17    | Application logic             |
| Swing      | GUI rendering                 |
| PostgreSQL | Relational data persistence   |
| JDBC       | Database connection layer     |
| Maven      | Build and dependency manager  |
| JUnit 5    | Testing framework             |
| Git        | Source control (v3 branch)    |

---

## 🚀 Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/RafailBesparas/Aircraft_Maintenance_Tracker.git
cd Aircraft_Maintenance_Tracker
git checkout version-3

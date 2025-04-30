# Software Design Description (Updated for Version 2)

## 1. Introduction

### 1.1 Purpose
This document provides the software design for the Aircraft Maintenance Tracker, Version 2. It supports DO-178C objectives by documenting the internal architecture, component interactions, and data structures used to implement the software requirements described in the Software Requirements Specification (SRS).

### 1.2 Scope
This version introduces maintenance task scheduling and tracking, aircraft record management, and GUI enhancements. The software is implemented in Java using the Swing library for GUI and PostgreSQL for persistence. The design adheres to the Model-View-Presenter (MVP) architectural pattern.

## 2. System Overview

The Aircraft Maintenance Tracker is a desktop-based application composed of the following high-level components:
- **Model:** Represents domain entities (`Aircraft`, `MaintenanceTask`).
- **View:** Manages GUI display (`AircraftViewImplementation`, `MaintenanceTaskViewer`).
- **Presenter:** Controls application logic (`AircraftPresenter`).
- **Database:** Stores aircraft and maintenance data in relational tables.

## 3. System Architecture

```
+----------------------+       +----------------------+       +----------------------+
|      View Layer      | <-->  |   Presenter Layer    | <-->  |      Model Layer     |
| (Swing UI Classes)   |       |  (Business Logic)    |       |  (Aircraft, Tasks)   |
+----------------------+       +----------------------+       +----------------------+
                                        |
                                        v
                              +----------------------+
                              |   PostgreSQL DB      |
                              +----------------------+
```

### 3.1 MVP Roles
| Component | Class(es) | Description |
|-----------|-----------|-------------|
| Model | `Aircraft`, `MaintenanceTask` | Represents persistent domain data. |
| View | `AircraftViewImplementation`, `MaintenanceTaskViewer` | Provides the user interface. |
| Presenter | `AircraftPresenter` | Coordinates data loading, event responses, and view updates. |

## 4. Module Design

### 4.1 Aircraft.java
- Fields: `id`, `model`, `tailNumber`
- Used to represent aircraft data throughout the system.

### 4.2 MaintenanceTask.java
- Fields: `id`, `aircraftId`, `taskDescription`, `dueDate`, `status`, `aircraftDisplayName`
- Used to store and display scheduled maintenance data.

### 4.3 AircraftPresenter.java
- Core logic controller:
    - `loadAircraft()`, `addAircraft()`, `updateAircraft()`, `deleteAircraft()`
    - `addMaintenanceTask()` and `loadMaintenanceTasks()`
    - SQL queries to interact with the database

### 4.4 AircraftViewImplementation.java
- Main GUI interface
- Handles user inputs for aircraft and task creation
- Triggers presenter actions

### 4.5 MaintenanceTaskViewer.java
- Displays all tasks in a sorted table view
- Uses `JTable` for clean tabular layout

## 5. Data Design

### 5.1 Database Tables

#### aircraft
| Field | Type | Description |
|-------|------|-------------|
| id | SERIAL | Primary key |
| model | VARCHAR | Aircraft model |
| tail_number | VARCHAR | Registration code |

#### maintenance_task
| Field | Type | Description |
|-------|------|-------------|
| id | SERIAL | Primary key |
| aircraft_id | INT | Foreign key to aircraft.id |
| task_description | VARCHAR | Task details |
| due_date | DATE | When task is due |
| status | VARCHAR | Task state: "Pending" or "Completed" |

## 6. Error Handling

- View layer shows dialog messages for exceptions
- Presenter logs and routes errors from failed DB operations
- Date parsing is strictly validated with format `yyyy-MM-dd`

## 7. Security and Validation

- No authentication in Version 2
- Input validation includes:
    - Date format enforcement
    - Null/empty check for task and aircraft entries

## 8. Design for Future Enhancements

- Status toggle for maintenance tasks (e.g., mark completed)
- PDF export support
- User login and role-based access

---

This SDD reflects the working implementation of Version 2 and supports future extension while preserving DO-178C traceability and modular software structure.

# Software Requirements Specification (Updated for Version 2)

## 1. Introduction

### 1.1 Purpose
The purpose of the Aircraft Maintenance Tracker system is to provide a structured, modular, and certifiable desktop application for tracking aircraft records and their associated maintenance tasks. The system ensures safe operational planning, compliance with maintenance schedules, and supports traceability and correctness according to DO-178C aviation software certification standards.

### 1.2 Scope
The system allows users to:
- Add, update, view, and delete aircraft records.
- Schedule and view maintenance tasks associated with each aircraft.
- Display alerts for incorrect input or invalid operations.
- Persist all data in a PostgreSQL database.
- View scheduled maintenance tasks sorted by due date.

The application is built using Java, Swing, and PostgreSQL and follows the Model-View-Presenter (MVP) architectural pattern to ensure clear modularity, maintainability, and auditability.

### 1.3 Definitions, Acronyms, and Abbreviations
| Term | Definition |
|------|------------|
| MVP | Model-View-Presenter architecture |
| GUI | Graphical User Interface |
| DO-178C | Software Certification Standard for Airborne Systems |
| CRUD | Create, Read, Update, Delete |
| JDBC | Java Database Connectivity |

## 2. Overall Description

### 2.1 Product Perspective
The Aircraft Maintenance Tracker is a standalone Java desktop application that communicates with a PostgreSQL database for persistent storage.

### 2.2 Product Functions
- **Aircraft Management:** Add, view, update, and delete aircraft records.
- **Maintenance Task Scheduling:** Add and view maintenance tasks with due dates.
- **Task Viewer:** Maintenance tasks are listed in a GUI window, sorted by due date.
- **Error Handling:** Alerts and input validation for empty or malformed data.

### 2.3 User Characteristics
- Basic computer literacy expected.
- No technical database or development knowledge is required.

### 2.4 Constraints
- Java 17+ and PostgreSQL 12+ must be installed locally.
- No multi-user concurrency or networking in Version 2.

## 3. Specific Requirements

| ID | Requirement |
|----|-------------|
| REQ-01 | The system shall allow users to create, view, update, and delete aircraft records. |
| REQ-02 | The system shall persist aircraft and maintenance task records in a PostgreSQL database. |
| REQ-03 | The system shall allow the scheduling of maintenance tasks with a due date and status. |
| REQ-04 | The system shall retrieve and display all aircraft and tasks on demand. |
| REQ-05 | The system shall provide a GUI window to list all maintenance tasks sorted by due date. |
| REQ-06 | The system shall validate date input in YYYY-MM-DD format. |
| REQ-07 | The system shall use meaningful identifiers (model and tail number) in all task-related UI elements instead of database IDs. |
| REQ-08 | The system shall alert users to any critical errors such as failed database operations or invalid input. |

---

This updated SRS reflects the features completed in Version 2, including full CRUD operations, maintenance task management, and robust user interface feedback mechanisms.

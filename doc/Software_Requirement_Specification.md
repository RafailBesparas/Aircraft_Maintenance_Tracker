# Software Requirements Specification (Updated for Version 3)

## 1. Introduction

### 1.1 Purpose
The purpose of the Aircraft Maintenance Tracker system is to provide a structured, modular, and certifiable desktop application for managing aircraft records and their associated maintenance tasks. The system supports traceability, auditability, and safe maintenance planning, aligning with DO-178C software certification standards (DAL C assumed).

### 1.2 Scope
Version 3 of the application includes enhancements over Version 2 with the following capabilities:
- Secure login interface with support for honeytrap decoy mode.
- Import functionality for aircraft and maintenance data (CSV, XML, Excel).
- KPI Dashboard showing fleet-wide maintenance health.
- Aircraft-specific detail views and maintenance summaries.
- Modular MVP design for traceability and future certification efforts.

The system is built using Java, Swing, and PostgreSQL, and follows a Model-View-Presenter (MVP) architecture.

### 1.3 Definitions, Acronyms, and Abbreviations

| Term     | Definition                                                |
|----------|-----------------------------------------------------------|
| MVP      | Model-View-Presenter architecture                         |
| GUI      | Graphical User Interface                                  |
| DO-178C  | Software Certification Standard for Airborne Systems      |
| CRUD     | Create, Read, Update, Delete                              |
| JDBC     | Java Database Connectivity                                |
| DAL      | Design Assurance Level (from DO-178C standard)            |
| KPI      | Key Performance Indicator                                 |

## 2. Overall Description

### 2.1 Product Perspective
The Aircraft Maintenance Tracker is a Java Swing desktop application designed for standalone use. It persists its data in a local PostgreSQL database and incorporates import/export utilities to integrate with external maintenance schedules and registries.

### 2.2 Product Functions
- **Aircraft Management:** Add, view, update, and delete aircraft records.
- **Maintenance Task Scheduling:** Add and view maintenance tasks, with due date and status.
- **Authentication:** Login screen differentiating real and decoy system access.
- **Task Viewer:** Lists tasks in a sortable table GUI by due date.
- **Dashboard:** Displays total aircraft, pending tasks, and recent completions.
- **Data Import:** Bulk upload from CSV/XML/Excel for aircraft and maintenance tasks.
- **Detail View:** Aircraft-specific summary popup including maintenance statistics.

### 2.3 User Characteristics
- Users are expected to have basic computing knowledge.
- No programming or database knowledge is required.
- Users must be authorized for access; decoy mode is used to protect data.

### 2.4 Constraints
- Java 17+ and PostgreSQL 12+ must be available locally.
- Version 3 does not support networking or multi-user concurrency.
- Credentials are hardcoded for prototype purposes (to be secured in future).

## 3. Specific Requirements

| ID      | Requirement                                                                                          |
|---------|------------------------------------------------------------------------------------------------------|
| REQ-01  | The system shall provide login access with real and decoy mode activation based on password input.   |
| REQ-02  | The system shall allow CRUD operations for aircraft records.                                         |
| REQ-03  | The system shall persist aircraft and maintenance task data in a PostgreSQL database.                |
| REQ-04  | The system shall allow creation and tracking of maintenance tasks, including due dates and status.  |
| REQ-05  | The system shall provide GUI screens for viewing tasks sorted by due date.                          |
| REQ-06  | The system shall validate all date inputs using the `YYYY-MM-DD` format.                            |
| REQ-07  | The system shall display aircraft model and tail number in all task views.                           |
| REQ-08  | The system shall support import of aircraft and tasks via CSV, XML, and Excel formats.               |
| REQ-09  | The system shall reject duplicate aircraft entries based on tail number.                             |
| REQ-10  | The system shall display KPI metrics: total aircraft, pending tasks, and completed tasks this month. |
| REQ-11  | The system shall log unauthorized access attempts to a `security-audit.log` file.                    |
| REQ-12  | The system shall alert users to errors such as failed database operations or invalid input.          |
| REQ-13  | The system shall disable critical features in honeytrap (decoy) mode.                                |

---

**Version:** 3.0  
**Prepared by:** Rafail  
**Last Updated:** 2025-05-07  
**Compliance Focus:** DO-178C DAL C (Data Integrity Assurance)

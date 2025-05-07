# Verification Plan (Version 3)

## 1. Introduction

This document defines the verification strategy for the Aircraft Maintenance Tracker software, Version 3. It outlines activities to ensure the software implementation satisfies all functional and safety requirements as specified in the Software Requirements Specification (SRS) and aligns with DO-178C DAL C compliance standards.

## 2. Verification Scope

This version includes new verification focus areas:
- Authentication flow (real vs decoy)
- GUI-based feature enablement/disablement
- Import functionality for CSV/XML/Excel
- KPI Dashboard metrics
- Secure audit logging and error handling

## 3. Verification Environment

| Component         | Description                         |
|-------------------|-------------------------------------|
| OS                | Windows 10+ / Linux / macOS         |
| Java Version      | Java SE 17                          |
| Build Tool        | Maven                               |
| Database          | PostgreSQL 12+                      |
| Testing Framework | JUnit 5                             |

## 4. Verification Techniques

| Technique      | Description                                          |
|----------------|------------------------------------------------------|
| Manual Testing | GUI walkthroughs, visual checks, edge-case inputs   |
| Unit Testing   | JUnit 5 tests for database access and models        |
| Integration    | Presenter ↔ View ↔ Model interaction validation     |
| Static Review  | Code walkthroughs for logic and security flaws      |
| Log Verification | Ensure honeytrap activation logs are written     |

## 5. Test Plan Summary

| Test ID | Requirement ID | Test Description                                                     | Method       | Expected Result                                |
|--------|----------------|------------------------------------------------------------------------|--------------|-------------------------------------------------|
| T-01   | REQ-01         | Test login routing: real vs decoy password                             | Manual       | Correct mode activated and view launched        |
| T-02   | REQ-02         | Add Aircraft via GUI                                                   | Manual       | Aircraft saved to DB and shown in list          |
| T-03   | REQ-02         | Update/Delete Aircraft                                                 | Manual       | Aircraft updated or removed from view + DB      |
| T-04   | REQ-03         | Schedule maintenance task                                              | Manual       | Task appears in DB and linked to aircraft       |
| T-05   | REQ-04         | Retrieve all tasks and aircraft                                        | Manual       | Full data visible in GUI                        |
| T-06   | REQ-05         | Open MaintenanceTaskViewer and validate sorted output                  | Manual       | Tasks sorted by due date                        |
| T-07   | REQ-06         | Enter invalid date (e.g., wrong format)                                | Manual       | Error alert shown; task not saved               |
| T-08   | REQ-07         | Check aircraft info in task display                                    | Manual       | Task shows readable aircraft name/tailNumber    |
| T-09   | REQ-08         | Import aircraft/tasks from CSV, XML, Excel                             | Manual       | Data inserted into DB; no duplicates            |
| T-10   | REQ-09         | Attempt duplicate aircraft import                                      | Manual/Test  | Duplicate is skipped or rejected                |
| T-11   | REQ-10         | Open Dashboard; validate KPI counters                                  | Manual       | Total aircraft, pending, and monthly counts correct |
| T-12   | REQ-11         | Trigger honeytrap login and inspect log                                | File Check   | Log file entry appears with timestamp           |
| T-13   | REQ-12         | Simulate DB disconnection or bad input                                 | Manual/Test  | GUI shows relevant error message                |
| T-14   | REQ-13         | Access aircraft/task features in decoy mode                            | Manual       | Buttons disabled; data fake or hidden           |

## 6. Acceptance Criteria

- All critical path and edge-case tests pass
- No unhandled exceptions or crashes occur during manual interaction
- Database integrity is maintained after imports and CRUD ops
- Audit log reflects unauthorized access attempts
- Static analysis flags are resolved before release

## 7. Non-Conformance Handling

- All test failures are documented with screenshots or logs
- GitHub Issues / Jira tickets track fixes
- Regression testing confirms resolution
- Tests are re-run after each fix or refactor

## 8. Tools

- **Maven** – build and test orchestration
- **JUnit 5** – unit and integration testing
- **PostgreSQL CLI / pgAdmin** – DB verification
- **Manual checklists** – for UI flows and honeytrap response
- **Text diff tools** – to compare logs and imports
- **Static analysis** – IntelliJ, SonarLint

---

This verification plan provides full traceability from requirements to tests, ensuring that Version 3 of the Aircraft Maintenance Tracker fulfills its safety, correctness, and compliance objectives under DO-178C.

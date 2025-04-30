# Verification Plan (Version 2)

## 1. Introduction

This document defines the verification strategy for the Aircraft Maintenance Tracker software, Version 2. It covers verification activities to ensure the software meets all requirements as outlined in the Software Requirements Specification (SRS) and supports DO-178C compliance.

## 2. Verification Scope

The scope includes:
- GUI launch and event verification
- CRUD operation validation
- Maintenance task scheduling
- PostgreSQL connectivity and schema checks
- Date validation and error handling

## 3. Verification Environment

| Component        | Description                          |
|------------------|--------------------------------------|
| OS               | Windows 10+ / Linux / macOS          |
| Java Version     | Java SE 17                           |
| Build Tool       | Maven                                |
| Database         | PostgreSQL 12+                       |
| Testing Framework| JUnit 5                              |

## 4. Verification Techniques

| Technique     | Description                                         |
|---------------|-----------------------------------------------------|
| Manual Test   | Run GUI and perform test cases by hand             |
| Unit Test     | JUnit 5 tests for models, database access          |
| Integration   | GUI + Presenter + DB interactions                  |
| Static Review | Source code and documentation review               |

## 5. Test Plan Summary

| Test ID | Requirement ID | Test Description                                            | Method       | Expected Result                       |
|---------|----------------|-------------------------------------------------------------|--------------|----------------------------------------|
| T-01    | REQ-01         | Add Aircraft via GUI                                        | Manual       | Aircraft added to DB and shown in list|
| T-02    | REQ-01         | Update Aircraft info                                        | Manual       | Changes visible in GUI and DB         |
| T-03    | REQ-01         | Delete Aircraft                                             | Manual       | Aircraft removed from list and DB     |
| T-04    | REQ-02         | Test DB connection                                          | JUnit        | DB connection is successful           |
| T-05    | REQ-03         | Add Maintenance Task via GUI                                | Manual       | Task saved and linked to aircraft     |
| T-06    | REQ-04         | View Aircraft and Tasks                                     | Manual       | All current data appears in GUI       |
| T-07    | REQ-05         | Maintenance Task Viewer opens and sorts tasks              | Manual       | Tasks shown sorted by due date        |
| T-08    | REQ-06         | Invalid date input handling                                 | Manual       | Error shown, task not saved           |
| T-09    | REQ-07         | Tasks show aircraft model and tail number (not ID)         | Manual       | GUI shows readable aircraft label     |
| T-10    | REQ-08         | Show error on DB failure or bad input                       | Manual/Test  | Error messages appear in GUI          |

## 6. Acceptance Criteria

- All automated and manual tests pass
- GUI performs as expected under test cases
- System gracefully handles errors and invalid input
- Code review and static analysis show no major issues

## 7. Non-Conformance Handling

- Test failures are logged with error output
- Issue tracker used to assign and track bug fixes
- All failed tests must be resolved and re-executed

## 8. Tools

- Maven (build/test runner)
- JUnit 5 (unit tests)
- PostgreSQL CLI (schema/connection testing)
- Manual checklist for GUI walkthrough

---

This verification plan validates the full functionality of Version 2 of the Aircraft Maintenance Tracker and aligns with DO-178C traceability and software assurance expectations.

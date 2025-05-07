# Traceability Matrix (Version 3)

This matrix maps software requirements (from the SRS) to their design components, implementation artifacts, and verification methods. It supports DO-178C DAL C traceability objectives.

| Requirement ID | Requirement Description                                                                                          | Design Component(s)                                                                                       | Verification Method/Test ID       |
|----------------|-------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|------------------------------------|
| REQ-01         | The system shall provide login access with real and decoy mode activation based on password input.               | LoginWindow.java, Main.java                                                                               | Auth Path Test, Honeytrap Trigger (T-01) |
| REQ-02         | The system shall allow CRUD operations for aircraft records.                                                     | Aircraft.java, AircraftPresenter.java, AircraftViewImplementation.java                                     | CRUD Tests (T-02 to T-04)          |
| REQ-03         | The system shall persist aircraft and maintenance task data in a PostgreSQL database.                            | Database.java, SQL Schema                                                                                  | Connection + Insert/Update Tests (T-05) |
| REQ-04         | The system shall allow creation and tracking of maintenance tasks, including due dates and status.               | MaintenanceTask.java, AircraftPresenter.java, AircraftViewImplementation.java                              | Task Creation/Status Tests (T-06)  |
| REQ-05         | The system shall provide GUI screens for viewing tasks sorted by due date.                                       | MaintenanceTaskViewer.java                                                                                 | GUI Task Sorting Test (T-07)       |
| REQ-06         | The system shall validate all date inputs using the `YYYY-MM-DD` format.                                         | AircraftViewImplementation.java                                                                            | Input Validation Test (T-08)       |
| REQ-07         | The system shall display aircraft model and tail number in all task views.                                       | MaintenanceTask.java, AircraftPresenter.java, MaintenanceTaskViewer.java                                   | Task Display Formatting Test (T-09)|
| REQ-08         | The system shall support import of aircraft and tasks via CSV, XML, and Excel formats.                           | AircraftImportHandler.java, MaintenanceTaskImportHandler.java                                              | Import Simulation Tests (T-10 to T-12) |
| REQ-09         | The system shall reject duplicate aircraft entries based on tail number.                                          | AircraftImportHandler.java                                                                                 | Duplicate Filter Test (T-13)       |
| REQ-10         | The system shall display KPI metrics: total aircraft, pending tasks, and completed tasks this month.             | MaintenanceDashboardPanel.java, AircraftPresenter.java                                                     | KPI Display Test (T-14)            |
| REQ-11         | The system shall log unauthorized access attempts to a `security-audit.log` file.                                | Main.java                                                                                                  | Log File Content Test (T-15)       |
| REQ-12         | The system shall alert users to errors such as failed database operations or invalid input.                      | AircraftViewImplementation.java, AircraftPresenter.java                                                    | Error Injection/Recovery Test (T-16) |
| REQ-13         | The system shall disable critical features in honeytrap (decoy) mode.                                             | AircraftViewImplementation.java                                                                            | Feature Disablement Test (T-17)    |

---

- **T-01 to T-17** refer to formal tests outlined in the *Software Verification Plan*.
- This matrix ensures **bidirectional traceability** (requirement ↔ design ↔ verification) per DO-178C guidelines.

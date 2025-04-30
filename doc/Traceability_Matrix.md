# Traceability Matrix (Version 2)

This matrix maps software requirements (from the SRS) to their design components, implementation, and verification tests.

| Requirement ID | Requirement Description                                                                 | Design Component(s)                            | Verification Method/Test ID     |
|----------------|------------------------------------------------------------------------------------------|------------------------------------------------|-------------------------------|
| REQ-01         | The system shall allow users to create, view, update, and delete aircraft records.      | Aircraft.java, AircraftPresenter.java, AircraftViewImplementation.java | CRUD Tests, GUI Tests, T-01 to T-03 |
| REQ-02         | The system shall persist aircraft and maintenance task records in a PostgreSQL database. | Database.java, SQL schema                     | DatabaseTest.java, T-04       |
| REQ-03         | The system shall allow scheduling of maintenance tasks with due date and status.        | MaintenanceTask.java, AircraftPresenter.java, AircraftViewImplementation.java | Task Add + View GUI Tests, T-05 |
| REQ-04         | The system shall retrieve and display all aircraft and tasks on demand.                 | AircraftPresenter.java, AircraftViewImplementation.java, MaintenanceTaskViewer.java | GUI Integration Test, T-06     |
| REQ-05         | The system shall provide a GUI window to list all maintenance tasks sorted by due date. | MaintenanceTaskViewer.java                    | Task Viewer GUI Launch Test, T-07 |
| REQ-06         | The system shall validate date input in YYYY-MM-DD format.                              | AircraftViewImplementation.java                | Manual + Unit Date Input Test, T-08 |
| REQ-07         | The system shall use model and tail number in task UI, not database IDs.                | MaintenanceTask.java, MaintenanceTaskViewer.java, AircraftPresenter.java | Task Display Test, T-09         |
| REQ-08         | The system shall alert users to any critical errors such as failed DB operations.       | AircraftViewImplementation.java, AircraftPresenter.java | Error Injection Test, T-10     |

---

- T-01 to T-10 refer to tests defined in the Verification Plan.
- This matrix ensures full forward and backward traceability in line with DO-178C practices.
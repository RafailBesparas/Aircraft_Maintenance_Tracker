
### 3.1 MVP Roles

| Component  | Class(es)                                                            | Description                         |
|------------|----------------------------------------------------------------------|-------------------------------------|
| Model      | `Aircraft`, `MaintenanceTask`                                        | Persistent data representation      |
| View       | `AircraftViewImplementation`, `MaintenanceTaskViewer`, `AircraftDetailView`, `MaintenanceDashboardPanel`, `LoginWindow` | User interface and display logic    |
| Presenter  | `AircraftPresenter`                                                  | Business logic and coordination     |
| Utility    | `AircraftImportHandler`, `MaintenanceTaskImportHandler`             | File I/O and data ingestion         |

## 4. Module Design

### 4.1 `Main.java`
- Entry point
- Launches LoginWindow
- Determines execution mode (real vs. honeytrap)
- Logs decoy attempts

### 4.2 `LoginWindow.java`
- GUI authentication
- Password-based routing to Main.launchRealMode() or Main.launchHoneyTrapMode()

### 4.3 `AircraftPresenter.java`
- Coordinates between model and view
- CRUD for aircraft and maintenance tasks
- Handles business rules and user feedback

### 4.4 `AircraftViewImplementation.java`
- Main user interface
- Aircraft/task management
- Calls presenter methods
- Switches off features in honeytrap mode

### 4.5 `MaintenanceDashboardPanel.java`
- Displays KPIs:
  - Total aircraft
  - Pending maintenance
  - Completed tasks this month

### 4.6 `AircraftDetailView.java`
- Per-aircraft popup view with:
  - Model
  - Tail number
  - Task counts (pending/completed)

### 4.7 `MaintenanceTaskViewer.java`
- Table-based task list using JTable
- Displays task metadata (ID, aircraft, due date, status)

### 4.8 `AircraftImportHandler.java`
- Imports aircraft from:
  - CSV
  - Excel (XLSX)
  - XML
- Checks for duplicate tail numbers

### 4.9 `MaintenanceTaskImportHandler.java`
- Bulk maintenance task import from:
  - CSV, XLSX, XML
- Validates task data before DB insert

### 4.10 `Database.java`
- Static method for PostgreSQL connection
- **Security Warning:** Hardcoded credentials must be replaced with encrypted vault or env variables in production

## 5. Data Design

### 5.1 Database Tables

#### aircraft

| Field       | Type    | Description        |
|-------------|---------|--------------------|
| id          | SERIAL  | Primary key        |
| model       | VARCHAR | Aircraft model     |
| tail_number | VARCHAR | Unique tail ID     |

#### maintenance_task

| Field            | Type    | Description                  |
|------------------|---------|------------------------------|
| id               | SERIAL  | Primary key                  |
| aircraft_id      | INT     | FK to aircraft.id            |
| task_description | VARCHAR | Description of work          |
| due_date         | DATE    | Due date of task             |
| status           | VARCHAR | \"Pending\" or \"Completed\" |

## 6. Error Handling

- Presenter catches DB exceptions, reports via View
- View shows pop-up messages on errors
- `yyyy-MM-dd` enforced for dates
- Honeytrap log writes to file `security-audit.log`

## 7. Security and Validation

- Login authentication with restricted decoy mode
- No password hashing yet (planned in future release)
- Imports validate duplicates by tail number or task constraints
- Dashboard metrics prevent visual data manipulation

## 8. Design for Future Enhancements

- Switch to encrypted DB credential storage
- Implement RBAC (role-based access control)
- Expand honeytrap with session recording
- Add completion toggling for tasks
- Export reports to PDF or JSON

---

**Version:** 3.0  
**Prepared by:** Rafail  
**Last Updated:** 2025-05-07  
**DO-178C DAL Level (assumed):** DAL C (Data Integrity Focus)

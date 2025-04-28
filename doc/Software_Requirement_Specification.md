## What features your program must have.

# Software Requirements Specification

# 1. Introduction

- #### 1.1 Purpose

- The purpose of the Aircraft Maintenance Tracker system is to provide a simple and reliable tool to record, manage, and visualize aircraft maintenance activities, ensuring that maintenance schedules are respected to maintain operational safety and regulatory compliance.

- #### 1.2 Scope
- The system will enable users to:
- Add new aircraft records.
- View existing aircraft records. 
- Store records persistently in a PostgreSQL database.
- Alert users through the interface in case of data input errors (basic error handling).
- This application is developed using Java, Swing, and PostgreSQL following the Model-View-Presenter (MVP) design pattern to ensure modularity, maintainability, and traceability under DO-178C guidelines.

- #### 1.3 Definitions, Acronyms, and Abbreviations
- Term	Definition
MVP	Model-View-Presenter architectural pattern
DO-178C	Software Considerations in Airborne Systems and Equipment Certification
GUI	Graphical User Interface
JDBC	Java Database Connectivity

2. Overall Description
   2.1 Product Perspective
   The Aircraft Maintenance Tracker is a standalone Java desktop application.
   It interacts with a local PostgreSQL database server to persist maintenance data.

- #### 2.2 Product Functions
- Add Aircraft: Allow users to input a new aircraft’s model and tail number.
- View Aircraft List: Display a list of all recorded aircraft.
- Store Records: Save aircraft entries in a PostgreSQL database.
- Error Notifications: Display error messages for invalid or failed operations.

- #### 2.3 User Characteristics
- Users are expected to have basic computer literacy.
- No technical knowledge of databases or backend systems is required.

- #### 2.4 Constraints
- The system will run on local machines with Java 17+ and PostgreSQL 12+ installed.
- The application will not handle multi-user concurrency in Version 1.

- #### 3. Specific Requirements
- ID	Requirement
- REQ-01	The system shall allow the user to add a new aircraft record consisting of a model and tail number.
- REQ-02	The system shall store aircraft records persistently in a PostgreSQL database.
- REQ-03	The system shall retrieve and display all stored aircraft records upon startup.
- REQ-04	The system shall display user-friendly messages for successful and failed operations.
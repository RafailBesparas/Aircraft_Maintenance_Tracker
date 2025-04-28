## How the program architecture is organized (MVP pattern, database structure).

### Why use MVP Design Pattern
To separate concerns clearly between data, user interface, and application logic.

This separation improves:

Modularity (each part can be developed and tested independently),

Maintainability (easier to fix bugs or add features),

Traceability (important for DO-178C compliance),

Testability (presenter can be tested without needing a real UI).

Model	Represents the data (Aircraft information stored in PostgreSQL).
View	Displays information to the user (Java Swing GUI).
Presenter	Handles the business logic (coordinates Model and View, handles user actions).

# Why MVP instead of MVC
MVP improves testability because the Presenter is fully independent of the View implementation.

In MVC, View and Controller are often tightly linked, but MVP is cleaner for large projects needing certification, where traceability and verifiable separation are crucial (as per DO-178C).

# 1. Introduction
  
- #### 1.1 Purpose
- This Software Design Description (SDD) describes the system architecture, module decomposition, and internal data structures of the Aircraft Maintenance Tracker system.
- The design follows the Model-View-Presenter (MVP) architectural pattern to maintain modularity, separation of concerns, testability, and traceability under DO-178C guidelines.

- #### 1.2 Scope
- The Aircraft Maintenance Tracker allows users to add and view aircraft records stored in a PostgreSQL database through a Java Swing GUI.

- #### 2. System Overview
- The system consists of three primary layers:


- Layer:  Responsibility
- Model:  Represents the Aircraft data and structure.
- View:   Provides the user interface and displays information.
- Presenter: 	Manages business logic, connecting Model and View.
- Each layer: communicates strictly according to MVP principles.
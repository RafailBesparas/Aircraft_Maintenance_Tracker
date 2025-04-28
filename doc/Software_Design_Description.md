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
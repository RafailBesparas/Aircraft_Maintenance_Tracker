# Links each requirement to corresponding design and tests.

# 1. Introduction
- The Traceability Matrix shows the mapping between:
- Requirements from the SRS (what the system must do),
- Design Modules from the SDD (how the system implements it),
- Verification Tests from the Verification Plan (how we test it).
- This ensures that every requirement is implemented and verified, as required by DO-178C.

- #### 2. Traceability Table

##### Requirement ID	Requirement Description	Design Component(s)	Verification Test ID	Status
- REQ-01	Add a new aircraft record (model and tail number).	AircraftPresenter, AircraftViewImplementation	T-01	Implemented and Tested ✅
- REQ-02	Store aircraft records persistently in PostgreSQL.	AircraftPresenter, Database	T-02	Implemented and Tested ✅
- REQ-03	Retrieve and display all aircraft on startup.	AircraftPresenter, AircraftViewImplementation	T-03	Implemented and Tested ✅
- REQ-04	Display user-friendly messages for operations.	AircraftPresenter, AircraftViewImplementation	T-04	Implemented and Tested ✅

- #### 📋 Key
- Column:	Meaning
- Requirement: ID	As defined in SRS
- Requirement: Description	What the system must do
- Design Component(s):	Classes or modules that implement the requirement
- Verification: Test ID	Specific test verifying the implementation
- Status:	Whether it is implemented and verified

- #### 3. Notes
- No requirement is left unimplemented.
- Every requirement is covered by at least one design component and verified by at least one test case.
- Changes in requirements must update this matrix accordingly (as per DO-178C Change Control).
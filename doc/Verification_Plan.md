# How you will test and prove it works.

# 1. Introduction
- #### 1.1 Purpose
- The purpose of this Verification Plan is to define the strategy, techniques, and criteria for verifying that the Aircraft Maintenance Tracker system meets all specified functional and non-functional requirements as outlined in the Software Requirements Specification (SRS).
- The verification activities are planned according to DO-178C guidelines to ensure the correctness, consistency, and safety of the software.

- #### 2. Verification Scope
- Verification will cover the following:
- Functional verification of the core features (add, view, display aircraft).
- Database connection and integrity validation.
- User Interface interaction testing.
- Error handling testing.
- Traceability between requirements and corresponding verification actions.

- #### 3. Verification Environment
- Component:	Description
- Hardware:	Standard desktop or laptop computer
- Operating System:	Windows/Linux/MacOS
- Java Version:	Java SE 17 or higher
- Database:	PostgreSQL 12 or higher
- Tools: Maven, psql (PostgreSQL CLI), Git

- #### 4. Verification Techniques
- Technique: 	Description
- Review:	Manual review of source code and documents.
- Testing:	Black-box and white-box testing for features and database interactions.
- Analysis:	Static analysis to ensure adherence to coding standards.
- Demonstration:	Manual operation of the system to demonstrate behavior against requirements.

- #### 5. Test Plan
- Each requirement from the SRS will have an associated test:


#### Test ID	Requirement ID	Test Description	Method	Pass Criteria
- T-01	REQ-01	Test adding a new aircraft with valid model and tail number.	Black-box	Aircraft appears in the list and database.
- T-02	REQ-02	Test that aircraft records persist after restarting the application.	Demonstration	Same aircraft records are loaded after restart.
- T-03	REQ-03	Test that all aircraft records are loaded on startup.	Demonstration	List is populated with database entries.
- T-04	REQ-04	Test that error messages are shown when database access fails.	Black-box	Proper error message dialog appears without crash.

- #### 6. Acceptance Criteria
- The Aircraft Maintenance Tracker software will be considered verified when:
- All tests listed in the Test Plan have passed successfully.
- No critical or major issues remain unresolved.
- Source code passes manual review and follows DO-178C modularity and traceability principles.
- Database interaction is validated without data loss or corruption.

- #### 7. Non-Conformance Handling
- If any test fails:
- The defect will be documented immediately.
- Root cause analysis will be performed.
- Code will be corrected following change management.
- Test will be re-executed until successful.


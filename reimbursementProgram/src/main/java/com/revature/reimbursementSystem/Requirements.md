# Java Enterprise Foundations Project Requirements

## Project Description

For the foundations module of your training you are tasked with building an API that will support a new internal expense reimbursement system. This system will manage the process of reimbursing employees for expenses incurred while on company time. All registered employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

### Project Design Specifications and Documents

##### Relational Data Model
![Relational Model](https://github.com/220207-java-enterprise/assignments/blob/main/foundations-project/imgs/ERS%20Relational%20Model.png)

##### Reimbursement Types
Reimbursements are to be one of the following types:
- LODGING
- TRAVEL
- FOOD
- OTHER

##### System Use Case Diagrams
![System Use Case Diagrams](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20Use%20Case%20Diagram.png)

##### Reimbursment Status State Flow
![Reimbursment Status State Flow](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20State%20Flow%20Diagram.png)

### Technologies

**Persistence Tier**
- PostGreSQL (running on Docker)

**Application Tier**
- Java 8
- Apache Maven
- JDBC
- Java EE Servlets
- JSON Web Tokens
- JUnit
- Mockito

### Functional Requirements

- [x] An new employee or new finance manager can request registration with the system
- [x] An admin user can approve or deny new registration requests
- [x] The system will register the user's information for payment processing
- [x] A registered employee can authenticate with the system by providing valid credentials
- [x] An authenticated employee can view and manage their pending reimbursement requests
- [x] An authenticated employee can view their reimbursement request history (sortable and filterable)
- [x] An authenticated employee can submit a new reimbursement request
- [x] An authenticated finance manager can view a list of all pending reimbursement requests
- [x] An authenticated finance manager can view a history of requests that they have approved/denied
- [x] An authenticated finance manager can approve/deny reimbursement requests
- [x] An admin user can deactivate user accounts, making them unable to log into the system
- [x] An admin user can reset a registered user's password

### Non-Functional Requirements

- [x] Basic validation is enforced to ensure that invalid/improper data is not persisted
- [x] User passwords will be encrypted by the system before persisting them to the data source
- [x] Users are unable to recall reimbursement requests once they have been processed (only pending allowed)
- [x] Sensitive endpoints are protected from unauthenticated and unauthorized requesters using JWTs
- [x] Errors and exceptions are handled properly and their details are obfuscated from the user
- [x] The system conforms to RESTful architecture constraints
- [ ] The system's is tested with at least 80% line coverage in all service and utility classes
- [ ] The system keeps detailed logs on info, error, and fatal events that occur

### Suggested Bonus Features
- [ ] Authenticated employees are able to upload an receipt image along with their reimbursement request
- [x] Run your application within a Docker container
- [ ] Automate builds using Jenkins

## Scoring and Milestones

### Technical Scoring Rubric

| Requirement                                                                                       | Functional/Non-Functional | Value |
|---------------------------------------------------------------------------------------------------|---------------------------|-------|
| An new employee or new finance manager can request registration with the system                   | Functional                | 10    |
| An admin user can approve or deny new registration requests                                       | Functional                | 5     |
| A registered employee can authenticate with the system by providing valid credentials             | Functional                | 10    |
| An authenticated employee can view and manage their pending reimbursement requests                | Functional                | 10    |
| An authenticated employee can view their reimbursement request history (sortable and filterable)  | Functional                | 10    |
| An authenticated employee can submit a new reimbursement request                                  | Functional                | 10    |
| An authenticated finance manager can view a list of all pending reimbursement requests            | Functional                | 10    |
| An authenticated finance manager can view a history of requests that they have approved/denied    | Functional                | 10    |
| An authenticated finance manager can approve/deny reimbursement requests                          | Functional                | 10    |
| An admin user can deactivate user accounts, making them unable to log into the system             | Functional                | 5     |
| An admin user can reset a registered user's password                                              | Functional                | 5     |
| Basic validation is enforced to ensure that invalid/improper data is not persisted                | Non-Functional            | 10    |
| User passwords will be encrypted by the system before persisting them to the data source          | Non-Functional            | 5     |
| Users are unable to recall reimbursement requests once they have been processed                   | Non-Functional            | 10    |
| Sensitive endpoints are protected from unauthenticated and unauthorized requesters                | Non-Functional            | 5     |
| Errors and exceptions are handled properly and their details are obfuscated from the user         | Non-Functional            | 10    |
| The system conforms to RESTful architecture constraints                                           | Non-Functional            | 10    |
| The system's is tested with at least 80% line coverage in all service and utility classes         | Non-Functional            | 10    |
| The system's data schema and component design is documented and diagrammed                        | Non-Functional            | 5     |
| The system keeps detailed logs on info, error, and fatal events that occur                        | Non-Functional            | 5     |

### Soft Skill Scoring Rubric

| Metric                                                                                            | Value |
|---------------------------------------------------------------------------------------------------|-------|
| The associate is able to speak clearly and articulately                                           | 5     |
| The associate adheres to the presentation time limit                                              | 5     |
| The associate is able to correctly use technical terminology when discussing system functionality | 5     |
| The associate's demonstration is easy to follow and well-prepared                                 | 5     |

### Developer Scoring Rubric

| Metric                                                                                            | Value |
|---------------------------------------------------------------------------------------------------|-------|
| The application's codebase adheres to best practice naming conventions                            | 33    |
| The application's codebase follows some kind of organizational structure                          | 33    |
| The commit history of the project is detailed with concise and descriptive commits                | 34    |

## Milestones

- Project requirements delivered
- Remote repository is created and is being kept up to date
- Core model classes are created
- Registration/Authentication/User operations in progress
- Local DB instance running
- App to DB connection made
- Specified tables created with proper constraints
- Registration/Authentication/User operations complete
- Reimbursement operations in progress
- Basic persistence layer operations in progress
- Testing of business logic is in progress
- Registration/Authentication web endpoints are accessible and functional
- Reimbursement web endpoints are accessible and functional
- User passwords are encrypted when persisted to the DB
- Testing of business logic is in progress

#### 02Dec2022
- Project Presentations

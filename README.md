# Java Employee Reimbursement System

## Project Description
**as a note, reimbursements and tickets are used interchangeably in these instructions*

The ers_reimbursement system is an Application Program Interface (API) that allows companies to manage employee reimbursement requests.

This is achieved by allowing three types of users to access the system:
- Employee (Role 0): Employees can create tickets, after which they can access and manage their own pending reimbursement requests. Employees cannot access reimbursement requests after they have been resolved.


- Finance Manager (Role 1): Finance Managers can access all reimbursement tickets and ticket details and approve and deny tickets. However, they are not allowed to alter details of said tickets. Finance managers can also filter by pending tickets.


- Administrator (Role 2): Administrators can access all employees and help employees manage their own accounts by changing their account details. This enables Administrators to toggle access to accounts as well as change user roles.
  - Administrators do not explicitly delete users from database but rather disable database access to accounts. This is necessary to allow a user and the user's information to continue to be associated to a previously created ticket.

##### System Use Case Diagrams
![System Use Case Diagrams](https://raw.githubusercontent.com/221114-Java-React/Elias-Gonzalez-P1/main/reimbursementProgram/images/useCaseDiagram.png)


### Project Design Specifications and Documents

##### Relational Data Model
![Relational Model](https://raw.githubusercontent.com/221114-Java-React/Elias-Gonzalez-P1/main/reimbursementProgram/images/relationalModel.png)


##### Reimbursment System Flow
![Reimbursment Status State Flow](https://raw.githubusercontent.com/221114-Java-React/Elias-Gonzalez-P1/main/reimbursementProgram/images/SystemFlow.jpeg)

##### DEFAULT Endpoints

- /reimbursementSystem/users (POST/GET)
- /reimbursementSystem/users/update (PUT/GET)
- /reimbursementSystem/reimbursements (POST/GET)
- /reimbursementSystem/reimbursements/update (PUT/GET)
- /reimbursementSystem/auth (POST)

### Technologies

**Persistence Tier**
- PostGreSQL (running on Docker)

**Application Tier**
- **Java 8** - Main Programming Language
- **Apache Maven** - Dependency Manager
- **JDBC** - Database Connectivity API
- **Javalin** - Web Framework
- **JSON Web Tokens** - HTTP Payload Encryption
- **MessageDigest (SHA-256)** - Password Encryption
- **JUnit** - Java Unit Testing Framework
- **Mockito** - Java Class Mocking Framework

### Required Resources
- A directory named 'resources' must be created in the 'main' directory with a db.properties file. This must hold the properties of URL, username, and password to the PostGreSQL database. 
- In addition to the database properties, you must also add a salt string property to enable the JWT to sign authorization tokens with your custom 'salt'.

### Instructions
- First create the PostGreSQL database and configure the database to the Relational Data Model shown above.
  - DDL and DML starter files are available on request. Example usernames, passwords, and requests are included in the DML. 
  - Example password is passw0rd.
- After starting the database, configure the db.properties file described in Required Resources. The URL and Port must
be the same as the created database's URL and Port. 
- At this point you may configure the main driver's 'port' value in the MainDriver.java in order to specify the port you would like you make requests to.
- Generate HTTP requests to the paths specified in the Router.java class under the util directory.
  - Certain HTTP requests will require formatting. For example: POST to the /ticket endpoint will require an 'authorization' token in the header, 
as well as a body request. Please see the documentation below in 'Request Format' for more information. More detailed request formats can be provided at request or by looking under the DTO directory.


### Example Request Formats:
- Add User POST /reimbursementSystem/users
```
{
    "username":"tester001",
    "email":"tester001@gmail.com",
    "given_name":"Test",
    "surname":"McTest",
    "password1":"passw0rd",
    "password2":"passw0rd"
}
```
- Get All Tickets GET /reimbursementSystem/reimbursements
Place 'authorization' token in header
- Authenthicaton Request POST /reimbursementSystem/auth
```
{
    "username": "tester001",
    "password": "passw0rd"
}
```

### Functional Requirements

- [x] A new employee or new finance manager can request registration with the system
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


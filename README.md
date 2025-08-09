Users Microservice

This project is a Spring Boot-based microservice that manages user-related functionalities for the PCMS system. It includes features such as user authentication, authorization, and management of user plans. The service integrates with a MySQL database and is designed to work in a distributed system with Eureka for service discovery.

Features
Authentication and Authorization: Implements JWT-based authentication and role-based access control using Spring Security.
User Management: Provides APIs for managing users, including fetching user lists, updating user statuses, and retrieving user details.
Plan Management: Supports operations for managing user plans, such as adding, updating, and deleting plans.
Billing: Generates usage-based bills for users in PDF format.
CORS Configuration: Allows cross-origin requests for seamless integration with frontend applications.
Service Discovery: Integrated with Eureka for service registration and discovery.
Key Components
Security:

JwtAuthenticationFilter: Filters incoming requests to validate JWT tokens.
JwtHelper: Handles JWT token creation and validation.
SecurityConfig: Configures security settings, including role-based access and stateless sessions.
Controllers:

AuthController: Handles authentication-related endpoints.
HomeController: Manages user and plan-related operations.
Services:

UserService: Handles user-related business logic.
PlansService: Manages plan-related operations.
BillService: Generates bills and calculates usage statistics.
Configuration:

AppConfig: Provides beans for password encoding and authentication management.
CorsConfig: Configures CORS settings.
Prerequisites
Java: JDK 17 or higher.
Maven: Apache Maven 3.9.9 (configured with Maven Wrapper).
Database: MySQL with the following credentials:
URL: jdbc:mysql://127.0.0.1:3306/users
Username: root
Password: Darklord@1924
Running the Application
Clone the repository.
Configure the database connection in application.properties.
Build the project using Maven:
Run the application:
Access the application at http://localhost:8081.
API Endpoints
Authentication:
POST /auth/login: Authenticate a user and generate a JWT token.
User Management:
GET /home/getUsersList: Fetch all users (Admin only).
PUT /home/updateUserStatus: Approve or reject a user (Admin only).
Plan Management:
POST /USERPLANS/plan/addPlan: Add a new plan.
GET /home/getAllPlans: Fetch all plans (Admin only).
Billing:
GET /billing/generateBill/{userPlanId}: Generate a bill for a user plan.
Dependencies
Spring Boot Starter Security
Spring Boot Starter Web
Spring Boot Starter Data JPA
MySQL Connector
JSON Web Token (JJWT)
iText PDF for bill generation
 

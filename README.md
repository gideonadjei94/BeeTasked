# BeeTasked
A full-stack Java and React task management web application that allows team leaders to create and manage workspaces, assign tasks, and track progress seamlessly. This app supports role-based access, where only admins can assign and delete tasks, while users can view and complete tasks using a team code.

[![BeeTasked and 2 more pages - Profile 1 - Microsoft​ Edge 5_17_2024 5_44_13 PM](https://github.com/gideonadjei94/TaskManager/assets/124469965/88aee9ad-0290-48c9-8df3-179593d94149)](https://github.com/gideonadjei94/TaskManager/assets/124469965/5734af14-96e0-4be9-ad98-4505abd8ce57)

https://github.com/gideonadjei94/TaskManager/assets/124469965/fb27e2ea-9d15-4e74-85bf-12a68a124264

https://github.com/gideonadjei94/TaskManager/assets/124469965/c15c1c35-9176-47a5-bfa5-2443188ed3f2

# Features
* Create Task Assignment Space: Admins can create a new workspace for a team, known as the "Task Assignment Space."
* Team Code Generation: Upon creation, a unique team code is generated and sent via email to the workspace creator (Admin) to share with the team.
* Role-based Access:
  * Admins: Can create tasks, assign tasks, and delete tasks.
  * Users (Team Members): Can log in using the team code, view assigned tasks, and mark tasks as complete.
* Email Integration: Automatically sends the team code to the admin’s email upon workspace creation.
* Task Management: Admins can assign tasks to specific team members, and team members can manage their task statuses.
* User Authentication: Admins and users authenticate using the team code shared by the workspace admin.

# Technologies Used
## Backend:
* Spring Boot: Manages the backend APIs and task assignment logic.
* Java: Core backend language for business logic.
* Spring Data JPA: Used for database interactions with task and user management.
* MongoDBL: Database to store tasks, users, and workspace details.
* Spring Mail: To send emails with the team code.
  
## Frontend:
* ReactJS: Frontend framework for building a dynamic user interface.
* JavaScript (ES6+): For interactive client-side functionality.
* Redux: State management for handling the frontend logic.
  
## Miscellaneous:
* Axios: For making HTTP requests from the frontend to the backend.
* Git: Version control to track and manage project changes.
* Postman: For API testing.

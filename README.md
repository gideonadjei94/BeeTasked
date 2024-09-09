# BeeTasked
A full-stack Java and React task management web application that allows team leaders to create and manage workspaces, assign tasks, and track progress seamlessly. This app supports role-based access, where only admins can assign and delete tasks, while users can view and complete tasks using a team code.

![BeeTasked and 2 more pages - Profile 1 - Microsoft​ Edge 5_17_2024 5_44_13 PM](https://github.com/user-attachments/assets/7e528aee-9f10-4851-8940-2d80e4f20928)
![BeeTasked and 2 more pages - Profile 1 - Microsoft​ Edge 5_17_2024 5_44_13 PM](https://github.com/user-attachments/assets/1719d66f-4c63-4dc5-bb86-cb7581ba36dc)
https://github.com/user-attachments/assets/158ae43c-af5c-48df-bdc2-081c42724500

https://github.com/user-attachments/assets/74bde0ba-0bd7-4e14-98ce-45f98a1647f5

https://github.com/user-attachments/assets/9d5763a9-89ee-4cf5-94fb-3f9830b613ed

https://github.com/user-attachments/assets/b88c7c88-a4b2-4ba5-97dc-b6fddc6baa0b

https://github.com/user-attachments/assets/d9ee10d0-6dfa-425b-8af8-3707acde221e

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

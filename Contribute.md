# Contributing to [Project Name]

Thank you for your interest in contributing to [Project Name]! We welcome contributions from the community to make this project even better. Please take a moment to review the guidelines below.

## Getting Started

To contribute to [Project Name], follow these steps:

1. Fork the repository.
2. Clone your forked repository to your local machine.
3. Make sure you have MySQL installed on your local machine.
4. Open your preferred MySQL management tool (e.g., MySQL Workbench).
5. Create a new database named `taskcompass`.

## Database Configuration

The application uses MySQL as the database. To configure the database connection, follow these steps:

1. Open the `configClass.java` file located in the `src/main/java/com/alphaS/alphasolutions` directory.
2. Ensure that the following lines in the `configClass` file are set up correctly:

   ```java
   dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
   dataSource.setUrl("jdbc:mysql://localhost:3306/taskcompass");
   dataSource.setUsername("root");
   dataSource.setPassword("SabrinaMathilde");

Modify the values accordingly if your MySQL configuration is different.

 ## Database Schema

To set up the database schema, follow these steps:

1. Open your preferred MySQL management tool (e.g., MySQL Workbench).
2. Connect to your local MySQL instance.
3. Open a new SQL script window.
4. Copy the following SQL statements into the script window:


5. Execute the SQL script to create the necessary tables.

## Guidelines

Please follow these guidelines when contributing to [Project Name]:

- Before starting any work, create a new branch from the main branch with a descriptive name.
- Keep your code clean, well-structured, and follow the existing coding style.
- Write clear commit messages that explain the purpose of your changes.
- If you're adding new features or modifying existing ones, include appropriate tests.
- Document any new functions, classes, or significant changes in the codebase.
- Ensure your code passes all existing tests and doesn't introduce any new issues.

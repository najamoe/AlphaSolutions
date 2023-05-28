# Contributing to Taskcompass

Thank you for your interest in contributing to Taskcompass! We welcome contributions from the community to make this project even better. Please take a moment to review the guidelines below.

## Getting Started

To contribute to Taskcompass, follow these steps:

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

```
CREATE SCHEMA IF NOT EXISTS taskcompass;

CREATE TABLE taskcompass.Employee (
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    phone_no INT,
    employee_id INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE taskcompass.Client (
    client_name VARCHAR(255),
    contact_po_no INT,
    contact_person VARCHAR(255),
    company_po_no INT,
    address VARCHAR(255),
    zip_code INT,
    country VARCHAR(255),
    client_id INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE taskcompass.Project (
    project_name VARCHAR(255),
    project_description VARCHAR(255),
    start_date DATE,
    end_date DATE,
    client_id INT,
    employee_id INT,
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (employee_id) REFERENCES taskcompass.Employee (employee_id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES taskcompass.Client (client_id) ON DELETE CASCADE
);

ALTER TABLE taskcompass.Client
    ADD COLUMN project_id INT,
    ADD FOREIGN KEY (project_id) REFERENCES taskcompass.Project (project_id) ON DELETE CASCADE;

CREATE TABLE taskcompass.Subproject (
    sub_project_name VARCHAR(255),
    sub_project_description VARCHAR(255),
    subproject_id INT PRIMARY KEY AUTO_INCREMENT,
    project_id INT,
    FOREIGN KEY (project_id) REFERENCES taskcompass.Project (project_id) ON DELETE CASCADE
);

CREATE TABLE taskcompass.Task (
    task_name VARCHAR(255),
    description_task VARCHAR(255),
    est_days INT,
    est_hours INT,
    est_minutes INT,
    subproject_id INT,
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (subproject_id) REFERENCES taskcompass.subproject (subproject_id) ON DELETE CASCADE
);
```

5. Execute the SQL script to create the necessary tables.

## Deployment

Taskcompass is hosted on Render.com. Once you have made your changes and tested them locally, you can submit a pull request. Our team will review the changes and deploy them to the production environment.

## Guidelines

Please follow these guidelines when contributing to Taskcompass:

- Before starting any work, create a new branch from the main branch with a descriptive name.
- Keep your code clean, well-structured, and follow the existing coding style.
- Write clear commit messages that explain the purpose of your changes.
- If you're adding new features or modifying existing ones, include appropriate tests.
- Document any new functions, classes, or significant changes in the codebase.
- Ensure your code passes all existing tests and doesn't introduce any new issues.

## Contact
If you have any questions or need further assistance, feel free to contact us at:

- <a href="https://github.com/najamoe">Naja Egede Moe </a>
- <a href="https://github.com/MathildeTrendy">Mathilde Trend</a>
- <a href="https://github.com/sabr5840">Sabrina Ebbesen</a>

We appreciate your contribution and look forward to your involvement in improving Taskcompass!


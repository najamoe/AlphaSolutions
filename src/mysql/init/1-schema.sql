CREATE SCHEMA IF NOT EXISTS taskcompass;

USE taskcompass;

CREATE TABLE IF NOT EXISTS Employee (
                                        employee_id INT PRIMARY KEY AUTO_INCREMENT,
                                        first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    phone_no INT,
    employee_country VARCHAR(255),
    title VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS Client (
                                      client_id INT PRIMARY KEY AUTO_INCREMENT,
                                      client_name VARCHAR(255),
    contact_po_no INT,
    contact_person VARCHAR(255),
    company_po_no INT,
    address VARCHAR(255),
    zip_code INT,
    country VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS Team (
                                    team_id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(255),
    project_name VARCHAR(255),
    employee_id INT,
    teamemployees_id INT
    );

CREATE TABLE IF NOT EXISTS Team_employees (
                                              teamemployees_id INT PRIMARY KEY AUTO_INCREMENT,
                                              employee_id INT,
                                              team_id INT,
                                              FOREIGN KEY (employee_id) REFERENCES Employee (employee_id),
    FOREIGN KEY (team_id) REFERENCES Team (team_id)
    );

CREATE TABLE IF NOT EXISTS Task (
                                    task_id INT PRIMARY KEY AUTO_INCREMENT,
                                    task_name VARCHAR(255),
    description_task VARCHAR(255),
    est_time TIME,
    title_needed VARCHAR(255),
    status_name VARCHAR(255),
    status_color VARCHAR(255),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES Employee (employee_id)
    );

CREATE TABLE IF NOT EXISTS Project (
                                       project_id INT PRIMARY KEY AUTO_INCREMENT,
                                       project_name VARCHAR(255),
    project_description VARCHAR(255),
    start_date DATE,
    end_date DATE,
    client_id INT,
    employee_id INT,
    FOREIGN KEY (client_id) REFERENCES Client (client_id),
    FOREIGN KEY (employee_id) REFERENCES Employee (employee_id)
    );

CREATE TABLE IF NOT EXISTS Sub_project (
                                           subproject_id INT PRIMARY KEY AUTO_INCREMENT,
                                           sub_project_name VARCHAR(255),
    sub_project_description VARCHAR(255),
    project_id INT,
    FOREIGN KEY (project_id) REFERENCES Project (project_id)
    );

ALTER TABLE taskcompass.Team
    ADD CONSTRAINT fk_team_employee_id
        FOREIGN KEY (employee_id) REFERENCES taskcompass.Employee (employee_id);

ALTER TABLE taskcompass.Team
    ADD CONSTRAINT fk_team_teamemployees_id
        FOREIGN KEY (teamemployees_id) REFERENCES taskcompass.Team_employees (teamemployees_id);

ALTER TABLE taskcompass.Team_employees
    ADD CONSTRAINT fk_teamemployees_employee_id
        FOREIGN KEY (employee_id) REFERENCES taskcompass.Employee (employee_id);

ALTER TABLE taskcompass.Team_employees
    ADD CONSTRAINT fk_teamemployees_team_id
        FOREIGN KEY (team_id) REFERENCES taskcompass.Team (team_id);


CREATE SCHEMA IF NOT EXISTS taskcompass;

CREATE TABLE taskcompass.Employee (
                                      first_name VARCHAR(255),
                                      last_name VARCHAR(255),
                                      email VARCHAR(255),
                                      username VARCHAR(255),
                                      password VARCHAR(255),
                                      phone_no INT,
                                      employee_country VARCHAR(255),
                                      title VARCHAR(255),
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

CREATE TABLE taskcompass.Task (
                                  task_name VARCHAR(255),
                                  description_task VARCHAR(255),
                                  est_time TIME,
                                  title_needed VARCHAR(255),
                                  task_id INT PRIMARY KEY AUTO_INCREMENT,
                                  status_name VARCHAR(255),
                                  status_color VARCHAR(255),
                                  employee_id INT,
                                  FOREIGN KEY (employee_id) REFERENCES taskcompass.Employee (employee_id) ON DELETE CASCADE
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

CREATE TABLE taskcompass.Sub_project (
                                         sub_project_name VARCHAR(255),
                                         sub_project_description VARCHAR(255),
                                         subproject_id INT PRIMARY KEY AUTO_INCREMENT,
                                         project_id INT,
                                         FOREIGN KEY (project_id) REFERENCES taskcompass.Project (project_id) ON DELETE CASCADE
);

CREATE TABLE taskcompass.Team (
                                  team_name VARCHAR(255),
                                  team_id INT PRIMARY KEY AUTO_INCREMENT,
                                  employee_id INT,
                                  subproject_id INT,
                                  FOREIGN KEY (employee_id) REFERENCES taskcompass.Employee (employee_id) ON DELETE CASCADE,
                                  FOREIGN KEY (subproject_id) REFERENCES taskcompass.Sub_project (subproject_id) ON DELETE CASCADE
);

CREATE TABLE taskcompass.Team_employees(
                                           employee_id INT,
                                           team_id INT,
                                           teamemployees_id INT PRIMARY KEY AUTO_INCREMENT,
                                           FOREIGN KEY (employee_id) REFERENCES taskcompass.Employee (employee_id) ON DELETE CASCADE,
                                           FOREIGN KEY (team_id) REFERENCES taskcompass.Team (team_id) ON DELETE CASCADE
);

ALTER TABLE taskcompass.Task
    ADD COLUMN subproject_id INT,
    ADD FOREIGN KEY (subproject_id) REFERENCES taskcompass.Sub_project (subproject_id) ON DELETE CASCADE;

ALTER TABLE taskcompass.Team
    ADD COLUMN teamemployees_id INT,
    ADD FOREIGN KEY (teamemployees_id) REFERENCES taskcompass.Team_employees (teamemployees_id) ON DELETE CASCADE;

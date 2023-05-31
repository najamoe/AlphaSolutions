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
                                  FOREIGN KEY (subproject_id) REFERENCES taskcompass.Subproject (subproject_id) ON DELETE CASCADE
);



INSERT INTO taskcompass.Employee (first_name, last_name, email, username, password, phone_no, user_country, title)
VALUES ('John', 'Doe', 'john.doe@example.com', 'johndoe', '1234', 12345678, 'DK', 'Developer'),
       ('Jane', 'Smith', 'jane.smith@example.com', 'janesmith', '1234', 98765432, 'DK', 'Project Manager'),
       ('Mike', 'Johnson', 'mike.johnson@example.com', 'mikejohnson', '1234', 56789012, 'DK', 'Designer');


INSERT INTO taskcompass.Client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country)
VALUES
    ('ABC Inc.', 11111111, 'John Smith', 99999999, '123 Main St.', 3400, 'DK'),
    ('XYZ Ltd.', 22222222, 'Jane Doe', 88888888, '456 Maple Ave.', 2950, 'DK'),
    ('PQR Co.', 33333333, 'Bob Johnson', 77777777, '789 Oak St.', 3450, 'DK');

INSERT INTO taskcompass.Task (task_name, description_task, est_time, title_needed, status_name, status_color)
VALUES
    ('Design UI', 'Design UI for the website', '2:30:00', 'Designer', 'In Progress', 'Yellow'),
    ('Develop Backend', 'Develop Backend for the website', '6:00:00', 'Backend Developer', 'Not Started', 'Red'),
    ('Write Tests', 'Write unit tests for the website', '1:00:00', 'Quality Assurance', 'Completed', 'Green');

INSERT INTO taskcompass.Project (project_name, project_description, start_date, end_date, client_id, user_id)
VALUES
    ('Project A', 'Development of Website', '2022-01-01', '2022-12-31', 1, 1),
    ('Project B', 'Design of Mobile App', '2023-01-01', '2023-12-31', 2, 2),
    ('Project C', 'Data Analytics', '2024-01-01', '2024-12-31', 3, 3);

INSERT INTO taskcompass.Sub_project (sub_project_name, sub_project_description, project_id)
VALUES
    ('Subproject 1A', 'Development of Frontend', 1),
    ('Subproject 1B', 'Development of Backend', 1),
    ('Subproject 2A', 'UI/UX Design', 2),
    ('Subproject 2B', 'Mobile App Development', 2),
    ('Subproject 3A', 'Data Collection', 3),
    ('Subproject 3B', 'Data Analysis', 3);

INSERT INTO taskcompass.Team (name, project_name, user_id)
VALUES
    ('Team A', 'Project A', 1),
    ('Team B', 'Project B', 2),
    ('Team C', 'Project C', 3);

INSERT INTO taskcompass.Team_employees (user_id, team_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);
INSERT INTO taskcompass.User (first_name, last_name, email, username, password, phone_no, user_country, title, user_id)
VALUES ('John', 'Doe', 'john.doe@example.com', 'johndoe', 'password123', 21467377, 'DK', 'Manager', 1),
       ('Jane', 'Doe', 'jane.doe@example.com', 'janedoe', 'password456', 98374654, 'DK', 'Developer', 2),
       ('Bob', 'Smith', 'bob.smith@example.com', 'bobsmith', 'password789', 23748576, 'DK', 'Designer', 3);

INSERT INTO taskcompass.Client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country, client_id)
VALUES ('ABC Corp', 48791326, 'John Smith', 28779135, 'Hovedgaden 4', 1235, 'DK', 1),
       ('XYZ Inc', 21293067, 'Jane Smith', 20936197, 'byvej 7', 6780, 'DK', 2);

INSERT INTO taskcompass.Task (task_name, description_task, est_time, title_needed, task_id)
VALUES ('Task 1', 'Design af database', '12:30:00', 'Developer', 1),
       ('Task 2', 'UX af forside', '08:15:00', 'Designer', 2);

INSERT INTO taskcompass.Project (project_name, description, start_date, end_date, client_id, user_id, project_id)
VALUES ('Project 1', 'Description of Project 1', '2023-05-01', '2023-06-30', 1, 1, 1),
       ('Project 2', 'Description of Project 2', '2023-07-01', '2023-08-31', 2, 2, 2);

INSERT INTO taskcompass.Sub_project (sub_project_name, sub_project_description, subproject_id, project_id)
VALUES ('Sub-project 1', 'Description of Sub-project 1', 1, 1),
       ('Sub-project 2', 'Description of Sub-project 2', 2, 2);

INSERT INTO taskcompass.Team (name, project_name, team_id, user_id, teamusers_id)
VALUES ('Team 1', 'Project 1', 1, 1, 1),
       ('Team 2', 'Project 2', 2, 2, 2);

INSERT INTO taskcompass.Team_users (user_id, team_id, teamusers_id)
VALUES (1, 1, 1),
       (2, 2, 2);

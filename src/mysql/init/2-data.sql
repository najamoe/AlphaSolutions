-- Insert sample data into the Employee table
INSERT INTO taskcompass.Employee (first_name, last_name, email, username, password, phone_no, employee_country, title)
VALUES
    ('John', 'Doe', 'johndoe@example.com', 'johndoe', '1234', 12345678, 'USA', 'Manager'),
    ('Jane', 'Smith', 'janesmith@example.com', 'janesmith', '1234', 98765210, 'UK', 'Developer'),
    ('Mike', 'Johnson', 'mikejohnson@example.com', 'mikejohnson', 'password3', 23478901, 'Canada', 'Designer');

-- Insert sample data into the Client table
INSERT INTO taskcompass.Client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country)
VALUES
    ('ABC Company', 1234789, 'John Smith', 9876521, '123 Main St', 12345, 'DK'),
    ('XYZ Corporation', 9854321, 'Jane Doe', 1234789, '456 Elm St', 54321, 'DK');

-- Insert sample data into the Task table
INSERT INTO taskcompass.Task (task_name, description_task, est_time, title_needed, employee_id)
VALUES
    ('Task 1', 'Description for Task 1', '10:00:00', 'Title Needed for Task 1', 1),
    ('Task 2', 'Description for Task 2', '05:30:00', 'Title Needed for Task 2', 2),
    ('Task 3', 'Description for Task 3', '08:45:00', 'Title Needed for Task 3', 3);

-- Insert sample data into the Project table
INSERT INTO taskcompass.Project (project_name, project_description, start_date, end_date, client_id, employee_id)
VALUES
    ('Project 1', 'Description for Project 1', '2023-01-01', '2023-02-28', 1, 1),
    ('Project 2', 'Description for Project 2', '2023-03-01', '2023-04-30', 2, 2);

-- Insert sample data into the Sub_project table
INSERT INTO taskcompass.Sub_project (sub_project_name, sub_project_description, project_id)
VALUES
    ('Subproject 1', 'Description for Subproject 1', 1),
    ('Subproject 2', 'Description for Subproject 2', 1),
    ('Subproject 3', 'Description for Subproject 3', 2);

-- Insert sample data into the Team table
INSERT INTO taskcompass.Team (team_name, project_name, employee_id, subproject_id)
VALUES
    ('Team 1', 'Project 1', 1, 1),
    ('Team 2', 'Project 2', 2, 3),
    ('Team 3', 'Project 2', 3, 3);

-- Insert sample data into the Team_employees table
INSERT INTO taskcompass.Team_employees (employee_id, team_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 3);

-- Update sample data in the Task table to associate with subprojects
UPDATE taskcompass.Task SET subproject_id = 1 WHERE task_id = 1;
UPDATE taskcompass.Task SET subproject_id = 2 WHERE task_id = 2;
UPDATE taskcompass.Task SET subproject_id = 3 WHERE task_id = 3;

-- Update sample data in the Team table to associate with subprojects
UPDATE taskcompass.Team SET subproject_id = 1 WHERE team_id = 1;
UPDATE taskcompass.Team SET subproject_id = 3 WHERE team_id = 2;
UPDATE taskcompass.Team SET subproject_id = 3 WHERE team_id = 3;

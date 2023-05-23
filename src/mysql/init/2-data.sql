INSERT INTO taskcompass.Employee (first_name, last_name, email, username, password, phone_no)
VALUES
    ('John', 'Doe', 'johndoe@example.com', 'johndoe', '1234', 12345678),
    ('Jane', 'Smith', 'janesmith@example.com', 'janesmith', '1234', 98765210),
    ('Mike', 'Johnson', 'mikejohnson@example.com', 'mikejohnson', 'password3', 23478901);

INSERT INTO taskcompass.Client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code)
VALUES
    ('ABC Company', 1234789, 'John Smith', 9876521, '123 Main St', 12345),
    ('XYZ Corporation', 9854321, 'Jane Doe', 1234789, '456 Elm St', 54321);


INSERT INTO taskcompass.Task (task_name, description_task, est_time, subproject_id)
VALUES
    ('Task 1', 'Description for Task 1', '10:00:00', 1),
    ('Task 2', 'Description for Task 2', '05:30:00', 2),
    ('Task 3', 'Description for Task 3', '08:45:00', 3);



INSERT INTO taskcompass.Project (project_name, project_description, start_date, end_date, client_id, employee_id)
VALUES
    ('Project 1', 'Description for Project 1', '2023-01-01', '2023-02-28', 1, 1),
    ('Project 2', 'Description for Project 2', '2023-03-01', '2023-04-30', 2, 2),
    ('Project 3', 'Description for Project 3', '2023-03-01', '2023-04-30', 2, 2),
    ('Project 2', 'Description for Project 2', '2023-03-01', '2023-04-30', 1, 1);

INSERT INTO taskcompass.Subproject (sub_project_name, sub_project_description, project_id)
VALUES
    ('Subproject 1', 'Description for Subproject 1', 1),
    ('Subproject 2', 'Description for Subproject 2', 1),
    ('Subproject 3', 'Description for Subproject 3', 2);

UPDATE taskcompass.Task SET subproject_id = 1 WHERE task_id = 1;
UPDATE taskcompass.Task SET subproject_id = 2 WHERE task_id = 2;
UPDATE taskcompass.Task SET subproject_id = 3 WHERE task_id = 3;



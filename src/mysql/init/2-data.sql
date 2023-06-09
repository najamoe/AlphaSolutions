-- Insert sample data into the Employee table
INSERT INTO taskcompass.Employee (first_name, last_name, email, username, password, phone_no )
VALUES
    ('John', 'Doe', 'johndoe@example.com', 'johndoe', '1234', 12345678),
    ('Jane', 'Smith', 'janesmith@example.com', 'janesmith', '1234', 98765210),
    ('Mike', 'Johnson', 'mikejohnson@example.com', 'mikejohnson', 'password3', 23478901);

-- Insert sample data into the Client table
INSERT INTO taskcompass.Client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country)
VALUES
    ('ABC Company', 1234789, 'John Smith', 9876521, '123 Main St', 1234, 'DK'),
    ('XYZ Corporation', 9854321, 'Jane Doe', 1234789, '456 Elm St', 5432, 'DK'),
    ('Pepsi Corp', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Cola Corp', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Faxe Corp', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Insta Corp', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Ubranista Corp', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('FITBIT', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Logitec inc.', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('TRUST aps', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Laugh limited', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK'),
    ('Barthur and co.', 48791326, 'John Hancock', 21467376, 'Byvej 6', 2950, 'DK');

-- Insert sample data into the Project table
INSERT INTO taskcompass.Project (project_name, project_description, start_date, end_date, client_id, employee_id)
VALUES
    ('Project 1', 'Description', '2023-01-01', '2023-02-28', 1, 1),
    ('Project 2', 'Description', '2023-03-01', '2023-04-30', 2, 2),
    ('Project 3', 'Description ', '2023-03-01', '2023-04-30', 3, 2),
    ('Project 4', 'Description', '2023-03-01', '2023-04-30', 1, 1),
    ('Project 5', 'Description ', '2023-01-01', '2023-02-28', 2, 2),
    ('Project 6', 'Description ', '2023-03-01', '2023-04-30', 3, 3),
    ('Project 7', 'Description', '2023-01-01', '2023-02-28', 1, 1),
    ('Project 8', 'Description ', '2023-03-01', '2023-04-30', 2, 2),
    ('Project 9', 'Description ', '2023-03-01', '2023-04-30', 3, 2),
    ('Project 10', 'Description ', '2023-03-01', '2023-04-30', 1, 1),
    ('Project 11', 'Description for ', '2023-01-01', '2023-02-28', 2, 2),
    ('Project 12', 'Description for ', '2023-03-01', '2023-04-30', 3, 3);

INSERT INTO taskcompass.Subproject (sub_project_name, sub_project_description, project_id)
VALUES
    ('Subproject 1', 'Description for Subproject 1', 1),
    ('Subproject 2', 'Description for Subproject 2', 2),
    ('Subproject 3', 'Description for Subproject 3', 3),
    ('Subproject 4', 'Description for Subproject 1', 4),
    ('Subproject 5', 'Description for Subproject 2', 5),
    ('Subproject 6', 'Description for Subproject 3', 6),
    ('Subproject 7', 'Description for Subproject 1', 7),
    ('Subproject 8', 'Description for Subproject 2', 8),
    ('Subproject 9', 'Description for Subproject 3', 9),
    ('Subproject 10', 'Description for Subproject 1', 10),
    ('Subproject 11', 'Description for Subproject 2', 11),
    ('Subproject 12', 'Description for Subproject 3', 12);


INSERT INTO taskcompass.Task (task_name, description_task, est_days, est_hours, est_minutes, subproject_id)
VALUES ('Task 1', 'Description ', 0, 10, 0, 1),
       ('Task 2', 'Description', 0, 5, 30, 2),
       ('Task 3', 'Description', 0, 8, 45, 3),
       ('Task 4', 'Description ', 0, 10, 0, 4),
       ('Task 5', 'Description ', 0, 5, 30, 5),
       ('Task 6', 'Description ', 0, 8, 45, 6),
       ('Task 7', 'Description ', 0, 10, 0, 7),
       ('Task 8', 'Description ', 0, 5, 30, 8),
       ('Task 9', 'Description ', 0, 8, 45, 9),
       ('Task 10', 'Description ', 0, 10, 0, 10),
       ('Task 11', 'Description ', 0, 5, 30, 11),
       ('Task 12', 'Description ', 2, 8, 45, 12),
       ('Task 13', 'Description ', 0, 10, 0, 1),
       ('Task 14', 'Description', 0, 5, 30, 2),
       ('Task 15', 'Description', 0, 8, 45, 3),
       ('Task 16', 'Description ', 0, 10, 0, 4),
       ('Task 17', 'Description ', 0, 5, 30, 5),
       ('Task 18', 'Description ', 0, 8, 45, 6),
       ('Task 19', 'Description ', 4, 10, 0, 7),
       ('Task 20', 'Description ', 0, 5, 30, 8),
       ('Task 21', 'Description ', 0, 8, 45, 9),
       ('Task 22', 'Description ', 0, 10, 0, 10),
       ('Task 23', 'Description ', 0, 5, 30, 11),
       ('Task 24', 'Description ', 0, 8, 45, 12),
       ('Task 25', 'Description ', 0, 10, 0, 1),
       ('Task 26', 'Description', 0, 5, 30, 2),
       ('Task 27', 'Description', 0, 8, 45, 3),
       ('Task 28', 'Description ', 6, 10, 0, 4),
       ('Task 29', 'Description ', 0, 5, 30, 5),
       ('Task 30', 'Description ', 0, 8, 45, 6),
       ('Task 31', 'Description ', 0, 10, 0, 7),
       ('Task 32', 'Description ', 0, 5, 30, 8),
       ('Task 33', 'Description ', 7, 8, 45, 9),
       ('Task 34', 'Description ', 0, 10, 0, 10),
       ('Task 35', 'Description ', 0, 5, 30, 11),
       ('Task 36', 'Description ', 0, 8, 45, 12),
       ('Task 37', 'Description ', 0, 10, 0, 1),
       ('Task 38', 'Description', 0, 5, 30, 2),
       ('Task 39', 'Description', 0, 8, 45, 3),
       ('Task 40', 'Description ', 0, 10, 0, 4),
       ('Task 41', 'Description ', 0, 5, 30, 5),
       ('Task 42', 'Description ', 0, 8, 45, 6),
       ('Task 43', 'Description ', 0, 10, 0, 7),
       ('Task 44', 'Description ', 0, 5, 30, 8),
       ('Task 45', 'Description ', 0, 8, 45, 9),
       ('Task 46', 'Description ', 0, 10, 0, 10),
       ('Task 47', 'Description ', 0, 5, 30, 11),
       ('Task 48', 'Description ', 0, 8, 45, 12);

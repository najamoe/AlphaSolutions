INSERT INTO taskcompass.User (first_name, last_name, email, username, password, phone_no, user_country, title, user_id)
VALUES ('John', 'Doe', 'johndoe@example.com', 'johndoe', 'password123', 12345678, 'USA', 'Software Engineer', 1),
       ('Jane', 'Doe', 'janedoe@example.com', 'janedoe', 'password123', 12345678, 'USA', 'Project Manager', 2),
       ('Bob', 'Smith', 'bobsmith@example.com', 'bobsmith', 'password123', 12345678, 'UK', 'UX Designer', 3),
       ('Alice', 'Johnson', 'alicejohnson@example.com', 'alicejohnson', 'password123', 12345678, 'Canada',
        'Frontend Developer', 4);

INSERT INTO taskcompass.Client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country,
                                client_id)
VALUES ('Acme Corporation', 12347894, 'John Smith', 56789014, '123 Main St', 12345, 'USA', 1),
       ('Globex Corporation', 12345895, 'Jane Doe', 67892345, '456 Maple Ave', 56789, 'USA', 2),
       ('Wayne Enterprises', 12345696, 'Bruce Wayne', 78923456, '789 Oak St', 67890, 'USA', 3);

INSERT INTO taskcompass.Project (project_name, description, start_date, end_date, client_id, user_id, project_id)
VALUES ('Project 1', 'This is project 1', '2022-01-01', '2022-03-31', 1, 1, 1),
       ('Project 2', 'This is project 2', '2022-04-01', '2022-06-30', 2, 2, 2),
       ('Project 3', 'This is project 3', '2022-07-01', '2022-09-30', 3, 3, 3);

INSERT INTO taskcompass.Sub_project (sub_project_name, sub_project_description, subproject_id, project_id)
VALUES ('Subproject 1.1', 'This is subproject 1.1', 1, 1),
       ('Subproject 1.2', 'This is subproject 1.2', 2, 1),
       ('Subproject 2.1', 'This is subproject 2.1', 3, 2),
       ('Subproject 2.2', 'This is subproject 2.2', 4, 2),
       ('Subproject 3.1', 'This is subproject 3.1', 5, 3),
       ('Subproject 3.2', 'This is subproject 3.2', 6, 3);

INSERT INTO taskcompass.Team (team_name, project_id, team_id)
VALUES ('Team 1', 1, 1),
       ('Team 2', 2, 2),
       ('Team 3', 3, 3);

INSERT INTO taskcompass.User_Team (user_id, team_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 1),
       (3, 2),
       (4, 2)
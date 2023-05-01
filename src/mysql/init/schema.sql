CREATE SCHEMA IF NOT EXISTS taskcompass;

CREATE TABLE taskcompass.User (
                                  first_name varchar(255),
                                  last_name varchar(255),
                                  email varchar(255),
                                  username varchar(255),
                                  password varchar(255),
                                  phone_no int,
                                  user_country varchar(255),
                                  title varchar(255),
                                  user_id int PRIMARY KEY
);



CREATE TABLE taskcompass.Client (
                                    client_name varchar(255),
                                    contact_po_no int,
                                    contact_person varchar(255),
                                    company_po_no int,
                                    address varchar(255),
                                    zip_code int,
                                    country varchar(255),
                                    client_id int PRIMARY KEY
);

CREATE TABLE taskcompass.Task (
                                  task_name varchar(255),
                                  description_task varchar(255),
                                  est_time time,
                                  title_needed varchar(255),
                                  task_id int PRIMARY KEY
);

CREATE TABLE taskcompass.Project (
                                     project_name varchar(255),
                                     description varchar(255),
                                     start_date date,
                                     end_date date,
                                     client_id int,
                                     user_id int,
                                     project_id int primary key,
                                     FOREIGN KEY (user_id) REFERENCES User (user_id),
                                     FOREIGN KEY (client_id) REFERENCES Client (client_id)
);

CREATE TABLE taskcompass.Sub_project (
                                         sub_project_name varchar(255),
                                         sub_project_description varchar(255),
                                         subproject_id int PRIMARY KEY,
                                         project_id int,
                                         FOREIGN KEY (project_id) REFERENCES Project (project_id)
);


CREATE TABLE taskcompass.Team (
                                  name varchar(255),
                                  project_name varchar(255),
                                  team_id int PRIMARY KEY,
                                  user_id int,
                                  teamusers_id int
);

CREATE TABLE taskcompass.Team_users (
                                        user_id int,
                                        team_id int,
                                        teamusers_id int PRIMARY KEY
);

ALTER TABLE taskcompass.Team
    ADD CONSTRAINT fk_team_user_id
        FOREIGN KEY (user_id) REFERENCES taskcompass.User (user_id);

ALTER TABLE taskcompass.Team
    ADD CONSTRAINT fk_team_teamusers_id
        FOREIGN KEY (teamusers_id) REFERENCES taskcompass.Team_users (teamusers_id);

ALTER TABLE taskcompass.Team_users
    ADD CONSTRAINT fk_teamusers_user_id
        FOREIGN KEY (user_id) REFERENCES taskcompass.User (user_id);

ALTER TABLE taskcompass.Team_users
    ADD CONSTRAINT fk_teamusers_team_id
        FOREIGN KEY (team_id) REFERENCES taskcompass.Team (team_id);
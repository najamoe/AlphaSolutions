CREATE TABLE `User` (
  `first_name` varchar,
  `last_name` varchar,
  `email` varchar,
  `username` varchar,
  `password` varchar,
  `phone_no` int,
  `user_country` varchar,
  `title` varchar,
  `user_id` int,
  KEY `pk` (`user_id`)
);

CREATE TABLE `Project` (
  `project_name` varchar,
  `description` varchar,
  `start_date` date,
  `end_date` date,
  `client_id` int,
  `user_id` int,
  `project_id` int,
  FOREIGN KEY (`project_id`) REFERENCES `User`(`username`),
  KEY `fk` (`client_id`, `user_id`),
  KEY `pk` (`project_id`)
);

CREATE TABLE `Client` (
  `client_name` varchar,
  `contact_po_no` int,
  `contact_person` varchar,
  `company_po_no` int,
  `address` varchar,
  `zip_code` int,
  `country` varchar,
  `client_id` int,
  KEY `pk` (`client_id`)
);

CREATE TABLE `Team_users` (
  `user_id` int,
  `team_id` int,
  `teamusers_id` int,
  KEY `fk` (`user_id`, `team_id`),
  KEY `pk` (`teamusers_id`)
);

CREATE TABLE `Task` (
  `task_name` varchar,
  `description_task` varchar,
  `est_time` time,
  `title_needed` varchar,
  `task_id` int,
  KEY `pk` (`task_id`)
);

CREATE TABLE `Sub_project` (
  `sub_project_name` varchar,
  `sub_project_description` varchar,
  `subproject_id` int,
  KEY `pk` (`subproject_id`)
);

CREATE TABLE `Team` (
  `name` varchar,
  `project_name` varchar,
  `team_id` int,
  `teamusers_id` int,
  FOREIGN KEY (`team_id`) REFERENCES `User`(`title`),
  KEY `pk` (`team_id`),
  KEY `fk` (`teamusers_id`)
);

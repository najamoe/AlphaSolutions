CREATE SCHEMA IF NOT EXISTS `taskcompass` ;

CREATE TABLE `User` (
  `first_name` varchar(255),
  `last_name`varchar(255),
  `email`varchar(255),
  `username` varchar(255),
  `password` varchar(255),
  `phone_no` int,
  `user_country` varchar(255),
  `title` varchar(255),
  `user_id` int PRIMARY KEY
);

CREATE TABLE `Project` (
  `project_name` varchar(255),
  `description` varchar(255),
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
  `client_name` varchar(255),
  `contact_po_no` int,
  `contact_person` varchar(255),
  `company_po_no` int,
  `address` varchar(255),
  `zip_code` int,
  `country` varchar(255),
  `client_id` int PRIMARY KEY
);

CREATE TABLE `Team_users` (
  `user_id` int,
  `team_id` int,
  `teamusers_id` int,
  KEY `fk` (`user_id`, `team_id`),
  KEY `pk` (`teamusers_id`)
);

CREATE TABLE `Task` (
  `task_name` varchar(255),
  `description_task` varchar(255),
  `est_time` time,
  `title_needed` varchar(255),
  `task_id` int PRIMARY KEY
);

CREATE TABLE `Sub_project` (
  `sub_project_name` varchar(255),
  `sub_project_description` varchar(255),
  `subproject_id` int PRIMARY KEY
 );

CREATE TABLE `Team` (
  `name` varchar(255),
  `project_name` varchar(255),
  `team_id` int,
  `teamusers_id` int,
  FOREIGN KEY (`team_id`) REFERENCES `User`(`title`),
  KEY `pk` (`team_id`),
  KEY `fk` (`teamusers_id`)
);

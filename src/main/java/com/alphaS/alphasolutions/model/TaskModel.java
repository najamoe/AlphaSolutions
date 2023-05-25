package com.alphaS.alphasolutions.model;

import java.time.LocalDate;
import java.time.LocalTime;

    public class TaskModel {
        private int taskId;
        private String taskName;
        private String taskDescription;
        private int estDays;
        private int estHours;
        private int estMinutes;

        public TaskModel(int taskId, String taskName, String taskDescription, int estDays, int estHours, int estMinutes) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.estDays = estDays;
            this.estHours = estHours;
            this.estMinutes = estMinutes;
        }

        public TaskModel() {

        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public int getEstDays() {
            return estDays;
        }

        public void setEstDays(int estDays) {
            this.estDays = estDays;
        }

        public int getEstHours() {
            return estHours;
        }

        public void setEstHours(int estHours) {
            this.estHours = estHours;
        }

        public int getEstMinutes() {
            return estMinutes;
        }

        public void setEstMinutes(int estMinutes) {
            this.estMinutes = estMinutes;
        }
    }

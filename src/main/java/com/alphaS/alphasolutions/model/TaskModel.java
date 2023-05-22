package com.alphaS.alphasolutions.model;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TaskModel {
    private int taskId;
    private String taskName;
    private String taskDescription;
    private LocalTime estTime;
    private LocalDate deadline;
    private String jobTitleNeeded;
    private String status;
    private Color color;
    private EmployeeModel assignedEmployee;

    public TaskModel(int taskId, String taskName, String taskDescription, LocalTime estTime, LocalDate deadline, String jobTitleNeeded, String status, Color color) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.estTime = estTime;
        this.deadline = deadline;
        this.jobTitleNeeded = jobTitleNeeded;
        this.status = status;
        this.color = color;
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

    public LocalTime getEstTime() {
        return estTime;
    }

    public void setEstTime(LocalTime estTime) {
        this.estTime = estTime;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getJobTitleNeeded() {
        return jobTitleNeeded;
    }

    public void setJobTitleNeeded(String jobTitleNeeded) {
        this.jobTitleNeeded = jobTitleNeeded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}

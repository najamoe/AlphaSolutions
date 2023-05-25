package com.alphaS.alphasolutions.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskModel {
    private int taskId;
    private String taskName;
    private String taskDescription;
    private LocalTime estTime;

    public TaskModel(int taskId, String taskName, String taskDescription, LocalTime estTime) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.estTime = estTime;
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


}

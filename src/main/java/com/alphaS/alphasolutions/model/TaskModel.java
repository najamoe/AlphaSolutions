package com.alphaS.alphasolutions.model;

import java.time.LocalTime;

public class TaskModel {

    private String taskName;
    private String taskDescription;
    private LocalTime estTime;
    private String jobTitleNeeded;

    public TaskModel(String taskName, String taskDescription, LocalTime estTime, String jobTitleNeeded) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.estTime = estTime;
        this.jobTitleNeeded = jobTitleNeeded;
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

    public String getJobTitleNeeded() {
        return jobTitleNeeded;
    }

    public void setJobTitleNeeded(String jobTitleNeeded) {
        this.jobTitleNeeded = jobTitleNeeded;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", estTime=" + estTime +
                ", jobTitleNeeded='" + jobTitleNeeded + '\'' +
                '}';
    }
}

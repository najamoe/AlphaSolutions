package com.alphaS.alphasolutions.model;

import java.time.LocalDate;

public class ProjectModel {

    private int projectId;
    private String projectName;
    private String projectDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private int client_id;
    private int user_id;

    public ProjectModel(int projectId, String projectName, String projectDescription, LocalDate startDate, LocalDate endDate, int client_id, int user_id) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client_id = client_id;
        this.user_id = user_id;
    }

    public ProjectModel() {

    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectDescriptopn='" + projectDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", client_id=" + client_id +
                ", user_id=" + user_id +
                '}';
    }
}

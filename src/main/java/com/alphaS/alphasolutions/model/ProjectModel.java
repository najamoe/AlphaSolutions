package com.alphaS.alphasolutions.model;

import java.time.LocalDate;

public class ProjectModel {

    private String projectName;
    private String projectDescriptopn;
    private LocalDate startDate;
    private LocalDate endDate;

    public ProjectModel(String projectName, String projectDescriptopn, LocalDate startDate, LocalDate endDate) {
        this.projectName = projectName;
        this.projectDescriptopn = projectDescriptopn;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescriptopn() {
        return projectDescriptopn;
    }

    public void setProjectDescriptopn(String projectDescriptopn) {
        this.projectDescriptopn = projectDescriptopn;
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

    @Override
    public String toString() {
        return "ProjectModel{" +
                "projectName='" + projectName + '\'' +
                ", projectDescriptopn='" + projectDescriptopn + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

package com.alphaS.alphasolutions.model;

public class TeamModel {

    private String teamName;
    private String projectName;

    public TeamModel(String teamName, String projectName) {
        this.teamName = teamName;
        this.projectName = projectName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    @Override
    public String toString() {
        return "TeamModel{" +
                "teamName='" + teamName + '\'' +
                ", projectName='" + projectName + '\'' +
               '}';
    }
}

package com.alphaS.alphasolutions.model;

public class TeamModel {

    private String teamName;
    private String projectName;

    public TeamModel(String teamName, String projectName) {
        this.teamName = teamName;
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

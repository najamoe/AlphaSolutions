package com.alphaS.alphasolutions.model;

public class TeamModel {

    private String teamName;
    private int teamId;

    public TeamModel(String teamName, int teamId) {
        this.teamName = teamName;
        this.teamId = teamId;
    }

    public TeamModel() {

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}






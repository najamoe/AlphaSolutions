package com.alphaS.alphasolutions.model;

public class TeamModel {


    public TeamModel() {

    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public TeamModel(int teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    private int teamId;
    private String teamName;

}

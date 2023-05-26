package com.alphaS.alphasolutions.model;

public class SubprojectModel {
    private int subprojectId;
    private String subprojectName;
    private String subprojectDescription;

    public SubprojectModel(int subprojectId, String subProjectName, String subProjectDescription) {
        this.subprojectId = subprojectId;
        this.subprojectName = subProjectName;
        this.subprojectDescription = subProjectDescription;
    }

    public SubprojectModel() {
    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public String getSubprojectDescription() {
        return subprojectDescription;
    }

    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
    }

    @Override
    public String toString() {
        return "SubprojectModel{" +
                "subprojectId=" + subprojectId +
                ", subProjectName='" + subprojectName + '\'' +
                ", subProjectDescription='" + subprojectDescription + '\'' +
                '}';
    }
}

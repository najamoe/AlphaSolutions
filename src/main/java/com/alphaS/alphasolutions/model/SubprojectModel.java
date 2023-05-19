package com.alphaS.alphasolutions.model;

public class SubprojectModel {
    private int subprojectId;
    private String subProjectName;
    private String subProjectDescription;

    public SubprojectModel(int subprojectId, String subProjectName, String subProjectDescription) {
        this.subprojectId = subprojectId;
        this.subProjectName = subProjectName;
        this.subProjectDescription = subProjectDescription;
    }

    public SubprojectModel() {
    }


    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubProjectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSubProjectDescription() {
        return subProjectDescription;
    }

    public void setSubProjectDescription(String subProjectDescription) {
        this.subProjectDescription = subProjectDescription;
    }

    @Override
    public String toString() {
        return "SubProjectModel{" +
                "subProjectId='" + subprojectId + '\'' +
                "subProjectName='" + subProjectName + '\'' +
                ", subProjectDescription='" + subProjectDescription + '\'' +
                '}';
    }
}

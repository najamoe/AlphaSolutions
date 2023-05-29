package com.alphaS.alphasolutions.model;

public class SubprojectModel {
    private int subprojectId;
    private String subprojectName;
    private String subprojectDescription;

    public SubprojectModel(int subprojectId, String subprojectName, String subprojectDescription) {
        this.subprojectId = subprojectId;
        this.subprojectName = subprojectName;
        this.subprojectDescription = subprojectDescription;
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
                ", subprojectName='" + subprojectName + '\'' +
                ", subProjectDescription='" + subprojectDescription + '\'' +
                '}';
    }
}

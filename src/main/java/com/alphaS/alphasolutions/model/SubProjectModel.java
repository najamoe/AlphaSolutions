package com.alphaS.alphasolutions.model;

public class SubProjectModel {
    private String subProjectName;
    private String subProjectDescription;

    public SubProjectModel(String subProjectName, String subProjectDescription) {
        this.subProjectName = subProjectName;
        this.subProjectDescription = subProjectDescription;
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
                "subProjectName='" + subProjectName + '\'' +
                ", subProjectDescription='" + subProjectDescription + '\'' +
                '}';
    }
}

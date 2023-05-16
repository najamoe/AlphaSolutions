package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String createProject(String projectName, String projectDescription, LocalDate startDate, LocalDate endDate) throws SQLException {
        return projectRepository.createProject(projectName, projectDescription, startDate, endDate);
    }

    public void readProject(int projectId) throws SQLException {
        projectRepository.readProject(projectId);
    }

    public List<ProjectModel> searchProject(String search) throws SQLException {
        return projectRepository.searchProject(search);
    }

    public String editProject(int projectId, String newProjectName, String newProjectDescription, LocalDate newStartDate, LocalDate newEndDate) {
        return projectRepository.editProject(projectId, newProjectName, newProjectDescription, newStartDate, newEndDate);
    }

    public String deleteProject(int projectId) {
        return projectRepository.deleteProject(projectId);
    }


}

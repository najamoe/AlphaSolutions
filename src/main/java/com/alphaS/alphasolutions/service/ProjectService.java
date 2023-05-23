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

    //Creates a project, only for the logged in employee
    public String createProject(ProjectModel project, String username, String password) throws SQLException {
        return projectRepository.createProject(project, username, password);
    }

    //For the entire list of projects associated with the logged in employee
    public List<ProjectModel> readProjects(String username, String password) throws SQLException {
        return projectRepository.readProjects(username, password);
    }

    public String editProject(int projectId, String newProjectName, String newProjectDescription, LocalDate newStartDate, LocalDate newEndDate) {
        return projectRepository.editProject(projectId, newProjectName, newProjectDescription, newStartDate, newEndDate);
    }

    public String deleteProject(int projectId) {
        return projectRepository.deleteProject(projectId);
    }

    public ProjectModel readSpecificProject(int projectId, String username, String password) {
        return projectRepository.readSpecificProject(projectId, username, password);
    }

   
}

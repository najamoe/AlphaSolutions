package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.service.ProjectService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/createproject")
    public String createProject(Model model) {
        model.addAttribute("project", new ProjectModel());
        return "createproject";
    }
    @PostMapping("/createproject")
    public String createProject(@ModelAttribute("project") ProjectModel project) {
        try {
            projectService.createProject(project.getProjectName(), project.getProjectDescription(), project.getStartDate(), project.getEndDate());
            return "projectsuccess";
        } catch (SQLException e) {
            e.printStackTrace();
            return "projecterror";
        }
    }

    @GetMapping("/readprojects")
    public String getAllProjects(Model model) {
        try {
            List<ProjectModel> projects = projectService.readProjects();

            model.addAttribute("projects", projects);

            return "readprojects";
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve projects";
            model.addAttribute("error", errorMessage);
            return "readprojects";
        }
    }

    @GetMapping("/projects/{projectId}")
    public String readProject(@PathVariable int projectId) {
        try {
            projectService.readProjects();

            return "Project details retrieved successfully";
        } catch (SQLException e) {
            return "Failed to retrieve project details";
        }
    }

    @GetMapping("/search")
    public String searchProject() {
      return "search";
    }
    @PostMapping("/search")
    public String searchProjectPost(@RequestParam String projectName, Model model) {
        try {
            List<ProjectModel> projects = projectService.searchProject(projectName);
            model.addAttribute("projects", projects);
            return "search";
        } catch (SQLException e) {
            // Handle the exception as needed
            return "error"; // Return the name of the view/template for displaying an error message
        }
    }



    @PostMapping("project/delete")
    public String deleteProject(@RequestParam int projectID){
        String message = projectService.deleteProject(projectID);
        return message;
    }
}

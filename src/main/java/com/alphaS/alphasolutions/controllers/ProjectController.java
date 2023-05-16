package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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

    //TODO: READ

    @GetMapping("/project/search")
    @ResponseBody
    public ResponseEntity<String> searchProject(@RequestParam String projectName) {
        try {
            String result = projectService.searchProject(projectName).toString();
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search project");
        }
    }
    @PostMapping("project/delete")
    public String deleteProject(@RequestParam int projectID){
        String message = projectService.deleteProject(projectID);
        return message;
    }
}

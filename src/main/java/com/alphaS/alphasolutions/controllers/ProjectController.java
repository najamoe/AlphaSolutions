package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.service.ClientService;
import com.alphaS.alphasolutions.service.ProjectService;
import com.alphaS.alphasolutions.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final ClientService clientService;

    public ProjectController(ProjectService projectService, EmployeeService employeeService, ClientService clientService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.clientService = clientService;
    }

    @GetMapping("/createproject")
    public String createProject(Model model) {
        model.addAttribute("project", new ProjectModel());
        return "createproject";
    }

    @PostMapping("/createproject")
    public String createProject(@ModelAttribute("project") ProjectModel project, Model model, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            String result = projectService.createProject(project, username, password);

            if (result.startsWith("Project successfully created")) {
                int projectId = Integer.parseInt(result.substring(result.lastIndexOf(" ") + 1));
                return "projectsuccess";
            } else {
                model.addAttribute("error", result);
                return "projecterror";
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while creating the project.");
            return "projecterror";
        }
    }
    @GetMapping("/readprojects")
    public String readCreatedProjects(Model model, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            List<ProjectModel> projects = projectService.readProjects(username, password);
            model.addAttribute("projects", projects);

            return "readprojects";
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve projects from the database. Please try again later.";
            model.addAttribute("error", errorMessage);
            return "readprojects";
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
            return "error";
        }
    }

    @PostMapping("/project/delete/{projectId}")
    public String deleteProject(@PathVariable("projectId") int projectId) {
        String deletionMessage = projectService.deleteProject(projectId);

        if (deletionMessage.equals("Project deleted successfully")) {
            return "redirect:/deletedproject";
        } else {
            return "redirect:/readproject.html?error=" + deletionMessage;
        }
    }
    @GetMapping("/project/delete/{projectId}")
    public String showDeleteProjectPage(@PathVariable("projectId") int projectId, Model model) {

        model.addAttribute("projectId", projectId);
        return "deletedproject";
    }

    @GetMapping("/deletedproject")
    public String showDeletedProjectPage(Model model) {
        return "deletedproject";
    }


    //endpoints for showing the project
    @GetMapping("/project")
    public String viewProject() {


        return "project";
    }


}

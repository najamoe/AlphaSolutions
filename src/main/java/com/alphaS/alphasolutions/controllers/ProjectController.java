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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public String createProject(@ModelAttribute("project") ProjectModel project, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            String result = projectService.createProject(project, username, password);

            if (result.startsWith("Project successfully created")) {
                int projectId = Integer.parseInt(result.substring(result.lastIndexOf(" ") + 1));
                redirectAttributes.addFlashAttribute("projectId", projectId); // Pass projectId as a flash attribute
                return "redirect:/createclient"; // Redirect to the createclient endpoint
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

    @GetMapping("/project/details/{projectId}")
    public String readSpecificProject(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        ProjectModel project = projectService.readSpecificProject(projectId, username, password);
        if (project == null) {
            // Handle case where project is not found
            return "projectNotFound";
        }

        model.addAttribute("project", project);
        return "project";
    }


    @PostMapping("/project/details/{projectId}")
    public String editSpecificProject(@PathVariable("projectId") int projectId,
                                      @RequestParam("newProjectName") String newProjectName,
                                      @RequestParam("newProjectDescription") String newProjectDescription,
                                      @RequestParam("newStartDate") String newStartDate,
                                      @RequestParam("newEndDate") String newEndDate,
                                      Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        LocalDate startDate = LocalDate.parse(newStartDate);
        LocalDate endDate = LocalDate.parse(newEndDate);

        String result = projectService.editProject(projectId, newProjectName, newProjectDescription, startDate, endDate);

        // Update the model with the updated project attributes
        ProjectModel project = projectService.readSpecificProject(projectId, username, password);
        model.addAttribute("project", project);

        // Add the result message to the model for display
        model.addAttribute("result", result);

        return "project";
    }


    @GetMapping("/project/delete/{projectId}")
    public String showDeleteProjectPage(@PathVariable("projectId") int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "deletedproject";
    }

    @PostMapping("/project/delete/{projectId}")
    public String deleteProject(@PathVariable("projectId") int projectId) {
        String deletionMessage = projectService.deleteProject(projectId);
        String errorOnDelete = "An error occured, project not deleted";

        if (deletionMessage.equals("Project and associated records deleted successfully")) {
            return "redirect:/deletedproject";
        } else {
            return "redirect:/readproject.html?error=" + errorOnDelete;
        }
    }

    @GetMapping("/deletedproject")
    public String showDeletedProjectPage(Model model) {
        return "deletedproject";
    }



}

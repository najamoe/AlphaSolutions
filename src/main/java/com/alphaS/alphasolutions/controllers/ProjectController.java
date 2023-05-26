package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.ClientModel;
import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final ClientService clientService;
  private final TaskService taskService;
  private final SubprojectService subprojectService;

    public ProjectController(ProjectService projectService, EmployeeService employeeService, ClientService clientService, TaskService taskService, SubprojectService subprojectService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.taskService = taskService;
        this.subprojectService = subprojectService;
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
                return "error";
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while creating the project.");
            return "error";
        }
    }
    @GetMapping("/dashboard")
    public String readCreatedProjects(Model model, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            List<ProjectModel> projects = projectService.readProjects(username, password);
            model.addAttribute("projects", projects);

            return "dashboard";
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve projects from the database. Please try again later.";
            model.addAttribute("error", errorMessage);
            return "dashboard";
        }
    }

    //For reading project details on project template
    @GetMapping("/project/details/{projectId}")
    public String readSpecificProject(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws SQLException {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        ProjectModel project = projectService.readSpecificProject(projectId, username, password);
        ClientModel client = clientService.readSpecificClient(projectId);
        SubprojectModel subproject = subprojectService.readSpecificSubproject(projectId);

        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("subproject", subproject);
        model.addAttribute("clientId", client.getClientId()); // Add the client ID to the model

        return "project";
    }


    //For editing projects

    @PostMapping("/project/details/{projectId}")
    public String editSpecificProject(@PathVariable("projectId") int projectId,
                                      @RequestParam("clientId") int clientId,
                                      @RequestParam("newClientName") String newClientName,
                                      @RequestParam("newContactPoNo") int newContactPoNo,
                                      @RequestParam("newContactPerson") String newContactPerson,
                                      @RequestParam("newCompanyPoNo") int newCompanyPoNo,
                                      @RequestParam("newAddress") String newAddress,
                                      @RequestParam("newZipcode") int newZipcode,
                                      @RequestParam("newCountry") String newCountry,
                                      @RequestParam("newProjectName") String newProjectName,
                                      @RequestParam("newProjectDescription") String newProjectDescription,
                                      @RequestParam("newStartDate") String newStartDate,
                                      @RequestParam("newEndDate") String newEndDate,
                                      Model model, HttpSession session) throws SQLException {
        // Edit client details

        String clientResult = clientService.editClient(newClientName, newContactPoNo, newContactPerson, newCompanyPoNo,
                newAddress, newZipcode, newCountry, clientId);

        // Edit project details
        LocalDate startDate = LocalDate.parse(newStartDate);
        LocalDate endDate = LocalDate.parse(newEndDate);
        String projectResult = projectService.editProject(projectId, newProjectName, newProjectDescription, startDate, endDate);

        // Update the model with the updated client attributes
        ClientModel client = clientService.readSpecificClient(clientId);
        model.addAttribute("client", client);

        // Update the model with the updated project attributes
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        ProjectModel project = projectService.readSpecificProject(projectId, username, password);
        model.addAttribute("project", project);

        // Add the result messages to the model for display
        model.addAttribute("clientResult", clientResult);
        model.addAttribute("projectResult", projectResult);
        model.addAttribute("showPopup", true); // Add a flag to show the pop-up

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

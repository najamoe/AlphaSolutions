package com.alphaS.alphasolutions.controllers;



import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.repositories.ClientRepository;
import com.alphaS.alphasolutions.repositories.ProjectRepository;
import com.alphaS.alphasolutions.repositories.TeamRepository;
import com.alphaS.alphasolutions.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
public class TaskCompassController {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final TeamRepository teamRepository;


    public TaskCompassController(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ClientRepository clientRepository, TeamRepository teamRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("")
    public String Index(){
        return "index";
    }

    @GetMapping("/signin")
    public String signin(){
        return "index";
    }
    @PostMapping("/signin")
    public String signinpostmapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model){
        try {
            if (employeeModel != null) {
                session.setAttribute("user", employeemModel); // store user in session
                return "redirect:/dashboard"; // redirect to dashboard page if login is successful
            } else {
                model.addAttribute("error", "Username or password incorrect"); // add error message to model
                return "signin"; // return sign-in page if login is unsuccessful
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred during login"); // add error message to model
            return "signin"; // return sign-in page if an error occurs during login
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }

    @PostMapping("/clients/create")
    public ResponseEntity<String> createClient(@RequestParam String clientName,
                                               @RequestParam String contactPoNo,
                                               @RequestParam String contactPerson,
                                               @RequestParam String companyPoNo,
                                               @RequestParam String address,
                                               @RequestParam String zipCode,
                                               @RequestParam String country,
                                               @RequestParam String clientId) {
        try {
            String result = clientRepository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create client");
        }
    }

    @GetMapping("/clients/search")
    @ResponseBody
    public ResponseEntity<String> searchClient(@RequestParam String clientName) {
        try {
            String result = clientRepository.searchClients(clientName).toString();
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search client");
        }
    }

    @PostMapping("/clients/edit")
    public ResponseEntity<String> editClient(@RequestParam String clientName,
                                             @RequestParam String contactPoNo,
                                             @RequestParam String contactPerson,
                                             @RequestParam String companyPoNo,
                                             @RequestParam String address,
                                             @RequestParam String zipCode,
                                             @RequestParam String country,
                                             @RequestParam String clientId) {
        try {
            String result = clientRepository.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit client");
        }
    }
    @PostMapping("/clients/delete")
    public String deleteClient(@RequestParam int clientID){
        String message = clientRepository.deleteClient(clientID);
        return message;
    }

    @GetMapping("/createproject")
    public String CreateProject(Model model) {
        model.addAttribute("project", new ProjectModel());
        return "createproject";
    }

    @PostMapping("/createproject") //TODO: SESSIONS
    public ResponseEntity<String> createProject(@RequestBody ProjectModel project) {
        try {
            projectRepository.createProject(project.getProjectName(), project.getProjectDescription(), project.getStartDate(), project.getEndDate());
            return ResponseEntity.ok("Project successfully created");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create project");
        }
    }

    @GetMapping("/project/search")
    @ResponseBody
    public ResponseEntity<String> searchProject(@RequestParam String projectName) {
        try {
            String result = projectRepository.searchProjects(projectName).toString();
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search project");
        }
    }
    @PostMapping("project/delete")
    public String deleteProject(@RequestParam int projectID){
        String message = projectRepository.deleteProject(projectID);
        return message;
    }

    @PostMapping("/createTeam")
    @ResponseBody
    public ResponseEntity<String> createTeam(HttpServletRequest request) {
        try {
            String teamName = request.getParameter("teamName");
            int teamId = teamRepository.createTeam(teamName);
            return ResponseEntity.ok().body(Integer.toString(teamId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create team");
        }
    }

    @PostMapping("/addEmployeeToTeam")
    @ResponseBody
    public ResponseEntity<String> addEmployeeToTeam(HttpServletRequest request) {
        try {
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            String[] usernames = request.getParameterValues("usernames[]");
            List<String> userNames = Arrays.asList(usernames);
            teamRepository.AddEmployeeToTeam(teamId, userNames);
            return ResponseEntity.ok().body("Team members added successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add team members.");
        }
    }

    @PostMapping("/editTeamName")
    @ResponseBody
    public ResponseEntity<String> editTeamName(HttpServletRequest request) {
        try {
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            String teamName = request.getParameter("teamName");
            String message = teamRepository.editTeamName(teamId, teamName);
            return ResponseEntity.ok().body(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit team name");
        }
    }

    @PostMapping("/removeEmployeeFromTeam")
    @ResponseBody
    public ResponseEntity<String> removeEmployeeFromTeam(HttpServletRequest request) {
        try {
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String message = teamRepository.removeEmployeeFromTeam(teamId, userId);
            return ResponseEntity.ok().body(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove team member");
        }
    }

}

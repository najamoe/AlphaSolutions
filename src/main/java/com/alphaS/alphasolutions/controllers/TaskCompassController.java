package com.alphaS.alphasolutions.controllers;



import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.model.SubProjectModel;
import com.alphaS.alphasolutions.model.UserModel;
import com.alphaS.alphasolutions.repositories.ProjectRepository;
import com.alphaS.alphasolutions.repositories.UserRepository;
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

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskCompassController(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("")
    public String Index(){
        return "index";
    }

    @GetMapping("/login")
    public String signin(){
        return "index";
    }
    @PostMapping("/login")
    public String signinpostmapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model){
        try {
            UserModel usermodel = userRepository.signin(username, password);
            if (usermodel != null) {
                session.setAttribute("user", usermodel); // store user in session
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
            String result = projectRepository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create client");
        }
    }

    @GetMapping("/createproject")
    public String CreateProject(Model model) {
        model.addAttribute("project", new ProjectModel());
        model.addAttribute("subProject", new SubProjectModel());
        return "createproject";
    }

    @PostMapping("/createproject") //TODO: SESSIONS
    public ResponseEntity<String> createProject(@RequestBody ProjectModel project, @RequestBody(required = false) SubProjectModel subProject) {
        try {
            projectRepository.createProject(project.getProjectName(), project.getProjectDescription(), project.getStartDate(), project.getEndDate(), subProject);
            return ResponseEntity.ok("Project successfully created");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create project");
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
            String result = projectRepository.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
            } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit client");
        }

    }

    @GetMapping("/clients/search")
    @ResponseBody
    public ResponseEntity<String> searchClient(@RequestParam String clientName) {
        try {
            String result = projectRepository.searchClients(clientName).toString();
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search client");
        }
    }

    @PostMapping("/clients/delete")
    public String deleteClient(@RequestParam int clientID){
        String message = projectRepository.deleteClient(clientID);
        return message;
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
            int teamId = projectRepository.createTeam(teamName);
            return ResponseEntity.ok().body(Integer.toString(teamId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create team");
        }
    }

    @PostMapping("/addTeamMembers")
    @ResponseBody
    public ResponseEntity<String> addTeamMembers(HttpServletRequest request) {
        try {
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            String[] usernames = request.getParameterValues("usernames[]");
            List<String> userNames = Arrays.asList(usernames);
            projectRepository.addTeamMembers(teamId, userNames);
            return ResponseEntity.ok().body("Team members added successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add team members.");
        }
    }
}

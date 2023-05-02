package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.UserModel;
import com.alphaS.alphasolutions.repositories.repository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class TaskCompassController {

    private final repository repository;

    public TaskCompassController(repository repository) {
        this.repository = repository;
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
            UserModel usermodel = repository.signin(username, password);
            session.setAttribute("user", usermodel); // store user in session
            return "dashboard"; // return dashboard page if login is successful
        } catch (SQLException e) {
            model.addAttribute("error", "Username or password incorrect"); // add error message to model
            return "signin"; // return sign in page if login is unsuccessful
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
            String result = repository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create client");
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
            String result = repository.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
            } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit client");
        }

    }

    @GetMapping("/clients/search")
    @ResponseBody
    public ResponseEntity<String> searchClient(@RequestParam String clientName) {
        try {
            String result = repository.searchClients(clientName).toString();
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search client");
        }
    }

    @PostMapping("/clients/delete")
    public String deleteClient(@RequestParam int clientID){
        String message = repository.deleteClient(clientID);
        return message;
    }

    @PostMapping("project/delete")
    public String deleteProject(@RequestParam int projectID){
        String message = repository.deleteProject(projectID);
        return message;
    }

    @PostMapping("/createTeam")
    @ResponseBody
    public ResponseEntity<String> createTeam(HttpServletRequest request) {
        try {
            String teamName = request.getParameter("teamName");
            int teamId = repository.createTeam(teamName);
            return ResponseEntity.ok().body(Integer.toString(teamId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create team");
        }
    }










}

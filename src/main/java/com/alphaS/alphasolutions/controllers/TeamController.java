package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    //TODO: Where do we access the team methods? PM page or?
    @PostMapping("/createTeam")
    public ResponseEntity<String> createTeam(@RequestParam("teamName") String teamName, @RequestParam("subProjectId") int subProjectId) {
        try {
            String result = teamService.createTeam(teamName, subProjectId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create team");
        }
    }

    @PostMapping("/{teamId}/addEmployeeToTeam")
    public ResponseEntity<String> addMemberToTeam(@PathVariable int teamId, @RequestParam String firstName, @RequestParam String lastName) {
        try {
            boolean added = teamService.addEmployeeToTeam(teamId, firstName, lastName);
            if (added) {
                return ResponseEntity.ok("Member added to the team");
            } else {
                return ResponseEntity.badRequest().body("Failed to add member to the team");
            }
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding team member");
        }
    }

    @PostMapping("/editTeamName")
    public ResponseEntity<String> editTeamName(@RequestParam int teamId, @RequestParam String teamName) {
        try {
            String message = teamService.editTeamName(teamId, teamName);
            return ResponseEntity.ok().body(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit team name");
        }
    }


    @PostMapping("/removeEmployeeFromTeam")
    public ResponseEntity<String> removeEmployeeFromTeam(HttpServletRequest request) {
        try {
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String message = teamService.deleteEmployeeFromTeam(teamId, userId);
            return ResponseEntity.ok().body(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove team member");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchEmployees(@RequestParam("searchName") String searchName) {
        try {
            List<String> suggestions = teamService.searchEmployees(searchName);
            return ResponseEntity.ok(suggestions);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

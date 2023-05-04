package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.repositories.TeamRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
public class TeamController {
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
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
    public ResponseEntity<String> editTeamName(@RequestParam int teamId, @RequestParam String teamName) {
        try {
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

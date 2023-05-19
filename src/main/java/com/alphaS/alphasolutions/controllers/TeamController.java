package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.model.TeamModel;
import com.alphaS.alphasolutions.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /*
    @PostMapping("/createTeam")
    public ResponseEntity<String> createTeam(@RequestParam("teamName") String teamName, @RequestParam("subProjectId") int subProjectId) {
        try {
            String result = teamService.createTeam(teamName, subProjectId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create team");
        }
    }

     */


    @GetMapping("/project/{subProjectId}/createTeam")
    public String createSubproject(@PathVariable int subProjectId, Model model) {
        model.addAttribute("subproject", new TeamModel());
        return "createTeam";
    }

    @PostMapping("/project/{subProjectId}/createTeam")
    public String CreateSubprojectForm(@PathVariable int subProjectId, Model model) {
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("team", new SubprojectModel());
        return "createTeam";
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


    @GetMapping("/employees/search")
    @ResponseBody
    public ResponseEntity<List<String>> searchEmployees(@RequestParam("query") String query) {
        try {
            List<String> matchingEmployeeNames = teamService.findMatchingEmployeeNames(query);
            return ResponseEntity.ok(matchingEmployeeNames);
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }







/*
    @PostMapping("/addEmployeeToTeam")
    public ResponseEntity<String> addEmployeeToTeam(@RequestParam("teamId") int teamId, @RequestParam("employeeName") String employeeName) {
        try {
            String[] nameParts = employeeName.split(" ", 2);
            if (nameParts.length != 2) {
                return ResponseEntity.badRequest().body("Invalid employee name");
            }
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            boolean added = teamService.addEmployeeToTeam(teamId, firstName, lastName);
            if (added) {
                return ResponseEntity.ok("Employee added to the team");
            } else {
                return ResponseEntity.badRequest().body("Failed to add employee to the team");
            }
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding employee to the team");
        }
    }

 */



    /*
    @PostMapping("/editTeamName")
    public ResponseEntity<String> editTeamName(@RequestParam int teamId, @RequestParam String teamName) {
        try {
            String message = teamService.editTeamName( teamName);
            return ResponseEntity.ok().body(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit team name");
        }
    }

     */

    //TODO

    @PostMapping("/project/{subProjectId}/Teams/{teamId}/edit")
    @ResponseBody
    public ResponseEntity<String> editTeam(@RequestParam String teamName) {
        try {
            String result = teamService.editTeamName( teamName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit subproject");
        }
    }

    //TODO

    @PostMapping("/removeEmployeeFromTeam")
    public ResponseEntity<String> removeEmployeeFromTeam(HttpServletRequest request) {
        try {
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            String message = teamService.deleteEmployeeFromTeam(teamId, employeeId);
            return ResponseEntity.ok().body(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove team member");
        }
    }





    //TODO
/*
    @PostMapping("/project/{projectId}/subproject/delete")
    public String deleteSubproject(@RequestParam int teamId, @RequestParam int employeeId) throws SQLException {
        String message = teamService.deleteEmployeeFromTeam(teamId, employeeId);
        return message;
    }
*/
    //TODO





}

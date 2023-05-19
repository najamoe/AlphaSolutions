package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public String createTeam(String teamName) throws SQLException {
       return teamRepository.createTeam(teamName);
    }

    public boolean addEmployeeToTeam(int teamId, String firstName, String lastName) throws SQLException {
        return teamRepository.addEmployeeToTeam(teamId, firstName, lastName);
    }

    public String deleteEmployeeFromTeam(int teamId, int employeeId) throws SQLException {
        return teamRepository.deleteEmployeeFromTeam(teamId, employeeId);
    }

    public String editTeamName(String teamName) throws SQLException {
        return teamRepository.editTeamName(teamName);
    }

    public List<String> findMatchingEmployeeNames(String query) throws SQLException {
        return teamRepository.findMatchingEmployeeNames(query);
    }



}

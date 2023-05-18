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

    public String createTeam(String teamName, int subProjectId) throws SQLException {
       return teamRepository.createTeam(teamName, subProjectId);
    }

    public boolean addEmployeeToTeam(int teamId, String firstName, String lastName) throws SQLException {
        return teamRepository.addEmployeeToTeam(teamId, firstName, lastName);
    }

    public String deleteEmployeeFromTeam(int teamId, int userId) throws SQLException {
        return teamRepository.deleteEmployeeFromTeam(teamId, userId);
    }

    public String editTeamName(int teamId, String teamName) throws SQLException {
        return teamRepository.editTeamName(teamId, teamName);
    }

    public List<String> searchEmployees(String searchName) throws SQLException {
        return teamRepository.searchEmployees(searchName);
    }



}

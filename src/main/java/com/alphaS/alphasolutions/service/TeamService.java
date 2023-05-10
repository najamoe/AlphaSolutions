package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public int createTeam(String teamName) throws SQLException {
       return teamRepository.createTeam(teamName);
    }

    public void AddEmployeeToTeam(int teamId, List<String> userNames) throws SQLException {
        teamRepository.AddEmployeeToTeam(teamId, userNames);
    }

    public String deleteEmployeeFromTeam(int teamId, int userId) throws SQLException {
        return teamRepository.deleteEmployeeFromTeam(teamId, userId);
    }

    public String editTeamName(int teamId, String teamName) throws SQLException {
        return teamRepository.editTeamName(teamId, teamName);
    }
}

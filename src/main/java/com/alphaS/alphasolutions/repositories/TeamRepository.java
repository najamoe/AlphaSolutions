package com.alphaS.alphasolutions.repositories;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;


@Repository
public class TeamRepository {

    private final DataSource dataSource;

    public TeamRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Method for creating a team
    public int createTeam(String teamName) throws SQLException {
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {

            // Create a new team
            String sql = "INSERT INTO taskcompass.Team name VALUES ('" + teamName + "')";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            // Get the ID of the newly created team
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Creating team failed, no ID obtained.");
                }
            }
        }
    }

    //Method for adding a member to a team
    public void AddEmployeeToTeam(int teamId, List<String> userNames) throws SQLException {
        String sql = "INSERT INTO taskcompass.user_team (user_id, team_id) SELECT u.id, t.id FROM taskcompass.user u  JOIN taskcompass.team t ON t.team_name = ?  WHERE u.first_name = ? AND u.last_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            for (String userName : userNames) {
                String[] nameParts = userName.split("\\s+");
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                stmt.setInt(1, teamId);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                int rowCount = stmt.executeUpdate();
                if (rowCount == 0) {
                    // Log error and continue with next user
                    System.err.println("Employee with username " + userName + " not found.");
                }
            }
            con.commit();
        } catch (SQLException ex) {
            // Log error and rollback transaction
            System.err.println("Error adding team members: " + ex.getMessage());
            throw ex;
        }
    }


    //Method for removing a member form a team
    public String removeEmployeeFromTeam(int teamId, int userId) throws SQLException {
        String message;

        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM taskcompass.Team_users WHERE team_id = ? AND user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, teamId);
            preparedStatement.setInt(2, userId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = userId + " has been removed from the " + teamId + " team, successfully";
            } else {
                message = userId + " is not a member of the " + teamId + " team";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    //Method for editing a team name
    public String editTeamName(int teamId, String teamName) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "UPDATE taskcompass.Team SET name = ? WHERE team_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, teamName);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            return "New team name successfully updated";
        } else {
            return "Something went wrong, new team name not updated";
        }
    }
}

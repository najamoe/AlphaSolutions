package com.alphaS.alphasolutions.repositories;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


@Repository
public class TeamRepository {

    private final DataSource dataSource;

    public TeamRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Method for creating a team within a chosen subProject
    public String createTeam(String teamName, int subProjectId) throws SQLException {
        // Check if the subproject exists
        if (!subProjectExists(subProjectId)) {
            return "Sub-project does not exist";
        }

        Connection con = dataSource.getConnection();
        String sql = "INSERT INTO taskcompass.Team (name, project_Id) VALUES (?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, teamName);
        stmt.setInt(2, subProjectId);

        // Execute the query and get the result set
        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            return "Team successfully added";
        } else {
            return "Something went wrong, no team added";
        }
    }

    //Checks if subProject exists..
    private boolean subProjectExists(int subProjectId) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "SELECT COUNT(*) FROM taskcompass.SubProject WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, subProjectId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
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

    //TODO lav en metode for af både slette et team men også alle medlember i det team

    //Method for removing a member form a team
    public String deleteEmployeeFromTeam(int teamId, int userId) throws SQLException {
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
            return "New team name successfully updated ";
        } else {
            return "Something went wrong, new team name not updated";
        }
    }
}

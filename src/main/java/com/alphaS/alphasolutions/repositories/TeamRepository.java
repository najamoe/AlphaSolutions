package com.alphaS.alphasolutions.repositories;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class TeamRepository {

    private final DataSource dataSource;

    public TeamRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //TODO  lav om så et tesm bliver lavet indenunder et subprojket

    //Method for creating a team within a chosen subProject
    public String createTeam(String teamName, int subProjectId) throws SQLException {
        // Check if the subproject exists
        if (!subProjectExists(subProjectId)) {
            return "Sub-project does not exist";
        }
        try (Connection con = dataSource.getConnection()){
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
    public boolean addEmployeeToTeam(int teamId, String firstName, String lastName) throws SQLException {
        String sql = "INSERT INTO taskcompass.user_team (user_id, team_id) " +
                "SELECT u.user_id, ? " +
                "FROM taskcompass.Employee u " +
                "WHERE u.first_name = ? AND u.last_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            int rowCount = stmt.executeUpdate();
            if (rowCount == 0) {
                System.err.println("Failed to add member with name: " + firstName + " " + lastName);
                return false;
            }
            System.out.println("Member added to the team");
            return true;
        } catch (SQLException ex) {
            System.err.println("Error adding team member: " + ex.getMessage());
            throw ex;
        }
    }

    public List<String> searchEmployees(String searchName) throws SQLException {
        String sql = "SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM employees WHERE first_name LIKE ? OR last_name LIKE ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, "%" + searchName + "%");
            statement.setString(2, "%" + searchName + "%");
            ResultSet resultSet = statement.executeQuery();

            List<String> suggestions = new ArrayList<>();
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                suggestions.add(fullName);
            }
            return suggestions;
        } catch (SQLException ex) {
            throw new RuntimeException("Error searching employees: " + ex.getMessage());
        }
    }



    //TODO lav en metode for af både slette et team men også alle medlember i det team
    // tjek om den kan printe team navet ud som fejlbeskjed

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
                message = "Member has been removed from team, successfully";
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

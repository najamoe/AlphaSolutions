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

    //TODO
    //Method for creating a team within a chosen subProject
    public String createTeam(String teamName) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO taskcompass.Team (name, project_Id) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, teamName);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int subProjectId = rs.getInt(1);
                    return "team successfully added" + subProjectId;
                }
            }
            return "Failed to add team";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO


    //Method for adding a member to a team
    public boolean addEmployeeToTeam(int teamId, String firstName, String lastName) throws SQLException {
        String sql = "INSERT INTO taskcompass.employee_team (employee_id, team_id) " +
                "SELECT u.employee_id, ? " +
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

    public List<String> findMatchingEmployeeNames(String query) throws SQLException {
        String sql = "SELECT CONCAT(first_name, ' ', last_name) AS employee_name " +
                "FROM taskcompass.Employee " +
                "WHERE first_name LIKE ? OR last_name LIKE ? OR username LIKE ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            stmt.setString(3, likeQuery);
            List<String> matchingEmployeeNames = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String employeeName = rs.getString("employee_name");
                matchingEmployeeNames.add(employeeName);
            }
            return matchingEmployeeNames;
        } catch (SQLException ex) {
            System.err.println("Error retrieving matching employee names: " + ex.getMessage());
            throw ex;
        }
    }



    //TODO lav en metode for af både slette et team men også alle medlember i det team
    // tjek om den kan printe team navet ud som fejlbeskjed

    //Method for removing a member form a team
    public String deleteEmployeeFromTeam(int teamId, int employeeId) throws SQLException {
        String message;

        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM taskcompass.Team_employee WHERE team_id = ? AND employee_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, teamId);
            preparedStatement.setInt(2, employeeId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = "Member has been removed from team, successfully";
            } else {
                message = employeeId + " is not a member of the " + teamId + " team";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;

    }



    //Method for editing a team name
    public String editTeamName(String teamName) {
        try (Connection con = dataSource.getConnection()) {
           String sql = "UPDATE taskcompass.Team SET name = ? WHERE team_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, teamName);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Team name successfully updated";
            }
            return "Failed to edit team name";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /*
    public String editTeamName(int teamId, String teamName) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Team SET name = ? WHERE team_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, teamName);
            stmt.setInt(2, teamId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "New team name successfully updated";
            } else {
                return "Something went wrong, new team name not updated";
            }
        }
    }

    */
}

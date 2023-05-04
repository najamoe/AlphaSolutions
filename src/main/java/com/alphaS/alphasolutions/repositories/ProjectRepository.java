package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {


    private final DataSource dataSource;

    public ProjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public String createProject(String projectName, String projectDescription, LocalDate startDate, LocalDate endDate) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "INSERT INTO taskcompass.project (project_name, project_description, start_date, end_date) VALUES (?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, projectName);
        stmt.setString(2, projectDescription);
        stmt.setDate(3, Date.valueOf(startDate));
        stmt.setDate(4, Date.valueOf(endDate));

        // Udfør SQL-forespørgslen og få resultatet
        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int projectId = rs.getInt(1);
                return "Project successfully created" + projectId;
            }
        }
        return "Failed to create project";
    }

    public List<ProjectModel> searchProjects(String search) throws SQLException {
        List<ProjectModel> projects = new ArrayList<>();

        Connection con = dataSource.getConnection();
        String sql = "SELECT p.* FROM taskcompass.project p " +
                "INNER JOIN taskcompass.client c ON p.client_id = c.client_id " +
                "WHERE p.project_name LIKE ? OR p.project_id LIKE ? OR c.clientName LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setString(3, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Map the result set to your ProjectModel object and add it to the list
                ProjectModel project = new ProjectModel();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                // Set other fields as needed
                projects.add(project);
            }
        }
        return projects;
    }

    public String editProject(int projectId, String newProjectName, String newProjectDescription, LocalDate newStartDate, LocalDate newEndDate) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.project SET project_name=?, project_description=?, start_date=?, end_date=? WHERE project_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newProjectName);
            stmt.setString(2, newProjectDescription);
            stmt.setDate(3, Date.valueOf(newStartDate));
            stmt.setDate(4, Date.valueOf(newEndDate));
            stmt.setInt(5, projectId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Project successfully updated with your changes";
            }
            return "Failed to edit project";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: Make sure sub data is deleted
    public String deleteProject(int projectID) {
        String message;

        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM taskcompass.project WHERE project_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, projectID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = "Project with ID " + projectID + " has been deleted successfully";
            } else {
                message = "Project with ID " + projectID + " does not exist";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;
    }
}



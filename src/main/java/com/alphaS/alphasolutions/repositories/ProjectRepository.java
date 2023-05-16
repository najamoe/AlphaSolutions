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

    public void readProject(int projectId) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "SELECT * FROM taskcompass.Project WHERE project_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, projectId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String projectName = rs.getString("project_name");
            String projectDescription = rs.getString("project_description");
            LocalDate startDate = rs.getDate("start_date").toLocalDate();
            LocalDate endDate = rs.getDate("end_date").toLocalDate();
            int clientId = rs.getInt("client_id");
            int userId = rs.getInt("user_id");

            // Do something with the retrieved project data
            System.out.println("Project ID: " + projectId);
            System.out.println("Project Name: " + projectName);
            System.out.println("Project Description: " + projectDescription);
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Client ID: " + clientId);
            System.out.println("User ID: " + userId);
        } else {
            System.out.println("Project not found");
        }

        rs.close();
        stmt.close();
        con.close();
    }


    public List<ProjectModel> searchProject(String search) throws SQLException {
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
                ProjectModel project = new ProjectModel();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
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

    //Now deletes project and associated subprojects (deleteSubproject handles deletion of subprojects and all their associated tasks)
    public String deleteProject(int projectId) {
        try (Connection con = dataSource.getConnection()) {
            String subprojectsSql = "DELETE FROM taskcompass.Sub_project WHERE project_id=?";
            PreparedStatement subprojectsStmt = con.prepareStatement(subprojectsSql);
            subprojectsStmt.setInt(1, projectId);

            int subprojectsDeleted = subprojectsStmt.executeUpdate();
            String projectSql = "DELETE FROM taskcompass.Project WHERE project_id=?";
            PreparedStatement projectStmt = con.prepareStatement(projectSql);
            projectStmt.setInt(1, projectId);
            int projectDeleted = projectStmt.executeUpdate();

            if (projectDeleted > 0) {
                return "Project deleted successfully";
            }
            return "Project with ID " + projectId + " does not exist";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



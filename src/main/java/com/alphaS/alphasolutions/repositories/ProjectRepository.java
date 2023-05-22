package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.*;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProjectRepository {


    private final DataSource dataSource;

    public ProjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    public String createProject(ProjectModel project, String username, String password) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "INSERT INTO taskcompass.Project (project_name, project_description, start_date, end_date, employee_id) " +
                "SELECT ?, ?, ?, ?, e.employee_id " +
                "FROM taskcompass.Employee e " +
                "WHERE e.username = ? AND e.password = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getProjectName());
            stmt.setString(2, project.getProjectDescription());
            stmt.setDate(3, Date.valueOf(project.getStartDate()));
            stmt.setDate(4, Date.valueOf(project.getEndDate()));
            stmt.setString(5, username);
            stmt.setString(6, password);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int projectId = rs.getInt(1);
                    return "Project successfully created " + projectId;
                }
            }
            return "Failed to create project";
        }
    }


    public List<ProjectModel> readProjects(String username, String password) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "SELECT * FROM taskcompass.Project p " +
                "JOIN taskcompass.Employee e ON p.employee_id = e.employee_id " +
                "WHERE e.username = ? AND e.password = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        List<ProjectModel> projects = new ArrayList<>();
        while (rs.next()) {
            int projectId = rs.getInt("project_id");
            String projectName = rs.getString("project_name");
            String projectDescription = rs.getString("project_description");
            LocalDate startDate = rs.getDate("start_date").toLocalDate();
            LocalDate endDate = rs.getDate("end_date").toLocalDate();
            int clientId = rs.getInt("client_id");
            int employeeId = rs.getInt("employee_id");

            ProjectModel project = new ProjectModel();
            project.setProjectId(projectId);
            project.setProjectName(projectName);
            project.setProjectDescription(projectDescription);
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            project.setClientId(clientId);
            project.setEmployeeId(employeeId);

            projects.add(project);
        }

        rs.close();
        stmt.close();
        con.close();

        return projects;
    }

    public ProjectModel readSpecificProject(int projectId, String username, String password) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM taskcompass.Project p " +
                    "JOIN taskcompass.Employee e ON p.employee_id = e.employee_id " +
                    "WHERE p.project_id = ? AND e.username = ? AND e.password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, projectId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ProjectModel project = new ProjectModel();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setProjectDescription(rs.getString("project_description"));
                project.setStartDate(rs.getDate("start_date").toLocalDate());
                project.setEndDate(rs.getDate("end_date").toLocalDate());
                project.setClientId(rs.getInt("client_id"));
                project.setEmployeeId(rs.getInt("employee_id"));
                return project;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if the project is not found or not attached to the employee
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

    public ProjectModel addClientToProject(int projectId, int clientId, String username, String password) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Project SET client_id = ? WHERE project_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, clientId);
            stmt.setInt(2, projectId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Retrieve the updated project
                return readSpecificProject(projectId, username, password);
            } else {
                throw new SQLException("Failed to add client to project");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Now deletes project and associated subprojects (deleteSubproject handles deletion of subprojects and all their associated tasks)

    public String deletedProject(int projectId) {
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
            } else {
                return "Project with ID " + projectId + " does not exist";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}





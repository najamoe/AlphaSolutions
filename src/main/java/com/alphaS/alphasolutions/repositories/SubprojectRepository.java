package com.alphaS.alphasolutions.repositories;
import com.alphaS.alphasolutions.model.SubprojectModel;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class SubprojectRepository {

    private final DataSource dataSource;

    public SubprojectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public int createSubProject(String subprojectName, String subprojectDescription, int projectId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO taskcompass.Subproject (sub_project_name, sub_project_description, project_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, subprojectName);
            stmt.setString(2, subprojectDescription);
            stmt.setInt(3, projectId);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int subprojectId = rs.getInt(1);
                    return subprojectId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value or handle the failure case as per your requirement
    }

    public List<SubprojectModel> readSubProjects() throws SQLException {
        try (Connection con = dataSource.getConnection()){
            String sql = "SELECT * FROM taskcompass.Subproject";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<SubprojectModel> subprojects = new ArrayList<>();
            while (rs.next()) {
                SubprojectModel subproject = new SubprojectModel();
                subproject.setSubprojectId(rs.getInt("subproject_id"));
                subproject.setSubprojectName(rs.getString("sub_project_name"));
                subproject.setSubprojectDescription(rs.getString("sub_project_description"));
                subprojects.add(subproject);
            }
            return subprojects;
        }

    }


    public SubprojectModel readSpecificSubproject(int project_id) {
        try (Connection con = dataSource.getConnection()) {
            // Retrieve subproject details by projectId
            String subprojectSql = "SELECT * FROM taskcompass.Subproject WHERE project_id = ?";
            PreparedStatement subprojectStmt = con.prepareStatement(subprojectSql);
            subprojectStmt.setInt(1, project_id);
            ResultSet subprojectRs = subprojectStmt.executeQuery();

            if (subprojectRs.next()) {
                SubprojectModel subproject = new SubprojectModel();
                subproject.setSubprojectId(subprojectRs.getInt("subproject_id"));
                subproject.setSubprojectName(subprojectRs.getString("sub_project_name"));
                subproject.setSubprojectDescription(subprojectRs.getString("sub_project_description"));

                return subproject;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // Return null if the subproject is not found for the given projectId
    }

    public int getSubprojectId(int projectId) {
        try (Connection con = dataSource.getConnection()) {
            String subprojectSql = "SELECT subproject_id FROM taskcompass.Subproject WHERE project_id = ?";
            PreparedStatement subprojectStmt = con.prepareStatement(subprojectSql);
            subprojectStmt.setInt(1, projectId);
            ResultSet subprojectRs = subprojectStmt.executeQuery();

            if (subprojectRs.next()) {
                int subprojectId = subprojectRs.getInt("subproject_id");
                return subprojectId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1; // Return a default value or handle the failure case as per your requirement
    }


    public String editSubproject(String subprojectName, String subprojectDescription, int subprojectId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Subproject SET sub_project_name=?, sub_project_description=? WHERE subproject_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, subprojectName);
            stmt.setString(2, subprojectDescription);
            stmt.setInt(3, subprojectId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Changes for subproject successfully updated";
            }
            return "Failed to edit subproject";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //deleteSubproject deletes subproject and associated tasks
    public String deleteSubproject(int subprojectId) {
        try (Connection con = dataSource.getConnection()) {
            String tasksSql = "DELETE FROM taskcompass.Task WHERE subproject_id=?";
            PreparedStatement tasksStmt = con.prepareStatement(tasksSql);
            tasksStmt.setInt(1, subprojectId);
            tasksStmt.executeUpdate();
            String subprojectSql = "DELETE FROM taskcompass.Subproject WHERE subproject_id=?";
            PreparedStatement subprojectStmt = con.prepareStatement(subprojectSql);
            subprojectStmt.setInt(1, subprojectId);
            int subprojectDeleted = subprojectStmt.executeUpdate();

            if (subprojectDeleted > 0) {
                return "Subproject successfully deleted";
            }
            return "Subproject with ID " + subprojectId + " does not exist";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


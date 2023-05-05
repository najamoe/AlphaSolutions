package com.alphaS.alphasolutions.repositories;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;

@Repository
public class SubProjectRepository {

    private final DataSource dataSource;

    public SubProjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String createSubProject(int projectId, String subProjectName, String subProjectDescription) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO taskcompass.Sub_project (sub_project_name, sub_project_description, project_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, subProjectName);
            stmt.setString(2, subProjectDescription);
            stmt.setInt(3, projectId);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int subProjectId = rs.getInt(1);
                    return "Subproject successfully added" + subProjectId;
                }
            }
            return "Failed to add subproject";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String editSubProject(int subProjectId, String newSubProjectName, String newSubProjectDescription) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Sub_project SET sub_project_name=?, sub_project_description=? WHERE sub_project_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newSubProjectName);
            stmt.setString(2, newSubProjectDescription);
            stmt.setInt(3, subProjectId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Subproject successfully updated";
            }
            return "Failed to update subproject";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String deleteSubProject(int subProjectId) {
        try (Connection con = dataSource.getConnection()) {
            // delete the tasks associated with the subproject
            String sql = "DELETE FROM taskcompass.Task WHERE task_id IN (SELECT task_id FROM taskcompass.Sub_project WHERE subproject_id=?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, subProjectId);
            int tasksDeleted = stmt.executeUpdate();

            // delete the subproject
            sql = "DELETE FROM taskcompass.Sub_project WHERE subproject_id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, subProjectId);
            int subProjectDeleted = stmt.executeUpdate();

            if (subProjectDeleted > 0) {
                return "Subproject successfully deleted " + tasksDeleted;
            }
            return "Failed to delete subproject";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

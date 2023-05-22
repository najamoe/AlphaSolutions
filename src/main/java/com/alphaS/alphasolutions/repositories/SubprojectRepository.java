package com.alphaS.alphasolutions.repositories;
import com.alphaS.alphasolutions.model.SubprojectModel;
import org.springframework.stereotype.Repository;
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
//Test

    public String createSubProject(String subProjectName, String subProjectDescription, int projectId) {
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
                    return "Subproject successfully added with ID: " + subProjectId;
                }
            }
            return "Failed to add subproject";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<SubprojectModel> readSubProject(int projectId) {
        List<SubprojectModel> subProjects = new ArrayList<>();

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM taskcompass.sub_project WHERE project_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SubprojectModel subProject = new SubprojectModel();
                subProject.setSubProjectId(rs.getInt("sub_project_id"));
                subProject.setSubProjectName(rs.getString("sub_project_name"));
                subProject.setSubProjectDescription(rs.getString("sub_project_description"));

                subProjects.add(subProject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subProjects;
    }
    public String editSubproject(String SubProjectName, String SubProjectDescription) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Sub_project SET sub_project_name=?, sub_project_description=? WHERE sub_project_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, SubProjectName);
            stmt.setString(2, SubProjectDescription);
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
    public String deleteSubproject(int subProjectId) {
        try (Connection con = dataSource.getConnection()) {
            String tasksSql = "DELETE FROM taskcompass.Task WHERE subproject_id=?";
            PreparedStatement tasksStmt = con.prepareStatement(tasksSql);
            tasksStmt.setInt(1, subProjectId);
            tasksStmt.executeUpdate();
            String subprojectSql = "DELETE FROM taskcompass.Sub_project WHERE subproject_id=?";
            PreparedStatement subprojectStmt = con.prepareStatement(subprojectSql);
            subprojectStmt.setInt(1, subProjectId);
            int subprojectDeleted = subprojectStmt.executeUpdate();

            if (subprojectDeleted > 0) {
                return "Subproject successfully deleted";
            }
            return "Subproject with ID " + subProjectId + " does not exist";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

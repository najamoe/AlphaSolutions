package com.alphaS.alphasolutions.repositories;
import com.alphaS.alphasolutions.model.SubprojectModel;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Repository
public class SubprojectRepository {

    private final DataSource dataSource;

    public SubprojectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }



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

    //Total time for all tasks in one Subproject
    public String getTotalEstimatedTimeForSubproject(int subprojectId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT SUM(est_time) FROM taskcompass.Task t INNER JOIN taskcompass.Sub_project_task st ON t.task_id = st.task_id WHERE st.subproject_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, subprojectId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalSeconds = rs.getInt(1);
                long days = totalSeconds / (60 * 60 * 24);
                long hours = (totalSeconds % (60 * 60 * 24)) / (60 * 60);
                long minutes = (totalSeconds % (60 * 60)) / 60;

                return String.format("%d days, %d hours, %d minutes", days, hours, minutes);
            }

            return "Error";
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    //Total time for all subprojects task in project
    public String getCombinedTimeForProject(int projectId) {
        try (Connection con = dataSource.getConnection()) {
            // Retrieve the subprojects associated with the project
            String subprojectsSql = "SELECT * FROM taskcompass.sub_project WHERE project_id = ?";
            PreparedStatement subprojectsStmt = con.prepareStatement(subprojectsSql);
            subprojectsStmt.setInt(1, projectId);
            ResultSet subprojectsRs = subprojectsStmt.executeQuery();

            Duration combinedTime = Duration.ZERO;

            // Create an instance of SubprojectRepository
            SubprojectRepository subprojectRepository = new SubprojectRepository(dataSource);

            // Iterate over the subprojects and calculate the combined time
            while (subprojectsRs.next()) {
                int subprojectId = subprojectsRs.getInt("sub_project_id");
                String subprojectTime = subprojectRepository.getTotalEstimatedTimeForSubproject(subprojectId);
                combinedTime = combinedTime.plus(Duration.parse(subprojectTime));
            }

            long days = combinedTime.toDays();
            long hours = combinedTime.toHoursPart();
            long minutes = combinedTime.toMinutesPart();

            return String.format("%d days, %d hours, %d minutes", days, hours, minutes);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to calculate combined time for project", e);
        }
    }





}


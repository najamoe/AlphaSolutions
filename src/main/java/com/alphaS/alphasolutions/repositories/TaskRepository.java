package com.alphaS.alphasolutions.repositories;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;


@Repository
public class TaskRepository {

    private final DataSource dataSource;

    public TaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String addTaskToSubProject(String taskName, String taskDescription, LocalDate estTime, String jobTitleNeeded, String status, Color color, int subProjectId) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO taskcompass.Task (task_name, description_task, est_time, title_needed, status_name, status_color) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, taskName);
            stmt.setString(2, taskDescription);
            stmt.setDate(3, Date.valueOf(estTime));
            stmt.setString(4, jobTitleNeeded);
            stmt.setString(5, status);
            stmt.setString(6, color.toString());

            // execute the SQL statement to insert the task into the Task table
            int rowsInserted = stmt.executeUpdate();

            // get the generated task ID
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int taskId = rs.getInt(1);

            // prepare a SQL statement to insert the task ID and subproject ID into the Sub_project_task table
            sql = "INSERT INTO Sub_project_task (subproject_id, task_id) VALUES (?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, subProjectId);
            stmt.setInt(2, taskId);

            // execute the SQL statement to insert the subproject ID and task ID into the Sub_project_task table
            int rowsInserted2 = stmt.executeUpdate();

            if (rowsInserted > 0 && rowsInserted2 > 0) {
                return "Task successfully added";
            } else {
                return "Something went wrong, task not added";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong, task not added"; //Case an exception to type SQLException is caught...
        }
    }


    //Method for removing a task form a subproject
    public String removeTaskFromSubProject(int subProjetId, int taskId) throws SQLException {
        String message;

        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM Sub_project_task WHERE subproject_id = ? AND task_id = ?\n";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, subProjetId);
            preparedStatement.setInt(2, taskId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = taskId + " has been removed from the " + subProjetId + " team, successfully";
            } else {
                message = taskId + " is not a member of the " + subProjetId + " team";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    //Method for editing a task information
    public String editTask(String taskName, String taskDescription, LocalDate estTime, String jobTitleNeeded, String status, Color color) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "UPDATE taskcompass.Task Set task_name = ?, description_task = ?, est_time = ?, title_needed = ?, status_name = ?, status_color = ? WHERE task_id = ?";

        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, taskName);
        stmt.setString(2, taskDescription);
        stmt.setDate(3, Date.valueOf(estTime));
        stmt.setString(4, jobTitleNeeded);
        stmt.setString(5, status);
        stmt.setString(6, color.toString());
        // Execute the query and get the result set
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            return "New information successfully updated";
        } else {
            return "Something went wrong, new information not updated";
        }
    }





}

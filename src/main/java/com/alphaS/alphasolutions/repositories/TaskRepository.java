package com.alphaS.alphasolutions.repositories;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalTime;


@Repository
public class TaskRepository {

    private final DataSource dataSource;

    public TaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Method for creating a task to a subproject
    public int createTask(String taskName, String taskDescription, LocalTime estTime, int subprojectId) {
        try (Connection con = dataSource.getConnection()) {
            String taskSql = "INSERT INTO taskcompass.Task (task_name, description_task, est_time, subproject_id) VALUES (?, ?, ?, ?)";
            PreparedStatement taskStmt = con.prepareStatement(taskSql, Statement.RETURN_GENERATED_KEYS);

            taskStmt.setString(1, taskName);
            taskStmt.setString(2, taskDescription);
            taskStmt.setTime(3, Time.valueOf(estTime));
            taskStmt.setInt(4, subprojectId);

            int rowsInserted = taskStmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = taskStmt.getGeneratedKeys();
                if (rs.next()) {
                    int taskId = rs.getInt(1);
                    return taskId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value or handle the failure case as per your requirement
    }


    //Method for removing a task form a subproject
    public String deleteTaskFromSubproject(int taskId)  {
        String message;

        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM taskcompass.Task WHERE task_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, taskId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = taskId + " has been deleted";
            } else {
                message = taskId + " was not deleted";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    //Method for editing task information
    public String editTask(int taskId, String taskName, String taskDescription, LocalTime estTime) throws SQLException {
        try (Connection con = dataSource.getConnection()){
            String sql = "UPDATE taskcompass.Task SET task_name = ?, description_task = ?, est_time = ?  WHERE task_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, taskName);
            stmt.setString(2, taskDescription);
            stmt.setTime(3, Time.valueOf(estTime));
            stmt.setInt(4, taskId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Changes for task " + taskId + " successfully updated";
            }
            return "Failed to edit task";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




}

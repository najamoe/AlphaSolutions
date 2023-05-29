package com.alphaS.alphasolutions.repositories;
import com.alphaS.alphasolutions.model.TaskModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class TaskRepository {

    private final DataSource dataSource;

    public TaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Method for creating a task to a subproject
    public int createTask(String taskName, String taskDescription, int estDays, int estHours, int estMinutes, int subprojectId) {
        try (Connection con = dataSource.getConnection()) {
            String taskSql = "INSERT INTO taskcompass.Task (task_name, description_task, est_days, est_hours, est_minutes, subproject_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement taskStmt = con.prepareStatement(taskSql, Statement.RETURN_GENERATED_KEYS);

            taskStmt.setString(1, taskName);
            taskStmt.setString(2, taskDescription);
            taskStmt.setInt(3, estDays);
            taskStmt.setInt(4, estHours);
            taskStmt.setInt(5, estMinutes);
            taskStmt.setInt(6, subprojectId);

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

    public List<TaskModel> readTasks(int subprojectId) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            String taskSql = "SELECT * FROM taskcompass.Task WHERE subproject_id = ?";

            PreparedStatement taskStmt = con.prepareStatement(taskSql);
            taskStmt.setInt(1, subprojectId);
            ResultSet taskRs = taskStmt.executeQuery();

            List<TaskModel> tasks = new ArrayList<>();
            while (taskRs.next()) {
                TaskModel task = new TaskModel();
                task.setTaskId(taskRs.getInt("task_id"));
                task.setTaskName(taskRs.getString("task_name"));
                task.setTaskDescription(taskRs.getString("description_task"));
                task.setEstDays(taskRs.getInt("est_days"));
                task.setEstHours(taskRs.getInt("est_hours"));
                task.setEstMinutes(taskRs.getInt("est_minutes"));
                tasks.add(task);
            }
            return tasks;
        }
    }


    public String editTask(int taskId, String taskName, String taskDescription, int estDays, int estHours, int estMinutes) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Task SET task_name = ?, description_task = ?, est_days = ?, est_hours = ?, est_minutes = ? WHERE task_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, taskName);
            stmt.setString(2, taskDescription);
            stmt.setInt(3, estDays);
            stmt.setInt(4, estHours);
            stmt.setInt(5, estMinutes);
            stmt.setInt(6, taskId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Changes for task " + taskId + " successfully updated";
            }
            return "Failed to edit task";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                return taskId + " has been deleted";
            } else {
                return taskId + " was not deleted";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTotalTime(int subprojectId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT " +
                    "  CONCAT(" +
                    "    FLOOR(SUM(est_days)), ' days ', " +
                    "    FLOOR(SUM(est_hours)), ' hours ', " +
                    "    FLOOR(SUM(est_minutes)), ' minutes' " +
                    "  ) AS total_est_time " +
                    "FROM " +
                    "  taskcompass.Task " +
                    "WHERE " +
                    "  subproject_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, subprojectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String totalTime = resultSet.getString("total_est_time");
            return totalTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

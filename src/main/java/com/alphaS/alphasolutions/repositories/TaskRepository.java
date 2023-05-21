package com.alphaS.alphasolutions.repositories;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


@Repository
public class TaskRepository {

    private final DataSource dataSource;

    public TaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Method for creating a task to a subprojet
    public String createTask(String taskName, String taskDescription, LocalTime estTime, LocalDate deadline, String jobTitleNeeded, String status, Color color, int subProjectId) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            String taskSql = "INSERT INTO taskcompass.Task (task_name, description_task, est_time, deadline, title_needed, status_name, status_color) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement taskStmt = con.prepareStatement(taskSql, Statement.RETURN_GENERATED_KEYS);

            taskStmt.setString(1, taskName);
            taskStmt.setString(2, taskDescription);
            taskStmt.setTime(3, Time.valueOf(estTime));
            taskStmt.setDate(4, Date.valueOf(deadline));
            taskStmt.setString(5, jobTitleNeeded);
            taskStmt.setString(6, status);

            switch (status) {
                case "Not started":
                case "Pending":
                    color = Color.RED;
                    break;
                case "In progress":
                    color = Color.YELLOW;
                    break;
                case "Done":
                case "Completed":
                    color = Color.GREEN;
                    break;
                default:
                    color = Color.BLACK;
            }

            taskStmt.setString(7, color.toString());

            int rowsInserted = taskStmt.executeUpdate();

            // get the generated task ID
            ResultSet rs = taskStmt.getGeneratedKeys();
            rs.next();
            int taskId = rs.getInt(1);

            String subprojectTaskSql = "INSERT INTO taskcompass.Sub_project_task (subproject_id, task_id) VALUES (?, ?)";
            PreparedStatement subprojectTaskStmt = con.prepareStatement(subprojectTaskSql);
            subprojectTaskStmt.setInt(1, subProjectId);
            subprojectTaskStmt.setInt(2, taskId);

            int rowsInserted2 = subprojectTaskStmt.executeUpdate();

            if (rowsInserted > 0 && rowsInserted2 > 0) {
                return "Task successfully added";
            } else {
                return "Something went wrong, task not added";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong, task not added";
        }
    }

    //TODO kig på boolean

    //Method for removing a task form a subproject
    public String deleteTaskFromSubproject(int subProjetId, int taskId) throws SQLException {
        String message;

        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM Sub_project_task WHERE subproject_id = ? AND task_id = ?\n";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, subProjetId);
            preparedStatement.setInt(2, taskId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = taskId + " has been removed from " + subProjetId;
            } else {
                message = taskId + " is not a member of the " + subProjetId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    //Method for editing task information
    public String editTask(int taskId, String taskName, String taskDescription, LocalTime estTime, LocalDate deadline, String jobTitleNeeded, String status, Color color) throws SQLException {
        try (Connection con = dataSource.getConnection()){
            String sql = "UPDATE taskcompass.Task SET task_name = ?, description_task = ?, est_time = ?, deadline = ?, title_needed = ?, status_name = ?, status_color = ? WHERE task_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, taskName);
            stmt.setString(2, taskDescription);
            stmt.setTime(3, Time.valueOf(estTime));
            stmt.setDate(4, Date.valueOf(deadline));
            stmt.setString(5, jobTitleNeeded);
            stmt.setString(6, status);
            switch (status) {
                case "Not started":
                case "Pending":
                    color = Color.RED;
                    break;
                case "In progress":
                    color = Color.YELLOW;
                    break;
                case "Done":
                case "Completed":
                    color = Color.GREEN;
                    break;
                default:
                    color = Color.BLACK;
            }
            stmt.setString(7, color.toString());
            stmt.setInt(8, taskId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Changes for task " + taskId + " successfully updated";
            }
            return "Failed to edit task";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Method for assigning employee to a task
    public String assignEmployeeToTask(int taskId, int employeeId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Task SET assigned_employee_id = ? WHERE task_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            stmt.setInt(2, taskId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                return "Employee assigned to the task";
            } else {
                return "Failed to assign user to the task";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }




}

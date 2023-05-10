package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String createTask(String taskName, String taskDescription, LocalTime estTime, LocalDate deadline, String jobTitleNeeded, String status, Color color, int subProjectId) throws SQLException {
        return taskRepository.createTask(taskName, taskDescription, estTime, deadline, jobTitleNeeded, status, color, subProjectId);
    }

    public String deleteTaskFromSubproject(int subProjetId, int taskId) throws SQLException {
        return taskRepository.deleteTaskFromSubproject(subProjetId,taskId);
    }

    public String editTask(int taskId, String taskName, String taskDescription, LocalTime estTime, LocalDate deadline, String jobTitleNeeded, String status, Color color) throws SQLException {
        return taskRepository.editTask(taskId,taskName,taskDescription, estTime, deadline, jobTitleNeeded, status, color);
    }




}

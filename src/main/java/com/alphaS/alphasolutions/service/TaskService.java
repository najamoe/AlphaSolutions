package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.TaskModel;
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

    public int createTask(int subprojectId, TaskModel taskModel){
        return taskRepository.createTask(taskModel.getTaskName(), taskModel.getTaskDescription(), taskModel.getEstTime(), subprojectId);
    }

    public String deleteTaskFromSubproject(int taskId) throws SQLException {
        return taskRepository.deleteTaskFromSubproject(taskId);
    }

    public String editTask(int taskId, String taskName, String taskDescription, LocalTime estTime) throws SQLException {
        return taskRepository.editTask(taskId,taskName,taskDescription, estTime);
    }








}

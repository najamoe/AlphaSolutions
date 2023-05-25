package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskService() {

    }

    public int createTask(int projectId, TaskModel taskModel){
        return taskRepository.createTask(taskModel.getTaskName(), taskModel.getTaskDescription(), taskModel.getEstTime(), projectId);
    }

    public List<TaskModel> readTasks(int subprojectId) throws SQLException {
        return taskRepository.readTasks(subprojectId);
    }

    public List<TaskModel> readSpecificTask(int subprojectId) throws SQLException {
        return taskRepository.readTasks(subprojectId);
    }
    public String deleteTaskFromSubproject(int taskId) throws SQLException {
        return taskRepository.deleteTaskFromSubproject(taskId);
    }

    public String editTask(int taskId, String taskName, String taskDescription, LocalTime estTime) throws SQLException {
        return taskRepository.editTask(taskId,taskName,taskDescription, estTime);
    }


}

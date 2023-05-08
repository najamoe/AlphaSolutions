package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    //TODO: Make the methods only accessible via subproject
    //TODO skal kigge på forbindelse til subproject og task (task.getsubproject)
    @PostMapping("/createtask")
    public ResponseEntity<String> createTask(@RequestBody TaskModel task) {
        try {
            String message = taskRepository.createTask(task.getTaskName(), task.getTaskDescription(), task.getEstTime(), task.getDeadline(), task.getJobTitleNeeded(), task.getStatus(), task.getColor(), task.getTaskId());
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create task");
        }
    }

    @DeleteMapping("/deletetask")
    public ResponseEntity<String> deleteTaskFromSubproject(@RequestParam int subprojectId, @RequestParam int taskId) {
        try {
            String message = taskRepository.deleteTaskFromSubproject(subprojectId, taskId);
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }

    @PostMapping("/edittask")
    public ResponseEntity<String> editTask(@RequestBody TaskModel task) {
        try {
            String message = taskRepository.editTask(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getEstTime(), task.getDeadline(), task.getJobTitleNeeded(), task.getStatus(), task.getColor());
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to edit " + task.getTaskName());
        }
    }
}
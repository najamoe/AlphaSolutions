package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createtask/{subprojectId}")
    public ResponseEntity<String> createTask(@PathVariable("subprojectId") int subprojectId, @RequestBody TaskModel task) {
        try {
            // Check if the subproject with the given subprojectId exists and is accessible by the user
            // Call the taskService to create the task for the specific subproject
            String message = taskService.createTask(task.getTaskName(), task.getTaskDescription(), task.getEstTime(), task.getDeadline(), task.getJobTitleNeeded(), task.getStatus(), task.getColor(), subprojectId);

            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create task");
        }
    }

    @DeleteMapping("/deletetask")
    public ResponseEntity<String> deleteTaskFromSubproject(@RequestParam int subprojectId, @RequestParam int taskId) {
        try {
            String message = taskService.deleteTaskFromSubproject(subprojectId, taskId);
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }

    @PostMapping("/edittask")
    public ResponseEntity<String> editTask(@RequestBody TaskModel task) {
        try {
            String message = taskService.editTask(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getEstTime(), task.getDeadline(), task.getJobTitleNeeded(), task.getStatus(), task.getColor());
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to edit " + task.getTaskName());
        }
    }


    @PostMapping("/{taskId}/assign")
    public ResponseEntity<String> assignEmployeeToTask(@PathVariable int taskId, @RequestParam int employeeId) {
        try {
            String result = taskService.assignEmployeeToTask(taskId, employeeId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign user to the task");
        }
    }


}

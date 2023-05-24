package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.repositories.SubprojectRepository;
import com.alphaS.alphasolutions.repositories.TaskRepository;
import com.alphaS.alphasolutions.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final SubprojectRepository subprojectRepository;
    private final TaskRepository taskRepository;
    private final SubprojectController subprojectController;

    public TaskController(TaskService taskService, SubprojectController subprojectController, TaskRepository taskRepository, SubprojectRepository subprojectRepository) {
        this.taskService = taskService;
        this.subprojectRepository = subprojectRepository;
        this.taskRepository = taskRepository;
        this.subprojectController = subprojectController;
    }
    @GetMapping("/subprojectsuccess/{subprojectId}/createtask")
    public String addTaskToSubproject(@PathVariable int subprojectId, Model model) {
        TaskModel taskModel = new TaskModel();
        model.addAttribute("taskModel", new TaskModel());
        model.addAttribute("subprojectId", subprojectId);
        return "createtask";
    }

    @PostMapping("/subprojectsuccess/{subprojectId}/createtask")
    public String addTaskToSubproject(
            @PathVariable int subprojectId, @ModelAttribute("task") TaskModel taskModel, Model model,
            RedirectAttributes redirectAttributes)  {
            int taskId = taskService.createTask(subprojectId, taskModel);
            redirectAttributes.addAttribute("taskId", taskId);
            model.addAttribute("taskName", taskModel.getTaskName());
            return "redirect:/tasksuccess";
    }


    @GetMapping("/tasksuccess")
    public String taskSuccess(@RequestParam("taskId") int taskId, Model model) {
        model.addAttribute("taskId", taskId);
        return "taskSuccess";
    }


    @DeleteMapping("/deletetask")
    public ResponseEntity<String> deleteTaskFromSubproject(@RequestParam int taskId) {
        try {
            String message = taskService.deleteTaskFromSubproject(taskId);
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }

    @PostMapping("/edittask")
    public ResponseEntity<String> editTask(@RequestBody TaskModel task) {
        try {
            String message = taskService.editTask(task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getEstTime());
            return ResponseEntity.ok(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to edit " + task.getTaskName());
        }
    }




}

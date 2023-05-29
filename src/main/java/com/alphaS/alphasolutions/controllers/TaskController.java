package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.repositories.SubprojectRepository;
import com.alphaS.alphasolutions.repositories.TaskRepository;
import com.alphaS.alphasolutions.service.ProjectService;
import com.alphaS.alphasolutions.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.sql.SQLException;

@Controller
public class TaskController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final SubprojectRepository subprojectRepository;
    private final TaskRepository taskRepository;
    private final SubprojectController subprojectController;

    public TaskController(TaskService taskService, SubprojectRepository subprojectRepository,
                          TaskRepository taskRepository, SubprojectController subprojectController,
                          ProjectService projectService) {
        this.taskService = taskService;
        this.subprojectRepository = subprojectRepository;
        this.taskRepository = taskRepository;
        this.subprojectController = subprojectController;
        this.projectService = projectService;
    }

    @GetMapping("/subprojectsuccess/{subprojectId}/createtask")
    public String addTaskToSubproject(@PathVariable int subprojectId, Model model) {
        TaskModel taskModel = new TaskModel();
        model.addAttribute("taskModel", new TaskModel());
        model.addAttribute("subprojectId", subprojectId);
        return "createtask";
    }

    @PostMapping("/subprojectsuccess/{subprojectId}/createtask")
    public String addTaskToSubproject(@PathVariable int subprojectId, @ModelAttribute("taskModel") TaskModel taskModel,
                                      Model model, RedirectAttributes redirectAttributes) {
        int taskId = taskService.createTask(subprojectId, taskModel);

        // Add a success message to the model
        model.addAttribute("message", "Task created successfully!");

        // Clear the taskModel object to reset the form
        model.addAttribute("taskModel", new TaskModel());

        // Add the task details to the model
        model.addAttribute("taskId", taskId);
        model.addAttribute("subprojectId", subprojectId);

        return "createtask";
    }

    @GetMapping("/tasksuccess/{subprojectId}")
    public String taskSuccess(@PathVariable int subprojectId, Model model) throws SQLException {
        List<TaskModel> tasks = taskService.readTasks(subprojectId);
        model.addAttribute("tasks", tasks);
        return "tasksuccess";
    }


    @GetMapping("/subproject/{subprojectId}/task")
    public String getTasks(@PathVariable int subprojectId, Model model) throws SQLException {
        model.addAttribute("subprojectId", subprojectId);
        List<TaskModel> task = taskService.readTasks(subprojectId);
        model.addAttribute("task", task);
        return "task";
    }
    @GetMapping("/task/{taskId}/edit")
    public String getTask(@PathVariable int taskId, Model model) throws SQLException {
        // Retrieve the task ID from the database using the taskId
        int retrievedTaskId = taskService.getTaskId(taskId);
        // Retrieve the subproject name associated with the task
        String subprojectName = taskService.getSubprojectName(taskId);

        TaskModel task = taskService.getTaskById(taskId);
        // Add the task ID and subproject name to the model
        model.addAttribute("taskId", retrievedTaskId);
        model.addAttribute("subprojectName", subprojectName);
        model.addAttribute("task", task);

        return "edittask";
    }

    @PostMapping("/task/{taskId}/edit")
    public String editTask(
            @PathVariable("taskId") int taskId,
            @RequestParam("taskName") String taskName,
            @RequestParam("taskDescription") String taskDescription,
            @RequestParam("estDays") int estDays,
            @RequestParam("estHours") int estHours,
            @RequestParam("estMinutes") int estMinutes, Model model,
    RedirectAttributes redirectAttributes) throws SQLException {
        // Retrieve the task ID from the database using the taskId
        int retrievedTaskId = taskService.getTaskId(taskId);
        // Retrieve the subproject name associated with the task
        String subprojectName = taskService.getSubprojectName(taskId);

        // Perform the task editing logic using the retrievedTaskId
        String result = taskService.editTask(retrievedTaskId, taskName, taskDescription, estDays, estHours, estMinutes);

        // Add the result message to the model for display
        model.addAttribute("taskId", retrievedTaskId);
        model.addAttribute("subprojectName", subprojectName);
        model.addAttribute("result", result);

        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/task/" + taskId + "/edit";
    }


    @PostMapping("/tasksuccess/{subprojectId}")
    public String deleteTaskFromSubproject(@PathVariable int subprojectId, @RequestParam int taskId, RedirectAttributes redirectAttributes) throws SQLException {
        String message = taskService.deleteTaskFromSubproject(taskId);

        if (message.equals(taskId + " has been deleted")) {
            redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred - task not deleted");
        }

        return "redirect:/tasksuccess/" + subprojectId;
    }


}

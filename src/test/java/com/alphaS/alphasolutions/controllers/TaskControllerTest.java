package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTask() throws SQLException {
        // Arrange
        int taskId = 1;
        int retrievedTaskId = 2;
        String subprojectName = "Sample Subproject";
        TaskModel task = new TaskModel();

        when(taskService.getTaskId(taskId)).thenReturn(retrievedTaskId);
        when(taskService.getSubprojectName(taskId)).thenReturn(subprojectName);
        when(taskService.getTaskById(taskId)).thenReturn(task);

        // Act
        String viewName = taskController.getTask(taskId, model);

        // Assert
        assertEquals("edittask", viewName);

        // Verify that the necessary methods were called with the correct parameters
        verify(taskService).getTaskId(taskId);
        verify(taskService).getSubprojectName(taskId);
        verify(taskService).getTaskById(taskId);

        // Verify that the model attributes were added
        verify(model).addAttribute("taskId", retrievedTaskId);
        verify(model).addAttribute("subprojectName", subprojectName);
        verify(model).addAttribute("task", task);

    }
}
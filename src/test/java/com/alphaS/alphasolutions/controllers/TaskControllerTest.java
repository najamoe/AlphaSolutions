package com.alphaS.alphasolutions.controllers;
/*
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @InjectMocks
    private TaskController taskController;

    public TaskControllerTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void taskSuccess() throws SQLException {
        // Arrange
        int subprojectId = 123;
        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(new TaskModel()); // Add some example task models

        when(taskService.readTasks(subprojectId)).thenReturn(tasks);

        // Act
        String viewName = taskController.taskSuccess(subprojectId, model);

        // Assert
        assertEquals("tasksuccess", viewName); // Verify that the returned view name is "tasksuccess"
        verify(model).addAttribute("tasks", tasks); // Verify that tasks attribute is added to the model
        verify(taskService).readTasks(subprojectId); // Verify that the taskService.readTasks method is called with the correct subprojectId
    }


    @Test
    void getSpecificTask() throws SQLException {
        // Arrange
        int subprojectId = 1;
        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(new TaskModel()); // Add some example task models

        when(taskService.readTasks(subprojectId)).thenReturn(tasks);

        // Act
        String viewName = taskController.getSpecificTask(subprojectId, model);

        // Assert
        assertEquals("task", viewName); // Verify that the returned view name is "task"
        verify(model).addAttribute("subprojectId", subprojectId); // Verify that subprojectId attribute is added to the model
        verify(model).addAttribute("task", tasks); // Verify that task attribute is added to the model
        verify(taskService).readTasks(subprojectId); // Verify that the taskService.readTasks method is called with the correct subprojectId
    }
}

 */
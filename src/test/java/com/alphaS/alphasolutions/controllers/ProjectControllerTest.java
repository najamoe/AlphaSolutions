package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void deleteProject() {
        // Arrange
        int projectId = 1;
        String deletionMessage = "Project and associated records deleted successfully";

        // Mock the projectService.deleteProject method
        when(projectService.deleteProject(projectId)).thenReturn(deletionMessage);

        // Act
        String result = projectController.deleteProject(projectId);

        // Assert
        assertEquals("redirect:/deletedproject", result);

        // Verify that the projectService.deleteProject method was called with the correct projectId
        verify(projectService).deleteProject(projectId);
    }

    @Test
    void deleteProject_WithFailure() {
        // Arrange
        int projectId = 1;
        String deletionMessage = "Some error occurred during deletion";

        // Mock the projectService.deleteProject method
        when(projectService.deleteProject(projectId)).thenReturn(deletionMessage);

        // Act
        String result = projectController.deleteProject(projectId);

        // Assert
        assertEquals("redirect:/readproject.html?error=An error occured, project not deleted", result);

        // Verify that the projectService.deleteProject method was called with the correct projectId
        verify(projectService).deleteProject(projectId);
    }

    @Test
    void showDeletedProjectPage() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String result = projectController.showDeletedProjectPage(model);

        // Assert
        assertEquals("deletedproject", result);
    }
}
package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readSpecificProject() {
        // Arrange
        int projectId = 1;
        String username = "testuser";
        String password = "testpassword";
        ProjectModel expectedProject = new ProjectModel();
        // Set up any necessary data for the expectedProject

        // Mock the projectRepository.readSpecificProject method
        when(projectRepository.readSpecificProject(projectId, username, password)).thenReturn(expectedProject);

        // Act
        ProjectModel result = projectService.readSpecificProject(projectId, username, password);

        // Assert
        assertEquals(expectedProject, result);

        // Verify that the projectRepository.readSpecificProject method was called with the correct parameters
        verify(projectRepository).readSpecificProject(projectId, username, password);

    }



}
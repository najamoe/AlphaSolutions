package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.SubprojectModel;
import com.alphaS.alphasolutions.service.SubprojectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubprojectControllerTest {

    @InjectMocks
    private SubprojectController subprojectController;

    @Mock
    private SubprojectService subprojectService;

    @Mock
    private Model model;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readSubproject_subprojectExists_returnsSubprojectView() {
        // Arrange
        int projectId = 1;
        SubprojectModel subproject = new SubprojectModel();

        when(subprojectService.readSpecificSubproject(projectId)).thenReturn(subproject);

        // Act
        String viewName = subprojectController.readSubproject(projectId, model);

        // Assert
        assertEquals("subproject", viewName);

        // Verify that the necessary methods were called with the correct parameters
        verify(subprojectService).readSpecificSubproject(projectId);

        // Verify that the model attribute was added
        verify(model).addAttribute("subproject", subproject);
    }

    @Test
    void readSubproject_subprojectNotFound_returnsErrorView() {
        // Arrange
        int projectId = 1;

        when(subprojectService.readSpecificSubproject(projectId)).thenReturn(null);

        // Act
        String viewName = subprojectController.readSubproject(projectId, model);

        // Assert
        assertEquals("error", viewName);

        // Verify that the necessary methods were called with the correct parameters
        verify(subprojectService).readSpecificSubproject(projectId);

        // Verify that no model attribute was added
        verifyNoInteractions(model);
    }
}
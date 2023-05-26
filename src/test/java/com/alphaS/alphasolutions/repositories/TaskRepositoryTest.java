package com.alphaS.alphasolutions.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalTime;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TaskRepositoryTest {

    @InjectMocks
    private TaskRepository taskRepository;

    @Mock //Allows us to control the behavior of the dependencies and simulate the database interactions
    private DataSource dataSource;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Set up a mock database connection
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    void createTask() throws SQLException {
        //Unit test for whether a task can be created and saved in the database

        //Arrange - create test data
        String taskName = "Test task";
        String taskDescription = "Task Description";
        int estDays = 12;
        int estHours = 14;
        int estMinutes = 45;
        int subprojectId = 1;

        //Set up a mock PreparedStatement and ResultSet
        PreparedStatement taskStmt = mock(PreparedStatement.class);
        ResultSet generatedKeys = mock(ResultSet.class);

        //Act - Set up the expected behavior of the PreparedStmt and resultSet
        when(dataSource.getConnection().prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(taskStmt);
        when(taskStmt.executeUpdate()).thenReturn(1);
        when(taskStmt.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(1);

        // Call the createTask method
        String result = String.valueOf(taskRepository.createTask(taskName, taskDescription, estDays, estHours, estMinutes, subprojectId));

        //Assert the result
        assertEquals("Task successfully added", result);

        // Verify that the necessary methods were called
        verify(dataSource.getConnection()).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(taskStmt).setString(eq(1), eq(taskName));
        verify(taskStmt).setString(eq(2), eq(taskDescription));
        verify(taskStmt).setInt(3, estDays);
        verify(taskStmt).setInt(4, estHours);
        verify(taskStmt).setInt(5, estMinutes);

        verify(taskStmt).executeUpdate();
        verify(taskStmt).getGeneratedKeys();
        verify(generatedKeys).next();
        verify(generatedKeys).getInt(1);
    }


    @Test
    void editTask() throws SQLException {
        // Unit test for editing task information

        // Arrange - create test data
        int taskId = 1;
        String taskName = "Updated Task";
        String taskDescription = "Updated Description";
        int estDays = 12;
        int estHours = 14;
        int estMinutes = 45;

        // Set up a mock Connection and PreparedStatement
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        // Set up the expected behavior of the Connection and PreparedStatement
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        // Call the editTask method
        String result = taskRepository.editTask(taskId, taskName, taskDescription, estDays, estHours, estMinutes);

        // Assert the result
        assertEquals("Changes for task " + taskId + " successfully updated", result);

        // Verify that the PreparedStatement was called with the correct values
        verify(statement).setString(1, taskName);
        verify(statement).setString(2, taskDescription);
        verify(statement).setInt(3, estDays);
        verify(statement).setInt(4, estHours);
        verify(statement).setInt(5, estMinutes);
        verify(statement).setInt(6, taskId);

        // Verify that the executeUpdate method was called
        verify(statement).executeUpdate();
    }
}
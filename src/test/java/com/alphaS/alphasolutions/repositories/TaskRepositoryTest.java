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

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

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
        assertEquals(result, result);

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
    void getTotalTime() throws SQLException {
        // Arrange
        int subprojectId = 1;
        String expectedTotalTime = "10 days 2 hours 30 minutes";

        // Mock the necessary objects and their behaviors
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("total_est_time")).thenReturn(expectedTotalTime);

        // Act
        String totalTime = taskRepository.getTotalTime(subprojectId);

        // Assert
        assertEquals(expectedTotalTime, totalTime);

        // Verify that the necessary methods were called with the correct parameters
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setInt(1, subprojectId);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getString("total_est_time");

    }


}
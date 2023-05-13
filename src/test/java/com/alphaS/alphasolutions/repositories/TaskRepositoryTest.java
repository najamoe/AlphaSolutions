package com.alphaS.alphasolutions.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
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
        LocalTime estTime = LocalTime.of(15,00);//object representing 3:30 AM
        LocalDate deadline = LocalDate.now().plusDays(10);
        String jobTitleNeeded = "job title";
        String status = "Not started";
        Color color = Color.RED;
        int subProjectId = 1;

        //Set up a mock PreparedStatement and ResultSet
        PreparedStatement taskStmt = mock(PreparedStatement.class);
        PreparedStatement subprojectTaskStmt = mock(PreparedStatement.class);
        ResultSet generatedKeys = mock(ResultSet.class);

        //Act - Set up the expected behavior of the PreparedStmt and resultSet
        when(dataSource.getConnection().prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(taskStmt);
        when(taskStmt.executeUpdate()).thenReturn(1);
        when(taskStmt.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(1);
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(subprojectTaskStmt);
        when(subprojectTaskStmt.executeUpdate()).thenReturn(1);

        // Call the createTask method
        String result = taskRepository.createTask(taskName, taskDescription, estTime, deadline, jobTitleNeeded, status, color, subProjectId);

        //Assert the result
        assertEquals("Task successfully added", result);

        // Verify that the necessary methods were called
        verify(dataSource.getConnection()).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(taskStmt).setString(eq(1), eq(taskName));
        verify(taskStmt).setString(eq(2), eq(taskDescription));
        verify(taskStmt).setTime(eq(3), any(Time.class));
        verify(taskStmt).setDate(eq(4), any(Date.class));
        verify(taskStmt).setString(eq(5), eq(jobTitleNeeded));
        verify(taskStmt).setString(eq(6), eq(status));
        verify(taskStmt).setString(eq(7), eq(color.toString()));
        verify(taskStmt).executeUpdate();
        verify(taskStmt).getGeneratedKeys();
        verify(generatedKeys).next();
        verify(generatedKeys).getInt(1);
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(subprojectTaskStmt).setInt(eq(1), eq(subProjectId));
        verify(subprojectTaskStmt).setInt(eq(2), anyInt());
        verify(subprojectTaskStmt).executeUpdate();
    }

    @Test
    void deleteTaskFromSubproject() throws SQLException {
        //Unit test for whether a task can be removed both in a chosen subproject and the database

        //Create test data
        int subProjectId = 1;
        int taskId = 1;

        //Set up a mock preparedStmt
        PreparedStatement preparedStmt = mock(PreparedStatement.class);

        //Set up the expacted behavior of the stmt
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStmt);
        when(preparedStmt.executeUpdate()).thenReturn(1);

        //Call the method
        String result = taskRepository.deleteTaskFromSubproject(subProjectId, taskId);

        //Assert result
        assertEquals(taskId + " has been removed from " + subProjectId, result);

        //Verify the necessary method were called
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(preparedStmt).setInt(eq(1), eq(subProjectId));
        verify(preparedStmt).setInt(eq(2), eq(taskId));
        verify(preparedStmt).executeUpdate();

    }

    @Test
    void editTask() throws SQLException {
        // Unit test for whether a task can be edited and saved in the database

        // Create test data
        int taskId = 1;
        String taskName = "Test task";
        String taskDescription = "Task Description";
        LocalTime estTime = LocalTime.of(15, 30); // object representing 15:30 AM
        LocalDate deadline = LocalDate.now().plusDays(10);
        String jobTitleNeeded = "job title";
        String status = "Not started";
        Color color = Color.RED;
        int subProjectId = 1;

        // Set up a mock preparedStmt and dataSource
        PreparedStatement preparedStmt = mock(PreparedStatement.class);
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStmt);
        when(preparedStmt.executeUpdate()).thenReturn(1);

        // Call the method
        String result = taskRepository.editTask(taskId, taskName, taskDescription, estTime, deadline, jobTitleNeeded, status, color);

        // Assert result
        assertEquals("New information successfully updated", result);

        // Verify
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStmt).setString(eq(1), eq(taskName));
        verify(preparedStmt).setString(eq(2), eq(taskDescription));
        verify(preparedStmt).setTime(eq(3), any(Time.class));
        verify(preparedStmt).setDate(eq(4), any(Date.class));
        verify(preparedStmt).setString(eq(5), eq(jobTitleNeeded));
        verify(preparedStmt).setString(eq(6), eq(status));
        verify(preparedStmt).setString(eq(7), eq(color.toString()));
        verify(preparedStmt).setInt(eq(8), eq(taskId));
        verify(preparedStmt).executeUpdate();
    }


}
package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TaskRepositoryTest {

    @Mock
    private DataSource dataSource;
    @InjectMocks
    private TaskRepository taskRepository;



    TaskRepositoryTest(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Test
    public void testReadTasks() throws SQLException {
        // Mock the necessary objects
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        // Create a list of tasks to be returned by the method
        List<TaskModel> expectedTasks = new ArrayList<>();
        TaskModel task1 = new TaskModel();
        task1.setTaskId(1);
        task1.setTaskName("Task 1");
        // Add other properties...
        expectedTasks.add(task1);

        // Configure the mock objects and their behavior
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("task_id")).thenReturn(task1.getTaskId());
        when(resultSet.getString("task_name")).thenReturn(task1.getTaskName());
        // Add other property configurations...

        // Create an instance of the service to test
        TaskService taskService = new TaskService();

        // Call the method to retrieve the tasks
        List<TaskModel> actualTasks = taskService.readTasks(1);

        // Verify the interactions and assert the result
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setInt(1, 1);
        verify(statement).executeQuery();
        // Verify other property interactions...

        assertEquals(expectedTasks, actualTasks);
    }

}

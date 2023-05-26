package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;


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
        int taskId = 1;
        String taskName = "Updated Task";
        String taskDescription = "Updated Description";
        LocalTime estTime = LocalTime.of(13, 30);


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

        when(statement.executeUpdate()).thenReturn(1);

        // Call the editTask method
        String result = taskRepository.editTask(taskId, taskName, taskDescription, estTime);

        // Assert the result
        assertEquals("Changes for task " + taskId + " successfully updated", result);

        // Verify that the PreparedStatement was called with the correct values
        verify(statement).setString(1, taskName);
        verify(statement).setString(2, taskDescription);
        verify(statement).setTime(3, Time.valueOf(estTime));
        verify(statement).setInt(4, taskId);



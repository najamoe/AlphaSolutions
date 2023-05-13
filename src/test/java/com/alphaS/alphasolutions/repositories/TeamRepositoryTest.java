package com.alphaS.alphasolutions.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TeamRepositoryTest {

    @InjectMocks
    private TeamRepository teamRepository ;

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
    void createTeam() throws SQLException {
        //Unit test for creating a team and save it in the database

        //Create test data
        String teamName = "Test teamName";
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
        String result = teamRepository.createTeam(teamName, subProjectId);

        //Assert the result
        assertEquals("team successfully added", result);

        // Verify that the necessary methods were called
        verify(dataSource.getConnection()).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(taskStmt).setString(eq(1), eq(teamName));
        verify(subprojectTaskStmt).setInt(eq(1), eq(subProjectId));
        verify(subprojectTaskStmt).executeUpdate();

    }

    @Test
    void addEmployeeToTeam() throws SQLException {
        //Unit test for whether an employee can be added to a chosen team can be saved in the database

        //Create test data
        int teamId = 1;
        List<String> employeeNames = Arrays.asList("John Doe", "Jane Smith", "Mike Johnson");

        //Set up a mock preparedStmt
        PreparedStatement preparedStmt = mock(PreparedStatement.class);

        //Set up the expected behavior of the stmt
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStmt);
        when(preparedStmt.executeUpdate()).thenReturn(1);

        //Call method
        teamRepository.AddEmployeeToTeam(teamId, employeeNames);

        // Verify the necessary method calls
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(preparedStmt).setInt(eq(1), eq(teamId));

        // Verify that executeUpdate is called for each employee name
        for (int i = 0; i < employeeNames.size(); i++) {
            String userName = employeeNames.get(i);
            String[] nameParts = userName.split("\\s+");
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            verify(preparedStmt).setString(eq(2), eq(firstName));
            verify(preparedStmt).setString(eq(3), eq(lastName));
            verify(preparedStmt).executeUpdate();
        }
    }


    @Test
    void deleteEmployeeFromTeam() throws SQLException {
        //Unit test for whether a team can be removed in the database

        //Create test data
        int teamId = 1;
        int userId = 1;

        //Set up a mock preparedStmt
        PreparedStatement preparedStmt = mock(PreparedStatement.class);

        //Set up the expected behavior of the stmt
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStmt);
        when(preparedStmt.executeUpdate()).thenReturn(1);

        //Call the method
        String result = teamRepository.deleteEmployeeFromTeam(teamId, userId);

        //Assert result
        assertEquals(userId + " has been removed from the " + teamId, result);

        //Verfiy the necessary method were called
        verify(dataSource.getConnection().prepareStatement(anyString()));
        verify(preparedStmt).setInt(eq(1), eq(teamId));
        verify(preparedStmt).setInt(eq(2), eq(userId));
        verify(preparedStmt).executeUpdate();

    }

    @Test
    void editTeamName() throws SQLException {
        // Unit test for editing task information

        // Arrange - create test data
        int teamId = 1;
        String teamName = "Test teamName";

        // Set up a mock Connection and PreparedStatement
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        // Set up the expected behavior of the Connection and PreparedStatement
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        // Call the editTask method
        String result = teamRepository.editTeamName(teamId, teamName);

        // Assert the result
        assertEquals("New team name successfully updated ", result);

        // Verify that the PreparedStatement was called with the correct values
        verify(statement).setString(1, teamName);
        verify(statement).setInt(8, teamId);

        // Verify that the executeUpdate method was called
        verify(statement).executeUpdate();

    }



}

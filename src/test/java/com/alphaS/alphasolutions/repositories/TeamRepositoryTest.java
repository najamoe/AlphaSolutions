package com.alphaS.alphasolutions.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
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
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet generatedKeys = mock(ResultSet.class);
        ResultSet rs = mock(ResultSet.class);

        //Act - Set up the expected behavior of the PreparedStmt and resultSet
        when(dataSource.getConnection().prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(1);
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        // Call the createTask method
        String result = teamRepository.createTeam(teamName, subProjectId);

        //Assert the result
        assertEquals("Team successfully added", result);

        // Verify that the necessary methods were called
        verify(dataSource.getConnection()).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
        verify(preparedStatement).setString(eq(1), eq(teamName));
        verify(preparedStatement).setInt(eq(1), eq(subProjectId));
        verify(preparedStatement).executeUpdate();

    }

    @Test
    void addEmployeeToTeam() throws SQLException {
        //Unit test for whether an employee can be added to a chosen team can be saved in the database
        // Arrange
        int teamId = 1;
        String firstName = "John";
        String lastName = "Doe";

        // Set up a mock PreparedStatement
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        // Set up the expected behavior of the PreparedStatement for success
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act - Success scenario
        boolean successResult = teamRepository.addEmployeeToTeam(teamId, firstName, lastName);

        // Assert - Success scenario
        assertTrue(successResult);

        // Verify that the necessary methods were called for success
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(preparedStatement).setInt(eq(1), eq(teamId));
        verify(preparedStatement).setString(eq(2), eq(firstName));
        verify(preparedStatement).setString(eq(3), eq(lastName));
        verify(preparedStatement).executeUpdate();

        // Set up the expected behavior of the PreparedStatement for failure
        when(preparedStatement.executeUpdate()).thenReturn(0);

        // Act - Failure scenario
        boolean failureResult = teamRepository.addEmployeeToTeam(teamId, firstName, lastName);

        // Assert - Failure scenario
        assertFalse(failureResult);

    }

    @Test
    void deleteEmployeeFromTeam() throws SQLException {
        //Unit test for whether a team can be removed in the database

        //Create test data
        int teamId = 1;
        int employeeId = 10;

        //Set up a mock preparedStmt
        PreparedStatement preparedStmt = mock(PreparedStatement.class);

        //Set up the expected behavior of the stmt
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStmt);
        when(preparedStmt.executeUpdate()).thenReturn(1);

        //Call the method
        String result = teamRepository.deleteEmployeeFromTeam(teamId, employeeId);

        //Assert result
        assertEquals("Member has been removed from team, successfully", result);

        //Verfiy the necessary method were called
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(preparedStmt).setInt(eq(1), eq(teamId));
        verify(preparedStmt).setInt(eq(2), eq(employeeId));
        verify(preparedStmt).executeUpdate();

    }

}

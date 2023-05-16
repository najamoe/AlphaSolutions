/*package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.EmployeeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EmployeeRepositoryTest {

    @InjectMocks
    private EmployeeRepository employeeRepository;

    @Mock
    private DataSource dataSource;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Set up a mock database connection
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testLogIn() throws SQLException {
        // Create a test EmployeeModel
        EmployeeModel testEmployee = new EmployeeModel();
        // Set the testEmployee properties

        // Set up a mock PreparedStatement and ResultSet
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        // Set up the expected behavior of the PreparedStatement and ResultSet
        when(dataSource.getConnection().prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate a successful login
        // Set the necessary properties on the resultSet to match the testEmployee

        // Call the logIn method
        EmployeeModel result = employeeRepository.logIn("testUsername", "testPassword");

        // Assert the result
        assertNotNull(result);
        // Assert the properties of the result match the testEmployee

        // Verify that the necessary methods were called
        verify(dataSource.getConnection()).prepareStatement(anyString());
        verify(preparedStatement).setString(anyInt(), eq("testUsername"));
        verify(preparedStatement).setString(anyInt(), eq("testPassword"));
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        // Verify the necessary properties on the resultSet were accessed
    }
}
*/
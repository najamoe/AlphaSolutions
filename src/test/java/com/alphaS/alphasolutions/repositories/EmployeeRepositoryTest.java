package com.alphaS.alphasolutions.repositories;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeRepositoryTest {

    @InjectMocks
    private EmployeeRepository employeeRepository;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logIn_validCredentials_returnsEmployeeModel() throws SQLException {
        // Arrange
        int employeeId = 1;
        String username = "testuser";
        String password = "testpassword";

        // Mock the necessary objects and their behaviors
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(eq("employee_id"))).thenReturn(employeeId);

        // Act
        EmployeeModel employee = employeeRepository.logIn(username, password);

        // Assert
        assertEquals(employeeId, employee.getEmployeeId());
        assertEquals(username, employee.getUsername());
        assertEquals(password, employee.getPassword());

        // Verify that the necessary methods were called with the correct parameters
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setString(1, username);
        verify(statement).setString(2, password);
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("employee_id");  // Ensure the correct column name is used
    }

    @Test
    void logIn_invalidCredentials_throwsSQLException() throws SQLException {
        // Arrange
        String username = "testuser";
        String password = "testpassword";

        // Mock the necessary objects and their behaviors
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Act and Assert
        assertThrows(SQLException.class, () -> employeeRepository.logIn(username, password), "Could not validate user");

        // Verify that the necessary methods were called with the correct parameters
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setString(1, username);
        verify(statement).setString(2, password);
        verify(statement).executeQuery();
        verify(resultSet).next();
    }
}

package com.alphaS.alphasolutions.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClientRepositoryTest {

    @InjectMocks
    private ClientRepository clientRepository;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void editClient_successfulUpdate() throws SQLException {
        // Arrange
        String newClientName = "New Client";
        int newContactPoNo = 123456;
        String newContactPerson = "John Doe";
        int newCompanyPoNo = 789012;
        String newAddress = "New Address";
        int newZipcode = 12345;
        String newCountry = "New Country";
        int clientId = 1;

        // Mock the necessary objects and their behaviors
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        // Act
        String result = clientRepository.editClient(newClientName, newContactPoNo, newContactPerson, newCompanyPoNo,
                newAddress, newZipcode, newCountry, clientId);

        // Assert
        assertEquals("Client successfully updated with your changes", result);

        // Verify that the necessary methods were called with the correct parameters
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setString(1, newClientName);
        verify(statement).setInt(2, newContactPoNo);
        verify(statement).setString(3, newContactPerson);
        verify(statement).setInt(4, newCompanyPoNo);
        verify(statement).setString(5, newAddress);
        verify(statement).setInt(6, newZipcode);
        verify(statement).setString(7, newCountry);
        verify(statement).setInt(8, clientId);
        verify(statement).executeUpdate();

    }

    @Test
    void editClient_failedUpdate() throws SQLException {
        // Arrange
        // Set up the necessary parameters for a failed update (e.g., no rows updated)
        // Note: Modify the parameters according to your failure scenario
        String newClientName = "New Client";
        int newContactPoNo = 123456;
        String newContactPerson = "John Doe";
        int newCompanyPoNo = 789012;
        String newAddress = "New Address";
        int newZipcode = 12345;
        String newCountry = "New Country";
        int clientId = 1;

        // Mock the necessary objects and their behaviors
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(0);

        // Act
        String result = clientRepository.editClient(newClientName, newContactPoNo, newContactPerson, newCompanyPoNo,
                newAddress, newZipcode, newCountry, clientId);

        // Assert
        assertEquals("Failed to edit client", result);

        // Verify that the necessary methods were called with the correct parameters
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setString(1, newClientName);
        verify(statement).setInt(2, newContactPoNo);
        verify(statement).setString(3, newContactPerson);
        verify(statement).setInt(4, newCompanyPoNo);
        verify(statement).setString(5, newAddress);
        verify(statement).setInt(6, newZipcode);
        verify(statement).setString(7, newCountry);
        verify(statement).setInt(8, clientId);
        verify(statement).executeUpdate();
    }
}